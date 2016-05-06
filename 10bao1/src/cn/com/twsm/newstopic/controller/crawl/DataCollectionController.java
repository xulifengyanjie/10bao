package cn.com.twsm.newstopic.controller.crawl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.twsm.newstopic.controller.common.SingleTableController;
import cn.com.twsm.newstopic.model.CrawlData;
import cn.com.twsm.newstopic.model.Interface;
import cn.com.twsm.newstopic.service.BaseService;
import cn.com.twsm.newstopic.service.DataCollectionService;
import cn.com.twsm.newstopic.util.Constant;
import cn.com.twsm.newstopic.util.General;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;


/**
 * 数据采集
 * @author xulifeng
 *
 */
@Controller
@RequestMapping("/dataCollection")
public class DataCollectionController extends SingleTableController<CrawlData>{
    @Resource
	private DataCollectionService dataCollectionService;
	/**
	 * 门户网站列表
	 * 默认门户网站列表
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@RequestMapping("/crawlDataList")
	public ModelAndView crawlDataList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="type", defaultValue=Constant.DATA_ACQUISITION_TYPE) String type,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("type", type);
		whereMap.put("keyword", keyword);
		PageInfo<CrawlData> page = dataCollectionService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.addObject("page",page);
		modelAndView.addObject("keyword",keyword);
		modelAndView.setViewName("crawl/portalSiteList");
		return modelAndView;
	}
	
	@RequestMapping("/weiboDataList")
	public ModelAndView weiboDataList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="type", defaultValue="weibo") String type,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("type", type);
		whereMap.put("keyword", keyword);
		PageInfo<CrawlData> page = dataCollectionService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.addObject("page",page);
		modelAndView.addObject("keyword",keyword);
		modelAndView.setViewName("crawl/weiboList");
		return modelAndView;
	}
	
	
	@RequestMapping("/weixinDataList")
	public ModelAndView weixinDataList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="type", defaultValue="weixin") String type,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("type", type);
		whereMap.put("keyword", keyword);
		PageInfo<CrawlData> page = dataCollectionService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.addObject("page",page);
		modelAndView.addObject("keyword",keyword);
		modelAndView.setViewName("crawl/weixinList");
		return modelAndView;
	}
	
	@RequestMapping("/forumDataList")
	public ModelAndView forumDataList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="type", defaultValue="forum") String type,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("type", type);
		whereMap.put("keyword", keyword);
		PageInfo<CrawlData> page = dataCollectionService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.addObject("page",page);
		modelAndView.addObject("keyword",keyword);
		modelAndView.setViewName("crawl/forumList");
		return modelAndView;
	}
	
	@RequestMapping("/mobileDataList")
	public ModelAndView mobileDataList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="type", defaultValue="mobile") String type,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("type", type);
		whereMap.put("keyword", keyword);
		PageInfo<CrawlData> page = dataCollectionService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.addObject("page",page);
		modelAndView.addObject("keyword",keyword);
		modelAndView.setViewName("crawl/mobileList");
		return modelAndView;
	}
	
	@RequestMapping("/overseasMainstreamMediaDataList")
	public ModelAndView overseasMainstreamMediaDataList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="type", defaultValue="overseasMainstreamMedia") String type,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("type", type);
		whereMap.put("keyword", keyword);
		PageInfo<CrawlData> page = dataCollectionService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.addObject("page",page);
		modelAndView.addObject("keyword",keyword);
		modelAndView.setViewName("crawl/overseasMainstreamMediaList");
		return modelAndView;
	}
	
	/**
	 *跳转添加页面
	 * @return
	 */
	@RequestMapping(value="/addSelect")
	public ModelAndView addUser(@RequestParam(value="type") String type){
		ModelAndView modelAndView = new ModelAndView();
		if(type.equals("overseasMainstreamMedia")){
			
			modelAndView.setViewName("/crawl/addOverseasMainstreamMedia");
		}else if(type.equals("mobile")){
			modelAndView.setViewName("/crawl/addMobile");
		}else if(type.equals("forum")){
			modelAndView.setViewName("/crawl/addForum");
		}else if(type.equals("weixin")){
			modelAndView.setViewName("/crawl/addWeixin");
		}else if(type.equals("weibo")){
			modelAndView.setViewName("/crawl/addWeibo");
		}else if(type.equals("portal")){
			modelAndView.setViewName("/crawl/addPortal");
		}
		return modelAndView;
	}
	
