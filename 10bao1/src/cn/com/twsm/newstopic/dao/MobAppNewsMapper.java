package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.MobAppNews;


public interface MobAppNewsMapper extends BaseMapper{

	List<MobAppNews> getHotByWhere(Map<String, Object> whereMap);

}