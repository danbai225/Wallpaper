/**
 * @author DanBai
 * @create 2020-05-15 9:39
 * @desc 用户接口
 **/
package cn.p00q.wallpaper.controller.restful.v1;

import cn.p00q.wallpaper.entity.Response;
import cn.p00q.wallpaper.entity.User;
import cn.p00q.wallpaper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {
    @Autowired
    UserService userService;

    @PostMapping("/reg")
    Response reg(@Validated({User.Register.class}) User user){
        return userService.reg(user);
    }
}
