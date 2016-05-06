package cn.com.twsm.newstopic.controller.office;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.twsm.newstopic.controller.common.SingleTableController;
import cn.com.twsm.newstopic.exception.RoleException;
import cn.com.twsm.newstopic.model.Permission;
import cn.com.twsm.newstopic.model.Role;
import cn.com.twsm.newstopic.model.RolePermissionRelation;
import cn.com.twsm.newstopic.model.TreeNode;
import cn.com.twsm.newstopic.service.BaseService;
import cn.com.twsm.newstopic.service.PermissionService;
import cn.com.twsm.newstopic.service.RoleService;

import com.alibaba.fastjson.JSONArray;
/**
 * 角色管理
 * @author cp
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController extends  SingleTableController<Role>{
	@Resource
	private RoleService roleService;
	
	@Resource
	private PermissionService permissionService;

	@Override
	protected BaseService getBaseService() {
		return roleService;
	}

	@Override
	protected String getBaseViewName() {
		return "role";
	}

	@Override
	protected Map<String, Object> getWhereMap() {
		Map<String, Object> whereMap = new HashMap<String,Object>();
		return whereMap;
	}
	
	@RequestMapping("/add")
	public ModelAndView add(){
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("orderBy", "SORT");
		List<Permission> permissionList = permissionService.getByWhere(whereMap);
		ModelAndView model = new ModelAndView();
		model.addObject("permissionTree", JSONArray.toJSONString(getTree(permissionList,null)));
		model.setViewName("role/add");
		return model;
	}
	
	

	/**
	 * 添加操作
	 * @return
	 * @throws RoleException 
	 */
	@ResponseBody
	@RequestMapping("/doAdd")
	public String doAdd(Role role) throws RoleException{
		String permissionIds = role.getPermissionIds();
		try {
			List<String> list = new ArrayList<String>();
			if(StringUtils.isNotEmpty(permissionIds)){
				list = Arrays.asList(permissionIds.split(","));
			}
			List<RolePermissionRelation> relations = new ArrayList<RolePermissionRelation>();
			RolePermissionRelation rolePermissionRelation = null;
			for(String str : list){
				rolePermissionRelation = new RolePermissionRelation();
				rolePermissionRelation.setRoleId(role.getId());
				rolePermissionRelation.setPermissionId(Integer.valueOf(str));
				relations.add(rolePermissionRelation);
			}
			roleService.addRole(role,relations);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RoleException("角色添加失败！");
		}
		return JSONArray.toJSONString("success");
	}
	/**
	 * 跳转到修改页面
	 * @return
	 */
	@RequestMapping(value="/edit/{id}" , method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable Integer id){
		Role role = roleService.getPermissionByRoleId(id);
		List<Permission> permissions = role.getPermissions();
		List<Permission> permissionList = permissionService.getByWhere(new HashMap<String,Object>());
		ModelAndView model = new ModelAndView();
		model.addObject("item", role);
		List<Integer> pIds = new ArrayList<Integer>();
		for(Permission perm : permissions){
			pIds.add(perm.getId());
		}
		String permissionIds = StringUtils.join(pIds, ",");
		model.addObject("permissionIds", permissionIds);
		model.addObject("permissionTree", JSONArray.toJSONString(getTree(permissionList,permissions)));
		model.setViewName("role/edit");
		return model;
	}
	
	/**
	 * 修改操作
	 * @return
	 * @throws RoleException 
	 */
	@RequestMapping("/doEdit")
	@ResponseBody
	public String doEdit(Role role) throws RoleException{
		String permissionIds = role.getPermissionIds();
		List<String> list = new ArrayList<String>();
		try {
			if(StringUtils.isNotEmpty(permissionIds)){
				list = Arrays.asList(permissionIds.split(","));
			}
			List<RolePermissionRelation> relations = new ArrayList<RolePermissionRelation>();
			RolePermissionRelation rolePermissionRelation = null;
			for(String str : list){
				rolePermissionRelation = new RolePermissionRelation();
				rolePermissionRelation.setRoleId(role.getId());
				rolePermissionRelation.setPermissionId(Integer.valueOf(str));
				relations.add(rolePermissionRelation);
			}
			List<Integer> roleIds = new ArrayList<Integer>();
			roleIds.add(role.getId());
			roleService.editRole(role, relations, roleIds);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RoleException("角色修改失败！");
		}
		return JSONArray.toJSONString("success");
	}
	/**
	 * 删除多条操作
	 * @return
	 */
	@RequestMapping(value="/dels")
	@ResponseBody
	public String dels(@RequestParam("ids[]") List<Integer> ids){
		roleService.delRoles(ids);
		return JSONArray.toJSONString("success");
	}
	private List<TreeNode> getTree(List<Permission> permissionList,List<Permission> checkedPermissionList){
		List<TreeNode> tree = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		if(permissionList != null){
			for(Permission d : permissionList){
				node = new TreeNode();
				node.setId(d.getId());
				node.setpId(d.getParentId());
				node.setName(d.getPermissionName());
				if(checkedPermissionList != null && checkedPermissionList.contains(d)){
					node.setChecked(true);
				}
				tree.add(node);
			}
		}
		return tree;
	}
}
