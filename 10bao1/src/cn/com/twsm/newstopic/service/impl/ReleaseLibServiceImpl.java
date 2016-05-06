package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.ReleaseLibMapper;
import cn.com.twsm.newstopic.service.ReleaseLibService;

@Service
public class ReleaseLibServiceImpl extends BaseServiceImpl implements ReleaseLibService{
	@Resource
	private ReleaseLibMapper releaseLibMapper;
	
	@Override
	public BaseMapper getMapper() {
		return releaseLibMapper;
	}
	
}
