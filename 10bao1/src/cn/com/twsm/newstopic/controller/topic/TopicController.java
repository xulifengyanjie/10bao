package cn.com.twsm.newstopic.controller.topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

import cn.com.twsm.newstopic.model.Department;
import cn.com.twsm.newstopic.model.Topic;
import cn.com.twsm.newstopic.model.TreeNode;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.service.DepartmentService;
import cn.com.twsm.newstopic.service.TopicService;
import cn.com.twsm.newstopic.service.UserService;
import cn.com.twsm.newstopic.util.General;

/**
 * 专题
 * @author WuHao
 *
 */
@Controller
@RequestMapping("/topic")
public class TopicController {

	@Resource
	private TopicService topicService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private UserService userService;
	/**
	 * 专题列表 
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@RequestMapping("/topicList")
	public ModelAndView topicList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="departmentId", defaultValue="") Integer departmentId,@RequestParam(value="keyword", defaultValue="") String keyword,@RequestParam(value="startTime", defaultValue="") String startTime,@RequestParam(value="endTime", defaultValue="") String endTime){
		ModelAndView modelAndView = new ModelAndView();
		Map<String , Object> whereMap = new HashMap<String , Object>();
		if(departmentId!=null&&departmentId>0)
			whereMap.put("departmentId", departmentId);
		if(General.isNotEmpty(keyword))
			whereMap.put("keyword", keyword);
		if(General.isNotEmpty(startTime))
			whereMap.put("startTime", startTime);
		if(General.isNotEmpty(endTime))
			whereMap.put("endTime", endTime);
		PageInfo<Topic> page = topicService.getByPage(pageNum, pageSize, whereMap);
		whereMap.clear();
		List<Department> departList = departmentService.getByWhere(whereMap);
		modelAndView.setViewName("topic/topicList");
		modelAndView.addObject("page",page);
		modelAndView.addObject("departmentList", departList);
		modelAndView.addObject("departmentTree", JSONArray.toJSONString(getTree(departList,null)));
		modelAndView.addObject("keyword", keyword);
		modelAndView.addObject("startTime", startTime);
		modelAndView.addObject("endTime", endTime);
		return modelAndView;
	}
	
	
	/** 
	 * 查公文详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/topicDetail/{id}" , method=RequestMethod.GET)
	public ModelAndView quetyTopicDetail(@PathVariable Integer id){
		ModelAndView modelAndView = new ModelAndView();
		Topic topic = topicService.selectByPrimaryKey(id);
		List<Department> departmentList = departmentService.getByWhere(new HashMap<String,Object>());
		modelAndView.addObject("departmentList", departmentList);
		modelAndView.addObject("departmentTree", JSONArray.toJSONString(getTree(departmentList,null)));
		modelAndView.setViewName("topic/topicDetail");
		modelAndView.addObject("topic",topic);
		return modelAndView;
	}
	
	
	/**
	 * 公文添加页面
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView addUser(){
		ModelAndView modelAndView = new ModelAndView();
		List<Department> departmentList = departmentService.getByWhere(new HashMap<String,Object>());
		modelAndView.setViewName("/topic/addTopic");
		modelAndView.addObject("departmentList", departmentList);
		modelAndView.addObject("departmentTree", JSONArray.toJSONString(getTree(departmentList,null)));
		return modelAndView;
	}
	
	/**
	 * 公文添加
	 * @return
	 */
	@RequestMapping("/doAdd")
	@ResponseBody
	public String doAddUser(Topic topic){
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		User user = userService.getByLoginName(loginName);
		topic.setAddTime(General.getNow());
		topic.setAddUserId(user.getId());
		topic.setDepartmentId(1);//先默认1
		topicService.insert(topic);
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 公文修改
	 * @return
	 */
	@RequestMapping("/doEdit")
	public ModelAndView doEditTopic(Topic topic){
		topic.setAddTime(General.getNow());
		topicService.updateByPrimaryKey(topic);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/topic/topicList");
		return modelAndView;
	}
	
	/**
	 * 公文删除
	 * @return
	 */
	@RequestMapping(value="/delTopic")
	@ResponseBody
	public String delTopic(@RequestParam Integer id){
		topicService.deleteByPrimaryKey(id);
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
}
