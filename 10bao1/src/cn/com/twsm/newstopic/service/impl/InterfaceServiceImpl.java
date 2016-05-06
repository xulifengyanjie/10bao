package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.InterfaceMapper;
import cn.com.twsm.newstopic.service.InterfaceService;

@Service
public class InterfaceServiceImpl extends BaseServiceImpl implements InterfaceService {

	@Resource
	private InterfaceMapper interfaceMapper;
	
	@Override
	public BaseMapper getMapper() {
		return interfaceMapper;
	}

}
