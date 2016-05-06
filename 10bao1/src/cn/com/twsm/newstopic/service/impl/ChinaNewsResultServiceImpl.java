package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.ChinaNewsResultMapper;
import cn.com.twsm.newstopic.model.ChinaNewsResult;
import cn.com.twsm.newstopic.service.ChinaNewsResultService;

@Service
public class ChinaNewsResultServiceImpl extends BaseServiceImpl implements ChinaNewsResultService{

	@Resource
	private ChinaNewsResultMapper chinaNewsResultMapper;
	@Override
	public int insertBatch(List<ChinaNewsResult> records) {
		return chinaNewsResultMapper.insertBatch(records);
	}

	@Override
	public BaseMapper getMapper() {
		return chinaNewsResultMapper;
	}

	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return chinaNewsResultMapper.selectByCrawlDate(whereMap);
	}
	
}
