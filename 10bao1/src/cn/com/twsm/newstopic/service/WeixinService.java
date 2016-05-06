package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.Weixin;
import cn.com.twsm.newstopic.model.WeixinResult;

public interface WeixinService extends BaseService{
	
	int batchAddWeixinNews(List<Weixin> weixinNewsList,Catalog catalog,List<WeixinResult> resultList);
	
	List<String> selectByCrawlDate(Map<String,Object> whereMap);
	
	Weixin getByNewsNum(String newsNum);
	
	List<Weixin> getByNewsNums(List<WeixinResult> result);
}
