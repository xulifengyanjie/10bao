package cn.com.twsm.newstopic.controller.common;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.twsm.newstopic.exception.CommonException;
import cn.com.twsm.newstopic.service.BaseService;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

/**
 * 单表操作公共控制器
 * @author cp
 *
 * @param <T>
 */
public abstract class SingleTableController<T> extends BaseController{
	
	protected abstract BaseService getBaseService();
	
	protected abstract String getBaseViewName();
	
	protected final String LIST = getBaseViewName() + "/list";
	
	protected final String ADD = getBaseViewName() + "/add";
	
	protected final String EDIT = getBaseViewName() + "/edit";
	
	protected final String REDIRECT_LIST = "redirect:/"+getBaseViewName()+"/list";
	
	protected abstract Map<String,Object> getWhereMap();
	
	/**
	 * 公共查询列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/list")
	protected ModelAndView list(T t ,HttpServletRequest request,@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		Map<String,Object> tmpMap = getWhereMap();
		for(Map.Entry<String, Object> m : tmpMap.entrySet()){
			Object obj = PropertyUtils.getProperty(t, m.getKey());
			if(obj == null){
				whereMap.put(m.getKey(), m.getValue());
			}else{
				whereMap.put(m.getKey(), obj);
			}
		}
		PageInfo<T> page = getBaseService().getByPage(pageNum, pageSize, whereMap);
		modelAndView.setViewName(LIST);
		modelAndView.addObject("page", page);
		modelAndView.addObject("item", t);
		return modelAndView;
	}
	
	/**
	 * 公共跳转添加页面
	 * @return
	 */
	@RequestMapping("/add")
	protected ModelAndView add(){
		ModelAndView model = new ModelAndView();
		model.setViewName(ADD);
		return model;
	}
	
	/**
	 * 公共添加操作
	 * @return
	 * @throws CommonException 
	 */
	@ResponseBody
	@RequestMapping("/doAdd")
	protected String doAdd(T t) throws CommonException{
		String result = "success";
		try {
			getBaseService().insert(t);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException("修改失败！");
		}
		return JSONArray.toJSONString(result);
	}
	
	/**
	 * 公共跳转到修改页面
	 * @return
	 */
	@RequestMapping(value="/edit/{id}" , method=RequestMethod.GET)
	protected ModelAndView edit(@PathVariable Integer id){
		T t = getBaseService().selectByPrimaryKey(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("item", t);
		modelAndView.setViewName(EDIT);
		return modelAndView;
	}
	
	/**
	 * 公共修改操作
	 * @return
	 * @throws CommonException 
	 */
	@ResponseBody
	@RequestMapping("/doEdit")
	protected String doEdit(T t) throws CommonException{
		String result = "success";
		try {
			getBaseService().updateByPrimaryKey(t);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException("修改失败！");
		}
		return JSONArray.toJSONString(result);
	}
	
	/**
	 * 公共删除单条操作
	 * @return
	 */
	@RequestMapping(value="/del/{id}" , method=RequestMethod.GET)
	protected String del(@PathVariable Integer id){
		getBaseService().deleteByPrimaryKey(id);
		return REDIRECT_LIST;
	}
	
	/**
	 * 公共删除单多条操作
	 * @return
	 */
	@RequestMapping(value="/dels")
	@ResponseBody
	protected String dels(@RequestParam("ids[]") List<Integer> ids){
		getBaseService().deleteByIds(ids);
		return JSONArray.toJSONString("success");
	}
}
