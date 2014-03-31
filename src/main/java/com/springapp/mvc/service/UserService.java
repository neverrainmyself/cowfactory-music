package com.springapp.mvc.service;

import com.springapp.mvc.domain.User;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 1/17/14
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {
    User createUserIfNeeded(String userId, String accessToken, String displayName, String imageUrl, String profileUrl, String name);
    User find(String guid);
}
