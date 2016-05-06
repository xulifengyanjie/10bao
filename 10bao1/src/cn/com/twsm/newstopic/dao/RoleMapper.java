package cn.com.twsm.newstopic.dao;

import cn.com.twsm.newstopic.model.Role;

public interface RoleMapper extends BaseMapper{
	Role getPermissionByRoleId(Integer id);
}