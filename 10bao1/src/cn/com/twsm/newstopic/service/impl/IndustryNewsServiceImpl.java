package cn.com.twsm.newstopic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.CatalogMapper;
import cn.com.twsm.newstopic.dao.IndustryNewsMapper;
import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.IndustryNews;
import cn.com.twsm.newstopic.service.IndustryNewsService;

@Service
public class IndustryNewsServiceImpl extends BaseServiceImpl implements
		IndustryNewsService {
	@Resource
	private IndustryNewsMapper industryNewsMapper;
	
	@Resource
	private CatalogMapper catalogMapper;
	
	@Override
	public BaseMapper getMapper() {
		return industryNewsMapper;
	}

	@Override
	public void batchAddIndustryNews(List<IndustryNews> records,Catalog catalog) {
		industryNewsMapper.insertBatch(records);
		catalogMapper.updateByPrimaryKey(catalog);
	}

}
