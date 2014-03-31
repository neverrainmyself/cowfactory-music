package com.springapp.mvc;

import com.springapp.mvc.Utils.SecurityUtils;
import com.springapp.mvc.command.MusicCommand;
import com.springapp.mvc.command.NextMusicCommand;
import com.springapp.mvc.command.PlayOneResponse;
import com.springapp.mvc.domain.Comment;
import com.springapp.mvc.domain.Image;
import com.springapp.mvc.domain.Music;
import com.springapp.mvc.service.MusicService;
import com.springapp.mvc.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private MusicService musicService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConnectionFactoryRegistry connectionFactoryRegistry;
    private static final String redirectUrl = "http://dowhatcowdo.com/auth";
    private static final String[] interceptPages = new String[]{
      "intercept/login","intercept/delete","intercept/quitaddproduct","intercept/beforepublish","intercept/errorsave","intercept/publishtodraft","intercept/cannotpublish","intercept/publishsuccess"
    };

    @RequestMapping("/loginastemp")
    public String login() {
        OAuth2ConnectionFactory<?> weibo = (OAuth2ConnectionFactory<?>) connectionFactoryRegistry.getConnectionFactory("weibo");
        OAuth2Operations oAuthOperations = weibo.getOAuthOperations();
        Map<String, List<String>> parameters = new HashMap<String, List<String>>();
        ArrayList<String> strings = new ArrayList<String>();

        strings.add(redirectUrl);
        parameters.put("redirect_uri", strings);

        String url = oAuthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, new OAuth2Parameters(parameters));
        return "redirect:"+url;
    }
	@RequestMapping(method = RequestMethod.GET)
	public String loadLatestProducts(ModelMap model) {
		return "index";
	}

    @RequestMapping("/defaultmustics")
    public String default100Musics(ModelMap model, @RequestParam String type) {
        model.put("musics",musicService.loadDefault100Musics(type));
        return "musics";
    }

    @RequestMapping("/top100")
    public String top100(ModelMap model, @RequestParam String type) {
        model.put("musics",musicService.top100(type));
        return "musics";
    }


    @RequestMapping("/playone")
    public @ResponseBody
    PlayOneResponse default100Musics(HttpServletRequest request) {
        return musicService.playOne(ServletRequestUtils.getStringParameter(request,"guid",null));
    }


    @RequestMapping("/detail")
    public String loadMusicDetail(ModelMap model, @RequestParam String guid) {
        model.put("music", musicService.findMusic(guid));
        return "thingdetail";
    }
    @RequestMapping("/profile")
    public String profile(ModelMap model, @RequestParam String guid) {
        model.put("user", userService.find(guid));
        model.put("musics", musicService.findMusicsByUserGuid(guid));
        return "userprofile";
    }
    @RequestMapping("/next")
    public @ResponseBody
    NextMusicCommand loadNextMusic(@RequestParam String guid) {
        return musicService.findNext(guid, true);

    }



    @RequestMapping("/intercept")
    public String intercept(@RequestParam int p,HttpServletRequest request,ModelMap modelMap) {
        modelMap.put("attrs", ServletRequestUtils.getStringParameters(request, "paras"));
        return interceptPages[p];
    }

    @RequestMapping("/loadimage")
    public void loadImage(@RequestParam String guid, HttpServletResponse response) throws IOException {
        Image image = musicService.loadMusicImage(guid);
        FileCopyUtils.copy(new FileInputStream(new File(image.getPath())), response.getOutputStream());
    }

    @RequestMapping("/loadmusic")
    public void loadMusic(@RequestParam String guid, HttpServletResponse response) throws IOException {
        try {
            Music music = musicService.findMusic(guid);
            FileCopyUtils.copy(new FileInputStream(new File(music.getPath())), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    @RequestMapping("/listened")
    public void listened(@RequestParam String guid, HttpServletResponse response) throws IOException {
            musicService.listened(guid);
    }

    @RequestMapping("/download")
    public void download(@RequestParam String guid, HttpServletResponse response) throws IOException {
        String musicPath = musicService.download(guid);
        try {
            if(StringUtils.hasText(musicPath)){
                FileInputStream in = new FileInputStream(musicPath);
                IOUtils.copy(in, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @RequestMapping("/musiccomments")
    public @ResponseBody List<Comment> comments(@RequestParam String guid,@RequestParam int page) throws IOException {
        return musicService.comments(guid,page);
    }


}