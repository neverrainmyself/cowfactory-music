package com.springapp.mvc;

import com.springapp.mvc.domain.Status;
import com.springapp.mvc.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 14-3-3
 * Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private MusicService musicService;

    @RequestMapping("/main")
    public String needToVerify(Model model, HttpServletRequest request) {
        String type = ServletRequestUtils.getStringParameter(request, "type", Status.VERIFY.toString());
        model.addAttribute("musics", musicService.findForAdmin(Status.valueOf(type)));
        model.addAttribute("status", type);
        return "admin/verify";
    }
    @RequestMapping("/allowpublish")
    public @ResponseBody boolean changeToVerify(@RequestParam String guid) {
        musicService.allowPublish(guid);
        return true;
    }

    @RequestMapping("/reject")
    public @ResponseBody boolean reject(@RequestParam String guid) {
        musicService.reject(guid);
        return true;
    }
}
