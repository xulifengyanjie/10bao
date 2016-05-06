package cn.com.twsm.newstopic.dao;

import java.util.List;

import cn.com.twsm.newstopic.model.UserRoleRelation;


public interface UserRoleRelationMapper extends BaseMapper{
    void insertBatch(List<UserRoleRelation> relations);
    
    void deleteByUserIds(List<Integer> userIds);
}