package cn.com.twsm.newstopic.controller.analysis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analysis")
public class ApplicationAnalysisController {
	@RequestMapping(value="/{type}")
	public String list(@PathVariable String type){
		
		return "analysis/list";
	}
}
