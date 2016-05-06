package cn.com.twsm.newstopic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.dao.NoteMapper;
import cn.com.twsm.newstopic.service.NoteService;

@Transactional
@Service("noteService")
public class NoteServiceImpl extends BaseServiceImpl implements NoteService{

	@Resource
	private NoteMapper noteMapper;
	
	@Override
	public BaseMapper getMapper() {
		return noteMapper;
	}

}
