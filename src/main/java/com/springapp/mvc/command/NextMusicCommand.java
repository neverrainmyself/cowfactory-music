package com.springapp.mvc.command;

import com.springapp.mvc.domain.Music;
import com.springapp.mvc.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 14-3-3
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
public class NextMusicCommand {
    private Music music;
    private boolean liked;

    public NextMusicCommand(Music music, User user) {
        this.music = music;
        if(user!=null){
            String guid = music.getGuid();
            List<String> liked = user.getLiked();
            this.liked = liked != null && liked.contains(guid);
        }
    }

    public Music getMusic() {
        return music;
    }

    public boolean isLiked() {
        return liked;
    }
}
