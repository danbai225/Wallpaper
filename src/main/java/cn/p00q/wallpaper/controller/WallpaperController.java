/**
 * @author DanBai
 * @create 2020-05-14 16:33
 * @desc 壁纸控制器
 **/
package cn.p00q.wallpaper.controller;

import cn.p00q.wallpaper.constant.WallpaperConstant;
import cn.p00q.wallpaper.entity.Response;
import cn.p00q.wallpaper.entity.Wallpaper;
import cn.p00q.wallpaper.service.WallpaperService;

import cn.p00q.wallpaper.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WallpaperController {
    @Autowired
    WallpaperService wallpaperService;
    @PostMapping("/uploadWallpaper")
    @ResponseBody
    public Response uploadFile(@RequestParam("file") MultipartFile file,Wallpaper wallpaper){
        //后缀.zip
        if(FileUtils.getPrefix(file.getOriginalFilename()).toLowerCase().equals(Wallpaper.File_PREFIX)){
            return wallpaperService.saveWallpaper(file,wallpaper);
        }
        return Response.Err(WallpaperConstant.NOT_ZIP);
    }
}
