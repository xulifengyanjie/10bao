package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.ForeignNewsResult;

public interface ForeignNewsResultService extends BaseService{
	
	int insertBatch(List<ForeignNewsResult> records);
	
	List<String> selectByCrawlDate(Map<String,Object> whereMap);
	
}
