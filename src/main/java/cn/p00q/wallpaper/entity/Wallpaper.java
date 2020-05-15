package cn.p00q.wallpaper.entity;

import java.util.Date;
import javax.persistence.*;

/**
 * @author DanBai
 */
public class Wallpaper {
    public static String File_PREFIX="zip";
    /**
     * 壁纸id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 壁纸名字
     */
    private String name;

    /**
     * 上传者
     */
    private String uploader;

    /**
     * 上传时间
     */
    @Column(name = "upload_time")
    private Date uploadTime;

    /**
     * 作者
     */
    private String author;

    /**
     * 审核
     */
    private Boolean audit;

    /**
     * 壁纸链接地址
     */
    private String url;

    /**
     * 壁纸介绍
     */
    private String introduction;

    /**
     * 截图url
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 获取壁纸id
     *
     * @return id - 壁纸id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置壁纸id
     *
     * @param id 壁纸id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取壁纸名字
     *
     * @return name - 壁纸名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置壁纸名字
     *
     * @param name 壁纸名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取上传者
     *
     * @return uploader - 上传者
     */
    public String getUploader() {
        return uploader;
    }

    /**
     * 设置上传者
     *
     * @param uploader 上传者
     */
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    /**
     * 获取上传时间
     *
     * @return upload_time - 上传时间
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * 设置上传时间
     *
     * @param uploadTime 上传时间
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * 获取作者
     *
     * @return author - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取审核
     *
     * @return audit - 审核
     */
    public Boolean getAudit() {
        return audit;
    }

    /**
     * 设置审核
     *
     * @param audit 审核
     */
    public void setAudit(Boolean audit) {
        this.audit = audit;
    }

    /**
     * 获取壁纸链接地址
     *
     * @return url - 壁纸链接地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置壁纸链接地址
     *
     * @param url 壁纸链接地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取壁纸介绍
     *
     * @return introduction - 壁纸介绍
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置壁纸介绍
     *
     * @param introduction 壁纸介绍
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取截图url
     *
     * @return img_url - 截图url
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置截图url
     *
     * @param imgUrl 截图url
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}