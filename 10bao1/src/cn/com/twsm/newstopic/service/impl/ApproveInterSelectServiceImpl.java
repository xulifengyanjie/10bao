package cn.com.twsm.newstopic.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.ApproveInterSelectMapper;
import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.service.ApproveInterSelectService;

@Service
public class ApproveInterSelectServiceImpl extends BaseServiceImpl implements ApproveInterSelectService {

	@Resource
	private ApproveInterSelectMapper approveInterSelectMapper;
	
	@Override
	public BaseMapper getMapper() {
		return approveInterSelectMapper;
	}


}
