package cn.com.twsm.newstopic.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.CollectionTypeMapper;
import cn.com.twsm.newstopic.service.CollectionTypeService;

@Service
public class CollectionTypeServiceImpl extends BaseServiceImpl implements CollectionTypeService {

	@Resource
	private CollectionTypeMapper collectionTypeMapper;
	
	@Override
	public BaseMapper getMapper() {
		return collectionTypeMapper;
	}


}
