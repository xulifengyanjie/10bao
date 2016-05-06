package cn.com.twsm.newstopic.controller.management;



import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.ChinaNews;
import cn.com.twsm.newstopic.model.ChinaNewsResult;
import cn.com.twsm.newstopic.service.CatalogService;
import cn.com.twsm.newstopic.service.ChinaNewsResultService;
import cn.com.twsm.newstopic.service.ChinaNewsService;
import cn.com.twsm.newstopic.util.General;

import com.github.pagehelper.PageInfo;


/**
 * 数据集中检索
 * @author cp
 *
 */
@Controller
@RequestMapping("/search")
public class NewsManageController {
	@Resource
	private ChinaNewsService chinaNewsService;
	
	@Resource
	private ChinaNewsResultService chinaNewsResultService;
	
	@Resource
	private CatalogService catalogService;
	/**
	 * 新闻查询
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView newsList(@RequestParam(value="searchWord", defaultValue="") String searchWord,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws UnsupportedEncodingException{
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		PageInfo<ChinaNewsResult> page = null;
		Catalog catalog = catalogService.getByCatalogCode("news");
		if(StringUtils.isNotEmpty(searchWord)){
			searchWord = new String(searchWord.getBytes("ISO-8859-1"),"UTF-8");
		}
		
		whereMap.put("batchNum", catalog.getCrawlTime());
		whereMap.put("catalogId", catalog.getId());
		whereMap.put("orderBy", "PUBLISH_TIME");
		whereMap.put("title", searchWord);
		page = chinaNewsService.getByPage(pageNum, pageSize, whereMap);
		
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("page", page);
		List<ChinaNews> newsList = new ArrayList<ChinaNews>();
		modelMap.put("searchWord", searchWord);
		modelMap.put("newsList", newsList);
		
		modelAndView.addAllObjects(modelMap);
		modelAndView.setViewName("search/searchList");
		return modelAndView;
	}
}
