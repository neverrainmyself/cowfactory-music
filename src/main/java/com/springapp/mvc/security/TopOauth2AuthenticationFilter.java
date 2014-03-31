package com.springapp.mvc.security;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.springapp.mvc.domain.User;
import com.springapp.mvc.service.UserService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 12/17/13
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class TopOauth2AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String code = request.getParameter("code");
        NameValuePair clientId = new NameValuePair("client_id", "451802371");
        NameValuePair clientSecret = new NameValuePair("client_secret", "1c11de725161333b0c26798f65fffdd9");
        NameValuePair grantType = new NameValuePair("grant_type", "authorization_code");
        NameValuePair redirectUri = new NameValuePair("redirect_uri", "http://dowhatcowdo.com/auth");
        NameValuePair code_ = new NameValuePair("code", code);
        NameValuePair[] body = {clientId, clientSecret, grantType, redirectUri, code_};
        PostMethod method = new PostMethod("https://api.weibo.com/oauth2/access_token");
        method.setRequestBody(body);
        HttpClient httpClient = new HttpClient();
        try {
            httpClient.executeMethod(method);
            String accessResponse = method.getResponseBodyAsString();
            JSONObject jsonObject = new JSONObject(accessResponse);
            String accessToken = jsonObject.getString("access_token");
            String uid = jsonObject.getString("uid");

            GetMethod getMethod = new GetMethod("https://api.weibo.com/2/users/show.json?access_token=" + accessToken + "&uid=" + uid);
            httpClient.executeMethod(getMethod);
            String userResponse = getMethod.getResponseBodyAsString();
            JSONObject userObj = new JSONObject(userResponse);
            String userId = userObj.getString("id");
            String displayName = userObj.getString("screen_name");
            String name = userObj.getString("name");
            String profileImageUrl = userObj.getString("avatar_large");
            String weiboUrl = userObj.getString("profile_url");
            return new UsernamePasswordAuthenticationToken(new OAuthAuthentication(userId, displayName, profileImageUrl, weiboUrl, name, accessToken), null);
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        } catch (JSONException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        OAuthAuthentication userProfile = (OAuthAuthentication) authResult.getPrincipal();
        String accessToken = userProfile.getAccessToken();
        String displayName = userProfile.getDisplayName();
        String imageUrl = userProfile.getProfileImageUrl();
        String profileUrl = userProfile.getWeiboUrl();
        String name = userProfile.getName();
        String userId = userProfile.getUserId();

        User user = userService.createUserIfNeeded(userId,accessToken,displayName,imageUrl,profileUrl,name);
        authResult = new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
