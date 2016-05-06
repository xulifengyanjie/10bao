package cn.com.twsm.newstopic.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.twsm.newstopic.model.Permission;


public class MenuTree {
	
	
	public static Map<String,Object> mapArray = new LinkedHashMap<String, Object>();
	public List<Permission> menuCommon;
	public List<Object> list = new ArrayList<Object>();
	
	public List<Object> menuList(List<Permission> menu){    
		this.menuCommon = menu;
		for (Permission x : menu) {   
			Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
			if(null != x && x.getParentId()==0){
				mapArr.put("id", x.getId());
				mapArr.put("name", x.getPermissionName());
				mapArr.put("pid", x.getParentId());	
				mapArr.put("url", x.getPermissionUrl());	
				mapArr.put("child", menuChild(x.getId()));	
				list.add(mapArr);
			}
		}	
		return list;
	}
	
	
	public List<?> menuChild(int id){
		List<Object> lists = new ArrayList<Object>();
		for(Permission a:menuCommon){
			Map<String,Object> childArray = new LinkedHashMap<String, Object>();
			if(null != a && a.getParentId() == id){
				childArray.put("id", a.getId());
				childArray.put("name", a.getPermissionName());
				childArray.put("url", a.getPermissionUrl());
				childArray.put("pid", a.getParentId());
				childArray.put("child", menuChild(a.getId()));	
				lists.add(childArray);
			}
		}
		return lists;
		
	}
}