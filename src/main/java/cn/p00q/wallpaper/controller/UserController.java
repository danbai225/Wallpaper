/**
 * @author DanBai
 * @create 2020-05-15 9:02
 * @desc 用户控制器
 **/
package cn.p00q.wallpaper.controller;

import cn.p00q.wallpaper.constant.UserConstant;
import cn.p00q.wallpaper.constant.WallpaperConstant;
import cn.p00q.wallpaper.entity.User;
import cn.p00q.wallpaper.service.UserService;
import cn.p00q.wallpaper.utils.ReturnMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/activation")
    public String activation(String id){
        userService.activation(id);
        return "activation";
    }
    @GetMapping("/user")
    public String user(HttpServletRequest request){
        User user= (User) request.getSession().getAttribute(UserConstant.USER);
        if(user!=null){
            request.getSession().setAttribute("user",userService.selectByUserName(user.getUsername()));
            return "user";
        }
        return "redirect:/login";
    }
    @GetMapping("/exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute(UserConstant.USER);
        return "redirect:/";
    }
    @PostMapping("/login")
    public String login(@Validated({User.Login.class}) User user, HttpServletRequest request, HttpServletResponse response, Model model){
        ReturnMap r = userService.login(user);
        if(r.succeed){
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(60 * 60 * 24*7);
            session.setAttribute("user",r.get("user",User.class));
            session.setAttribute("url", WallpaperConstant.CONTACT+"?token="+r.get("token",String.class));
            session.setAttribute("token", r.get("token",String.class));
            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setMaxAge(60 * 60 * 24*7);
            response.addCookie(cookie);
            return "redirect:/user";
        }
        model.addAttribute("msg",r.getMsg());
        return "/login";
    }
}
