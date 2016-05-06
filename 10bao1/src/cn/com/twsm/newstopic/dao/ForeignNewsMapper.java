package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.ForeignNews;
import cn.com.twsm.newstopic.model.ForeignNewsResult;


public interface ForeignNewsMapper extends BaseMapper{
	int insertBatch(List<ForeignNews> foreignNews);
	
	List<String> selectByCrawlDate(Map<String,Object> whereMap);
	
	ForeignNews getByNewsNum(String newsNum);
	
	List<ForeignNews> getByNewsNums(List<ForeignNewsResult> result);
	
	List<ForeignNews> newsSourceCount();
}