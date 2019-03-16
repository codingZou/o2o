package com.zj.o2o.service.impl;

import com.zj.o2o.dao.LocalAuthDao;
import com.zj.o2o.dao.PersonInfoDao;
import com.zj.o2o.dto.ImageHolder;
import com.zj.o2o.dto.LocalAuthExecution;
import com.zj.o2o.entity.LocalAuth;
import com.zj.o2o.entity.PersonInfo;
import com.zj.o2o.enums.LocalAuthStateEnum;
import com.zj.o2o.service.LocalAuthService;
import com.zj.o2o.util.ImageUtil;
import com.zj.o2o.util.MD5;
import com.zj.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zj
 * @create 2019-03-15 16:16
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService {
    @Autowired
    private LocalAuthDao localAuthDao;
    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public LocalAuth getLocalAuthByUserNameAndPwd(String username, String password) {
        return localAuthDao.queryLocalAuthByUserNameAndPwd(username, password);
    }

    @Override
    @Transactional
    public LocalAuthExecution register(LocalAuth localAuth, ImageHolder profileImg) throws RuntimeException {
        if (localAuth == null || localAuth.getPassword() == null
                || localAuth.getUserName() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        try {
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            if (localAuth.getPersonInfo() != null && localAuth.getPersonInfo().getUserId() == null) {
                localAuth.getPersonInfo().setCreateTime(new Date());
                localAuth.getPersonInfo().setLastEditTime(new Date());
                localAuth.getPersonInfo().setEnableStatus(1);
                try {
                    PersonInfo personInfo = localAuth.getPersonInfo();
                    int effectedNum = personInfoDao.insertPersonInfo(personInfo);
                    localAuth.setUserId(personInfo.getUserId());
                    if (effectedNum <= 0) {
                        throw new RuntimeException("添加用户信息失败");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("insertPersonInfo error: "
                            + e.getMessage());
                }

            }
            int effectedNum = localAuthDao.insertLocalAuth(localAuth);
            if (effectedNum <= 0) {
                throw new RuntimeException("注册账号失败");
            } else {
                // 添加图片
                if (profileImg != null) {
                    try {
                        addProfileImg(localAuth, profileImg);
                    } catch (Exception e) {
                        throw new RuntimeException("addUserProfileImg error: " + e.getMessage());
                    }
                    // 更新用户信息
                    personInfoDao.updatePersonInfo(localAuth.getPersonInfo());
                } else {
                    return new LocalAuthExecution(LocalAuthStateEnum.NULL_IGM);
                }

            }
        } catch (RuntimeException e) {
            throw new RuntimeException("insertLocalAuth error: " + e.getMessage());
        }
        return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
    }

    @Transactional
    @Override
    public LocalAuthExecution modifyLocalAuth(LocalAuth localAuth, String newPassword) {
        if (localAuth != null && localAuth.getUserId() != null && localAuth.getUserName() != null
                && localAuth.getPassword() != null && newPassword != null) {
            localAuth.setLastEditTime(new Date());
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int effectedNUm = 0;
            try {
                // 更新前先看用户输入的账号密码是否有误
                LocalAuth userAuth = localAuthDao.queryLocalAuthByUserNameAndPwd(localAuth.getUserName(),
                        localAuth.getPassword());
                if (userAuth != null) {
                    // 判断新密码是否与旧密码相同
                    if (!userAuth.getPassword().equals(MD5.getMd5(newPassword))) {
                        effectedNUm = localAuthDao.updateLocalAuth(localAuth, MD5.getMd5(newPassword));
                        if (effectedNUm <= 0) {
                            throw new RuntimeException("更新密码失败");
                        }
                        return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
                    } else {
                        return new LocalAuthExecution(LocalAuthStateEnum.SAME_PSW);
                    }
                } else {
                    return new LocalAuthExecution(LocalAuthStateEnum.LOGINFAIL);
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }

    /**
     * 添加头像图片
     *
     * @param localAuth
     * @param profileImg
     */
    private void addProfileImg(LocalAuth localAuth, ImageHolder profileImg) {
        String dest = PathUtil.getUserImagePath(localAuth.getPersonInfo().getUserId());
        String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);
        localAuth.getPersonInfo().setProfileImg(profileImgAddr);
    }
}
