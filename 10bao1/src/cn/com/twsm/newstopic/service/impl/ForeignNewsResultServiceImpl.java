package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.ForeignNewsResultMapper;
import cn.com.twsm.newstopic.model.ForeignNewsResult;
import cn.com.twsm.newstopic.service.ForeignNewsResultService;

@Service
public class ForeignNewsResultServiceImpl extends BaseServiceImpl implements ForeignNewsResultService{

	@Resource
	private ForeignNewsResultMapper foreignNewsResultMapper;
	
	@Override
	public BaseMapper getMapper() {
		return foreignNewsResultMapper;
	}
	@Override
	public int insertBatch(List<ForeignNewsResult> records) {
		return foreignNewsResultMapper.insertBatch(records);
	}
	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return foreignNewsResultMapper.selectByCrawlDate(whereMap);
	}

}
