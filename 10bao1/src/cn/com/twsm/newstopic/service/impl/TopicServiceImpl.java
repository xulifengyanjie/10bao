package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.TopicMapper;
import cn.com.twsm.newstopic.service.TopicService;

@Service
public class TopicServiceImpl extends BaseServiceImpl implements TopicService {

	@Resource
	private TopicMapper topicMapper;

	@Override
	public BaseMapper getMapper() {
		return topicMapper;
	}

}
