package com.springapp.mvc.domain;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 14-3-11
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class Comment {
    private User user;
    private String comment;
    private String guid;

    public User getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    public static Comment instance() {
        return new Comment();
    }

    public Comment changeUser(User user) {
        this.user = user;
        return this;
    }

    public Comment changeComment(String content) {
        this.comment = content;
        return this;
    }

    public Comment changeGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public String getGuid() {
        return guid;
    }
}
