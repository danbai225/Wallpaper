package cn.p00q.wallpaper.mapper;

import cn.p00q.wallpaper.entity.Wallpaper;
import cn.p00q.wallpaper.utils.MyMapper;
import org.springframework.stereotype.Repository;

/**
 * @author DanBai
 */
@Repository
public interface WallpaperMapper extends MyMapper<Wallpaper> {
    /**
     *  查询行数
     * @return 行数
     */
    int wallpaperSize();
}