package cn.com.twsm.newstopic.controller.management;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.Weibo;
import cn.com.twsm.newstopic.model.Weixin;
import cn.com.twsm.newstopic.model.WeixinResult;
import cn.com.twsm.newstopic.service.CatalogService;
import cn.com.twsm.newstopic.service.WeixinResultService;
import cn.com.twsm.newstopic.service.WeixinService;
import cn.com.twsm.newstopic.util.General;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

/**
 * 微信Controller
 *
 */
@Controller
@RequestMapping("weixin")
public class WeixinController{
	@Resource
	private WeixinService weixinService;
	
	@Resource
	private WeixinResultService weixinResultService;
	
	@Resource
	private CatalogService catalogService;
	
	@RequestMapping("/list")
	public ModelAndView newsList(@RequestParam(value="date", defaultValue="") String date,@RequestParam(value="timestamp", defaultValue="") String timestamp,@RequestParam(value="searchWord", defaultValue="") String searchWord,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws UnsupportedEncodingException{
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		PageInfo<WeixinResult> page = null;
		Catalog catalog = catalogService.getByCatalogCode("weixin");
		List<String> crawlTimeList = null;
		if(StringUtils.isNotEmpty(searchWord)){
			searchWord = new String(searchWord.getBytes("ISO-8859-1"),"UTF-8");
		}
		//获取新闻批次信息
		if(StringUtils.isNotEmpty(date)){
			if(catalog != null){
				whereMap.put("batchDate", date);
				whereMap.put("catalogId", catalog.getId());
				crawlTimeList =  weixinResultService.selectByCrawlDate(whereMap);
				whereMap.clear();
			}
		
			if(crawlTimeList != null && crawlTimeList.size() > 0){
				if(StringUtils.isNotEmpty(timestamp)){
					whereMap.put("batchNum", timestamp);
				}else{
					whereMap.put("batchNum", crawlTimeList.get(0));
					timestamp = crawlTimeList.get(0);
				}
				whereMap.put("catalogId", catalog.getId());
				page = weixinResultService.getByPage(pageNum, pageSize, whereMap);
			}else{
				page = null;
			}
		}else{
			if(catalog != null){
				whereMap.put("batchNum", catalog.getCrawlTime());
				whereMap.put("catalogId", catalog.getId());
				page = weixinResultService.getByPage(pageNum, pageSize, whereMap);
				date = catalog.getCrawlTime().substring(0,8);
				timestamp = catalog.getCrawlTime();
				
				
				whereMap.clear();
				whereMap.put("batchDate", date);
				whereMap.put("catalogId", catalog.getId());
				crawlTimeList =  weixinResultService.selectByCrawlDate(whereMap);
				
			}else{
				page = null;
			}
		}
		if(StringUtils.isEmpty(date)){
			date = General.getNow("yyyyMMdd");
		}
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("page", page);
		List<Weixin> newsList = new ArrayList<Weixin>();
		if(page != null){
			List<WeixinResult> resultList = page.getList();
			if(resultList != null){
				newsList = weixinService.getByNewsNums(resultList);
				if(newsList != null){
					for(Weixin news : newsList){
						for(WeixinResult result : resultList){
							if(news.getNewsNum().equals(result.getNewsNum())){
								news.setWeight(result.getWeight());
								news.setSort(result.getSort());
							}
						}
					}
					Collections.sort(newsList, new Comparator<Weixin>(){
						public int compare(Weixin o1, Weixin o2) {
							return o1.getSort().compareTo(o2.getSort());
						}
					});
				}
			}
		}
		modelMap.put("catalog", catalog);
		modelMap.put("crawlTimeList", crawlTimeList);
		modelMap.put("date",date);
		modelMap.put("timestamp", timestamp);
		modelMap.put("searchWord", searchWord);
		modelMap.put("newsList", newsList);
		
		modelAndView.addAllObjects(modelMap);
		modelAndView.setViewName("weixin/weixinList");
		return modelAndView;
	}
	
	
	/**
	 * 新闻聚合内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/{catalogName}/{newsNum}",produces = {"application/json;charset=UTF-8"})
	public String newsAggregation(@PathVariable String catalogName,@PathVariable String newsNum){
		List<Weixin> newsList = new ArrayList<Weixin>();
		Weixin news = new Weixin();
		news.setTitle("mybatis sql in 查询");
		news.setNewsNum("234456666776");
		news.setNewsLink("http://www.mybatis.org/core/getting-started.html");
		news.setCollectSource("腾讯新闻");
		news.setCollectTime("2016-04-25 09:00:00");
		newsList.add(news);
		newsList.add(news);
		newsList.add(news);
		newsList.add(news);
		newsList.add(news);
		return JSONArray.toJSONString(newsList);
	}
	
	/**
	 * 新闻趋势
	 */
	@ResponseBody
	@RequestMapping(value="/trend/{newsNum}",produces = {"application/json;charset=UTF-8"})
	public String newsTrend(@PathVariable String newsNum){
		List<String> tList = new ArrayList<String>();
		List<Double> bList = new ArrayList<Double>();
		Map<String,List> jMap = new HashMap<String,List>();
		tList.add("2016-04-25 09:00:00");
		tList.add("2016-04-25 10:00:00");
		tList.add("2016-04-25 11:00:00");
		bList.add(1.32d);
		bList.add(2.32d);
		bList.add(3.32d);
		bList.add(4.32d);
		jMap.put("xList", tList);
		jMap.put("yList", bList);
		return JSONArray.toJSONString(jMap);
	}
	
	@ResponseBody
	@RequestMapping(value="/source/{newsNum}",produces = {"application/json;charset=UTF-8"})
	public String newsSource(@PathVariable String newsNum){
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("newsNum", newsNum);
		List<Weixin> newsList = weixinService.getByWhere(whereMap);
		if(newsList != null && newsList.size() > 0){
			return JSONArray.toJSONString(newsList.get(0));
		}else{
			return JSONArray.toJSONString(new Weixin());
		}
	}
	
}
