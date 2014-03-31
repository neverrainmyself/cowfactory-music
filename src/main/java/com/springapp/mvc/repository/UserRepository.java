package com.springapp.mvc.repository;

import com.springapp.mvc.Utils.GuidGenerator;
import com.springapp.mvc.Utils.SecurityUtils;
import com.springapp.mvc.domain.Music;
import com.springapp.mvc.domain.User;
import com.springapp.mvc.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2/24/14
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRepository {
    @Autowired
    private RedisDatasource redisDatasource;
    String weiboIdToGuid = "getuserguid:{weiboid}";
    String user = "user:{guid}";
    String userMusicList = "usermusiclist:{guid}";
    String likedList = "userliked:{guid}";

    public String guid(String weiboUserId) {
        return redisDatasource.getString(weiboIdToGuid.replace("{weiboid}", weiboUserId));
    }

    public UserDetails user(String guid) {
        Map<String,String> userMap = redisDatasource.getAll(user.replace("{guid}",guid));
        if(userMap == null || userMap.isEmpty()) {
            return null;
        }
        List<String> likedList = redisDatasource.getList(this.likedList.replace("{guid}", guid));
        return User.instance()
                .changeRole(UserRole.valueOf(userMap.get("role")))
                .changeWeiboId(userMap.get("weiboId"))
                .changeWeiboProfileUrl(userMap.get("weiboProfileUrl"))
                .changeWeiboImage(userMap.get("weiboImage"))
                .changeWeiboDisplayName(userMap.get("weiboDisplayName"))
                .changeWeiboName(userMap.get("weiboName"))
                .changeAccessToken(userMap.get("accessToken"))
                .changeGuid(guid)
                .changeLikedList(likedList);
    }

    public void save(User user) {
        String weiboId = user.getWeiboId();
        String guid = user.getGuid();
        redisDatasource.set(weiboIdToGuid.replace("{weiboid}",weiboId), guid);
        redisDatasource.put(this.user.replace("{guid}", guid),user.map());
    }

    public void addMusicToUser(User user, Music music) {
        redisDatasource.rpush(userMusicList.replace("{guid}",user.getGuid()),music.getGuid());
    }

    public List<String> userMusics(User user) {
        return redisDatasource.getList(userMusicList.replace("{guid}",user.getGuid()));
    }

    public void deleteMusic(String guid) {
        redisDatasource.lrem(userMusicList.replace("{guid}", SecurityUtils.loadUser().getGuid()), guid);

    }

    public boolean likeMusic(String guid) {
        String userGuid = SecurityUtils.loadUser().getGuid();
        String key = likedList.replace("{guid}", userGuid);
        int isLiked = redisDatasource.lrem(key, guid);
        if(isLiked==0) {
            redisDatasource.lpush(key,guid);
            return true;
        }
        return false;
    }
}
