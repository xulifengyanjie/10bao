package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.twsm.newstopic.dao.ArchiveMapper;
import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.service.ArchiveService;

@Transactional
@Service
public class ArchiveServiceImpl extends BaseServiceImpl implements ArchiveService {

	@Resource
	private ArchiveMapper archiveMapper;

	@Override
	public BaseMapper getMapper() {
		return archiveMapper;
	}

	
}
