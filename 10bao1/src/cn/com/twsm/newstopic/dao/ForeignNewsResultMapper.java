package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.ForeignNewsResult;


public interface ForeignNewsResultMapper extends BaseMapper{

	int insertBatch(List<ForeignNewsResult> records);
    
    List<String> selectByCrawlDate(Map<String,Object> whereMap);
}