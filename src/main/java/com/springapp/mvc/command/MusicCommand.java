package com.springapp.mvc.command;

import com.springapp.mvc.domain.MusicType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2/21/14
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class MusicCommand {
    private String guid;
    private String title;
    private String subTitle;
    private String content;
    private String path;
    private String headerImage;
    private MusicType musicType;
    private int liked;
    private int listened;
    private UserCommand userCommand;

    private List<UserCommand> likedList;

    public MusicCommand(String guid, String title, String subTitle, String content, String path, String headerImage, UserCommand userCommand) {
        this(guid, title, subTitle, content,path,0,0);
        this.headerImage = headerImage;
        this.userCommand = userCommand;
    }

    public MusicCommand(String guid, String title, String subTitle, String content,String path,int liked,int listened) {
        this.guid = guid;
        this.title = title;
        this.subTitle = subTitle;
        this.path = path;
        this.content = content;
        this.liked = liked;
        this.listened = listened;
    }

    public String getGuid() {
        return guid;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getPath() {
        return path;
    }

    public UserCommand getUserCommand() {
        return userCommand;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public String getContent() {
        return content;
    }

    public int getLiked() {
        return liked;
    }

    public int getListened() {
        return listened;
    }

    public List<UserCommand> getLikedList() {
        return likedList;
    }

    public void setLikedList(List<UserCommand> likedList) {
        this.likedList = likedList;
    }

    public MusicType getMusicType() {
        return musicType;
    }

    public void setMusicType(MusicType musicType) {
        this.musicType = musicType;
    }
}
