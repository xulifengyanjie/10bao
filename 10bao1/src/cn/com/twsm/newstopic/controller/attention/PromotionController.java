package cn.com.twsm.newstopic.controller.attention;

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
import cn.com.twsm.newstopic.model.Promotion;
import cn.com.twsm.newstopic.service.BaseService;
import cn.com.twsm.newstopic.service.ChinaNewsService;
import cn.com.twsm.newstopic.service.PromotionService;
import cn.com.twsm.newstopic.util.General;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

/**
 * 定向关注Controller
 * 
 * @author xulifeng
 * 
 */
@Controller
@RequestMapping("/promotion")
public class PromotionController extends SingleTableController<Promotion> {

	@Resource
	private PromotionService promotionService;
	@Resource
    private ChinaNewsService chinaNewsService;
	/**
	 * 跳转定向关注添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addAttention")
	public ModelAndView addAttention() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("news/addAttention");
		return modelAndView;
	}

	/**
	 * 定向关注添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doAttentionAdd")
	@ResponseBody
	public String doAddUser(Promotion promotion) {
		promotion.setAddTime(General.getNow());
		promotionService.insert(promotion);
		return JSONArray.toJSONString("success");
	}

	/**
	 * 定向关注列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/attentionList", method = RequestMethod.GET)
	public ModelAndView attentionList(
			@RequestParam(value = "pageNum", defaultValue = "1")
			int pageNum, @RequestParam(value = "pageSize", defaultValue = "10")
			int pageSize, @RequestParam(value = "type1", defaultValue = "境内")
			String type1) {
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("type1", type1);
		PageInfo<Promotion> page = promotionService.getByPage(pageNum,
				pageSize, whereMap);

		List<Promotion> promotionList = promotionService.getByWhere(whereMap);
		modelAndView.addObject("page", page);
		modelAndView.addObject("promotionList", promotionList);
		// modelAndView.addObject("keyword",keyword);
		// modelAndView.addObject("status",status);
		modelAndView.setViewName("news/attentionList");
		return modelAndView;
	}

	/**
	 * 异步获取定向关注列表
	 *
	 * @param type1
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxAttentionList", produces = { "application/json;charset=UTF-8" })
	public String attentionList1(
			@RequestParam(value = "type1", defaultValue = "境内")
			String type1) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("type1", type1);
		List<Promotion> promotionList = promotionService.getByWhere(whereMap);
		if (promotionList != null && promotionList.size() > 0) {
			return JSONArray.toJSONString(promotionList);
		}
		return null;
	}
	
	/**
	 * 跳转定向关注编辑页面
	 * @return
	 */
	@RequestMapping(value="/editAttention/{id}" , method=RequestMethod.GET)
	public ModelAndView editAttention(@PathVariable Integer id){
		Promotion promotion = promotionService.selectByPrimaryKey(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("news/edit");
		modelAndView.addObject("promotion",promotion);
		return modelAndView;
	}
	/**
	 * 

	@RequestMapping(value="/doEditAttention")
	public ModelAndView doEditData(Promotion promotion){
		promotion.setAddTime(General.getNow());
		promotionService.updateByPrimaryKey(promotion);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/promotion/attentionList");
		return modelAndView;
	}
	 */
	
	/**
	 * 根据新闻标题获取新闻数据
	 *
	 * @param type1
	 * @return
	 */
	
	@RequestMapping(value = "/newsList", method = RequestMethod.GET)
	public ModelAndView newsList(
			@RequestParam(value = "pageNum", defaultValue = "1")
			int pageNum, @RequestParam(value = "pageSize", defaultValue = "10")
			int pageSize, @RequestParam(value = "type", defaultValue = "境内")
			String type) {
		ModelAndView modelAndView = new ModelAndView();
		PageInfo<Map> page = promotionService.getByPageType(pageNum,
				pageSize, type);
		modelAndView.addObject("page", page);
		modelAndView.addObject("type", type);
		modelAndView.setViewName("news/newAttentionList");
		return modelAndView;
	}

	@Override
	protected BaseService getBaseService() {
		return promotionService;
	}

	@Override
	protected String getBaseViewName() {
		return "promotion";
	}

	@Override
	protected Map<String, Object> getWhereMap() {
		Map<String, Object> whereMap = new HashMap<String,Object>();
		return whereMap;
	}

}
