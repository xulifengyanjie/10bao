package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.BbsNews;
import cn.com.twsm.newstopic.model.BbsNewsResult;

public interface BbsNewsMapper extends BaseMapper {
	
	int insertBatch(List<BbsNews> bbsNewsList);

	List<String> selectByCrawlDate(Map<String, Object> whereMap);

	BbsNews getByNewsNum(String newsNum);

	List<BbsNews> getByNewsNums(List<BbsNewsResult> result);
}