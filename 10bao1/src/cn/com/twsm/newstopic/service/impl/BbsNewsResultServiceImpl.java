package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.BbsNewsResultMapper;
import cn.com.twsm.newstopic.model.BbsNewsResult;
import cn.com.twsm.newstopic.service.BbsNewsResultService;

@Service
public class BbsNewsResultServiceImpl extends BaseServiceImpl implements BbsNewsResultService{
	@Resource
	private BbsNewsResultMapper bbsNewsResultMapper;
	
	@Override
	public BaseMapper getMapper() {
		return bbsNewsResultMapper;
	}

	@Override
	public int insertBatch(List<BbsNewsResult> records) {
		return bbsNewsResultMapper.insertBatch(records);
	}

	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return bbsNewsResultMapper.selectByCrawlDate(whereMap);
	}

}
