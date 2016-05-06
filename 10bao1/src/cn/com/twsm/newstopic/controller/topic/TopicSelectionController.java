package cn.com.twsm.newstopic.controller.topic;

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

import cn.com.twsm.newstopic.model.ApproveInterSelect;
import cn.com.twsm.newstopic.model.CollectionType;
import cn.com.twsm.newstopic.model.TopicSelection;
import cn.com.twsm.newstopic.model.Topic;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.service.ApproveInterSelectService;
import cn.com.twsm.newstopic.service.CollectionTypeService;
import cn.com.twsm.newstopic.service.InterviewService;
import cn.com.twsm.newstopic.service.TopicSelectionService;
import cn.com.twsm.newstopic.service.TopicService;
import cn.com.twsm.newstopic.service.UserService;
import cn.com.twsm.newstopic.util.Constant;
import cn.com.twsm.newstopic.util.General;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;


/**
 * 选题策划
 * @author WuHao
 *
 */
@Controller
@RequestMapping("/topicSelection")
public class TopicSelectionController {

	@Resource
	private InterviewService interviewService;
	@Resource
	private TopicService topicService;
	@Resource
	private UserService userService;
	@Resource
	private CollectionTypeService collectionTypeService;
	@Resource
	private TopicSelectionService topicSelectionService;
	@Resource
	private ApproveInterSelectService approveInterSelectService;
	/**
	 * 选题列表
	 * 默认选题列表
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@RequestMapping("/selectionList")
	public ModelAndView interSelectionList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="status",defaultValue="") String status,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if(General.isNotEmpty(status))
			whereMap.put("status", status);
		whereMap.put("keyword", keyword);
		PageInfo<TopicSelection> page = topicSelectionService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.addObject("page",page);
		modelAndView.addObject("keyword",keyword);
		if(Constant.TOPIC_SELECT_STATUS_APPROVAL.equals(status))
			modelAndView.addObject("status","待审批");
		else if(Constant.TOPIC_SELECT_STATUS_PUBLISH.equals(status))
			modelAndView.addObject("status","审批通过");
		else if(Constant.TOPIC_SELECT_STATUS_RECHECK.equals(status))
			modelAndView.addObject("status","复审");
		else
			modelAndView.addObject("status","待编辑");
		modelAndView.setViewName("topicSelection/selectionList");
		return modelAndView;
	}
	
	
	
	/** 
	 * 查选题详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/topicSelectionDetail/{id}" , method=RequestMethod.GET)
	public ModelAndView quetyTopicSelectionDetail(@PathVariable Integer id){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("topic_type", Constant.COLLECT_TYPE_SELECT);
		List<Topic> topicList = topicService.getByWhere(whereMap);
		whereMap.clear();
		List<User> userList = userService.getByWhere(whereMap);
		TopicSelection topicSelection = topicSelectionService.selectByPrimaryKey(id);
		topicSelection.setUserList(userList);
		topicSelection.setTopicList(topicList);
		modelAndView.setViewName("topicSelection/topicSelectionDetail");
		modelAndView.addObject("topicSelection",topicSelection);
		return modelAndView;
	}
	
	
	/**
	 * 选题添加页面
	 * @return
	 */
	@RequestMapping("/addSelect")
	public ModelAndView addUser(){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("topic_type", Constant.COLLECT_TYPE_SELECT);
		List<Topic> topicList = topicService.getByWhere(whereMap);
		whereMap.clear();
		List<User> userList = userService.getByWhere(whereMap);
		modelAndView.setViewName("/topicSelection/addTopicSelection");
		modelAndView.addObject("userList",userList);
		modelAndView.addObject("topicList",topicList);
		return modelAndView;
	}
	
