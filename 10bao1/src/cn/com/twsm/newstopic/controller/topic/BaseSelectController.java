package cn.com.twsm.newstopic.controller.topic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.twsm.newstopic.model.MobAppNews;
import cn.com.twsm.newstopic.service.MobAppNewsService;
import cn.com.twsm.newstopic.util.General;

@Controller
@RequestMapping("/baseSelect")
public class BaseSelectController {

	@Resource
	private MobAppNewsService appNewsService;
	
	@RequestMapping("/hotNews")
	public ModelAndView hotNews(){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("addTime", General.getNow());
		whereMap.put("appName", "网易新闻");
		List<MobAppNews> wyList = appNewsService.getHotByWhere(whereMap);
		whereMap.put("appName", "搜狐新闻");
		List<MobAppNews> shList = appNewsService.getHotByWhere(whereMap);
		whereMap.put("appName", "人民日报");
		List<MobAppNews> xhList = appNewsService.getHotByWhere(whereMap);
		whereMap.put("appName", "新华炫闻");
		List<MobAppNews> rmList = appNewsService.getHotByWhere(whereMap);
		
		modelAndView.addObject("wyList", wyList);
		modelAndView.addObject("shList", shList);
		modelAndView.addObject("xhList", xhList);
		modelAndView.addObject("rmList", rmList);
		modelAndView.setViewName("baseSelect/hotNews");
		return modelAndView;
	}
	
	@RequestMapping("/hotWord")
	public ModelAndView hotWord(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("baseSelect/hotWord");
		return modelAndView;
	}
	@RequestMapping("/hotSpeak")
	public ModelAndView hotSpeak(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("baseSelect/hotSpeak");
		return modelAndView;
	}
}
