package cn.com.twsm.newstopic.controller.topic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/person")
public class PersonSelectController {

	@RequestMapping("/recommend")
	public ModelAndView recommend(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="type",defaultValue="") String type,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("personSelect/recommend");
		return modelAndView;
	}
	
	@RequestMapping("/analyze")
	public ModelAndView analyze(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			@RequestParam(value="type",defaultValue="") String type,@RequestParam(value="keyword", defaultValue="") String keyword){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("personSelect/analyze");
		return modelAndView;
	}
}
