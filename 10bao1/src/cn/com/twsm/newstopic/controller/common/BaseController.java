package cn.com.twsm.newstopic.controller.common;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公共控制器
 * 
 * @author cp
 * 
 */
public class BaseController {
	/*protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(int.class, new CustomNumberEditor(
				int.class, true));
		binder.registerCustomEditor(long.class, new CustomNumberEditor(
				long.class, true));
	}*/

	/**
	 * 请求异常
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 */
	@RequestMapping(value = "/error_404", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String error_404() throws Exception {
		return "{\"msg\":\"找不到页面\"}";
	}

	/**
	 * 服务器异常
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/error_500", produces = "text/html;charset=UTF-8")
	public String error_500() {
		return "{\"msg\":\"服务器处理失败\"}";
	}
}
