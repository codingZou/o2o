package com.zj.o2o.service;

import com.zj.o2o.dto.ImageHolder;
import com.zj.o2o.dto.LocalAuthExecution;
import com.zj.o2o.entity.LocalAuth;

/**
 * @author zj
 * @create 2019-03-15 16:05
 */
public interface LocalAuthService {

    LocalAuth getLocalAuthByUserNameAndPwd(String username, String password);

    LocalAuthExecution register(LocalAuth localAuth, ImageHolder thumbnail) throws RuntimeException;

    LocalAuthExecution modifyLocalAuth(LocalAuth localAuth, String newPassword);
}
