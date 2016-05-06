package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.Weibo;
import cn.com.twsm.newstopic.model.WeiboResult;

public interface WeiboService extends BaseService{
	int batchAddWeiboNews(List<Weibo> weiboNewsList,Catalog catalog,List<WeiboResult> resultList);
	
	List<String> selectByCrawlDate(Map<String,Object> whereMap);
	
	Weibo getByNewsNum(String newsNum);
	
	List<Weibo> getByNewsNums(List<WeiboResult> result);
}
