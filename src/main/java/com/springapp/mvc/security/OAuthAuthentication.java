package com.springapp.mvc.security;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 12/17/13
 * Time: 10:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class OAuthAuthentication {
    private String userId;
    private String displayName;
    private String profileImageUrl;
    private String weiboUrl;
    private String name;
    private String accessToken;

    public OAuthAuthentication(String userId, String displayName, String profileImageUrl, String weiboUrl, String name, String accessToken) {
        this.userId = userId;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
        this.weiboUrl = weiboUrl;
        this.name = name;
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getWeiboUrl() {
        return weiboUrl;
    }

    public String getName() {
        return name;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
