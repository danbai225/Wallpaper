/**
 * @author DanBai
 * @create 2020-05-14 17:07
 * @desc 壁纸服务
 **/
package cn.p00q.wallpaper.service;

import cn.p00q.wallpaper.entity.Response;
import cn.p00q.wallpaper.entity.Wallpaper;
import cn.p00q.wallpaper.mapper.WallpaperMapper;
import cn.p00q.wallpaper.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Service
public class WallpaperService {
    @Autowired
    WallpaperMapper wallpaperMapper;
    public Response saveWallpaper(MultipartFile file, Wallpaper wallpaper){
        //设置id(行数+1)
        wallpaper.setId(WallpaperSize()+1);
        wallpaper.setUrl("127.0.0.1:8081/wallpaper/"+wallpaper.getId()+"/index.html");
        wallpaper.setAudit(false);
        //添加到数据库
        wallpaperMapper.insert(wallpaper);
        String path=FileUtils.getApplicationPath()+"wallpaper"+File.separator+wallpaper.getId()+"."+Wallpaper.File_PREFIX;
        //保存到服务器
        FileUtils.saveFile(file,path);
        String destPath=FileUtils.getApplicationPath()+"wallpaper"+File.separator+wallpaper.getId()+File.separator;
        //解压
            if(FileUtils.unPackZip(new File(path),"",destPath)){
            return Response.Ok("上传成功",wallpaper.getUrl());
        }
        return Response.Err("上传失败");
    }
    public int WallpaperSize(){
        return wallpaperMapper.wallpaperSize();
    }
}
