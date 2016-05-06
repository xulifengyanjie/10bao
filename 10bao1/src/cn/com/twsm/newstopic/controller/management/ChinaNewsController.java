package cn.com.twsm.newstopic.controller.management;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import cn.com.twsm.newstopic.controller.common.BaseController;
import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.ChinaNews;
import cn.com.twsm.newstopic.model.ChinaNewsResult;
import cn.com.twsm.newstopic.service.CatalogService;
import cn.com.twsm.newstopic.service.ChinaNewsResultService;
import cn.com.twsm.newstopic.service.ChinaNewsService;
import cn.com.twsm.newstopic.util.General;
import cn.com.twsm.newstopic.util.RpcUtil;
import cn.com.twsm.newstopic.vo.CountVo;
import cn.com.twsm.newstopic.vo.NewsAggregationVo;
import cn.com.twsm.newstopic.vo.SubNewsVo;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

/**
 * 国内新闻Controller
 * @author cp
 *
 */
@Controller
@RequestMapping("/news")
public class ChinaNewsController extends BaseController{
	private static final String INTERFACE_NAME = "SimilarNewsData.getNews";
	
	@Resource
	private ChinaNewsService chinaNewsService;
	
	@Resource
	private ChinaNewsResultService chinaNewsResultService;
	
	@Resource
	private CatalogService catalogService;

	@RequestMapping("/newsList")
	public ModelAndView list(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("news/list");
		return modelAndView;
	}
	
