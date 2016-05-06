package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.MobAppNews;

public interface MobAppNewsService extends BaseService{

	List<MobAppNews> getHotByWhere(Map<String, Object> whereMap);

	
}
