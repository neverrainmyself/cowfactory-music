package com.springapp.mvc.Utils;

import com.springapp.mvc.domain.User;
import com.springapp.mvc.security.OAuthAuthentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created with IntelliJ IDEA.
 * User: zhanjche
 * Date: 13-8-14
 * Time: 上午11:12
 * To change this template use File | Settings | File Templates.
 */
public class SecurityUtils {

    public static User loadUser(){
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            return (User) authentication.getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public static void refereshUser(User user) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities()));
    }
}
