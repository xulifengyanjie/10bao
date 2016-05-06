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

import cn.com.twsm.newstopic.model.ApproveArchive;
import cn.com.twsm.newstopic.model.Archive;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.service.ApproveArchiveService;
import cn.com.twsm.newstopic.service.ArchiveService;
import cn.com.twsm.newstopic.service.UserService;
import cn.com.twsm.newstopic.util.Constant;
import cn.com.twsm.newstopic.util.General;

/**
 * 公文
 * @author WuHao
 *
 */
@Controller
@RequestMapping("/archive")
public class ArchiveController {

	@Resource
	private ArchiveService archiveService;
	@Resource
	private ApproveArchiveService approveArchiveService;
	@Resource
	private UserService userService;
	/**
	 * 公文登记列表 
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@RequestMapping("/archiveList")
	public ModelAndView archiveList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize){
		ModelAndView modelAndView = new ModelAndView();
		Map<String , Object> whereMap = new HashMap<String , Object>();
		whereMap.put("status", Constant.ARCHIVE_STATUS_APPROVE);
		whereMap.put("status1", Constant.ARCHIVE_STATUS_NOAPPROVE);
		PageInfo<Archive> page = archiveService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.setViewName("archive/archiveList");
		modelAndView.addObject("page",page);
		return modelAndView;
	}
	
	/**
	 * 发文登记簿列表 
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@RequestMapping("/pubArchiveList")
	public ModelAndView pubArchiveList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize){
		ModelAndView modelAndView = new ModelAndView();
		Map<String , Object> whereMap = new HashMap<String , Object>();
		whereMap.put("status", Constant.ARCHIVE_STATUS_APPROVE);
		PageInfo<Archive> page = archiveService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.setViewName("archive/pubArchiveList");
		modelAndView.addObject("page",page);
		return modelAndView;
	}
	
	/**
	 * 公文审批列表 
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@RequestMapping("/appArchiveList")
	public ModelAndView appArchiveList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize){
		ModelAndView modelAndView = new ModelAndView();
		Map<String , Object> whereMap = new HashMap<String , Object>();
		whereMap.put("status", Constant.ARCHIVE_STATUS_SUBMIT);
		PageInfo<Archive> page = archiveService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.setViewName("archive/appArchiveList");
		modelAndView.addObject("page",page);
		return modelAndView;
	}
	
	/** 
	 * 查公文详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/archiveDetail/{id}" , method=RequestMethod.GET)
	public ModelAndView quetyArchiveDetail(@PathVariable Integer id){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		List<User> userList = userService.getByWhere(whereMap);

		Archive archive = archiveService.selectByPrimaryKey(id);
		whereMap.put("id", archive.getApprovalUserId());
		List<User> user = userService.getByWhere(whereMap);
		if(user!=null&&user.size()==1)
			archive.setAppUser(user.get(0).getUserName());
		modelAndView.setViewName("archive/archiveDetail");
		modelAndView.addObject("archive",archive);
		modelAndView.addObject("userList",userList);
		return modelAndView;
	}
	
	/** 
	 * 审批公文
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/shenpiArchive/{id}" , method=RequestMethod.GET)
	public ModelAndView shenpiArchive(@PathVariable Integer id){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		List<User> userList = userService.getByWhere(whereMap);

		Archive archive = archiveService.selectByPrimaryKey(id);
		whereMap.put("id", archive.getApprovalUserId());
		List<User> user = userService.getByWhere(whereMap);
		if(user!=null&&user.size()==1)
			archive.setAppUser(user.get(0).getUserName());
		modelAndView.setViewName("archive/archiveSp");
		modelAndView.addObject("archive",archive);
		modelAndView.addObject("userList",userList);
		return modelAndView;
	}
	
	/**
	 * 公文添加页面
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView add(){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		List<User> userList = userService.getByWhere(whereMap);
		modelAndView.setViewName("/archive/addArchive");
		modelAndView.addObject("userList", userList);
		return modelAndView;
	}
	
	/**
	 * 公文添加
	 * @return
	 */
	@RequestMapping("/doAdd")
	@ResponseBody
	public String doAdd(Archive archive){
		archive.setAddTime(General.getNow());
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		User user = userService.getByLoginName(loginName);
		archive.setAddUserId(user.getId());
		archive.setStatus(Constant.ARCHIVE_STATUS_NOAPPROVE);
		archive.setApprovalStatus(Constant.ARCHIVE_STATUS_NOAPPROVE);
		archiveService.insert(archive);
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 公文修改
	 * @return
	 */
	@RequestMapping("/doEdit")
	public ModelAndView doEditArchive(Archive archive){
		archiveService.updateByPrimaryKey(archive);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/archive/archiveList");
		return modelAndView;
	}
	
	/**
	 * 公文删除
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delArchive(@RequestParam Integer id){
		archiveService.deleteByPrimaryKey(id);
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 发布公文
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/pubArchive/{id}" , method=RequestMethod.GET)
	public ModelAndView publishArchive(@PathVariable Integer id){
		Archive archive = archiveService.selectByPrimaryKey(id);
		archive.setStatus(Constant.ARCHIVE_STATUS_PUBLISH);
		archiveService.updateByPrimaryKey(archive);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/archive/archiveList");
		return modelAndView;
	}
	
	/**
	 * 提交公文
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/subArchive/{id}" , method=RequestMethod.GET)
	public ModelAndView subArchive(@PathVariable Integer id){
		Archive archive = archiveService.selectByPrimaryKey(id);
		archive.setStatus(Constant.ARCHIVE_STATUS_SUBMIT);
		archiveService.updateByPrimaryKey(archive);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/archive/archiveList");
		return modelAndView;
	}
	
	/**
	 * 审批公文
	 * @param approveContent
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/appArchive")
	@ResponseBody
	public String approveArchive(@RequestParam String approveContent , @RequestParam Integer cid,
			@RequestParam String status){
		ApproveArchive approveArchive = new ApproveArchive();
		Archive archive = archiveService.selectByPrimaryKey(cid);
		archive.setStatus(status);
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		User user = userService.getByLoginName(loginName);
		approveArchive.setUserId(user.getId());
		approveArchive.setArchiveId(cid);
		approveArchive.setConnent(approveContent);
		approveArchive.setApproveTime(General.getNow());
		approveArchiveService.insert(approveArchive);
		archiveService.updateByPrimaryKey(archive);
//		modelAndView.setViewName("redirect:/archive/archiveList");
		return JSONArray.toJSONString("success");
		
	}
}
