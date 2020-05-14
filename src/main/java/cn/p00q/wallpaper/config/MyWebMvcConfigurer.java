/**
 * @author DanBai
 * @create 2020-05-14 10:40
 * @desc
 **/
package cn.p00q.wallpaper.config;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //获取classpath
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        //拼接path classpath的上级+自定义的images
        String gitPath=jarF.getParentFile()+ File.separator+"images"+File.separator;
        //添加映射  (idea打包会映射在target目录)
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+gitPath);
    }

}
