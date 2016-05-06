package cn.com.twsm.newstopic.service.impl;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.InterviewMapper;
import cn.com.twsm.newstopic.service.InterviewService;

@Service
public class InterviewServiceImpl extends BaseServiceImpl implements InterviewService{

	@Resource
	private InterviewMapper interviewMapper;

	@Override
	public BaseMapper getMapper() {
		return interviewMapper;
	}

}
