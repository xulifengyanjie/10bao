package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.EditLibMapper;
import cn.com.twsm.newstopic.service.EditNewsService;

@Service
public class EditNewsServiceImpl extends BaseServiceImpl implements EditNewsService {
	@Resource
	private EditLibMapper editLibMapper;
	
	@Override
	public BaseMapper getMapper() {
		return editLibMapper;
	}
	
}
