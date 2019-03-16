package com.zj.o2o.dao;

import com.zj.o2o.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

/**
 * @author zj
 * @create 2019-03-15 15:28
 */
public interface LocalAuthDao {
    /**
     * 登录查询
     *
     * @param username
     * @param password
     * @return
     */
    LocalAuth queryLocalAuthByUserNameAndPwd(@Param("userName") String username,
                                             @Param("password") String password);

    /**
     * 注册平台账号
     *
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     * 修改账户密码
     *
     * @param localAuth
     * @param newPassword
     * @return
     */
    int updateLocalAuth(@Param("localAuth") LocalAuth localAuth,
                        @Param("newPassword") String newPassword);
}
