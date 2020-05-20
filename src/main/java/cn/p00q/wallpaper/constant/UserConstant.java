/**
 * @author DanBai
 * @create 2020-05-15 10:46
 * @desc 用户相关常量
 **/
package cn.p00q.wallpaper.constant;

public class UserConstant {
    public static final String REDIS_ACTIVATION_PREFIX="activation-";
    public static final String REG_OK="注册成功,请检查邮箱的激活链接,点击激活账号";
    public static final String USER_ALREADY_EXISTS="用户已存在";
    public static final String EMAIL_ALREADY_EXISTS="邮箱已注册";
    public static final String REG_EMAIL_TITLE="在线H5壁纸注册激活邮件";
    public static final String REG_EMAIL_CONTENT="欢迎注册在线H5壁纸,请点击以下链接激活您的账号:";
    public static final String ACTIVATION_URL="http://wallpaper.p00q.cn/activation?id=";
    public static final String USER="user";
    public static final String NOT_LOGIN="未登录";
    public static final String NOT_ACTIVATE="账号没有激活!";
    public static final String OK_LOGIN="登录成功!";
    public static final String ERR_PASS_OR_NAME="账号或密码错误!";
}
