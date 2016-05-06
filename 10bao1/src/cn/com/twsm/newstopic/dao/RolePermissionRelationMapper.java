package cn.com.twsm.newstopic.dao;

import java.util.List;

import cn.com.twsm.newstopic.model.RolePermissionRelation;


public interface RolePermissionRelationMapper extends BaseMapper{
	void insertBatch(List<RolePermissionRelation> rolePermissionRelations);
	
	void deleteByRoleIds(List<Integer> roleIds);
}