package cn.com.twsm.newstopic.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.dao.BaseMapper;
import cn.com.twsm.newstopic.service.BaseService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public abstract class BaseServiceImpl implements BaseService{


	@Override
	public <T> int deleteByPrimaryKey(Integer id) {
		return getMapper().deleteByPrimaryKey(id);
	}

	@Override
	public <T> int deleteByIds(List<Integer> ids) {
		return getMapper().deleteByIds(ids);
	}

	@Override
	public <T> int insert(T t) {
		return getMapper().insert(t);
	}

	@Override
	public <T> int insertSelective(T record) {
		return getMapper().insertSelective(record);
	}

	@Override
	public <T> T selectByPrimaryKey(Integer id) {
		return getMapper().selectByPrimaryKey(id);
	}

	@Override
	public <T> int updateByPrimaryKeySelective(T record) {
		return getMapper().updateByPrimaryKeySelective(record);
	}

	@Override
	public <T> int updateByPrimaryKey(T record) {
		return getMapper().updateByPrimaryKey(record);
	}

	@Override
	public <T> List<T> getByWhere(Map<String, Object> whereMap) {
		return getMapper().getByWhere(whereMap);
    }

	@Override
	public <T> PageInfo<T> getByPage(int pageNum, int pageSize,
			Map<String, Object> whereMap) {
		PageHelper.startPage(pageNum, pageSize);
		List<T> newsList = getMapper().getByWhere(whereMap);
		PageInfo<T> page = new PageInfo<T>(newsList,10);
		return page;
	}
	@Override
	public <T> PageInfo<T> getByPageType(int pageNum, int pageSize, String type) {
		PageHelper.startPage(pageNum, pageSize);
		List<T> newsList = getMapper().getByType(type);
		PageInfo<T> page = new PageInfo<T>(newsList,10);
		return page;
	}

	public abstract BaseMapper getMapper();
	
}
