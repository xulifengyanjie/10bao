package cn.com.twsm.newstopic.controller.topic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/behavious")
public class BehaviousController {

	@RequestMapping("/area")
	public ModelAndView area(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("behavious/area");
		return modelAndView;
	}
	@RequestMapping("/rate")
	public ModelAndView rate(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("behavious/rate");
		return modelAndView;
	}
}
