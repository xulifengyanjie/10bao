package cn.com.twsm.newstopic.controller.office;

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

import cn.com.twsm.newstopic.model.Notice;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.service.NoticeService;
import cn.com.twsm.newstopic.service.UserService;
import cn.com.twsm.newstopic.util.Constant;
import cn.com.twsm.newstopic.util.General;

/**
 * 公告
 * @author WuHao
 *
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
	

	@Resource
	private NoticeService noticeService;
	@Resource
	private UserService userService;

	/**
	 * 查询公告列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/noticeList")
	public ModelAndView queryNoticeWork(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="status", defaultValue="") String status){
		ModelAndView modelAndView = new ModelAndView();
		Map<String , Object> whereMap = new HashMap<String, Object>();
		User user ;
		if(General.isNotEmpty(status))
			whereMap.put("status", status);
		PageInfo<Notice> page = noticeService.getByPage(pageNum, pageSize, whereMap);
		if(page.getList()!=null&&page.getList().size()>0){
			for (int i = 0; i < page.getList().size(); i++) {
				user = userService.selectByPrimaryKey(page.getList().get(i).getApprovalUserId()==null?1:page.getList().get(i).getApprovalUserId());
				page.getList().get(i).setAddUserName(user.getUserName());
			}
		}
		modelAndView.setViewName("notice/noticeList");
		modelAndView.addObject("page",page);
		modelAndView.addObject("status",status);
		return modelAndView;
	}
	
	/**
	 * 查新公告详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/noticeDetail/{id}" , method=RequestMethod.GET)
	public ModelAndView quetyNoticeDetail(@PathVariable Integer id){
		ModelAndView modelAndView = new ModelAndView();
		Notice notice = noticeService.selectByPrimaryKey(id);
		modelAndView.setViewName("notice/noticeDetail");
		modelAndView.addObject("notice",notice);
		return modelAndView;
	}
	
	/**
	 * 公告添加页面
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView addUser(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/notice/addNotice");
		return modelAndView;
	}
	
	/**
	 * 公告添加
	 * @return
	 */
	@RequestMapping("/doAdd")
	@ResponseBody
	public String doAdd(Notice notice){
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		User user = userService.getByLoginName(loginName);
		notice.setAddUserId(user.getId());
		noticeService.insert(notice);
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 公告修改
	 * @return
	 */
	@RequestMapping("/doEdit")
	public ModelAndView doEditNotice(Notice notice){
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		User user = userService.getByLoginName(loginName);
		notice.setApprovalUserId(user.getId());
		noticeService.updateByPrimaryKeySelective(notice);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/notice/noticeList");
		return modelAndView;
	}
	
	/**
	 * 公告删除
	 * @return
	 */
	@RequestMapping(value="/delete/{id}" , method=RequestMethod.GET)
	@ResponseBody
	public String delNotice(@PathVariable Integer id){
		noticeService.deleteByPrimaryKey(id);
		return JSONArray.toJSONString("success");
	}
	
	
	/**
	 * 公共删除单多条操作
	 * @return
	 */
	@RequestMapping(value="/dels")
	@ResponseBody
	public String dels(@RequestParam("ids[]") List<Integer> ids){
		noticeService.delsNotice(ids);
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 发布公告
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/publicNotice/{id}" , method=RequestMethod.GET)
	public ModelAndView publishNotice(@PathVariable Integer id){
		Notice notice = noticeService.selectByPrimaryKey(id);
		notice.setStatus(Constant.NOTICE_STATUS_PUBLISH);
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		User user = userService.getByLoginName(loginName);
		notice.setApprovalUserId(user.getId());
		noticeService.updateByPrimaryKey(notice);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/notice/noticeList");
		return modelAndView;
	}
	
}
