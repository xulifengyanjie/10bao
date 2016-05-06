package cn.com.twsm.newstopic.dao;

import java.util.List;

import cn.com.twsm.newstopic.model.IndustryNews;

public interface IndustryNewsMapper extends BaseMapper{
	public void insertBatch(List<IndustryNews> records);
}