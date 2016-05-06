package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.Weibo;
import cn.com.twsm.newstopic.model.WeiboResult;


public interface WeiboMapper extends BaseMapper{
	int insertBatch(List<Weibo> weiboNewsList);

	List<String> selectByCrawlDate(Map<String, Object> whereMap);

	Weibo getByNewsNum(String newsNum);

	List<Weibo> getByNewsNums(List<WeiboResult> result);
}