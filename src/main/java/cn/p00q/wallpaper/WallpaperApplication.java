package cn.p00q.wallpaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author DanBai
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.p00q.wallpaper.mapper")
@EnableAsync
public class WallpaperApplication {

    public static void main(String[] args) {
        SpringApplication.run(WallpaperApplication.class, args);
    }

}
