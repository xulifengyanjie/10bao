package cn.com.twsm.newstopic.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.NoticeMapper;
import cn.com.twsm.newstopic.service.NoticeService;

@Service
public class NoticeServiceImpl extends BaseServiceImpl implements NoticeService {

	@Resource
	private NoticeMapper noticeMapper;

	@Override
	public BaseMapper getMapper() {
		return noticeMapper;
	}

	@Override
	public int delsNotice(List<Integer> ids) {
		return noticeMapper.deleteByIds(ids);
	}

}
