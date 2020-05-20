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
import cn.p00q.wallpaper.entity.Wallpaper;
import cn.p00q.wallpaper.mapper.UserMapper;
import cn.p00q.wallpaper.utils.EmailUtil;
import cn.p00q.wallpaper.utils.ReturnMap;
import cn.p00q.wallpaper.utils.StringUtils;
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
    @Autowired
    WallpaperService wallpaperService;
    /**
     * 注册
     * @param user
     * @return
     */
    public Response reg(User user){
        if(getUserByEmail(user.getEmail())!=null){
            return Response.Err(UserConstant.EMAIL_ALREADY_EXISTS);
        }
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

    /**
     * 用户登录
     * @param user 登录信息
     */
    public ReturnMap login(User user){
        User dataUser = selectByUserName(user.getUsername());
        if(dataUser!=null){
            //加密pass
            String pass=DigestUtils.md5DigestAsHex((user.getUsername()+user.getPassword()).getBytes());
            //验证密码
            if(dataUser.getPassword().equals(pass)){
                if(dataUser.getType()==User.TYPE_VISITOR){
                    ReturnMap returnMap = ReturnMap.error(UserConstant.NOT_ACTIVATE);
                    return returnMap;
                }
                String token=StringUtils.getUUID32();
                //存入token
                redisTemplate.opsForValue().set(token,dataUser,7,TimeUnit.DAYS);
                ReturnMap returnMap = ReturnMap.succeed(UserConstant.OK_LOGIN);
                returnMap.put("token",token);
                returnMap.put("user",dataUser);
                return returnMap;
            }
        }
        return ReturnMap.error(UserConstant.ERR_PASS_OR_NAME);
    }

    /**
     * 根据token获取用户
     * @param token
     * @return
     */
    public User getUserByToken(String token){
       User user=(User)redisTemplate.opsForValue().get(token);
       if(user!=null){
           redisTemplate.expire(token,7,TimeUnit.DAYS);
           return selectByUserName(user.getUsername());
       }
       return null;
    }
    public User getUserByEmail(String email){
        User user =new User();
        user.setEmail(email);
        return userMapper.selectOne(user);
    }
    public void setWallpaper(User user,int id){
        Wallpaper wallpaper = wallpaperService.selectById(id);
        if(wallpaper!=null){
            user.setUrl(wallpaper.getUrl());
            updateUser(user);
        }
    }
}
