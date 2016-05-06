package cn.com.twsm.newstopic.service;

import java.util.List;

import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.model.UserRoleRelation;

public interface UserService extends BaseService{ 
	User getByLoginName(String loginName);
	
	User getByLoginNameAndPwd(String loginName,String password);

    User getUserPermissions(String id);
    
    List<User> queryUserNameList();
    
    void addUser(User user,List<UserRoleRelation> relations);
    
    void editUser(User user,List<UserRoleRelation> relations);
    
    void delUser(List<Integer> userIds);
}
