package cn.com.twsm.newstopic.controller.management;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 栏目发布情况Controller
 *
 */
@Controller
@RequestMapping("publish")
public class CatalogPublishController{
	
	@RequestMapping("list")
	public String list(){
		return "publishedCatalog/list";
	}
}
