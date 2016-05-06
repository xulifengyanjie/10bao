package cn.com.twsm.newstopic.controller.topic;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.com.twsm.newstopic.model.ApproveInterSelect;
import cn.com.twsm.newstopic.model.Interview;
import cn.com.twsm.newstopic.model.TopicSelection;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.service.ApproveInterSelectService;
import cn.com.twsm.newstopic.service.InterviewService;
import cn.com.twsm.newstopic.service.TopicSelectionService;
import cn.com.twsm.newstopic.service.UserService;
import cn.com.twsm.newstopic.util.Constant;
import cn.com.twsm.newstopic.util.General;

@Controller
@RequestMapping("/approveSel")
public class ApproveInterSelectController {

	@Resource
	private ApproveInterSelectService approveInterSelectService;
	@Resource
	private InterviewService interviewService;
	@Resource
	private TopicSelectionService topicSelectionService;
	@Resource
	private UserService userService;
	/**
	 * 选题审批
	 * @return
	 */
	@RequestMapping("/approveSelect")
	@ResponseBody
	public String approveSelect(@RequestParam String appContent , @RequestParam Integer selectId,
			@RequestParam String status){
		ApproveInterSelect approveInterSelect = new ApproveInterSelect();
		TopicSelection select = topicSelectionService.selectByPrimaryKey(selectId);
		select.setStatus(status);
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		User user = userService.getByLoginName(loginName);
		select.setApprovalUserId(user.getId());
		approveInterSelect.setUserId(user.getId());
		approveInterSelect.setSelectId(selectId);
		approveInterSelect.setContent(appContent);
		approveInterSelect.setApproveTime(General.getNow());
		approveInterSelectService.insert(approveInterSelect);
		topicSelectionService.updateByPrimaryKey(select);
//		modelAndView.setViewName("redirect:/archive/archiveList");
		return JSONArray.toJSONString("success");
	}
	
	
	
	/**
	 * 采访审批
	 * @return
	 */
	@RequestMapping("/approveInter")
	@ResponseBody
	public String approveInter(@RequestParam String appContent , @RequestParam Integer selectId,
			@RequestParam String status){
		ApproveInterSelect approveInterSelect = new ApproveInterSelect();
		Interview interview = interviewService.selectByPrimaryKey(selectId);
		interview.setStatus(status);
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		User user = userService.getByLoginName(loginName);
		interview.setApprovalUserId(user.getId());
		approveInterSelect.setApproveTime(General.getNow());
		approveInterSelect.setContent(appContent);
		approveInterSelect.setUserId(user.getId()); //当前审批人
		approveInterSelect.setType(Constant.COLLECT_TYPE_INTERVIEW);
		approveInterSelectService.insert(approveInterSelect);
		interviewService.updateByPrimaryKey(interview);
		return JSONArray.toJSONString("success");
	}

}