	/**
	 * 数据添加
	 * @return
	 */
	/**
	 * 
	@RequestMapping(value="/doAdd")
	public ModelAndView doAddUser(CrawlData crawlData){
		dataCollectionService.insert(crawlData);
		ModelAndView modelAndView = new ModelAndView();
		if(crawlData.getType().equals("overseasMainstreamMedia")){
			modelAndView.setViewName("redirect:/dataCollection/overseasMainstreamMediaDataList");
		}else if(crawlData.getType().equals("mobile")){
			modelAndView.setViewName("redirect:/dataCollection/mobileDataList");
		}else if(crawlData.getType().equals("forum")){
			modelAndView.setViewName("redirect:/dataCollection/forumDataList");
		}else if(crawlData.getType().equals("weixin")){
			modelAndView.setViewName("redirect:/dataCollection/weixinDataList");
		}else if(crawlData.getType().equals("weibo")){
			modelAndView.setViewName("redirect:/dataCollection/weiboDataList");
		}else if(crawlData.getType().equals("portal")){
			modelAndView.setViewName("redirect:/dataCollection/crawlDataList");
		}
		return modelAndView;
	}
	 */
	
	/**
	 * 数据修改
	 * @return
	 */
	@RequestMapping(value="/doDataDetail/{id}" , method=RequestMethod.GET)
	public ModelAndView doEditData(@PathVariable Integer id){
		CrawlData crawlData = dataCollectionService.selectByPrimaryKey(id);
		ModelAndView modelAndView = new ModelAndView();
		if(crawlData.getType().equals("overseasMainstreamMedia")){
			modelAndView.setViewName("crawl/overseasMainstreamMediaDetail");
		}else if(crawlData.getType().equals("mobile")){
			modelAndView.setViewName("crawl/mobileDetail");
		}else if(crawlData.getType().equals("forum")){
			modelAndView.setViewName("crawl/forumDetail");
		}else if(crawlData.getType().equals("weixin")){
			modelAndView.setViewName("crawl/weixinDetail");
		}else if(crawlData.getType().equals("weibo")){
			modelAndView.setViewName("crawl/weiboDetail");
		}else if(crawlData.getType().equals("portal")){
			modelAndView.setViewName("crawl/portalDetail");
		}
		modelAndView.addObject("crawlData",crawlData);
		return modelAndView;
	}
	
	/**
	 * 数据修改
	 * @return
	 */
	/**
	 * 
	@RequestMapping(value="/doEditData")
	public ModelAndView doEditData(CrawlData crawlData){
		dataCollectionService.updateByPrimaryKey(crawlData);
		ModelAndView modelAndView = new ModelAndView();
		if(crawlData.getType().equals("overseasMainstreamMedia")){
			modelAndView.setViewName("redirect:/dataCollection/overseasMainstreamMediaDataList");
		}else if(crawlData.getType().equals("mobile")){
			modelAndView.setViewName("redirect:/dataCollection/mobileDataList");
		}else if(crawlData.getType().equals("forum")){
			modelAndView.setViewName("redirect:/dataCollection/forumDataList");
		}else if(crawlData.getType().equals("weixin")){
			modelAndView.setViewName("redirect:/dataCollection/weixinDataList");
		}else if(crawlData.getType().equals("weibo")){
			modelAndView.setViewName("redirect:/dataCollection/weiboDataList");
		}else if(crawlData.getType().equals("portal")){
			modelAndView.setViewName("redirect:/dataCollection/crawlDataList");
		}
		return modelAndView;
	}
	 */
	
	
	@Override
	protected BaseService getBaseService() {
		return dataCollectionService;
	}

	@Override
	protected String getBaseViewName() {
      
		return "dataCollection";
	}

	@Override
	protected Map<String, Object> getWhereMap() {
		Map<String, Object> whereMap = new HashMap<String,Object>();
		return whereMap;
	}
	
	@RequestMapping("/editStatus")
	@ResponseBody
	public String editStatus(@RequestParam Integer id){
		CrawlData crawlData = dataCollectionService.selectByPrimaryKey(id);
		if(General.isEmpty(crawlData.getStatus())||"close".equals(crawlData.getStatus()))
			crawlData.setStatus("open");
		else
			crawlData.setStatus("close");
		dataCollectionService.updateByPrimaryKey(crawlData);
		return JSONArray.toJSONString("success");
	}
	

}