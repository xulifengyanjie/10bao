package cn.com.twsm.newstopic.controller.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.ChinaNews;
import cn.com.twsm.newstopic.model.Permission;
import cn.com.twsm.newstopic.model.Promotion;
import cn.com.twsm.newstopic.model.Weibo;
import cn.com.twsm.newstopic.service.CatalogService;
import cn.com.twsm.newstopic.service.ChinaNewsService;
import cn.com.twsm.newstopic.service.PromotionService;
import cn.com.twsm.newstopic.service.WeiboService;

import com.github.pagehelper.PageInfo;

/**
 * 首页Controller
 * @author cp
 *
 */
@Controller
public class IndexController {
	@Resource
	private ChinaNewsService chinaNewsService;
	
	@Resource
	private CatalogService catalogService;
	@Resource
	private PromotionService promotionService;
	@Resource
	private WeiboService weiboService;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		Catalog catalog = catalogService.getByCatalogName("china");
		if(catalog != null){
			whereMap.put("crawlTime", catalog.getCrawlTime());
			whereMap.put("newsLevel", 1);
			whereMap.put("catalogId", catalog.getId());
		}
		PageInfo<ChinaNews> page = chinaNewsService.getByPage(1, 10, whereMap);
		whereMap.clear();
		PageInfo<Promotion> page1 = promotionService.getByPage(1, 10, whereMap);
		PageInfo<Weibo> page2 = weiboService.getByPage(1, 5, whereMap);
		modelAndView.addObject("newsList", page.getList());
		modelAndView.addObject("promotionList", page1.getList());
		modelAndView.addObject("weiboList", page2.getList());
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		List<Permission> permissions=(List<Permission>) session.getAttribute("menus");
		for(Permission permission:permissions){

			if(permission.getPermissionUrl().equals("/index1")){
				modelAndView.setViewName("common/index1");
				break;
			}else if(permission.getPermissionUrl().equals("/index2")){
				modelAndView.setViewName("common/index2");
				break;
			}else{
				permission.getPermissionUrl().equals("/index3");
				modelAndView.setViewName("common/index");
			}
		}
		return modelAndView;
	}
	@RequestMapping("/management")
	public String management(){
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/news/society";
	}
	@RequestMapping("/topic")
	public String topic(){
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/baseSelect/hotNews";
	}
	@RequestMapping("/feed")
	public String feedback(){
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/feedback/generalFeedbackList";
	}
	@RequestMapping("/office")
	public String office(){
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/user/list";
	}
	@RequestMapping("/crawl")
	public String crawl(){
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/dataCollection/crawlDataList";
	}
	@RequestMapping("/analysis")
	public String analysis(){
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/analysis/41";
	}
	
	@RequestMapping("/noPermission")
	public String noPermission(){
		return "/common/noPermission";
	}
}
