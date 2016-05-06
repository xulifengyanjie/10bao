package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.BbsInfo;
import cn.com.twsm.newstopic.model.BbsInfoResult;
import cn.com.twsm.newstopic.model.Catalog;

public interface BbsInfoService extends BaseService {
	int batchAddBbsInfo(List<BbsInfo> bbsInfo,Catalog catalog,List<BbsInfoResult> bbsInfoResultList);
	
	List<String> selectByCrawlDate(Map<String,Object> whereMap);
	
	BbsInfo getByNewsNum(String newsNum);
	
	List<BbsInfo> getByNewsNums(List<BbsInfoResult> result);
}
