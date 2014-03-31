package com.springapp.mvc.service;

import com.springapp.mvc.command.MusicCommand;
import com.springapp.mvc.command.NextMusicCommand;
import com.springapp.mvc.command.PlayOneResponse;
import com.springapp.mvc.command.UserCommand;
import com.springapp.mvc.command.response.SaveResponse;
import com.springapp.mvc.domain.*;
import org.apache.commons.fileupload.FileItemStream;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 1/17/14
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MusicService {
    Music findByGuid(String guid);
    void saveInitial(String guid, String header, String subHeader,  String content);
    SaveResponse saveDraft(String guid, String header, String subHeader, String content);
    SaveResponse publish(String guid, String header, String subHeader, String content);
    void deleteMusic(String guid);
    Image uploadImage(String guid, FileItemStream item) throws IOException;
    void removeImage(String imageGuid);
    Music create(String type);
    Image loadMusicImage(String guid);
    Music uploadMusic(String guid, FileItemStream item);
    void removeMusicFile(String guid);
    List<Music> findAllForLoginUser(Status status);
    Music findMusic(String guid);
    List<MusicCommand> loadDefault100Musics(String type);
    List<MusicCommand> top100(String type);
    PlayOneResponse playOne(String playGuid);
    Image uploadHeaderImage(String guid, FileItemStream item) throws IOException;

    NextMusicCommand findNext(String guid, boolean defaultList);
    void listened(String guid);
    List<Music> findMusicsByUserGuid(String guid);
    boolean like(String guid);
    List<MusicCommand> findForAdmin(Status status);
    void allowPublish(String guid);
    void addComment(String guid, String comment);
    List<Comment> comments(String guid, int page);
    String download(String guid);
    void reject(String guid);

    void deleteComment(String musicguid, String guid);
}
