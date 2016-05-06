package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.CatalogMapper;
import cn.com.twsm.newstopic.dao.WeiboMapper;
import cn.com.twsm.newstopic.dao.WeiboResultMapper;
import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.Weibo;
import cn.com.twsm.newstopic.model.WeiboResult;
import cn.com.twsm.newstopic.service.WeiboService;

@Service
public class WeiboServiceImpl extends BaseServiceImpl implements WeiboService{
	@Resource
	private WeiboMapper weiboMapper;
	
	@Resource
	private WeiboResultMapper weiboResultMapper;
	
	@Resource
	private CatalogMapper catalogMapper;
	
	@Override
	public BaseMapper getMapper() {
		return weiboMapper;
	}

	@Override
	public int batchAddWeiboNews(List<Weibo> weiboNewsList, Catalog catalog,
			List<WeiboResult> resultList) {
		catalogMapper.updateByPrimaryKey(catalog);
		if(weiboNewsList.size() != 0){
			weiboMapper.insertBatch(weiboNewsList);
		}
		if(resultList.size() != 0){
			weiboResultMapper.insertBatch(resultList);
		}
		return 0;
	}

	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return weiboMapper.selectByCrawlDate(whereMap);
	}

	@Override
	public Weibo getByNewsNum(String newsNum) {
		return weiboMapper.getByNewsNum(newsNum);
	}

	@Override
	public List<Weibo> getByNewsNums(List<WeiboResult> result) {
		return weiboMapper.getByNewsNums(result);
	}
	
}
