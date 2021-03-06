package com.zj.o2o.entity;


import java.util.Date;

/**
 * 用户详细信息实体
 *
 * @author zj
 * @create 2019-01-18 13:13
 */
public class PersonInfo {
    private Long userId;
    private String name;
    // 用户图片头像地址
    private String profileImg;
    private String email;
    private String gender;
    private String phone;
    // 用户状态
    private Integer enableStatus;
    // 用户身份标识 1.代表顾客 2.代表店家 3.代表超级管理员
    private Integer userType;
    // 用户创建时间
    private Date createTime;
    // 用户最后编辑时间
    private Date lastEditTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
