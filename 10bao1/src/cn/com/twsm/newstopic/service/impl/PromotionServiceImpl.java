package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.PromotionMapper;
import cn.com.twsm.newstopic.service.PromotionService;
@Service
public class PromotionServiceImpl extends BaseServiceImpl implements
		PromotionService {

	@Resource
	private PromotionMapper promotionMapper;
	
	@Override
	public BaseMapper getMapper() {
		return promotionMapper;
	}

}
