package cn.p00q.wallpaper.controller;

import cn.p00q.wallpaper.constant.UserConstant;
import cn.p00q.wallpaper.entity.User;
import cn.p00q.wallpaper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author DanBai
 */
@Controller
public class MainController {
    @Autowired
    UserService userService;
    @GetMapping("/")
    public String index(String token, Model model){
        if(token!=null){
            User user =userService.getUserByToken(token);
            if (user!=null){
                model.addAttribute("url",user.getUrl());
                model.addAttribute("token",token);
                return "wallpaper";
            }
        }
        return "index";
    }
    @GetMapping("/reg")
    public String reg(){
        return "reg";
    }
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        User user= (User) request.getSession().getAttribute(UserConstant.USER);
        if(user!=null){
            request.getSession().setAttribute("user",userService.selectByUserName(user.getUsername()));
            try {
                response.sendRedirect("/user");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "login";
    }
    @GetMapping("/coursebook")
    public String coursebook(){
        return "Coursebook";
    }
}
