package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.WeiboResult;

public interface WeiboResultService extends BaseService{
	int insertBatch(List<WeiboResult> records);

	List<String> selectByCrawlDate(Map<String, Object> whereMap);
}
