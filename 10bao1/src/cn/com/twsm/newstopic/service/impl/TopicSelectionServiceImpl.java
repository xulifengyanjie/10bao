package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.TopicSelectionMapper;
import cn.com.twsm.newstopic.service.TopicSelectionService;

@Service
public class TopicSelectionServiceImpl extends BaseServiceImpl implements TopicSelectionService {

	@Resource
	private TopicSelectionMapper topicSelectionMapper;

	@Override
	public BaseMapper getMapper() {
		return topicSelectionMapper;
	}

}
