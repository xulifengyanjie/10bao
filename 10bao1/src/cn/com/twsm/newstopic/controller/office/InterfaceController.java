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

import cn.com.twsm.newstopic.controller.common.SingleTableController;
import cn.com.twsm.newstopic.exception.CommonException;
import cn.com.twsm.newstopic.model.Interface;
import cn.com.twsm.newstopic.service.BaseService;
import cn.com.twsm.newstopic.service.InterfaceService;
import cn.com.twsm.newstopic.util.General;


/**
 * 接口管理
 * @author cp
 *
 */
@Controller
@RequestMapping("/interface")
public class InterfaceController extends SingleTableController<Interface>{
	@Resource
	private InterfaceService interfaceService;
	
	@Override
	protected BaseService getBaseService() {
		return interfaceService;
	}
	@Override
	protected String getBaseViewName() {
		return "interface";
	}
	@Override
	protected Map<String, Object> getWhereMap() {
		Map<String, Object> whereMap = new HashMap<String,Object>();
		return whereMap;
	}
	
	@RequestMapping("/add")
	public ModelAndView add(){
		ModelAndView modeAndView = new ModelAndView();
		modeAndView.setViewName("interface/add");
		return modeAndView;
	}
	
	@RequestMapping("/editStatus")
	@ResponseBody
	public String editStatus(@RequestParam Integer id){
		Interface interf = interfaceService.selectByPrimaryKey(id);
		if(General.isEmpty(interf.getStatus())||"close".equals(interf.getStatus()))
			interf.setStatus("open");
		else
			interf.setStatus("close");
		interfaceService.updateByPrimaryKey(interf);
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 公共添加操作
	 * @return
	 * @throws CommonException 
	 */
	@ResponseBody
	@RequestMapping("/doAdd")
	protected String doAdd(Interface interf){
		interf.setAddTime(General.getNow());
		interfaceService.insert(interf);
		return JSONArray.toJSONString("success");
	}
}
