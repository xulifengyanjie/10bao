package cn.com.twsm.newstopic.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

public interface BaseService {
	<T> int deleteByPrimaryKey(Integer id);

	<T> int deleteByIds(List<Integer> ids);
	
	<T> int insert(T t);

	<T> int insertSelective(T record);

    <T> T selectByPrimaryKey(Integer id);

    <T> int updateByPrimaryKeySelective(T record);

    <T> int updateByPrimaryKey(T record);
    
    <T> List<T> getByWhere(Map<String,Object> whereMap);
    
    <T> PageInfo<T> getByPage(int pageNum,int pageSize, Map<String, Object> whereMap);
    
    <T> PageInfo<T> getByPageType(int pageNum,int pageSize, String type);
}
