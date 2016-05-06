package cn.com.twsm.newstopic.dao;

import java.util.List;

import cn.com.twsm.newstopic.model.User;

public interface UserMapper extends BaseMapper{
	User getByLoginName(String loginName);
	
	User getByLoginNameAndPwd(String loginName,String password);

    User getUserPermissions(String id);

	List<User> queryUserNameList();
    
}