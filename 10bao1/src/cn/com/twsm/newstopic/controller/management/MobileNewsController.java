package cn.com.twsm.newstopic.controller.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.twsm.newstopic.model.MobAppNews;
import cn.com.twsm.newstopic.service.MobAppNewsService;

import com.github.pagehelper.PageInfo;

/**
 * 移动客户端新闻Controller
 *
 */
@Controller
@RequestMapping("/mobile")
public class MobileNewsController{
	@Resource
	private MobAppNewsService mobAppNewsService;
	
	@RequestMapping("/list")
	protected ModelAndView list(HttpServletRequest request,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("appName", "腾讯新闻");
		whereMap.put("column", "要闻");
		PageInfo<MobAppNews> page = mobAppNewsService.getByPage(pageNum, pageSize, whereMap);
		whereMap.put("appName", "网易新闻");
		whereMap.put("column", "头条");
		PageInfo<MobAppNews> page1 = mobAppNewsService.getByPage(pageNum, pageSize, whereMap);
		whereMap.put("appName", "经济日报");
		whereMap.put("column", "头条");
		PageInfo<MobAppNews> page2 = mobAppNewsService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.setViewName("mobile/mobileNewsList");
		modelAndView.addObject("page", page);
		modelAndView.addObject("page1", page1);
		modelAndView.addObject("page2", page2);
		return modelAndView;
	}
	
	@RequestMapping(value="/showAjaxData",method = RequestMethod.POST)
	protected @ResponseBody List<MobAppNews> showAjaxData( String appName,String column,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize){
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("appName", appName);
		whereMap.put("column", column);
		PageInfo<MobAppNews> page = mobAppNewsService.getByPage(pageNum, pageSize, whereMap);
		return page.getList();
	}
	
	
	@RequestMapping(value="/showItemNews",method = RequestMethod.POST)
	protected @ResponseBody List<MobAppNews> showItemNews( String appName,String column,Integer len,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize){
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("appName", appName);
		whereMap.put("column", column);
		if(len!=null){
			pageNum=pageNum+len+1;
		}
		PageInfo<MobAppNews> page = mobAppNewsService.getByPage(pageNum, pageSize, whereMap);
		return page.getList();
	}
	
	@RequestMapping("/searchList")
	protected @ResponseBody List<MobAppNews>  searchList(String startTime,String endTime,String keyWord,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws Exception{
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("startTime", startTime);
		whereMap.put("endTime", endTime);
		whereMap.put("keyWord", keyWord);
		PageInfo<MobAppNews> page = mobAppNewsService.getByPage(pageNum, pageSize, whereMap);
		return page.getList();
	}
	
	
}
