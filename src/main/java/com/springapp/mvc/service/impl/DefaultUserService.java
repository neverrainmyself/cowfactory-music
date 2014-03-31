package com.springapp.mvc.service.impl;

import com.springapp.mvc.domain.User;
import com.springapp.mvc.domain.UserRole;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 1/17/14
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultUserService implements UserService,UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUserIfNeeded(String userId, String accessToken, String displayName, String imageUrl, String profileUrl, String name) {
        User user = (User) loadUserByUsername(userId);
        if (user == null) {
            user = User.instance()
                    .changeWeiboId(userId)
                    .changeAccessToken(accessToken)
                    .changeWeiboDisplayName(displayName)
                    .changeWeiboImage(imageUrl)
                    .changeWeiboProfileUrl(profileUrl)
                    .changeWeiboName(name);
            if(user.getWeiboId().equals("2671158493")) {
                user = user.changeRole(UserRole.ADMIN);
            }
            userRepository.save(user);
            return user;
        }
        return user;
    }

    @Override
    public User find(String guid) {
        return (User) userRepository.user(guid);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String guid = userRepository.guid(username);
        if(StringUtils.hasText(guid)){
             return userRepository.user(guid);
        }
        return null;
    }
}
