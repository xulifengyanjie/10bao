package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.ChinaNews;
import cn.com.twsm.newstopic.model.ChinaNewsResult;

public interface ChinaNewsMapper extends BaseMapper {

	int insertBatch(List<ChinaNews> records);

	List<String> selectByCrawlDate(Map<String, Object> whereMap);

	ChinaNews getByNewsNum(String newsNum);

	List<ChinaNews> getByNewsNums(List<ChinaNewsResult> result);
	
	List<ChinaNews> newsSourceCount();
}