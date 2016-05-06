package cn.com.twsm.newstopic.controller.office;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.twsm.newstopic.controller.common.SingleTableController;
import cn.com.twsm.newstopic.model.Weight;
import cn.com.twsm.newstopic.service.BaseService;
import cn.com.twsm.newstopic.service.WeightService;

/**
 * 权重管理
 * @author cp
 *
 */
@Controller
@RequestMapping("/weight")
public class WeightController extends  SingleTableController<Weight> {
	@Resource
	private WeightService weightService;

	@Override
	protected BaseService getBaseService() {
		return weightService;
	}

	@Override
	protected String getBaseViewName() {
		return "weight";
	}

	@Override
	protected Map<String, Object> getWhereMap() {
		Map<String, Object> whereMap = new HashMap<String,Object>();
		whereMap.put("itemType", "news");
		return whereMap;
	}
	
	
}
