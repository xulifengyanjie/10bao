package cn.com.twsm.newstopic.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.UserMapper;
import cn.com.twsm.newstopic.dao.UserRoleRelationMapper;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.model.UserRoleRelation;
import cn.com.twsm.newstopic.service.UserService;

/**
 * 
 * @author cp
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private UserRoleRelationMapper userRoleRelationMapper;
	
	@Override
	public BaseMapper getMapper() {
		return userMapper;
	}

	@Override
	public User getByLoginName(String loginName) {
		return userMapper.getByLoginName(loginName);
	}

	@Override
	public User getByLoginNameAndPwd(String loginName, String password) {
		return userMapper.getByLoginNameAndPwd(loginName, password);
	}

	@Override
	public User getUserPermissions(String id) {
		return userMapper.getUserPermissions(id);
	}

	@Override
	public List<User> queryUserNameList() {
		return userMapper.queryUserNameList();
	}

	@Override
	@Transactional
	public void addUser(User user, List<UserRoleRelation> relations) {
		userMapper.insert(user);
		for(UserRoleRelation r : relations){
			r.setUserId(user.getId());
		}
		if(relations.size() > 0){
			userRoleRelationMapper.insertBatch(relations);
		}
	}

	@Override
	@Transactional
	public void editUser(User user, List<UserRoleRelation> relations) {
		
		List<Integer> userIds = new ArrayList<Integer>();
		userIds.add(user.getId());
		
		userMapper.updateByPrimaryKey(user);
		
		userRoleRelationMapper.deleteByUserIds(userIds);
		
		if(relations.size() > 0){
			userRoleRelationMapper.insertBatch(relations);
		}
		
		
	}

	@Override
	@Transactional
	public void delUser(List<Integer> userIds) {
		userMapper.deleteByIds(userIds);
		
		userRoleRelationMapper.deleteByUserIds(userIds);
	}
		
	
}
