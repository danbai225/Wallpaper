/**
 * @author DanBai
 * @create 2020-05-14 17:29
 * @desc 文件工具类
 **/
package cn.p00q.wallpaper.utils;

import cn.p00q.wallpaper.WallpaperApplication;
import net.lingala.zip4j.core.ZipFile;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;



public class FileUtils {
    public static String getPrefix(String filename){
        return filename.substring(filename.lastIndexOf(".")+1);
    }
    public static boolean saveFile(MultipartFile file,String path){
        File desFile = new File(path);
        if(!desFile.getParentFile().exists()){
            desFile.mkdirs();
        }
        try {
            file.transferTo(desFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static String getApplicationPath(){
        //获取classpath
        ApplicationHome h = new ApplicationHome(WallpaperApplication.class);
        File jarF = h.getSource();
        return jarF.getParentFile()+ File.separator;
    }
    /**
     * zip文件解压
     *
     * @param destPath 解压文件路径
     * @param zipFile  压缩文件
     * @param password 解压密码(如果有)
     */
    public static boolean unPackZip(File zipFile, String password, String destPath) {
        try {
            ZipFile zip = new ZipFile(zipFile);
            zip.extractAll(destPath);
            // 如果解压需要密码
            if (zip.isEncrypted()) {
                zip.setPassword(password);
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
