package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.Topic;

public interface TopicMapper extends BaseMapper{

	List<Topic> queryTopicNameList();

	List<Topic> queryTopicList(Map<String, Object> whereMap);
}