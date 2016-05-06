package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.DepartmentMapper;
import cn.com.twsm.newstopic.service.DepartmentService;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl implements DepartmentService {

	@Resource
	private DepartmentMapper departmentMapper;
	
	@Override
	public BaseMapper getMapper() {
		return departmentMapper;
	}

}
