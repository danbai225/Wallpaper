/**
 * @author DanBai
 * @create 2020-05-14 17:07
 * @desc 壁纸服务
 **/
package cn.p00q.wallpaper.service;

import cn.p00q.wallpaper.constant.WallpaperConstant;
import cn.p00q.wallpaper.entity.Response;
import cn.p00q.wallpaper.entity.User;
import cn.p00q.wallpaper.entity.Wallpaper;
import cn.p00q.wallpaper.mapper.WallpaperMapper;
import cn.p00q.wallpaper.utils.FileUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Service
public class WallpaperService {
    @Autowired
    WallpaperMapper wallpaperMapper;

    /**
     * 保存壁纸
     * @param file
     * @param wallpaper
     * @return
     */
    public Response saveWallpaper(MultipartFile file, Wallpaper wallpaper){
        //判断是否存在index.html
        boolean ifThere=false;
        try {
            InputStream in = new BufferedInputStream(file.getInputStream());
            ZipInputStream zin = new ZipInputStream(in,Charset.forName("gbk"));
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                } else {
                    String name = ze.getName();
                    if(name.toLowerCase().equals(WallpaperConstant.INDEX)){
                        ifThere=true;
                    }
                }
            }
            zin.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!ifThere){
            return Response.Err(WallpaperConstant.NOT_FIND_INDEX);
        }
        //设置id(行数+1)
        wallpaper.setId(WallpaperSize()+1);
        wallpaper.setUrl("http://wallpaper.p00q.cn/wallpaper/"+wallpaper.getId()+"/index.html");
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

    /**
     * 壁纸数量
     * @return
     */
    public int WallpaperSize(){
        return wallpaperMapper.wallpaperSize();
    }

    /**
     * 壁纸配置
     * @param user
     * @param response
     */
    public void configuration(User user, HttpServletResponse response){
        //获取壁纸
        Wallpaper wallpaper = selectByUrl(user.getUrl());
        String dir=FileUtils.getApplicationPath()+"configuration"+File.separator+user.getUsername()+File.separator;
        String Prefix=FileUtils.getPrefix(wallpaper.getImgUrl());
        String filename;
        if(Prefix==null||Prefix.equals("")){
            filename="img.png";
        }else {
            filename="img."+Prefix;
        }
        //下载壁纸截图
        FileUtils.downloadHttpUrl(wallpaper.getImgUrl(),dir,filename);
        //壁纸信息存json
        Map jsonMap =new HashMap<>(10);
        jsonMap.put("Title",wallpaper.getName());
        jsonMap.put("Thumbnail",filename);
        jsonMap.put("Preview",filename);
        jsonMap.put("Desc",wallpaper.getIntroduction());
        jsonMap.put("Author",wallpaper.getAuthor());
        jsonMap.put("Contact",WallpaperConstant.CONTACT);
        jsonMap.put("Type",3);
        jsonMap.put("FileName",wallpaper.getUrl());
        //保存到json文件
        File jsonFile=new File(dir+ WallpaperConstant.LIVELYINFO_FILENAME);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(jsonFile);
            OutputStream out = new BufferedOutputStream(outputStream);
            out.write(JSON.toJSONString(jsonMap).getBytes());
            out.flush();
            out.close();
            //压缩文件夹
            FileUtils.compressedFolder(dir,dir+"configuration.zip");
            //返回文件
            response.setContentType("application/zip");
            response.setHeader("Content-disposition", "attachment;filename="+WallpaperConstant.CONFIGURATION_FILENAME);
            //读取文件
            File zipFile=new File(dir+WallpaperConstant.CONFIGURATION_FILENAME);
            FileInputStream fileInputStream =new FileInputStream(zipFile);
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            out = response.getOutputStream();
            //输出
            out.write(buffer);
            out.flush();
            out.close();
            fileInputStream.close();
            zipFile.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据链接查询壁纸
     * @param url
     * @return
     */
    public Wallpaper selectByUrl(String url){
        Wallpaper wallpaper=new Wallpaper();
        wallpaper.setUrl(url);
        return wallpaperMapper.selectOne(wallpaper);
    }
    /**
     * 根据id查询壁纸
     * @param id
     * @return
     */
    public Wallpaper selectById(int id){
        Wallpaper wallpaper=new Wallpaper();
        wallpaper.setId(id);
        return wallpaperMapper.selectOne(wallpaper);
    }

    /**
     * 壁纸分页
     * @param page
     * @param size
     * @param audit
     * @return
     */
    public List<Wallpaper> list(int page,int size,boolean audit){
        PageHelper.startPage(page,size);
        // 设置分页查询条件
        Example example = new Example(Wallpaper.class);
        if(audit){
            example.createCriteria().andNotEqualTo("audit",false);
        }
        example.orderBy("id").desc();
        PageInfo<Wallpaper> pageInfo = new PageInfo<>(wallpaperMapper.selectByExample(example));
        List<Wallpaper> list = pageInfo.getList();
        return list;
    }
}
