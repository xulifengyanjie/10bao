package cn.com.twsm.newstopic.controller.management;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.ChinaNews;
import cn.com.twsm.newstopic.model.ForeignNews;
import cn.com.twsm.newstopic.model.ForeignNewsResult;
import cn.com.twsm.newstopic.service.CatalogService;
import cn.com.twsm.newstopic.service.ForeignNewsResultService;
import cn.com.twsm.newstopic.service.ForeignNewsService;
import cn.com.twsm.newstopic.util.General;
import cn.com.twsm.newstopic.util.RpcUtil;
import cn.com.twsm.newstopic.vo.CountVo;
import cn.com.twsm.newstopic.vo.NewsAggregationVo;
import cn.com.twsm.newstopic.vo.SubNewsVo;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

/**
 * 境外媒体Controller
 *
 */
@Controller
@RequestMapping("overseas")
public class OverseasNewsController{
	private static final String INTERFACE_NAME = "SimilarNewsData.getNews";
	@Resource
	private ForeignNewsService foreignNewsService;
	@Resource
	private ForeignNewsResultService foreignNewsResultService;
	@Resource
	private CatalogService catalogService;
	
	@RequestMapping("/list")
	public ModelAndView newsList(@RequestParam(value="date", defaultValue="") String date,@RequestParam(value="timestamp", defaultValue="") String timestamp,@RequestParam(value="searchWord", defaultValue="") String searchWord,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws UnsupportedEncodingException{
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		PageInfo<ForeignNews> page = null;
		Catalog catalog = catalogService.getByCatalogCode("overseas");
		List<String> crawlTimeList = null;
		if(StringUtils.isNotEmpty(searchWord)){
			searchWord = new String(searchWord.getBytes("ISO-8859-1"),"UTF-8");
		}
		//获取新闻批次信息
		if(StringUtils.isNotEmpty(date)){
			if(catalog != null){
				whereMap.put("batchDate", date);
				whereMap.put("catalogId", catalog.getId());
				crawlTimeList =  foreignNewsResultService.selectByCrawlDate(whereMap);
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
				whereMap.put("title", searchWord);
				page = foreignNewsService.getByPage(pageNum, pageSize, whereMap);
			}else{
				page = null;
			}
		}else{
			if(catalog != null){
				whereMap.put("batchNum", catalog.getCrawlTime());
				whereMap.put("catalogId", catalog.getId());
				whereMap.put("title", searchWord);
				page = foreignNewsService.getByPage(pageNum, pageSize, whereMap);
				date = catalog.getCrawlTime().substring(0,8);
				timestamp = catalog.getCrawlTime();
				
				
				whereMap.clear();
				whereMap.put("batchDate", date);
				whereMap.put("catalogId", catalog.getId());
				crawlTimeList =  foreignNewsResultService.selectByCrawlDate(whereMap);
				
			}else{
				page = null;
			}
		}
		if(StringUtils.isEmpty(date)){
			date = General.getNow("yyyyMMdd");
		}
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("page", page);
		List<ForeignNews> newsList = new ArrayList<ForeignNews>();
	/*	if(page != null){
			List<ForeignNews> resultList = page.getList();
			if(resultList != null){
				newsList = foreignNewsService.getByNewsNums(resultList);
				if(newsList != null){
					for(ForeignNews news : newsList){
						for(ForeignNewsResult result : resultList){
							if(news.getNewsNum().equals(result.getNewsNum())){
								news.setNewsIndex(new BigDecimal(result.getWeight()));
								news.setSort(result.getSort());
							}
						}
					}
					Collections.sort(newsList, new Comparator<ForeignNews>(){
						public int compare(ForeignNews o1, ForeignNews o2) {
							return o1.getSort().compareTo(o2.getSort());
						}
					});
				}
			}
		}*/
		modelMap.put("catalog", catalog);
		modelMap.put("crawlTimeList", crawlTimeList);
		modelMap.put("date",date);
		modelMap.put("timestamp", timestamp);
		modelMap.put("searchWord", searchWord);
		modelMap.put("newsList", newsList);
		
		modelAndView.addAllObjects(modelMap);
		modelAndView.setViewName("overseas/overseasList");
		return modelAndView;
	}
	
	
	/**
	 * 新闻聚合内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/{catalogName}/{newsNum}",produces = {"application/json;charset=UTF-8"})
	public String newsAggregation(@PathVariable String catalogName,@PathVariable String newsNum){
		ForeignNews news = foreignNewsService.getByNewsNum(newsNum);
		XmlRpcClient client = null;
		try {
			client = RpcUtil.getXmlRpcClient(RpcUtil.NEWS_AGGREGATION_RPC_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> list = new ArrayList<String>();
		list.clear();
		list.add(news.getNewsLink());
		String result = null;
		try {
			result = (String)client.execute(INTERFACE_NAME , list);
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 新闻趋势
	 */
	@ResponseBody
	@RequestMapping(value="/trend/{newsNum}",produces = {"application/json;charset=UTF-8"})
	public String newsTrend(@PathVariable String newsNum){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("newsNum", newsNum);
		List<ForeignNewsResult> resultList = foreignNewsResultService.getByWhere(whereMap);
		List<String> tList = new ArrayList<String>();
		List<BigDecimal> bList = new ArrayList<BigDecimal>();
		Map<String,List> jMap = new HashMap<String,List>();
		if(resultList != null){
			for(ForeignNewsResult result : resultList){
				try {
					tList.add(format2.format(format.parse(result.getBatchNum())));
					BigDecimal weight = new BigDecimal(result.getWeight());
					weight = weight.setScale(2,BigDecimal.ROUND_HALF_UP);
					bList.add(weight);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		jMap.put("xList", tList);
		jMap.put("yList", bList);
		return JSONArray.toJSONString(jMap);
	}
	
	@ResponseBody
	@RequestMapping(value="/source/{newsNum}",produces = {"application/json;charset=UTF-8"})
	public String newsSource(@PathVariable String newsNum){
		ForeignNews news = foreignNewsService.getByNewsNum(newsNum);
		XmlRpcClient client = null;
		try {
			client = RpcUtil.getXmlRpcClient(RpcUtil.NEWS_AGGREGATION_RPC_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> list = new ArrayList<String>();
		list.clear();
		list.add(news.getNewsLink());
		String result = null;
		try {
			result = (String)client.execute(INTERFACE_NAME , list);
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
		if(!StringUtils.isEmpty(result)){
			NewsAggregationVo  newsAggregation = JSONArray.parseObject(result, NewsAggregationVo.class);
			List<List<SubNewsVo>> newsList = newsAggregation.getNews();
			if(newsList != null && newsList.size() > 0){
				for(List<SubNewsVo> voList : newsList){
					for(SubNewsVo vo : voList){
						news.setTitle(vo.getTitle());
						news.setNewsLink(vo.getUrl());
						news.setPublishTime(vo.getPublishTime());
						news.setPublishMedia(vo.getCollectSource());
					}
				}
			}
		}
		return JSONArray.toJSONString(news);
	}
	
	@ResponseBody
	@RequestMapping(value="/count",produces = {"application/json;charset=UTF-8"})
	public String newsCount(){
		List<ForeignNews> newsList = foreignNewsService.newsSourceCount();
		List<String> yList = new ArrayList<String>();
		List<CountVo> xList = new ArrayList<CountVo>();
		if(newsList != null){
			CountVo count = null;
			for(ForeignNews news : newsList){
				if(!StringUtils.isNotEmpty(news.getPublishMedia())){
					continue;
				}
				count = new CountVo();
				yList.add(news.getPublishMedia());
				count.setName(news.getPublishMedia());
				count.setValue(news.getSort());
				xList.add(count);
			}
		}
		Map<String,Object> jMap = new HashMap<String,Object>();
		jMap.put("xList", xList);
		jMap.put("yList", yList);
		return JSONArray.toJSONString(jMap);
	}
	
}
