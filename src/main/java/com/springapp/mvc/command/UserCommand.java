package com.springapp.mvc.command;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2/21/14
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserCommand {
    private String guid;
    private String image;
    private String displayName;
    private List<String> likedList;

    public UserCommand(String guid, String image, String displayName, List<String> liked) {
        this.guid = guid;
        this.image = image;
        this.displayName = displayName;
        this.likedList = liked;
    }

    public String getGuid() {
        return guid;
    }

    public String getImage() {
        return image;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLikedList() {
        return likedList;
    }
}
