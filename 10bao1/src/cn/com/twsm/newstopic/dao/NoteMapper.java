package cn.com.twsm.newstopic.dao;

import java.util.Map;


public interface NoteMapper extends BaseMapper{

	int deleteByIds(Map<String, Object> map);
}