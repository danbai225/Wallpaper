/**
 * @author DanBai
 * @create 2020-05-15 9:02
 * @desc 用户控制器
 **/
package cn.p00q.wallpaper.controller;

import cn.p00q.wallpaper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/activation")
    public String activation(String id){
        userService.activation(id);
        return "activation";
    }
}
