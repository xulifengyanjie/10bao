package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.CatalogMapper;
import cn.com.twsm.newstopic.dao.ForeignNewsMapper;
import cn.com.twsm.newstopic.dao.ForeignNewsResultMapper;
import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.ForeignNews;
import cn.com.twsm.newstopic.model.ForeignNewsResult;
import cn.com.twsm.newstopic.service.ForeignNewsService;

@Service
public class ForeignNewsServiceImpl extends BaseServiceImpl implements ForeignNewsService{

	@Resource
	private ForeignNewsMapper foreignNewsMapper;
	
	@Resource
	private ForeignNewsResultMapper foreignNewsResultMapper;
	
	@Resource
	private CatalogMapper catalogMapper;
	
	@Override
	public BaseMapper getMapper() {
		return foreignNewsMapper;
	}

	@Transactional
	public int batchAddForeignNews(List<ForeignNews> foreignNews,
			Catalog catalog, List<ForeignNewsResult> newsResultList) {
		if(foreignNews.size() != 0){
			foreignNewsMapper.insertBatch(foreignNews);
		}
		if(newsResultList.size() != 0){
			foreignNewsResultMapper.insertBatch(newsResultList);
		}
		catalogMapper.updateByPrimaryKey(catalog);
		return 0;
	}

	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return foreignNewsMapper.selectByCrawlDate(whereMap);
	}

	@Override
	public ForeignNews getByNewsNum(String newsNum) {
		return foreignNewsMapper.getByNewsNum(newsNum);
	}

	@Override
	public List<ForeignNews> getByNewsNums(List<ForeignNewsResult> result) {
		return foreignNewsMapper.getByNewsNums(result);
	}

	@Override
	public List<ForeignNews> newsSourceCount() {
		return foreignNewsMapper.newsSourceCount();
	}


}
