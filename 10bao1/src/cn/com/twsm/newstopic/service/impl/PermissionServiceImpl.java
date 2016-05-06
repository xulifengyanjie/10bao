package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.PermissionMapper;
import cn.com.twsm.newstopic.service.PermissionService;

@Service
public class PermissionServiceImpl extends BaseServiceImpl implements PermissionService{
	@Resource
	private PermissionMapper permissionMapper;
	
	@Override
	public BaseMapper getMapper() {
		return permissionMapper;
	}
	
}
