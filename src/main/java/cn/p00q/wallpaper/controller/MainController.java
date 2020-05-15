package cn.p00q.wallpaper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author DanBai
 */
@Controller
public class MainController {
    @GetMapping("/")
    public String main(){
        return "index";
    }
}
