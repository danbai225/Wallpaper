/**
 * @author DanBai
 * @create 2020-05-17 19:44
 * @desc string工具类
 **/
package cn.p00q.wallpaper.utils;

import java.util.UUID;

public class StringUtils {

    public static String getUUID32(){

        return UUID.randomUUID().toString().replace("-", "").toLowerCase();

    }
}
