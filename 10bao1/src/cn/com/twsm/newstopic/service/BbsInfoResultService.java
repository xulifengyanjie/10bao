package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.BbsInfoResult;

public interface BbsInfoResultService extends BaseService{

	int insertBatch(List<BbsInfoResult> records);
	
	List<String> selectByCrawlDate(Map<String,Object> whereMap);
}
