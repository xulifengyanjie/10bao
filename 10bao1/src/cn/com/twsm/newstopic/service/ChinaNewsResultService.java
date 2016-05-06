package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.ChinaNewsResult;

public interface ChinaNewsResultService extends BaseService{
	int insertBatch(List<ChinaNewsResult> records);
	
	List<String> selectByCrawlDate(Map<String,Object> whereMap);
}
