package com.springapp.mvc;

import com.springapp.mvc.Utils.SecurityUtils;
import com.springapp.mvc.command.UserCommand;
import com.springapp.mvc.command.response.LikeResponse;
import com.springapp.mvc.command.response.SaveResponse;
import com.springapp.mvc.domain.*;
import com.springapp.mvc.service.MusicService;
import com.springapp.mvc.service.UserService;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 1/17/14
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/register")
public class    RegisterController {
    @Autowired
    private MusicService musicService;
    @Autowired
    private UserService userService;


    @RequestMapping("/main")
    public String registerMain(ModelMap modelMap,HttpServletRequest request) {
        String status = ServletRequestUtils.getStringParameter(request, "type", Status.PUBLISH.toString());
        Status searchPara = Status.PUBLISH;
        try {
            searchPara = Status.valueOf(status);
        } catch (IllegalArgumentException e) {
        }
        modelMap.put("musics", musicService.findAllForLoginUser(searchPara));
        modelMap.put("status", searchPara);
        return "registermain";
    }





    @RequestMapping("/createoredit")
    public String createProdut(ModelMap modelMap,HttpServletRequest request) throws ServletRequestBindingException {
        String guid = ServletRequestUtils.getStringParameter(request, "guid", null);
        boolean forceEdit = ServletRequestUtils.getBooleanParameter(request, "forceedit", false);
        Music music = musicService.findByGuid(guid);
        if(music == null) {
            String type = ServletRequestUtils.getRequiredStringParameter(request, "musictype");
            music = musicService.create(type);
        }
        if((music.isVerify()||music.isPublish())&& !forceEdit) {
            return "redirect:/intercept?p=5&paras=" + music.getGuid();
        }
        modelMap.put("music", music);
        return "publishform";
    }


    @RequestMapping(value="/save",method = RequestMethod.POST)
    public
    @ResponseBody
    SaveResponse saveBlog(HttpServletRequest request) throws FileUploadException, IOException, ServletRequestBindingException {
        String guid = ServletRequestUtils.getRequiredStringParameter(request, "guid");
        String header = ServletRequestUtils.getRequiredStringParameter(request, "header");
        String subHeader = ServletRequestUtils.getRequiredStringParameter(request, "subheader");
        String content = ServletRequestUtils.getRequiredStringParameter(request, "content");
        return musicService.saveDraft(guid,header,subHeader,content);
    }

    @RequestMapping(value="/publish",method = RequestMethod.POST)
    public
    @ResponseBody
    SaveResponse publish(HttpServletRequest request) throws FileUploadException, IOException, ServletRequestBindingException {
        String guid = ServletRequestUtils.getRequiredStringParameter(request, "guid");
        String header = ServletRequestUtils.getRequiredStringParameter(request, "header");
        String subHeader = ServletRequestUtils.getRequiredStringParameter(request, "subheader");
        String content = ServletRequestUtils.getRequiredStringParameter(request, "content");
        return musicService.publish(guid,header,subHeader,content);
    }

    @RequestMapping(value="/todelete",method = RequestMethod.POST)
    public @ResponseBody boolean toDelete(HttpServletRequest request) throws FileUploadException, IOException, ServletRequestBindingException {
        String guid = ServletRequestUtils.getRequiredStringParameter(request, "guid");
        String header = ServletRequestUtils.getRequiredStringParameter(request, "header");
        String subHeader = ServletRequestUtils.getRequiredStringParameter(request, "subheader");
        String content = ServletRequestUtils.getRequiredStringParameter(request, "content");
        musicService.saveInitial(guid, header, subHeader, content);
        return true;
    }
    @RequestMapping(value="/deleteproduct")
    public String delete(HttpServletRequest request) throws FileUploadException, IOException, ServletRequestBindingException {
        String guid = ServletRequestUtils.getRequiredStringParameter(request, "guid");
        musicService.deleteMusic(guid);
        return "redirect:/register/main";
    }

    @RequestMapping("/uploadimage")
    public @ResponseBody
    String uploadBlogImage(HttpServletRequest request) throws FileUploadException, IOException, ServletRequestBindingException {
        String guid = ServletRequestUtils.getRequiredStringParameter(request, "guid");
        boolean multipartContent = ServletFileUpload.isMultipartContent(request);
        if(multipartContent){
            ServletFileUpload fileUpload = new ServletFileUpload();
            FileItemIterator iterator = fileUpload.getItemIterator(request);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                if(StringUtils.hasText(item.getContentType())){
                    Image image = musicService.uploadImage(guid, item);
                    return image.getGuid();
                }

            }
        }
        return null;
    }

    @RequestMapping("/uploadheaderimage")
    public @ResponseBody
    String uploadHeaderImage(HttpServletRequest request) throws FileUploadException, IOException, ServletRequestBindingException {
        String guid = ServletRequestUtils.getRequiredStringParameter(request, "guid");
        boolean multipartContent = ServletFileUpload.isMultipartContent(request);
        if(multipartContent){
            ServletFileUpload fileUpload = new ServletFileUpload();
            FileItemIterator iterator = fileUpload.getItemIterator(request);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                if(StringUtils.hasText(item.getContentType())){
                    Image image = musicService.uploadHeaderImage(guid, item);
                    return image.getGuid();
                }

            }
        }
        return null;
    }

    @RequestMapping("/uploadmusic")
    public @ResponseBody
    boolean uploadMusic(HttpServletRequest request) throws FileUploadException, IOException, ServletRequestBindingException {
        String guid = ServletRequestUtils.getRequiredStringParameter(request, "guid");
        boolean multipartContent = ServletFileUpload.isMultipartContent(request);
        if(multipartContent){
            ServletFileUpload fileUpload = new ServletFileUpload();
            FileItemIterator iterator = fileUpload.getItemIterator(request);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                if(StringUtils.hasText(item.getContentType())){
                    musicService.uploadMusic(guid, item);

                }

            }
        }
        return true;
    }

    @RequestMapping("/removeimage")
    public @ResponseBody
    boolean removeImage(HttpServletRequest request) throws FileUploadException, IOException, ServletRequestBindingException {
        String imageGuid = ServletRequestUtils.getRequiredStringParameter(request, "imageguid");
        musicService.removeImage(imageGuid);
        return true;
    }
    @RequestMapping("/removemusicfile")
    public @ResponseBody
    boolean removeMusicFile(HttpServletRequest request) throws FileUploadException, IOException, ServletRequestBindingException {
        String guid = ServletRequestUtils.getRequiredStringParameter(request, "guid");
        musicService.removeMusicFile(guid);
        return true;
    }

    @RequestMapping("/addcomment")
    public @ResponseBody
    boolean addComment(HttpServletRequest request) throws FileUploadException, IOException, ServletRequestBindingException {
        String guid = ServletRequestUtils.getRequiredStringParameter(request, "guid");
        String comment = ServletRequestUtils.getRequiredStringParameter(request, "usercomment");
        musicService.addComment(guid,comment);
        return true;
    }
    @RequestMapping("/like")
    public @ResponseBody LikeResponse like(@RequestParam String guid) {
        boolean liked = musicService.like(guid);
        User user = SecurityUtils.loadUser();
        SecurityUtils.refereshUser(userService.find(user.getGuid()));
        return new LikeResponse(liked,user);
    }
    @RequestMapping("/deletecomment")
    public @ResponseBody boolean deletecomment(@RequestParam String guid,String musicguid) {
        musicService.deleteComment(musicguid,guid);
        return true;
    }
}
