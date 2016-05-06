package cn.com.twsm.newstopic.dao;

import cn.com.twsm.newstopic.model.Catalog;

public interface CatalogMapper extends BaseMapper{
	
	Catalog getByCatalogName(String catalogName);
	
	Catalog getByCatalogCode(String catalogCode);
}