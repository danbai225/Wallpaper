/**
 * @author DanBai
 * @create 2020-05-15 9:39
 * @desc 用户接口
 **/
package cn.p00q.wallpaper.controller.restful.v1;

import cn.p00q.wallpaper.constant.UserConstant;
import cn.p00q.wallpaper.constant.WallpaperConstant;
import cn.p00q.wallpaper.entity.Response;
import cn.p00q.wallpaper.entity.User;
import cn.p00q.wallpaper.service.UserService;
import cn.p00q.wallpaper.utils.ReturnMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {
    @Autowired
    UserService userService;

    @PostMapping("/reg")
    Response reg(@Validated({User.Register.class}) User user){
        return userService.reg(user);
    }
    @PostMapping("/login")
    Response login(@Validated({User.Login.class}) User user, HttpServletRequest request, HttpServletResponse response){
        ReturnMap r = userService.login(user);
        if(r.succeed){
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(60 * 60 * 24*7);
            session.setAttribute("user",r.get("user",User.class));
            session.setAttribute("url", WallpaperConstant.CONTACT+"?token="+r.get("token",String.class));
            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setMaxAge(60 * 60 * 24*7);
            response.addCookie(cookie);
        }
        return r.toResponse();
    }
    @PostMapping("/setWallpaper")
    @ResponseBody
    public Response set(HttpServletRequest request,int id){
        User user= (User) request.getSession().getAttribute("user");
        if (user!=null){
            userService.setWallpaper(user,id);
            return Response.Ok();
        }
        return Response.Err(UserConstant.NOT_LOGIN);
    }
    @GetMapping("/getWallpaper")
    @ResponseBody
    public Response get(String token){
        User user = userService.getUserByToken(token);
        if(user!=null){
            return Response.Ok("壁纸链接",user.getUrl());
        }
        return Response.Err(UserConstant.NOT_LOGIN);
    }
}
