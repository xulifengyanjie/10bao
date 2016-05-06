package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.BbsInfoResult;


public interface BbsInfoResultMapper extends BaseMapper{

	int insertBatch(List<BbsInfoResult> records);
    
    List<String> selectByCrawlDate(Map<String,Object> whereMap);
}