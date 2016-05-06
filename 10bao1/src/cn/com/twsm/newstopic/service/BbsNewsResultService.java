package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.BbsNewsResult;

public interface BbsNewsResultService extends BaseService {
	int insertBatch(List<BbsNewsResult> records);

	List<String> selectByCrawlDate(Map<String, Object> whereMap);
}
