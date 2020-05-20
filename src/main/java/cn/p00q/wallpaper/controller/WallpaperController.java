/**
 * @author DanBai
 * @create 2020-05-14 16:33
 * @desc 壁纸控制器
 **/
package cn.p00q.wallpaper.controller;

import cn.p00q.wallpaper.constant.UserConstant;
import cn.p00q.wallpaper.entity.User;
import cn.p00q.wallpaper.entity.Wallpaper;
import cn.p00q.wallpaper.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WallpaperController {
    @Autowired
    WallpaperService wallpaperService;
    @GetMapping("/configuration")
    public void configuration(HttpServletRequest request, HttpServletResponse response){
        User user =(User) request.getSession().getAttribute(UserConstant.USER);
        if(user!=null){
            wallpaperService.configuration((String) request.getSession().getAttribute("token"),user,response);
        }
    }
    @GetMapping("/WallpaperUpload")
    public String wallpaperUpload(){
        return "WallpaperUpload";
    }

    @GetMapping("/wallpaperInfo")
    public String wallpaperInfo(Integer id, Model model){
        Wallpaper wallpaper=wallpaperService.selectById(id);
        if(wallpaper!=null){
            model.addAttribute("wallpaper",wallpaper);
            return "wallpaperInfo";
        }
        return "error/404";
    }
    @GetMapping("/list")
    public String list(Integer page,Model model){
        if (page==null||page<0){
            page=1;
        }
        model.addAttribute("wallpapers",wallpaperService.list(page,10,false));
        model.addAttribute("page",page);
        return "list";
    }
}
