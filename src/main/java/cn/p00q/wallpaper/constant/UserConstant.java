/**
 * @author DanBai
 * @create 2020-05-15 10:46
 * @desc 用户相关常量
 **/
package cn.p00q.wallpaper.constant;

public class UserConstant {
    public static String REDIS_ACTIVATION_PREFIX="activation-";
    public static String REG_OK="注册成功,请检查邮箱的激活链接,点击激活账号";
    public static String USER_ALREADY_EXISTS="用户已存在";
    public static String REG_EMAIL_TITLE="淡白壁纸注册激活邮件";
    public static String REG_EMAIL_CONTENT="欢迎注册淡白影视,请点击以下链接激活您的账号:";
    public static String ACTIVATION_URL="http://127.0.0.1:8081/activation?id=";
}
