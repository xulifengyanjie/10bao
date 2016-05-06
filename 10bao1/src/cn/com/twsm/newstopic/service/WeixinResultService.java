package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.WeixinResult;

public interface WeixinResultService extends BaseService {
	int insertBatch(List<WeixinResult> records);

	List<String> selectByCrawlDate(Map<String, Object> whereMap);
}
