package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.BbsNews;
import cn.com.twsm.newstopic.model.BbsNewsResult;
import cn.com.twsm.newstopic.model.Catalog;

public interface BbsNewsService extends BaseService{
	int batchAddBbsNews(List<BbsNews> bbsNewsList,Catalog catalog,List<BbsNewsResult> newsResultList);
	
	int batchAddBbsNews(List<BbsNews> bbsNewsList,Catalog catalog);
	
	List<String> selectByCrawlDate(Map<String,Object> whereMap);
	
	BbsNews getByNewsNum(String newsNum);
	
	List<BbsNews> getByNewsNums(List<BbsNewsResult> result);
}
