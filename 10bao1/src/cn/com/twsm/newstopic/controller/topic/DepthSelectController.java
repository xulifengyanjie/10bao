package cn.com.twsm.newstopic.controller.topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;

import cn.com.twsm.newstopic.model.Department;
import cn.com.twsm.newstopic.model.TreeNode;
import cn.com.twsm.newstopic.service.DepartmentService;

@Controller
@RequestMapping("/depth")
public class DepthSelectController {
	
	@Resource
	private DepartmentService departmentService;
	
	
	@RequestMapping("/analyze")
	public ModelAndView analyze(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="type",defaultValue="") String type,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		Map<String , Object> whereMap = new HashMap<String , Object>();
		List<Department> departList = departmentService.getByWhere(whereMap);
		modelAndView.addObject("departmentList", departList);
		modelAndView.addObject("departmentTree", JSONArray.toJSONString(getTree(departList,null)));
		modelAndView.setViewName("depthSelect/analyze");
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
