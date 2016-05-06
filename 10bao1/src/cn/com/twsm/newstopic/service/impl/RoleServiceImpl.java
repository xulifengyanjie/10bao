package cn.com.twsm.newstopic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.RoleMapper;
import cn.com.twsm.newstopic.dao.RolePermissionRelationMapper;
import cn.com.twsm.newstopic.model.Role;
import cn.com.twsm.newstopic.model.RolePermissionRelation;
import cn.com.twsm.newstopic.service.RoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService{
	@Resource
	private RoleMapper roleMapper;
	
	@Resource
	private RolePermissionRelationMapper rolePermissionRelationMapper;
	
	@Override
	public BaseMapper getMapper() {
		return roleMapper;
	}

	@Override
	public Role getPermissionByRoleId(Integer id) {
		return roleMapper.getPermissionByRoleId(id);
	}

	@Override
	@Transactional
	public void addRole(Role role, List<RolePermissionRelation> rolePermissionRelations) {
		roleMapper.insert(role);
		for(RolePermissionRelation relation : rolePermissionRelations){
			relation.setRoleId(role.getId());
		}
		if(rolePermissionRelations.size() > 0){
			rolePermissionRelationMapper.insertBatch(rolePermissionRelations);
		}
		
	}
	@Override
	@Transactional
	public void editRole(Role role, List<RolePermissionRelation> rolePermissionRelations,List<Integer> roleIds) {
		roleMapper.updateByPrimaryKey(role);
		rolePermissionRelationMapper.deleteByRoleIds(roleIds);
		if(rolePermissionRelations.size() > 0){
			rolePermissionRelationMapper.insertBatch(rolePermissionRelations);
		}
	}

	@Override
	@Transactional
	public void delRoles(List<Integer> roleIds) {
		roleMapper.deleteByIds(roleIds);
		rolePermissionRelationMapper.deleteByRoleIds(roleIds);
	}
	
	
}
