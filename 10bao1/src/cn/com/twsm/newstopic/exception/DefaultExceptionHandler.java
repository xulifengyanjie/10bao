package cn.com.twsm.newstopic.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

public class DefaultExceptionHandler implements HandlerExceptionResolver {
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView model = new ModelAndView();
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("msg", ex.getMessage());
		ex.printStackTrace();
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
				.getHeader("X-Requested-With") != null && request.getHeader(
				"X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			model.addObject("msg", ex.getMessage());
			model.setViewName("common/error");
		}else{
			FastJsonJsonView view = new FastJsonJsonView();
			view.setAttributesMap(attributes);
			model.setView(view);
		}
		return model;
	}

}
