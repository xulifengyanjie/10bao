package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.BbsNewsResult;

public interface BbsNewsResultMapper extends BaseMapper {
	int insertBatch(List<BbsNewsResult> records);

	List<String> selectByCrawlDate(Map<String, Object> whereMap);
}