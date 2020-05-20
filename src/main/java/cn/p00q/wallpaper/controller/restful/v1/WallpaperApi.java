/**
 * @author DanBai
 * @create 2020-05-19 19:39
 * @desc 壁纸api
 **/
package cn.p00q.wallpaper.controller.restful.v1;

import cn.p00q.wallpaper.constant.UserConstant;
import cn.p00q.wallpaper.constant.WallpaperConstant;
import cn.p00q.wallpaper.entity.Response;
import cn.p00q.wallpaper.entity.User;
import cn.p00q.wallpaper.entity.Wallpaper;
import cn.p00q.wallpaper.service.WallpaperService;
import cn.p00q.wallpaper.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/wallpaper")
public class WallpaperApi {
    @Autowired
    WallpaperService wallpaperService;
    @PostMapping("/upload")
    @ResponseBody
    public Response uploadFile(@RequestParam("file") MultipartFile file, Wallpaper wallpaper, HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        if(user!=null){
            wallpaper.setUploader(user.getUsername());
            //后缀.zip
            if(FileUtils.getPrefix(file.getOriginalFilename()).toLowerCase().equals(Wallpaper.File_PREFIX)){
                return wallpaperService.saveWallpaper(file,wallpaper);
            }
            return Response.Err(WallpaperConstant.NOT_ZIP);
        }
        return Response.Err(UserConstant.NOT_LOGIN);
    }

}
