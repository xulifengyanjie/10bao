package cn.com.twsm.newstopic.dao;

import cn.com.twsm.newstopic.model.NewsSource;

public interface NewsSourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewsSource record);

    int insertSelective(NewsSource record);

    NewsSource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewsSource record);

    int updateByPrimaryKey(NewsSource record);
}