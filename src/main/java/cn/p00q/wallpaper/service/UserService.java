/**
 * @author DanBai
 * @create 2020-05-15 9:41
 * @desc 用户服务
 **/
package cn.p00q.wallpaper.service;

import cn.p00q.wallpaper.constant.UserConstant;
import cn.p00q.wallpaper.constant.WallpaperConstant;
import cn.p00q.wallpaper.entity.Response;
import cn.p00q.wallpaper.entity.User;
import cn.p00q.wallpaper.mapper.UserMapper;
import cn.p00q.wallpaper.utils.EmailUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    EmailUtil emailUtil;
    /**
     * 注册
     * @param user
     * @return
     */
    public Response reg(User user){
        if(selectByUserName(user.getUsername())==null){
            //密码加密
            user.setPassword(DigestUtils.md5DigestAsHex((user.getUsername()+user.getPassword()).getBytes()));
            //默认未激活账号
            user.setType(0);
            //默认壁纸
            user.setUrl(WallpaperConstant.DEFAULT_WALLPAPER_URL);
            //插入到数据库
            userMapper.insert(user);
            //发送激活邮件
            sendActivationEmail(user.getUsername(),user.getEmail());
            return Response.Ok(UserConstant.REG_OK);
        }
        return Response.Err(UserConstant.USER_ALREADY_EXISTS);
    }

    /**
     * 通过用户名查询一个账号
     * @param username 用户名
     * @return 用户
     */
    public User selectByUserName(String username){
        User user =new User();
        user.setUsername(username);
        return userMapper.selectOne(user);
    }

    /**
     * 激活账号
     * @param id
     * @return
     */
    public boolean activation(String id){
        String key=UserConstant.REDIS_ACTIVATION_PREFIX +id;
        if(redisTemplate.hasKey(key)){
            User user=selectByUserName((String)redisTemplate.opsForValue().get(key));
            if(user!=null){
                user.setType(1);
                if(updateUser(user)>0){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 更新账号
     * @param user
     * @return
     */
    public int updateUser(User user){
        return userMapper.updateByPrimaryKey(user);
    }

    /**
     * 发送激活邮件
     * @param username
     * @param email
     */
    public void sendActivationEmail(String username,String email){
        //激活id存入redis
        String activationId=RandomStringUtils.randomAlphanumeric(18);
        redisTemplate.opsForValue().set(UserConstant.REDIS_ACTIVATION_PREFIX+activationId,username,5, TimeUnit.MINUTES);
        //发送激活邮件
        emailUtil.sendEmail(UserConstant.REG_EMAIL_TITLE,UserConstant.REG_EMAIL_CONTENT+UserConstant.ACTIVATION_URL+activationId,email);
    }
}
