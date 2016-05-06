package cn.com.twsm.newstopic.controller.management;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.IndustryNews;
import cn.com.twsm.newstopic.service.CatalogService;
import cn.com.twsm.newstopic.service.IndustryNewsService;

import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/industry")
public class IndustryNewsController {

	@Resource
	private IndustryNewsService industryNewsService;
	
	@Resource
	private CatalogService catalogService;
	
	/**
	 * 新闻列表
	 * @param pageNum
	 * @param pageSize
	 * @param catalogName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/{catalogName}")
	public ModelAndView list(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,@PathVariable String catalogName) throws UnsupportedEncodingException{
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		PageInfo<IndustryNews> page = null;
		Catalog catalog = catalogService.getByCatalogName(catalogName);
		//获取新闻批次信息
		whereMap.put("crawlTime", catalog.getCrawlTime());
		whereMap.put("newsLevel", 1);
		whereMap.put("catalogId", catalog.getId());
		page = industryNewsService.getByPage(pageNum, pageSize, whereMap);
		List<IndustryNews> newsList = page.getList();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("page", page);
		
		modelAndView.addAllObjects(modelMap);
		modelAndView.setViewName("industry/industryNewsList");
		return modelAndView;
	}
	
}
