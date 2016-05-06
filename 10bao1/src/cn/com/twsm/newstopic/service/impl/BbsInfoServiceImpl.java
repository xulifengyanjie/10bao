package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.BbsInfoMapper;
import cn.com.twsm.newstopic.model.BbsInfo;
import cn.com.twsm.newstopic.model.BbsInfoResult;
import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.service.BbsInfoService;

public class BbsInfoServiceImpl extends BaseServiceImpl implements BbsInfoService{

	@Resource
	private BbsInfoMapper bbsInfoMapper;
	
	@Override
	public BaseMapper getMapper() {
		return bbsInfoMapper;
	}

	@Override
	public int batchAddBbsInfo(List<BbsInfo> bbsInfo, Catalog catalog,
			List<BbsInfoResult> bbsInfoResultList) {
		return bbsInfoMapper.batchAddBbsInfo(bbsInfo, catalog, bbsInfoResultList);
	}

	@Override
	public List<String> selectByCrawlDate(Map<String, Object> whereMap) {
		return bbsInfoMapper.selectByCrawlDate(whereMap);
	}

	@Override
	public BbsInfo getByNewsNum(String newsNum) {
		// TODO Auto-generated method stub
		return bbsInfoMapper.getByNewsNum(newsNum);
	}

	@Override
	public List<BbsInfo> getByNewsNums(List<BbsInfoResult> result) {
		// TODO Auto-generated method stub
		return bbsInfoMapper.getByNewsNums(result);
	}

}
