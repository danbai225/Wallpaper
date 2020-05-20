/**
 * @author DanBai
 * @create 2020-05-15 9:02
 * @desc 用户控制器
 **/
package cn.p00q.wallpaper.controller;

import cn.p00q.wallpaper.constant.UserConstant;
import cn.p00q.wallpaper.entity.User;
import cn.p00q.wallpaper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
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
        return "login";
    }
    @GetMapping("/exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute(UserConstant.USER);
        return "index";
    }
}