	/**
	 * 选题添加
	 * @return
	 */
	@RequestMapping("/doAddSelect")
	@ResponseBody
	public String doAddUser(TopicSelection topicSelection){
		topicSelection.setAddTime(General.getNow());
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		User user = userService.getByLoginName(loginName);
		topicSelection.setAddUserId(user.getId());
		topicSelection.setStatus(Constant.TOPIC_SELECT_STATUS_TOEDIT);
		topicSelectionService.insert(topicSelection);
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 选题修改
	 * @return
	 */
	@RequestMapping("/doEditSelect")
	public ModelAndView doEditTopicSelection(TopicSelection topicSelection){
		topicSelectionService.updateByPrimaryKey(topicSelection);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/topicSelection/selectionList");
		return modelAndView;
	}
	
	/**
	 * 选题删除
	 * @return
	 */
	@RequestMapping("/deleteSelect")
	@ResponseBody
	public String delTopicSelection(@RequestParam Integer id){
		topicSelectionService.deleteByPrimaryKey(id);
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("redirect:/topicSelection/selectionList");
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 选题撤回
	 * @return
	 */
	@RequestMapping(value="/recallSelect/{id}" , method=RequestMethod.GET)
	public ModelAndView recallSelect(@PathVariable Integer id){
		TopicSelection topicSelection = topicSelectionService.selectByPrimaryKey(id);
		topicSelection.setStatus(Constant.TOPIC_SELECT_STATUS_TOEDIT);
		topicSelectionService.updateByPrimaryKey(topicSelection);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/topicSelection/selectionList");
		return modelAndView;
	}
	
	/**
	 * 选题收藏
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/collectTopicSelection")
	public ModelAndView collectTopicSelection(@RequestParam Integer id[]){
		for (int i = 0; i < id.length; i++) {
			TopicSelection topicSelection = topicSelectionService.selectByPrimaryKey(id[i]);
			CollectionType collectionType = new CollectionType();
			collectionType.setAddTime(General.getNow());
			collectionType.setNewsId(topicSelection.getId());
			collectionType.setTitle(topicSelection.getTitle());
			collectionType.setType(Constant.COLLECT_TYPE_SELECT);
//			collectionType.setUserId(userId);
			collectionTypeService.insert(collectionType);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/topicSelection/selectionList");
		return modelAndView;
	}
	
	
	/**
	 * 选题审批列表
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@RequestMapping("/appSelList")
	public ModelAndView appSelList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="status",defaultValue="") String status,@RequestParam(value="keyword", defaultValue="") String keyword,
			@RequestParam(value="topicId", defaultValue="") String topicId){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if(General.isNotEmpty(status))
			whereMap.put("status", status);
		if(General.isNotEmpty(keyword))
			whereMap.put("keyword", keyword);
		if(General.isNotEmpty(topicId))
			whereMap.put("topicId", topicId);
		PageInfo<TopicSelection> page = topicSelectionService.getByPage(pageNum, pageSize, whereMap);
		whereMap.clear();
		whereMap.put("topic_type", Constant.COLLECT_TYPE_SELECT);
		List<Topic> topicList = topicService.getByWhere(whereMap);
		modelAndView.addObject("page",page);
		modelAndView.addObject("topicList",topicList);
		modelAndView.addObject("keyword",keyword);
		modelAndView.addObject("status",status);
		modelAndView.setViewName("topicSelection/appSelList");
		return modelAndView;
	}
	
	
	/** 
	 * 查选题详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/toApproveSelect/{id}" , method=RequestMethod.GET)
	public ModelAndView toApproveSelect(@PathVariable Integer id){
		ModelAndView modelAndView = new ModelAndView();
		TopicSelection topicSelection = topicSelectionService.selectByPrimaryKey(id);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("topic_type", Constant.COLLECT_TYPE_SELECT);
		List<Topic> topicList = topicService.getByWhere(whereMap);
		whereMap.clear();
		List<User> userList = userService.getByWhere(whereMap);
		whereMap.clear();
		whereMap.put("selectId",id);
		whereMap.put("type",Constant.COLLECT_TYPE_SELECT);
		List<ApproveInterSelect> list = approveInterSelectService.getByWhere(whereMap);
		if(list!=null&&list.size()>0){
			ApproveInterSelect approveInterSelect = list.get(0);
			topicSelection.setAppContent(approveInterSelect.getContent());
		}
		topicSelection.setUserList(userList);
		topicSelection.setTopicList(topicList);
		modelAndView.setViewName("topicSelection/approveSelect");
		modelAndView.addObject("topicSelection",topicSelection);
		return modelAndView;
	}
	
}