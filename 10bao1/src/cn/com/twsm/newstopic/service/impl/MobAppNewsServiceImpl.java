package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.MobAppNewsMapper;
import cn.com.twsm.newstopic.model.MobAppNews;
import cn.com.twsm.newstopic.service.MobAppNewsService;

@Service
public class MobAppNewsServiceImpl extends BaseServiceImpl implements MobAppNewsService {
	
	@Resource
	private MobAppNewsMapper mobAppNewsMapper;
	
	@Override
	public BaseMapper getMapper() {
		return mobAppNewsMapper;
	}

	@Override
	public List<MobAppNews> getHotByWhere(Map<String, Object> whereMap) {
		return mobAppNewsMapper.getHotByWhere(whereMap);
	}
	
}
