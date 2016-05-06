package cn.com.twsm.newstopic.controller.feedback;

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
 * 反馈分析
 * @author xulfieng
 *
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

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
	 * 总体反馈
	 * @param pageNum
	 * @param pageSize
	 * @param status
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/{url}")
	public ModelAndView generalFeedbackList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="status",defaultValue="") String status,@RequestParam(value="keyword", defaultValue="") String keyword,@PathVariable String url){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("feedback/"+url);
		return modelAndView;
	}
	
}