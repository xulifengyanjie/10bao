package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.ChinaNews;
import cn.com.twsm.newstopic.model.ChinaNewsResult;

public interface ChinaNewsService extends BaseService{
	
	int batchAddChinaNews(List<ChinaNews> chinaNewsList,Catalog catalog,List<ChinaNewsResult> newsResultList);
	
	int batchAddChinaNews(List<ChinaNews> chinaNewsList,Catalog catalog);
	
	List<String> selectByCrawlDate(Map<String,Object> whereMap);
	
	ChinaNews getByNewsNum(String newsNum);
	
	List<ChinaNews> getByNewsNums(List<ChinaNewsResult> result);
	
	List<ChinaNews> newsSourceCount();
}
