package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.ApproveArchiveMapper;
import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.service.ApproveArchiveService;

@Service
public class ApproveArchiveServiceImpl extends BaseServiceImpl implements ApproveArchiveService {

	@Resource
	private ApproveArchiveMapper approveArchiveMapper;
	@Override
	public BaseMapper getMapper() {
		return approveArchiveMapper;
	}


}
