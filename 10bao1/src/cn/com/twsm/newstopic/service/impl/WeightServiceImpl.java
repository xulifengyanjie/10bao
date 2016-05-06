package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.WeightMapper;
import cn.com.twsm.newstopic.service.WeightService;

@Service
public class WeightServiceImpl extends BaseServiceImpl implements WeightService {
	@Resource
	private WeightMapper weightMapper;
	@Override
	public BaseMapper getMapper() {
		return weightMapper;
	}

}
