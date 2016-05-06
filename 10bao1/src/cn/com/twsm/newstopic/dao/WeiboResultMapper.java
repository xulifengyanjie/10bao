package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.WeiboResult;


public interface WeiboResultMapper extends BaseMapper{
	int insertBatch(List<WeiboResult> records);

	List<String> selectByCrawlDate(Map<String, Object> whereMap);
}