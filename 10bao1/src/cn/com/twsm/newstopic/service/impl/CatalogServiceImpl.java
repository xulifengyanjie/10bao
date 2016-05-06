package cn.com.twsm.newstopic.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;






import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.CatalogMapper;
import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.service.CatalogService;

@Service("catalogService")
public class CatalogServiceImpl extends BaseServiceImpl implements CatalogService{
	@Resource
	private CatalogMapper catalogMapper;

	@Override
	public BaseMapper getMapper() {
		return catalogMapper;
	}

	@Override
	public Catalog getByCatalogName(String catalogName) {
		return catalogMapper.getByCatalogName(catalogName);
	}

	@Override
	public Catalog getByCatalogCode(String catalogCode) {
		return catalogMapper.getByCatalogCode(catalogCode);
	}
}
