package com.springapp.mvc.repository;

import com.springapp.mvc.Utils.GuidGenerator;
import com.springapp.mvc.Utils.SecurityUtils;
import com.springapp.mvc.command.UserCommand;
import com.springapp.mvc.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2/24/14
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class MusicRepository {
    @Autowired
    private RedisDatasource redisDatasource;
    @Autowired
    private UserRepository userRepository;
    String musicCommentList = "musiccomments:{guid}";
    String musicComment = "musiccomment:{guid}";
    String musicFullList = "fullmusiclist:{type}";
    String musicDraftList = "draftmusiclist:{type}";
    String musicVerifiedList = "verifiedmusiclist:{type}";
    String musicPublishList = "publishedmusiclist:{type}";
    String singleMusicLikedByList = "musiclikedby:{guid}";
    String mostLikedMusicList = "mostlikedmusiclist:{type}";
    String music = "music:{guid}";
    String musicImage = "musicImage:{guid}";

    public Music music(String guid) {
        if (!StringUtils.hasText(guid)) {
            return null;
        }
        Map<String, String> musicMap = redisDatasource.getAll(music.replace("{guid}", guid));
        if (musicMap == null || musicMap.isEmpty()) {
            return null;
        }
        return Music.instance()
                .changeTitle(musicMap.get("title"))
                .changeSubTitle(musicMap.get("subTitle"))
                .changeContent(musicMap.get("content"))
                .changePath(musicMap.get("path"))
                .changeFileName(musicMap.get("fileName"))
                .changeFileSize(musicMap.get("fileSize"))
                .changeStatus(Status.valueOf(musicMap.get("status")))
                .changeMusicType(MusicType.valueOf(musicMap.get("musicType")))
                .changeLiked(Integer.valueOf(musicMap.get("liked")))
                .changeCreatedAt(musicMap.get("createdAt"))
                .changeUserGuid(musicMap.get("userGuid"))
                .changeGuid(guid)
                .changeHeaderImage(musicMap.get("headerImage"))
                .changeListened(Integer.valueOf(musicMap.get("listened")));
    }


    public void save(Music music) {
        String key = this.music.replace("{guid}", music.getGuid());
        redisDatasource.del(key);
        redisDatasource.put(key, music.map());
    }

    public void delete(String guid) {
        redisDatasource.del(this.music.replace("{guid}", guid));
        String key = musicFullList.replace("{type}", MusicType.BEAT.toString());
        redisDatasource.lrem(key, guid);

        key = musicFullList.replace("{type}", MusicType.RAP.toString());
        redisDatasource.lrem(key, guid);


    }


    public List<Music> musics(List<String> guids) {
        List<Music> musics = new ArrayList<Music>();
        for (String guid : guids) {
            Music music = music(guid);
            if(!music.isInit()){
                musics.add(music);
            }
        }
        return musics;
    }

    public Image musicImage(String imageGuid) {
        Map<String, String> imageMap = redisDatasource.getAll(musicImage.replace("{guid}", imageGuid));
        return Image.instance()
                .changePath(imageMap.get("path"))
                .changeFileName(imageMap.get("fileName"))
                .changeFileSize(imageMap.get("fileSize"))
                .changeContentType(imageMap.get("contentType"))
                .changeGuid(imageGuid);
    }

    public void saveMusicImage(Image image) {
        redisDatasource.put(musicImage.replace("{guid}", image.getGuid()), image.map());
    }

    public void deleteMusicImage(String imageGuid) {
        redisDatasource.del(musicImage.replace("{guid}", imageGuid));
    }


    public void addMusicToVerifiedList(Music music) {
        redisDatasource.lpush(musicVerifiedList.replace("{type}", music.getMusicType().toString()), music.getGuid());
    }

    public List<Music> last100Musics(String type) {
        List<String> guids = redisDatasource.getList(musicPublishList.replace("{type}", type), 0, 100);
        List<Music> musics = new ArrayList<Music>();
        for (String guid : guids) {
            musics.add(music(guid));
        }
        return musics;
    }

    public List<Music> top100(String type) {
        Set<String> guids = redisDatasource.zreverange(mostLikedMusicList.replace("{type}",type), 0, 99);
        List<Music> musics = new ArrayList<Music>();
        for (String guid : guids) {
            musics.add(music(guid));
        }
        return musics;
    }

    public Music findNextOneFromDefaultList(String guid) {
        List<String> guids = redisDatasource.getList(musicPublishList.replace("{type}", music(guid).getMusicType().toString()));
        int index = guids.indexOf(guid);
        try {
            return music(guids.get(index + 1));
        } catch (Exception e) {
            return music(guids.get(0));
        }

    }


    public void liked(String guid) {
        Music music = music(guid);
        music.liked();
        save(music);
        User user = SecurityUtils.loadUser();
        redisDatasource.rpush(singleMusicLikedByList.replace("{guid}", guid), user.getGuid());
        redisDatasource.zincrby(mostLikedMusicList.replace("{type}",music.getMusicType().toString()), music.getGuid(),1);

    }
    public void unLike(String guid) {
        Music music = music(guid);
        music.unlike();
        save(music);
        User user = SecurityUtils.loadUser();
        redisDatasource.lrem(singleMusicLikedByList.replace("{guid}", guid), user.getGuid());
        redisDatasource.zincrby(mostLikedMusicList.replace("{type}",music.getMusicType().toString()), music.getGuid(),-1);
    }

    public void removeMusicFromVerifiedList(Music music) {
        redisDatasource.lrem(musicVerifiedList.replace("{type}", music.getMusicType().toString()), music.getGuid());
    }

    public List<Music> findAllDrafts() {
        List<String> beatGuids = redisDatasource.getList(musicDraftList.replace("{type}", MusicType.BEAT.toString()));
        List<String> rapGuids = redisDatasource.getList(musicDraftList.replace("{type}", MusicType.RAP.toString()));
        List<Music> musics = new ArrayList<Music>();
        for (String guid : beatGuids) {
            Music result = music(guid);
            if(result!=null){
                musics.add(result);
            }
        }
        for (String guid : rapGuids) {
            Music result = music(guid);
            if(result!=null){
                musics.add(result);
            }
        }
        return musics;
    }

    public void removeMusicFromDraftList(Music music) {
        redisDatasource.lrem(musicDraftList.replace("{type}", music.getMusicType().toString()), music.getGuid());
    }

    public void addMusicToPublishList(Music music) {
        redisDatasource.lpush(musicPublishList.replace("{type}", music.getMusicType().toString()), music.getGuid());
    }
    public void addMusicToBigList(Music music) {
        redisDatasource.lpush(musicFullList.replace("{type}", music.getMusicType().toString()), music.getGuid());
    }

    public void addMusicToDraftList(Music music) {
        removeMusicFromDraftList(music);
        String key = musicDraftList.replace("{type}", music.getMusicType().toString());
        redisDatasource.lpush(key, music.getGuid());
    }

    public List<Music> findAllNeedToPublish() {
        List<String> beatGuids = redisDatasource.getList(musicVerifiedList.replace("{type}", MusicType.BEAT.toString()));
        List<String> rapGuids = redisDatasource.getList(musicVerifiedList.replace("{type}", MusicType.RAP.toString()));
        List<Music> musics = new ArrayList<Music>();
        for (String guid : beatGuids) {
            Music result = music(guid);
            if(result!=null){
                musics.add(result);
            }
        }
        for (String guid : rapGuids) {
            Music result = music(guid);
            if(result!=null){
                musics.add(result);
            }
        }
        return musics;
    }

    public void removeMusicFromPublishList(Music music) {
        redisDatasource.lrem(musicPublishList.replace("{type}", music.getMusicType().toString()), music.getGuid());
    }


    public List<Music> findAllPublished() {
        List<String> beatGuids = redisDatasource.getList(musicPublishList.replace("{type}", MusicType.BEAT.toString()));
        List<String> rapGuids = redisDatasource.getList(musicPublishList.replace("{type}", MusicType.RAP.toString()));
        List<Music> musics = new ArrayList<Music>();
        for (String guid : beatGuids) {
            Music result = music(guid);
            if(result!=null){
                musics.add(result);
            }
        }
        for (String guid : rapGuids) {
            Music result = music(guid);
            if(result!=null){
                musics.add(result);
            }
        }
        return musics;
    }

    public List<String> last3likedUsers(Music music) {
        List<String> userGuids = redisDatasource.getList(singleMusicLikedByList.replace("{guid}", music.getGuid()));
        return userGuids.isEmpty()? Collections.EMPTY_LIST:userGuids.size()>3?userGuids.subList(0,2):userGuids;
    }

    public void addComment(Music music, User user, String comment) {
        String commentGuid = GuidGenerator.createGuid();
        HashMap<String, String> commentObj = new HashMap<String, String>();
        commentObj.put("user", user.getGuid());
        commentObj.put("music", music.getGuid());
        commentObj.put("content", comment);

        redisDatasource.put(musicComment.replace("{guid}",commentGuid), commentObj);
        redisDatasource.rpush(musicCommentList.replace("{guid}", music.getGuid()),commentGuid);
    }

    public List<Comment> allComments(Music music,int page) {
        int startAt = page >= 1 ? page * 10 : page;
        int endAt = page==0?9:(page+1)*10;
        List<Comment> comments = new ArrayList<Comment>();
        List<String> guids = redisDatasource.getList(musicCommentList.replace("{guid}", music.getGuid()),startAt,-1);
        for (String guid : guids) {
            Map<String, String> comment = redisDatasource.getAll(musicComment.replace("{guid}", guid));
            User user = (User) userRepository.user(comment.get("user"));
            comments.add(Comment.instance().changeUser(user).changeComment(comment.get("content")).changeGuid(guid));
        }
        return comments;
    }

    public List<User> last3likedBy(Music music) {
        List<User> users = new ArrayList<User>();
        List<String> userGuids = redisDatasource.getList(singleMusicLikedByList.replace("{guid}", music.getGuid()), 0, 2);
        for (String userGuid : userGuids) {
            users.add((User) userRepository.user(userGuid));
        }
        return users;
    }

    public int commentAmount(Music music) {
       return redisDatasource.llen(musicCommentList.replace("{guid}", music.getGuid()));
    }


    public void deleteComment(String musicguid, String guid) {
        redisDatasource.lrem(musicCommentList.replace("{guid}", musicguid), guid);
        redisDatasource.del(musicComment.replace("{guid}", guid));
    }
}
