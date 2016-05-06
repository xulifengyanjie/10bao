package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.CrawlDataMapper;
import cn.com.twsm.newstopic.service.DataCollectionService;

@Service
public class DataCollectionServicempl extends BaseServiceImpl implements DataCollectionService {

	@Resource
	private CrawlDataMapper crawlDataMapper;
	@Override
	public BaseMapper getMapper() {
		return crawlDataMapper;
	}


}
