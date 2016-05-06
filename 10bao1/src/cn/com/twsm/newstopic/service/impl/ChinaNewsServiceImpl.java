package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.CatalogMapper;
import cn.com.twsm.newstopic.dao.ChinaNewsMapper;
import cn.com.twsm.newstopic.dao.ChinaNewsResultMapper;
import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.ChinaNews;
import cn.com.twsm.newstopic.model.ChinaNewsResult;
import cn.com.twsm.newstopic.service.ChinaNewsService;

@Service("chinaNewsService")
public class ChinaNewsServiceImpl extends BaseServiceImpl implements ChinaNewsService{
	@Resource
	private ChinaNewsMapper chinaNewsMapper;
	
	@Resource
	private CatalogMapper catalogMapper;
	
	@Resource
	private ChinaNewsResultMapper chinaNewsResultMapper;
	
	@Override
	public BaseMapper getMapper() {
		return chinaNewsMapper;
	}

	@Transactional
	public int batchAddChinaNews(List<ChinaNews> chinaNewsList,Catalog catalog,List<ChinaNewsResult> newsResultList) {
		catalogMapper.updateByPrimaryKey(catalog);
		if(chinaNewsList.size() != 0){
			chinaNewsMapper.insertBatch(chinaNewsList);	
		}
		if(newsResultList.size() != 0){
			chinaNewsResultMapper.insertBatch(newsResultList);
		}
		return 0;
	}
	
	@Transactional
	public int batchAddChinaNews(List<ChinaNews> chinaNewsList, Catalog catalog) {
		catalogMapper.updateByPrimaryKey(catalog);
		return chinaNewsMapper.insertBatch(chinaNewsList);
	}

	@Override
	public List<String> selectByCrawlDate(Map<String,Object> whereMap) {
		return chinaNewsMapper.selectByCrawlDate(whereMap);
	}

	@Override
	public ChinaNews getByNewsNum(String newsNum) {
		return chinaNewsMapper.getByNewsNum(newsNum);
	}

	@Override
	public List<ChinaNews> getByNewsNums(List<ChinaNewsResult> result) {
		return chinaNewsMapper.getByNewsNums(result);
	}

	@Override
	public List<ChinaNews> newsSourceCount() {
		return chinaNewsMapper.newsSourceCount();
	}
}
