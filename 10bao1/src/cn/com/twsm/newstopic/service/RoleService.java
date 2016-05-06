package cn.com.twsm.newstopic.service;

import java.util.List;

import cn.com.twsm.newstopic.model.Role;
import cn.com.twsm.newstopic.model.RolePermissionRelation;

public interface RoleService extends BaseService{
	Role getPermissionByRoleId(Integer id);
	
	void addRole(Role role,List<RolePermissionRelation> rolePermissionRelations);
	
	void editRole(Role role,List<RolePermissionRelation> rolePermissionRelations,List<Integer> roleIds);
	
	void delRoles(List<Integer> roleIds);
}
