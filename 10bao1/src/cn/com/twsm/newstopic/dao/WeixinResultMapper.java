package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.WeixinResult;

public interface WeixinResultMapper extends BaseMapper{
	int insertBatch(List<WeixinResult> records);

	List<String> selectByCrawlDate(Map<String, Object> whereMap);
}