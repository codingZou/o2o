package com.zj.o2o.dao;

import com.zj.o2o.BaseTest;
import com.zj.o2o.entity.LocalAuth;
import com.zj.o2o.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author zj
 * @create 2019-03-15 16:28
 */
public class LocalAuthDaoTest extends BaseTest {
    @Autowired
    private LocalAuthDao localAuthDao;

    @Test
    public void queryLocalAuthByUserNameAndPwd() {
        String username = "admin";
        String password = "admin";
        LocalAuth localAuth = localAuthDao.queryLocalAuthByUserNameAndPwd(username, password);
        assertEquals("张三", localAuth.getPersonInfo().getName());
    }

    @Test
    public void insertLocalAuth() {
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(12l);

        localAuth.setPersonInfo(personInfo);
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        localAuth.setUserName("admin");
        localAuth.setPassword("admin");
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1, effectedNum);
    }

}