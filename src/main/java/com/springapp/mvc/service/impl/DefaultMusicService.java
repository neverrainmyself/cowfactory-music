package com.springapp.mvc.service.impl;

import com.springapp.mvc.Utils.SecurityUtils;
import com.springapp.mvc.command.MusicCommand;
import com.springapp.mvc.command.NextMusicCommand;
import com.springapp.mvc.command.PlayOneResponse;
import com.springapp.mvc.command.UserCommand;
import com.springapp.mvc.command.response.SaveResponse;
import com.springapp.mvc.domain.*;
import com.springapp.mvc.repository.MusicRepository;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.service.FileService;
import com.springapp.mvc.service.MusicService;
import org.apache.commons.fileupload.FileItemStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 1/17/14
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DefaultMusicService implements MusicService {
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Music findByGuid(String guid) {
        return musicRepository.music(guid);
    }

    @Override
    public void saveInitial(String guid, String header, String subHeader, String content) {
        Music music = findByGuid(guid);
        music.changeTitle(header);
        music.changeSubTitle(subHeader);
        music.changeContent(content);
        musicRepository.save(music);
    }

    @Override
    public SaveResponse saveDraft(String guid, String header, String subHeader, String content) {
        SaveResponse saveResponse = SaveResponse.instance().changeGuid(guid);
        Music music = findByGuid(guid);
        music.changeContent(content);


        if (!StringUtils.hasText(header)) {
            saveResponse.noHeader();
        } else {
            music.changeTitle(header);
        }
        if (!StringUtils.hasText(subHeader)) {
            saveResponse.noSubHeader();
        } else {
            music.changeSubTitle(subHeader);
        }
        if (!StringUtils.hasText(music.getPath())) {
            saveResponse.noMusic();
        }

        if (music.isVerify()) {
            musicRepository.removeMusicFromVerifiedList(music);
        }
        if (music.isPublish()) {
            musicRepository.removeMusicFromPublishList(music);
        }
        music.changeStatus(Status.DRAFT);
        musicRepository.save(music);

        musicRepository.addMusicToDraftList(music);

        return saveResponse;
    }

    @Override
    public SaveResponse publish(String guid, String header, String subHeader, String content) {
        SaveResponse saveResponse = SaveResponse.instance().changeGuid(guid);
        Music music = findByGuid(guid);
        music.changeContent(content);

        if (!StringUtils.hasText(header)) {
            saveResponse.noHeader();
        } else {
            music.changeTitle(header);
        }
        if (!StringUtils.hasText(subHeader)) {
            saveResponse.noSubHeader();
        } else {
            music.changeSubTitle(subHeader);
        }
        if (!StringUtils.hasText(music.getPath())) {
            saveResponse.noMusic();
        }

        if (music.isPublish()) {
            musicRepository.removeMusicFromPublishList(music);
        }
        if (saveResponse.isSuccessded()) {
            if (!music.isVerify()) {
                musicRepository.removeMusicFromVerifiedList(music);
                musicRepository.addMusicToVerifiedList(music);
                music.changeStatus(Status.VERIFY);
            }
        } else {
            music.changeStatus(Status.DRAFT);
        }
        musicRepository.save(music);


        return saveResponse;
    }

    @Override
    public void deleteMusic(String guid) {
        musicRepository.delete(guid);
        userRepository.deleteMusic(guid);
    }

    @Override
    public Music create(String type) {
        User user = (User) userRepository.user(SecurityUtils.loadUser().getGuid());
        Music music = Music.instance().changeStatus(Status.INIT).changeUserGuid(user.getGuid()).changeMusicType(MusicType.valueOf(type));
        musicRepository.save(music);
        musicRepository.addMusicToBigList(music);
        userRepository.addMusicToUser(user, music);
        return music;
    }

    @Override
    public Image loadMusicImage(String imageGuid) {
        return musicRepository.musicImage(imageGuid);
    }

    @Override
    public Music uploadMusic(String guid, FileItemStream item) {
        Music music = musicRepository.music(guid);
        fileService.uploadMusic(music, item);
        musicRepository.save(music);
        return music;
    }

    @Override
    public void removeMusicFile(String guid) {
        Music music = musicRepository.music(guid);
        music.changeFileSize(null).changeFileName(null).changePath(null);
        musicRepository.save(music);
    }

    @Override
    public List<Music> findAllForLoginUser(Status status) {
        User user = (User) userRepository.user(SecurityUtils.loadUser().getGuid());
//        List<MusicCommand> musicCommands = new ArrayList<MusicCommand>();
        List<Music> musics = musicRepository.musics(userRepository.userMusics(user));
        List<Music> remove = new ArrayList<Music>();
        for (Music music : musics) {
            if (music.getStatus().equals(status)) {
                fullFillInfo(music);
            }else {
                remove.add(music);
            }
        }
        musics.removeAll(remove);
        return musics;
    }

    @Override
    public Music findMusic(String guid) {
        Music music = musicRepository.music(guid);
        fullFillInfo(music);
        return music;
    }

    private void fullFillInfo(Music music) {
        User user = (User) userRepository.user(music.getUserGuid());
        music.changeUser(user);
        music.changeComment(musicRepository.commentAmount(music));
        music.changeLikedBy(musicRepository.last3likedBy(music));
    }

    @Override
    public List<MusicCommand> loadDefault100Musics(String type) {
        List<Music> musics = musicRepository.last100Musics(type);
        List<MusicCommand> musicCommands = new ArrayList<MusicCommand>();
        for (Music music : musics) {
            if (music != null) {
                toMusicCommand(musicCommands, music);
            }
        }
        return musicCommands;
    }

    @Override
    public List<MusicCommand> top100(String type) {
        List<Music> musics = musicRepository.top100(type);
        List<MusicCommand> musicCommands = new ArrayList<MusicCommand>();
        for (Music music : musics) {
            if (music != null) {
                toMusicCommand(musicCommands, music);
            }
        }
        return musicCommands;
    }

    @Override
    public PlayOneResponse playOne(String playGuid) {
        return null;
    }

    @Override
    public Image uploadHeaderImage(String guid, FileItemStream item) throws IOException {
        Image image = fileService.uploadImage(guid, item);
        Music music = findMusic(guid);
        musicRepository.save(music.changeHeaderImage(image.getGuid()));
        musicRepository.saveMusicImage(image);
        return image;
    }

    @Override
    public NextMusicCommand findNext(String guid, boolean defaultList) {
        if (defaultList) {
            Music nextMusic = musicRepository.findNextOneFromDefaultList(guid);
            User user = (User) userRepository.user(nextMusic.getUserGuid());
            nextMusic.changeUser(user);
            nextMusic.changeLikedBy(musicRepository.last3likedBy(nextMusic));
            nextMusic.changeComment(musicRepository.commentAmount(nextMusic));
            return new NextMusicCommand(nextMusic, SecurityUtils.loadUser());
        }
        return null;
    }

    @Override
    public void listened(String guid) {
        Music music = musicRepository.music(guid);
        music.listened();
        musicRepository.save(music);
    }

    @Override
    public List<Music> findMusicsByUserGuid(String guid) {
        List<String> musicGuids = userRepository.userMusics((User) userRepository.user(guid));
        List<Music> musics = new ArrayList<Music>();
        for (String musicGuid : musicGuids) {
            Music music = musicRepository.music(musicGuid);
            if (!music.isInit()) {
                musics.add(music);
            }
        }
        return musics;
    }

    @Override
    public boolean like(String guid) {
        boolean liked = userRepository.likeMusic(guid);
        if (liked) {
            musicRepository.liked(guid);
        } else {
            musicRepository.unLike(guid);
        }
        return liked;
    }

    @Override
    public List<MusicCommand> findForAdmin(Status status) {
        List<Music> musics = new ArrayList<Music>();
        switch (status) {
            case DRAFT:
                musics = musicRepository.findAllDrafts();
                break;
            case VERIFY:
                musics = musicRepository.findAllNeedToPublish();
                break;
            case PUBLISH:
                musics = musicRepository.findAllPublished();
                break;
        }
        List<MusicCommand> musicCommands = new ArrayList<MusicCommand>();
        for (Music music : musics) {
            toMusicCommand(musicCommands, music);
        }
        return musicCommands;
    }

    @Override
    public void allowPublish(String guid) {
        Music music = findMusic(guid);
        musicRepository.save(music.changeStatus(Status.PUBLISH));
        musicRepository.removeMusicFromVerifiedList(music);
        musicRepository.addMusicToPublishList(music);
    }

    @Override
    public void addComment(String guid, String comment) {
        User user = SecurityUtils.loadUser();
        musicRepository.addComment(findMusic(guid), user, comment);
    }

    @Override
    public List<Comment> comments(String guid, int page) {
        return musicRepository.allComments(findMusic(guid), page <= 0 ? 0 : page);
    }

    @Override
    public String download(String guid) {
        Music music = findMusic(guid);
        if(!music.getMusicType().equals(MusicType.BEAT)) {
            return music.getPath();
        }
        return null;
    }

    @Override
    public void reject(String guid) {
        Music music = findMusic(guid);
        music.changeStatus(Status.DRAFT);
        musicRepository.save(music);
        musicRepository.removeMusicFromVerifiedList(music);
        musicRepository.addMusicToDraftList(music);
    }

    @Override
    public void deleteComment(String musicguid, String guid) {
        musicRepository.deleteComment(musicguid,guid);
    }

    private void toMusicCommand(List<MusicCommand> musicCommands, Music music) {
        User user = (User) userRepository.user(music.getUserGuid());
        UserCommand userCommand = new UserCommand(user.getGuid(), user.getWeiboImage(), user.getWeiboDisplayName(), user.getLiked());
        MusicCommand musicCommand = new MusicCommand(music.getGuid(), music.getTitle(), music.getSubTitle(), music.getContent(), music.getPath(), music.getHeaderImage(), userCommand);
        musicCommand.setMusicType(music.getMusicType());
        musicCommands.add(musicCommand);
    }


    @Override
    public Image uploadImage(String musicGuid, FileItemStream item) throws IOException {
        Image image = fileService.uploadImage(musicGuid, item);
        musicRepository.saveMusicImage(image);
        return image;
    }


    @Override
    public void removeImage(String imageGuid) {
        musicRepository.deleteMusicImage(imageGuid);
    }

}
