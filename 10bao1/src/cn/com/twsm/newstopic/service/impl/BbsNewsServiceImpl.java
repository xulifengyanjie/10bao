package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.BbsNewsMapper;
import cn.com.twsm.newstopic.dao.BbsNewsResultMapper;
import cn.com.twsm.newstopic.dao.CatalogMapper;
import cn.com.twsm.newstopic.model.BbsNews;
import cn.com.twsm.newstopic.model.BbsNewsResult;
import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.service.BbsNewsService;

@Service
public class BbsNewsServiceImpl extends BaseServiceImpl implements BbsNewsService {
	@Resource
	private BbsNewsMapper bbsNewsMapper;
	
	@Resource
	private CatalogMapper catalogMapper;
	
	@Resource
	private BbsNewsResultMapper bbsNewsResultMapper;

	@Override
	public BaseMapper getMapper() {
		return bbsNewsMapper;
	}

	@Transactional
	public int batchAddBbsNews(List<BbsNews> bbsNewsList, Catalog catalog,
			List<BbsNewsResult> newsResultList) {
		catalogMapper.updateByPrimaryKey(catalog);
		if(bbsNewsList.size() != 0){
			bbsNewsMapper.insertBatch(bbsNewsList);
		}
		if(newsResultList.size() != 0){
			bbsNewsResultMapper.insertBatch(newsResultList);
		}
		return 0;
	}

	@Override
	public int batchAddBbsNews(List<BbsNews> bbsNewsList, Catalog catalog) {
		catalogMapper.updateByPrimaryKey(catalog);
		bbsNewsMapper.insertBatch(bbsNewsList);
		return 0;
	}

	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return bbsNewsMapper.selectByCrawlDate(whereMap);
	}

	@Override
	public BbsNews getByNewsNum(String newsNum) {
		return bbsNewsMapper.getByNewsNum(newsNum);
	}

	@Override
	public List<BbsNews> getByNewsNums(List<BbsNewsResult> result) {
		return bbsNewsMapper.getByNewsNums(result);
	}

}
