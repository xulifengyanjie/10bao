package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.WeiboResultMapper;
import cn.com.twsm.newstopic.model.WeiboResult;
import cn.com.twsm.newstopic.service.WeiboResultService;

@Service
public class WeiboResultServiceImpl extends BaseServiceImpl implements WeiboResultService{
	@Resource
	private WeiboResultMapper weiboResultMapper;
	
	@Override
	public BaseMapper getMapper() {
		return weiboResultMapper;
	}

	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return weiboResultMapper.selectByCrawlDate(whereMap);
	}

	@Override
	public int insertBatch(List<WeiboResult> records) {
		return weiboResultMapper.insertBatch(records);
	}
	
}
