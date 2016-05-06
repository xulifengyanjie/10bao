package cn.com.twsm.newstopic.controller.office;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

import cn.com.twsm.newstopic.model.CollectionType;
import cn.com.twsm.newstopic.service.CollectionTypeService;
import cn.com.twsm.newstopic.util.General;

@Controller
@RequestMapping("/collection")
public class CollectionController {

	@Resource
	private CollectionTypeService collectionTypeService;
	
	/**
	 * 用户收藏列表查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
		@RequestParam(value="type",defaultValue="") String type){
		ModelAndView modelAndView = new ModelAndView();
		Map<String , Object> whereMap = new HashMap<String, Object>();
		if(General.isNotEmpty(type))
			whereMap.put("type", type);
		PageInfo<CollectionType> page = collectionTypeService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.setViewName("collection/list");
		modelAndView.addObject("page",page);
		modelAndView.addObject("type",type);
		return modelAndView;
	}
	
	/**
	 * 备忘录删除
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public String del(@RequestParam Integer id){
		collectionTypeService.deleteByPrimaryKey(id);
		return JSONArray.toJSONString("success");
	}
}
