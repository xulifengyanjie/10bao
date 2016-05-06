package cn.com.twsm.newstopic.controller.topic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

import cn.com.twsm.newstopic.model.ApproveInterSelect;
import cn.com.twsm.newstopic.model.CollectionType;
import cn.com.twsm.newstopic.model.Interview;
import cn.com.twsm.newstopic.model.Topic;
import cn.com.twsm.newstopic.model.TopicSelection;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.service.ApproveInterSelectService;
import cn.com.twsm.newstopic.service.CollectionTypeService;
import cn.com.twsm.newstopic.service.InterviewService;
import cn.com.twsm.newstopic.service.TopicSelectionService;
import cn.com.twsm.newstopic.service.TopicService;
import cn.com.twsm.newstopic.service.UserService;
import cn.com.twsm.newstopic.util.Constant;
import cn.com.twsm.newstopic.util.General;


@Controller
@RequestMapping("/interview")
public class InterviewController{

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
	 * 采访列表
	 * 默认选题列表
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@RequestMapping("/interviewList")
	public ModelAndView interviewList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="status",defaultValue="") String status,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if(General.isNotEmpty(status))
			whereMap.put("status", status);
		if(General.isNotEmpty(keyword))
			whereMap.put("keyword", keyword);
		PageInfo<Interview> page = interviewService.getByPage(pageNum, pageSize, whereMap);
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
		modelAndView.setViewName("interview/interviewList");
		return modelAndView;
	}
	
	/** 
	 * 查采访详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/interviewDetail/{id}" , method=RequestMethod.GET)
	public ModelAndView quetyInterviewDetail(@PathVariable Integer id){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("topic_type", Constant.COLLECT_TYPE_SELECT);
		List<Topic> topicList = topicService.getByWhere(whereMap);
		whereMap.clear();
		List<User> userList = userService.getByWhere(whereMap);
		Interview interview = interviewService.selectByPrimaryKey(id);
						interview.setUserList(userList);
		interview.setTopicList(topicList);
		modelAndView.setViewName("interview/interviewDetail");
		modelAndView.addObject("interview",interview);
		return modelAndView;
	}
	
	
	/**
	 * 采访添加页面
	 * @return
	 */
	@RequestMapping("/addInter")
	public ModelAndView addInter(){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("topic_type", Constant.COLLECT_TYPE_SELECT);
		List<Topic> topicList = topicService.getByWhere(whereMap);
		whereMap.clear();
		List<User> userList = userService.getByWhere(whereMap);
		whereMap.clear();
		List<TopicSelection> selectList = topicSelectionService.getByWhere(whereMap);
		modelAndView.setViewName("/interview/addInterview");
		modelAndView.addObject("userList",userList);
		modelAndView.addObject("topicList",topicList);
		modelAndView.addObject("selectList",selectList);
		return modelAndView;
	}
	
	/**
	 * 采访添加
	 * @return
	 */
	@RequestMapping("/doAddInter")
	@ResponseBody
	public String doAddInter(Interview interview){
		interview.setAddTime(General.getNow());
		interview.setAddUserId(1);
		interview.setStatus(Constant.TOPIC_SELECT_STATUS_TOEDIT);
		interviewService.insert(interview);
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 采访修改
	 * @return
	 */
	@RequestMapping("/doEditInter")
	public ModelAndView doEditInterview(Interview interview){
		interviewService.updateByPrimaryKey(interview);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/interview/interviewList");
		return modelAndView;
	}
	
	/**
	 * 采访删除
	 * @return
	 */
	@RequestMapping("/deleteInter")
	@ResponseBody
	public String delInterview(@RequestParam Integer id){
		interviewService.deleteByPrimaryKey(id);
//		modelAndView.setViewName("redirect:/interview/interviewList");
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 采访收藏
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/collectInterview")
	public ModelAndView collectInterview(@RequestParam Integer id[]){
		for (int i = 0; i < id.length; i++) {
			Interview interview = interviewService.selectByPrimaryKey(id[i]);
			CollectionType collectionType = new CollectionType();
			collectionType.setAddTime(General.getNow());
			collectionType.setNewsId(interview.getId());
			collectionType.setTitle(interview.getTitle());
			collectionType.setType(Constant.COLLECT_TYPE_INTERVIEW);
//			collectionType.setUserId(userId);
			collectionTypeService.insert(collectionType);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/interview/interviewList");
		return modelAndView;
	}
	
	
	/**
	 * 采访撤回
	 * @return
	 */
	@RequestMapping(value="/recallinter/{id}" , method=RequestMethod.GET)
	public ModelAndView recallinter(@PathVariable Integer id){
		Interview interview = interviewService.selectByPrimaryKey(id);
		interview.setStatus(Constant.TOPIC_SELECT_STATUS_TOEDIT);
		interviewService.updateByPrimaryKey(interview);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/interview/interviewList");
		return modelAndView;
	}
	
	
	/**
	 * 采访审批列表
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@RequestMapping("/appViewList")
	public ModelAndView appViewList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
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
		PageInfo<Interview> page = interviewService.getByPage(pageNum, pageSize, whereMap);
		whereMap.clear();
		whereMap.put("topic_type", Constant.COLLECT_TYPE_INTERVIEW);
		List<Topic> topicList = topicService.getByWhere(whereMap);
		modelAndView.addObject("page",page);
		modelAndView.addObject("topicList",topicList);
		modelAndView.addObject("keyword",keyword);
		modelAndView.addObject("status",status);
		modelAndView.setViewName("/interview/appViewList");
		return modelAndView;
	}
	
	/** 
	 * 查采访详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/toApproveInter/{id}" , method=RequestMethod.GET)
	public ModelAndView toApproveInter(@PathVariable Integer id){
		ModelAndView modelAndView = new ModelAndView();
		Interview interview = interviewService.selectByPrimaryKey(id);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("topic_type", Constant.COLLECT_TYPE_INTERVIEW);
		List<Topic> topicList = topicService.getByWhere(whereMap);
		whereMap.clear();
		List<User> userList = userService.getByWhere(whereMap);
		whereMap.clear();
		whereMap.put("selectId",id);
		whereMap.put("type",Constant.COLLECT_TYPE_INTERVIEW);
		List<ApproveInterSelect> list = approveInterSelectService.getByWhere(whereMap);
		if(list!=null&&list.size()>0){
			ApproveInterSelect approveInterSelect = list.get(0);
			interview.setAppContent(approveInterSelect.getContent());
		}
		interview.setUserList(userList);
		interview.setTopicList(topicList);
		modelAndView.setViewName("interview/approveInter");
		modelAndView.addObject("interview",interview);
		return modelAndView;
	}

}
