package com.springapp.mvc.domain;

import com.springapp.mvc.Utils.GuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 1/17/14
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class User  implements UserDetails {
    private UserRole userRole;
    private String weiboId;
    private String weiboProfileUrl;
    private String weiboImage;
    private String weiboDisplayName;
    private String weiboName;
    private String accessToken;
    private String guid;
    private List<String> liked;
    private List<Music> musics;


    public User() {
        this.guid = GuidGenerator.createGuid();
        this.userRole = UserRole.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(this.userRole.name()));
        return grantedAuthorities;
    }

    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.weiboId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public boolean isRegisterRole() {
        return this.userRole.equals(UserRole.USER);
    }
    public boolean isAdminRole() {
        return this.userRole.equals(UserRole.ADMIN);
    }

    public String getWeiboId() {
        return weiboId;
    }

    public String getWeiboProfileUrl() {
        return weiboProfileUrl;
    }

    public String getWeiboImage() {
        return weiboImage;
    }

    public String getWeiboDisplayName() {
        return weiboDisplayName;
    }

    public String getWeiboName() {
        return weiboName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public static User instance() {
        return new User();
    }

    public User changeRole(UserRole role) {
        this.userRole = role;
        return this;
    }

    public User changeWeiboId(String weiboId) {
        this.weiboId = weiboId;
        return this;
    }

    public User changeWeiboProfileUrl(String weiboProfileUrl) {
        this.weiboProfileUrl = weiboProfileUrl;
        return this;
    }

    public User changeWeiboImage(String weiboImage) {
        this.weiboImage = weiboImage;
        return this;
    }

    public User changeWeiboDisplayName(String weiboDisplayName) {
        this.weiboDisplayName = weiboDisplayName;
        return this;
    }

    public User changeWeiboName(String weiboName) {
        this.weiboName = weiboName;
        return this;
    }

    public User changeAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public Map<String, String> map() {
        Map<String, String> userMap = new HashMap<String, String>();
        safeValue(userMap,"role", this.userRole.toString());
        safeValue(userMap, "weiboId", this.weiboId);
        safeValue(userMap, "weiboName", this.weiboName);
        safeValue(userMap, "weiboDisplayName", this.weiboDisplayName);
        safeValue(userMap, "weiboImage", this.weiboImage);
        safeValue(userMap, "weiboProfileUrl", this.weiboProfileUrl);
        safeValue(userMap, "accessToken", this.accessToken);
        return userMap;
    }

    private void safeValue(Map<String, String> musicMap, String name,String value) {
        if(StringUtils.hasText(value)){
            musicMap.put(name, value);
        }
    }

    public String getGuid() {
        return guid;
    }

    public User changeGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public List<String> getLiked() {
        return liked;
    }

    public User changeLikedList(List<String> likedList) {
        this.liked = likedList;
        return this;
    }
}
