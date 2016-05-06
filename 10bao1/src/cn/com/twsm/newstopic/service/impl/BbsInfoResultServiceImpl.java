package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.BbsInfoResultMapper;
import cn.com.twsm.newstopic.model.BbsInfoResult;
import cn.com.twsm.newstopic.service.BbsInfoResultService;

public class BbsInfoResultServiceImpl extends BaseServiceImpl implements BbsInfoResultService{

	@Resource
	private BbsInfoResultMapper bbsInfoResultMapper;
	
	@Override
	public int insertBatch(List<BbsInfoResult> records) {
		return bbsInfoResultMapper.insertBatch(records);
	}
	
	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return bbsInfoResultMapper.selectByCrawlDate(whereMap);
	}
	
	@Override
	public BaseMapper getMapper() {
		return bbsInfoResultMapper;
	}

}
