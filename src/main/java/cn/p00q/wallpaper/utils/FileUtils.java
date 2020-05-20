/**
 * @author DanBai
 * @create 2020-05-14 17:29
 * @desc 文件工具类
 **/
package cn.p00q.wallpaper.utils;

import cn.p00q.wallpaper.WallpaperApplication;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


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
            zip.setFileNameCharset("gbk");
            if(!zip.isValidZipFile()) {
                // 检查文件是否合法
                throw new Exception("文件名不合法");
            }
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
    /**
     * 下载文件---返回下载后的文件存储路径
     *
     * @param url      文件路径 10
     * @param dir      目标存储目录 11
     * @param fileName 存储文件名
     * @return
     */
    public static void downloadHttpUrl(String url, String dir, String fileName){
        try {
            URL httpurl = new URL(url);
            File dirfile = new File(dir);
            if (!dirfile.exists()) {
                dirfile.mkdirs();
            }
            org.apache.commons.io.FileUtils.copyURLToFile(httpurl, new File(dir + fileName));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void compressedFolder(String dirPath,String zipPath){
        try {

            ZipFile zipFile = new ZipFile(zipPath);
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.addFolder(dirPath, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}
