package cn.com.twsm.newstopic.service;

import cn.com.twsm.newstopic.model.Catalog;

public interface CatalogService extends BaseService{
	Catalog getByCatalogName(String catalogName);
	
	Catalog getByCatalogCode(String catalogCode);
}
