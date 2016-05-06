package cn.com.twsm.newstopic.controller.office;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

import cn.com.twsm.newstopic.controller.common.SingleTableController;
import cn.com.twsm.newstopic.model.Department;
import cn.com.twsm.newstopic.model.TreeNode;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.service.BaseService;
import cn.com.twsm.newstopic.service.DepartmentService;
import cn.com.twsm.newstopic.service.UserService;

/**
 * 部门管理
 * @author cp
 *
 */
@Controller
@RequestMapping("/department")
public class DepartmentController extends SingleTableController<Department>{
	@Resource
	private DepartmentService departmentService;
	@Resource
	private UserService userService;

	@Override
	protected BaseService getBaseService() {
		return departmentService;
	}

	@Override
	protected String getBaseViewName() {
		return "department";
	}
	
	@Override
	protected Map<String, Object> getWhereMap() {
		Map<String, Object> whereMap = new HashMap<String,Object>();
		whereMap.put("departmentName", "");
		return whereMap;
	}
	
	@RequestMapping("/add")
	public ModelAndView add(){
		List<Department> departmentList = departmentService.getByWhere(new HashMap<String,Object>());
		ModelAndView model = new ModelAndView();
		model.addObject("departmentList", departmentList);
		model.addObject("departmentTree", JSONArray.toJSONString(getTree(departmentList,null)));
		model.setViewName("department/add");
		return model;
	}
	
	/**
	 * 公共跳转到修改页面
	 * @return
	 */
	@RequestMapping(value="/edit/{id}" , method=RequestMethod.GET)
	protected ModelAndView edit(@PathVariable Integer id){
		Department department = departmentService.selectByPrimaryKey(id);
		List<Department> departmentList = departmentService.getByWhere(new HashMap<String,Object>());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("item", department);
		modelAndView.addObject("departmentList", departmentList);
		modelAndView.addObject("departmentTree", JSONArray.toJSONString(getTree(departmentList,department)));
		modelAndView.setViewName("department/edit");
		return modelAndView;
	}
	
	/**
	 * 跳转到员工列表
	 * @return
	 */
	@RequestMapping(value="/staff/{id}" , method=RequestMethod.GET)
	protected ModelAndView staff(@PathVariable Integer id){
		Department department = departmentService.selectByPrimaryKey(id);
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String,Object>();
		whereMap.put("departmentId", id);
		List<User> userList = userService.getByWhere(whereMap);
		modelAndView.addObject("item", department);
		modelAndView.addObject("userList",userList);
		modelAndView.setViewName("department/stafflist");
		return modelAndView;
	}
	
	/**
	 * 跳转到部门成员页面
	 * @return
	 */
	@RequestMapping(value="/stafflist" , method=RequestMethod.GET)
	public ModelAndView stafflist(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			Integer departmentId){
		ModelAndView modelAndView = new ModelAndView();
		Map<String , Object> whereMap = new HashMap<String, Object>();
		whereMap.put("departmentId", departmentId);
		PageInfo<User> page = userService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.addObject("page", page);
		modelAndView.addObject("departmentId", departmentId);
		modelAndView.setViewName("/department/stafflist");
		return modelAndView;
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
	
}