	@RequestMapping("/list")
	public ModelAndView newsList(@RequestParam(value="date", defaultValue="") String date,@RequestParam(value="timestamp", defaultValue="") String timestamp,@RequestParam(value="searchWord", defaultValue="") String searchWord,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws UnsupportedEncodingException{
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		PageInfo<ChinaNewsResult> page = null;
		Catalog catalog = catalogService.getByCatalogCode("news");
		List<String> crawlTimeList = null;
		if(StringUtils.isNotEmpty(searchWord)){
			searchWord = new String(searchWord.getBytes("ISO-8859-1"),"UTF-8");
		}
		//获取新闻批次信息
		if(StringUtils.isNotEmpty(date)){
			if(catalog != null){
				whereMap.put("batchDate", date);
				whereMap.put("catalogId", catalog.getId());
				crawlTimeList =  chinaNewsResultService.selectByCrawlDate(whereMap);
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
				page = chinaNewsService.getByPage(pageNum, pageSize, whereMap);
			}else{
				page = null;
			}
		}else{
			if(catalog != null){
				whereMap.put("batchNum", catalog.getCrawlTime());
				whereMap.put("catalogId", catalog.getId());
				whereMap.put("title", searchWord);
				page = chinaNewsService.getByPage(pageNum, pageSize, whereMap);
				date = catalog.getCrawlTime().substring(0,8);
				timestamp = catalog.getCrawlTime();
				
				
				whereMap.clear();
				whereMap.put("batchDate", date);
				whereMap.put("catalogId", catalog.getId());
				crawlTimeList =  chinaNewsResultService.selectByCrawlDate(whereMap);
				
			}else{
				page = null;
			}
		}
		if(StringUtils.isEmpty(date)){
			date = General.getNow("yyyyMMdd");
		}
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("page", page);
		List<ChinaNews> newsList = new ArrayList<ChinaNews>();
		/*if(page != null){
			List<ChinaNewsResult> resultList = page.getList();
			if(resultList != null){
				newsList = chinaNewsService.getByNewsNums(resultList);
				if(newsList != null){
					for(ChinaNews news : newsList){
						for(ChinaNewsResult result : resultList){
							if(news.getNewsNum().equals(result.getNewsNum())){
								news.setNewsIndex(new BigDecimal(result.getWeight()));
								news.setSort(result.getSort());
							}
						}
					}
					Collections.sort(newsList, new Comparator<ChinaNews>(){
						public int compare(ChinaNews o1, ChinaNews o2) {
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
		modelAndView.setViewName("news/chinaNewsList");
		return modelAndView;
	}
	
	/**
	 * 新闻列表
	 * @param pageNum
	 * @param pageSize
	 * @param catalogName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/{catalogName}")
	public ModelAndView list(@RequestParam(value="date", defaultValue="") String date,@RequestParam(value="timestamp", defaultValue="") String timestamp,@RequestParam(value="searchWord", defaultValue="") String searchWord,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,@PathVariable String catalogName) throws UnsupportedEncodingException{
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		PageInfo<ChinaNews> page = null;
		Catalog catalog = catalogService.getByCatalogName(catalogName);
		List<String> crawlTimeList = null;
		if(StringUtils.isNotEmpty(searchWord)){
			searchWord = new String(searchWord.getBytes("ISO-8859-1"),"UTF-8");
		}
		//获取新闻批次信息
		if(StringUtils.isNotEmpty(date)){
			if(catalog != null){
				whereMap.put("crawlDate", date);
				whereMap.put("catalogId", catalog.getId());
				crawlTimeList =  chinaNewsService.selectByCrawlDate(whereMap);
				whereMap.clear();
			}
		
			if(crawlTimeList != null && crawlTimeList.size() > 0){
				if(StringUtils.isNotEmpty(timestamp)){
					whereMap.put("crawlTime", timestamp);
				}else{
					whereMap.put("crawlTime", crawlTimeList.get(0));
					timestamp = crawlTimeList.get(0);
				}
				whereMap.put("newsLevel", 1);
				whereMap.put("title", searchWord);
				whereMap.put("catalogId", catalog.getId());
				page = chinaNewsService.getByPage(pageNum, pageSize, whereMap);
			}else{
				page = null;
			}
		}else{
			if(catalog != null){
				whereMap.put("crawlTime", catalog.getCrawlTime());
				whereMap.put("newsLevel", 1);
				whereMap.put("title", searchWord);
				whereMap.put("catalogId", catalog.getId());
				page = chinaNewsService.getByPage(pageNum, pageSize, whereMap);
				date = catalog.getCrawlTime().substring(0,8);
				timestamp = catalog.getCrawlTime();
				
				
				whereMap.clear();
				whereMap.put("crawlDate", date);
				whereMap.put("catalogId", catalog.getId());
				crawlTimeList =  chinaNewsService.selectByCrawlDate(whereMap);
				
			}else{
				page = null;
			}
		}
		if(StringUtils.isEmpty(date)){
			date = General.getNow("yyyyMMdd");
		}
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("page", page);
		modelMap.put("catalog", catalog);
		modelMap.put("crawlTimeList", crawlTimeList);
		modelMap.put("date",date);
		modelMap.put("timestamp", timestamp);
		modelMap.put("searchWord", searchWord);
		
		modelAndView.addAllObjects(modelMap);
		modelAndView.setViewName("news/chinaNewsList");
		return modelAndView;
	}
	/**
	 * 新闻聚合内容
	 * @param catalogName
	 * @param sort
	 * @param crawlTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/{catalogName}/{crawlTime}/{sort}",produces = {"application/json;charset=UTF-8"})
	public String subNewsList(@PathVariable String catalogName,@PathVariable int sort,@PathVariable String crawlTime){
		Map<String,Object> whereMap = new HashMap<String,Object>();
		Catalog catalog = catalogService.getByCatalogName(catalogName);
		List<ChinaNews> newsList = null;
		if(catalog != null){
			whereMap.put("crawlTime", crawlTime);
			whereMap.put("newsLevel", 2);
			whereMap.put("catalogId", catalog.getId());
			whereMap.put("sort", sort);
			newsList = chinaNewsService.getByWhere(whereMap);
		}
		return JSONArray.toJSONString(newsList);
	}
	
	/**
	 * 新闻聚合内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/{catalogName}/{newsNum}",produces = {"application/json;charset=UTF-8"})
	public String newsAggregation(@PathVariable String catalogName,@PathVariable String newsNum){
		ChinaNews news = chinaNewsService.getByNewsNum(newsNum);
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
		//NewsAggregationVo  newsAggregation = JSONArray.parseObject(result, NewsAggregationVo.class);
		//List<SubNewsVo> newsList = newsAggregation.getNews();
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
		List<ChinaNewsResult> resultList = chinaNewsResultService.getByWhere(whereMap);
		List<String> tList = new ArrayList<String>();
		List<BigDecimal> bList = new ArrayList<BigDecimal>();
		Map<String,List> jMap = new HashMap<String,List>();
		if(resultList != null){
			for(ChinaNewsResult result : resultList){
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
		ChinaNews news = chinaNewsService.getByNewsNum(newsNum);
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
		/*String str = JSONArray.toJSONString(news);
		NewsAggregationVo vo = new NewsAggregationVo();
		List<SubNewsVo> nList = new ArrayList<SubNewsVo>();
		SubNewsVo sn = new SubNewsVo();
		sn.setUrl("uuuuu");
		sn.setTitle("ttttt");
		sn.setCollectSource("ccc");
		sn.setCollectTime("ppppttt");
		SubNewsVo sn2 = new SubNewsVo();
		sn2.setUrl("uuuuu2");
		sn2.setTitle("ttttt2");
		sn2.setCollectSource("ccc2");
		sn2.setCollectTime("ppppttt2");
		nList.add(sn);
		nList.add(sn2);
		vo.setNews(nList);
		String json = JSONArray.toJSONString(vo);*/
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
		List<ChinaNews> newsList = chinaNewsService.newsSourceCount();
		List<String> yList = new ArrayList<String>();
		List<CountVo> xList = new ArrayList<CountVo>();
		if(newsList != null){
			CountVo count = null;
			for(ChinaNews news : newsList){
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
	
	
	/**
	 * 新闻趋势
	 * @param catalogName
	 * @param newsId
	 * @param days
	 * @return
	 * @throws IOException
	 * @throws XmlRpcException
	 *//*
	@ResponseBody
	@RequestMapping(value="/trend/{catalogName}/{newsId}/{days}",produces = {"application/json;charset=UTF-8"})
	public String trend(@PathVariable String catalogName,@PathVariable int newsId,@PathVariable int days) throws IOException, XmlRpcException{
		ChinaNews chinaNews = chinaNewsService.selectByPrimaryKey(newsId);
		XmlRpcClient client = RpcUtil.getXmlRpcClient(RpcUtil.INTERNAL_NEWS_RPC_URL);
		Object[] params = new Object[3];
		params[0] = chinaNews.getTitle();
		params[1] = catalogName;
		params[2] = days;
		String columnsJson = (String)client.execute("topnews.readWeightData", params);
		List<List<Object>> objList =(List<List<Object>>) JSONArray.parse(columnsJson);
		List<String> tList = new ArrayList<String>();
		List<Double> bList = new ArrayList<Double>();
		if(objList != null){
			if(objList.size() <= 1){
				tList.add("0");
				bList.add(0d);
			}
			for(List<Object> objs : objList){
				for(int i=0;i<objs.size();i++){
					if(i==0){
						tList.add(General.timeLong2Str((Long)objs.get(i),"yyyy-MM-dd HH:mm:ss"));
					}
					if(i==1){
						bList.add(((BigDecimal)objs.get(i)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					}
				}
			}
		}
		Map<String,List> jMap = new HashMap<String,List>();
		jMap.put("xList", tList);
		jMap.put("yList", bList);
		return JSONArray.toJSONString(jMap);
	}
	
	@ResponseBody
	@RequestMapping(value="/source/{newsId}",produces = {"application/json;charset=UTF-8"})
	public String source(@PathVariable int newsId) throws IOException, XmlRpcException{
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("id", newsId);
		List<ChinaNews> newsList = chinaNewsService.getByWhere(whereMap);
		List<ChinaNews> resultList = getSourceNews(newsList,whereMap);
		if(resultList != null && resultList.size() > 0){
			return JSONArray.toJSONString(resultList.get(0));
		}else{
			return JSONArray.toJSONString(new ChinaNews());
		}
		
	}
	
	private List<ChinaNews> getSourceNews(List<ChinaNews> newsList,Map<String,Object> whereMap){
		if(newsList == null){
			return null;
		}
		List<ChinaNews> tempList = null;
		//根据当前新闻查询与当前新闻相关的新闻
		if(newsList.size() == 1){
			whereMap.clear();
			whereMap.put("crawlTime", newsList.get(0).getCrawlTime());
			whereMap.put("catalogId", newsList.get(0).getCatalogId());
			whereMap.put("sort", newsList.get(0).getSort());
			whereMap.put("orderBy", "PUBLISH_TIME ASC");
			tempList = chinaNewsService.getByWhere(whereMap);
		}else if(newsList.size() != 0){
			whereMap.clear();
			whereMap.put("newsLinkMd5_in", newsList);
			whereMap.put("crawlTime_lt", newsList.get(0).getCrawlTime());
			whereMap.put("orderBy", "PUBLISH_TIME ASC");
			tempList = chinaNewsService.getByWhere(whereMap);
		}
		
		//如果已经找到最早的新闻
		if(tempList == null || tempList.size() <= 1){
			return newsList;
		}
		//如果没有找到最早的新闻
		else{
			return getSourceNews(tempList,whereMap);
		}
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		ChinaNews news = new ChinaNews();
		int i = 1/0;
		return JSONArray.toJSONString(news);
	}*/
	
	public static void main(String[] args) {
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
		System.out.println(JSONArray.toJSONString(jMap));
	}
}
