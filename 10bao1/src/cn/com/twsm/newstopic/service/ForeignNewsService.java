package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.ForeignNews;
import cn.com.twsm.newstopic.model.ForeignNewsResult;

public interface ForeignNewsService extends BaseService{
	int batchAddForeignNews(List<ForeignNews> foreignNews,Catalog catalog,List<ForeignNewsResult> newsResultList);
	
	List<String> selectByCrawlDate(Map<String,Object> whereMap);
	
	ForeignNews getByNewsNum(String newsNum);
	
	List<ForeignNews> getByNewsNums(List<ForeignNewsResult> result);

	List<ForeignNews> newsSourceCount();
}
