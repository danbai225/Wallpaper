package cn.p00q.wallpaper.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Email;

/**
 * @author DanBai
 */
public class User {
    public static final int TYPE_VISITOR = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_ASSESSOR = 3;
    public static final int TYPE_ADMIN = 4;

    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Length(min = 3,max = 16,message = "用户名长度在3-16",groups = {Register.class,Login.class})
    private String username;

    /**
     * 用户密码
     */
    @Length(min = 6,max = 16,message = "密码长度在6-16",groups = {Register.class,Login.class})
    private String password;

    /**
     * 用户类型 0未激活 1普通用户 2审核员 3管理员
     */
    private Integer type;

    /**
     * 创建时间
     */
    @Column(name = "creation_time")
    private Date creationTime;

    /**
     * 壁纸url
     */
    private String url;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确",groups = Register.class)
    private String email;

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户类型 0未激活 1普通用户 2审核员 3管理员
     *
     * @return type - 用户类型 0未激活 1普通用户 2审核员 3管理员
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置用户类型 0未激活 1普通用户 2审核员 3管理员
     *
     * @param type 用户类型 0未激活 1普通用户 2审核员 3管理员
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取创建时间
     *
     * @return creation_time - 创建时间
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取壁纸url
     *
     * @return url - 壁纸url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置壁纸url
     *
     * @param url 壁纸url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }
    public interface Register {
    }

    public interface Login {
    }
}
