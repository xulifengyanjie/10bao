package cn.com.twsm.newstopic.dao;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.Weixin;
import cn.com.twsm.newstopic.model.WeixinResult;

public interface WeixinMapper extends BaseMapper{
	int insertBatch(List<Weixin> weixinNewsList);

	List<String> selectByCrawlDate(Map<String, Object> whereMap);

	Weixin getByNewsNum(String newsNum);

	List<Weixin> getByNewsNums(List<WeixinResult> result);
}