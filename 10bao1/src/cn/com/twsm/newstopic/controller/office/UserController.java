package cn.com.twsm.newstopic.controller.office;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.twsm.newstopic.controller.common.SingleTableController;
import cn.com.twsm.newstopic.exception.UserException;
import cn.com.twsm.newstopic.model.Department;
import cn.com.twsm.newstopic.model.Role;
import cn.com.twsm.newstopic.model.TreeNode;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.model.UserRoleRelation;
import cn.com.twsm.newstopic.service.BaseService;
import cn.com.twsm.newstopic.service.DepartmentService;
import cn.com.twsm.newstopic.service.RoleService;
import cn.com.twsm.newstopic.service.UserService;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

/**
 * 用户管理控制器
 * @author cp
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends SingleTableController<User>{
	
	@Resource
	private UserService userService;
	
	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private RoleService roleService;
	
	/**
	 * 用户列表查询
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(User u,HttpServletRequest request,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize){
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("userName", u.getUserName());
		whereMap.put("loginName", u.getLoginName());
		PageInfo<User> page = userService.getByPage(pageNum, pageSize, whereMap);
		
		whereMap.clear();
		List<Role> roleList = roleService.getByWhere(whereMap);
		modelAndView.setViewName("user/list");
		modelAndView.addObject("page", page);
		modelAndView.addObject("roleList", roleList);
		modelAndView.addObject("item", u);
		return modelAndView;
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView add(){
		List<Department> departmentList = departmentService.getByWhere(new HashMap<String,Object>());
		List<Role> roleList = roleService.getByWhere(new HashMap<String,Object>());
		
		ModelAndView model = new ModelAndView();
		model.setViewName(ADD);
		model.addObject("roles",roleList);
		model.addObject("departmentTree", JSONArray.toJSONString(getTree(departmentList,null)));
		return model;
	}
	
	/**
	 * 跳转到修改页面
	 * @return
	 */
	@RequestMapping(value="/edit/{id}" , method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable Integer id){
		User user = userService.selectByPrimaryKey(id);
		User curUser = userService.getByLoginName(user.getLoginName());
		List<Role> roleList = roleService.getByWhere(new HashMap<String,Object>());
		List<Department> departmentList = departmentService.getByWhere(new HashMap<String,Object>());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("item", user);
		modelAndView.addObject("roles",roleList);
		modelAndView.addObject("roleList",curUser.getRoles());
		modelAndView.addObject("departmentList", departmentList);
		modelAndView.addObject("departmentTree", JSONArray.toJSONString(getTree(departmentList,null)));
		modelAndView.setViewName(EDIT);
		return modelAndView;
	}
	
	/**
	 * 公共添加操作
	 * @return
	 * @throws UserException 
	 */
	@ResponseBody
	@RequestMapping("/doAdd")
	protected String doAdd(User user) throws UserException{
		String password = user.getPassword();
		String pwdMd5 = DigestUtils.md2Hex(password);
		user.setPassword(pwdMd5);
		String roleIds = user.getRoleIds();
		List<String> list = new ArrayList<String>();
		try {
			if(StringUtils.isNotEmpty(roleIds)){
				list = Arrays.asList(roleIds.split(","));
			}
			List<UserRoleRelation> relations = new ArrayList<UserRoleRelation>();
			UserRoleRelation userRoleRelation = null;
			for(String str : list){
				userRoleRelation = new UserRoleRelation();
				userRoleRelation.setUserId(user.getId());
				userRoleRelation.setRoleId(Integer.valueOf(str));
				relations.add(userRoleRelation);
			}
			userService.addUser(user, relations);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new UserException("用户添加失败！");
		}
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 公共修改操作
	 * @return
	 * @throws UserException 
	 */
	@RequestMapping("/doEdit")
	@ResponseBody
	protected String doEdit(User user) throws UserException{
		String password = user.getPassword();
		if(StringUtils.isNotEmpty(password)){
			String pwdMd5 = DigestUtils.md2Hex(password);
			user.setPassword(pwdMd5);
		}
		String roleIds = user.getRoleIds();
		List<String> list = new ArrayList<String>();
		try {
			if(StringUtils.isNotEmpty(roleIds)){
				list = Arrays.asList(roleIds.split(","));
			}
			List<UserRoleRelation> relations = new ArrayList<UserRoleRelation>();
			UserRoleRelation userRoleRelation = null;
			for(String str : list){
				userRoleRelation = new UserRoleRelation();
				userRoleRelation.setUserId(user.getId());
				userRoleRelation.setRoleId(Integer.valueOf(str));
				relations.add(userRoleRelation);
			}
			userService.editUser(user, relations);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("用户修改失败！");
		}
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 公共删除单多条操作
	 * @return
	 */
	@RequestMapping(value="/dels")
	@ResponseBody
	protected String dels(@RequestParam("ids[]") List<Integer> ids){
		userService.delUser(ids);
		return JSONArray.toJSONString("success");
	}
	
	
	private List<TreeNode> getTree(List<Department> departmentList,Department checked){
		List<TreeNode> tree = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		if(departmentList != null){
			for(Department d : departmentList){
				node = new TreeNode();
				node.setId(d.getId());
				node.setpId(d.getParentId());
				node.setName(d.getDepartmentName());
				node.setOpen(true);
				if(checked != null && checked.getId().equals(d.getId())){
					node.setChecked(true);
				}
				tree.add(node);
			}
		}
		return tree;
	}

	@Override
	protected BaseService getBaseService() {
		return userService;
	}

	@Override
	protected String getBaseViewName() {
		return "user";
	}

	@Override
	protected Map<String, Object> getWhereMap() {
		Map<String, Object> whereMap = new HashMap<String,Object>();
		return whereMap;
	}
	
}
