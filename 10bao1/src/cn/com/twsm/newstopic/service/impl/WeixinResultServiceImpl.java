package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.WeixinResultMapper;
import cn.com.twsm.newstopic.model.WeixinResult;
import cn.com.twsm.newstopic.service.WeixinResultService;

@Service
public class WeixinResultServiceImpl extends BaseServiceImpl implements WeixinResultService{
	@Resource
	private WeixinResultMapper weixinResultMapper;
	
	@Override
	public int insertBatch(List<WeixinResult> records) {
		return weixinResultMapper.insertBatch(records);
	}

	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return weixinResultMapper.selectByCrawlDate(whereMap);
	}

	@Override
	public BaseMapper getMapper() {
		return weixinResultMapper;
	}
	
}
