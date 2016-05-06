package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.ChinaNewsResult;


public interface ChinaNewsResultMapper extends BaseMapper{
	
	int insertBatch(List<ChinaNewsResult> records);
    
    List<String> selectByCrawlDate(Map<String,Object> whereMap);
}