package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.CatalogMapper;
import cn.com.twsm.newstopic.dao.WeixinMapper;
import cn.com.twsm.newstopic.dao.WeixinResultMapper;
import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.Weixin;
import cn.com.twsm.newstopic.model.WeixinResult;
import cn.com.twsm.newstopic.service.WeixinService;

@Service
public class WeixinServiceImpl extends BaseServiceImpl implements WeixinService{
	@Resource
	private WeixinMapper weixinMapper;
	
	@Resource
	private CatalogMapper catalogMapper;
	
	@Resource
	private WeixinResultMapper weixinResultMapper;
	
	@Override
	public BaseMapper getMapper() {
		return weixinMapper;
	}

	@Override
	public int batchAddWeixinNews(List<Weixin> weixinNewsList, Catalog catalog,
			List<WeixinResult> resultList) {
		if(weixinNewsList.size() != 0){
			weixinMapper.insertBatch(weixinNewsList);
		}
		if(resultList.size() != 0){
			weixinResultMapper.insertBatch(resultList);
		}
		catalogMapper.updateByPrimaryKey(catalog);
		
		return 0;
	}

	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return weixinMapper.selectByCrawlDate(whereMap);
	}

	@Override
	public Weixin getByNewsNum(String newsNum) {
		return weixinMapper.getByNewsNum(newsNum);
	}

	@Override
	public List<Weixin> getByNewsNums(List<WeixinResult> result) {
		return weixinMapper.getByNewsNums(result);
	}
	
}
