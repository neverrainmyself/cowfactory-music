package com.springapp.mvc.command.response;

import com.springapp.mvc.domain.User;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 14-3-11
 * Time: 下午3:20
 * To change this template use File | Settings | File Templates.
 */
public class LikeResponse {
    private boolean liked;
    private User user;

    public LikeResponse(boolean liked, User user) {
        this.liked = liked;
        this.user = user;
    }

    public boolean isLiked() {
        return liked;
    }

    public User getUser() {
        return user;
    }
}
