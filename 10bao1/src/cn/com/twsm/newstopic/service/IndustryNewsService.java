package cn.com.twsm.newstopic.service;

import java.util.List;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.IndustryNews;

public interface IndustryNewsService extends BaseService{
	public void batchAddIndustryNews(List<IndustryNews> records,Catalog catalog);
}
