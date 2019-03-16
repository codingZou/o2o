package com.zj.o2o.dao;

import com.zj.o2o.entity.PersonInfo;

public interface PersonInfoDao {

    /**
     * @param personInfo
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);

    /**
     * @param personInfo
     * @return
     */
    int updatePersonInfo(PersonInfo personInfo);
    /* *//**
     * @param personInfoCondition
     * @param rowIndex
     * @param pageSize
     * @return
     *//*
    List<PersonInfo> queryPersonInfoList(
            @Param("personInfoCondition") PersonInfo personInfoCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    *//**
     * @param personInfoCondition
     * @return
     *//*
    int queryPersonInfoCount(
            @Param("personInfoCondition") PersonInfo personInfoCondition);

    *//**
     * @param userId
     * @return
     *//*
    PersonInfo queryPersonInfoById(long userId);


    *//**
     * @param userId
     * @return
     *//*
    int deletePersonInfo(long userId);*/
}
