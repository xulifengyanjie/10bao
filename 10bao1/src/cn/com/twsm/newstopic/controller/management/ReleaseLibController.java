package cn.com.twsm.newstopic.controller.management;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.twsm.newstopic.controller.common.SingleTableController;
import cn.com.twsm.newstopic.model.EditLib;
import cn.com.twsm.newstopic.service.BaseService;
import cn.com.twsm.newstopic.service.ReleaseLibService;

/**
 * 报社发布内容Controller
 *
 */
@Controller
@RequestMapping("release")
public class ReleaseLibController extends SingleTableController<EditLib>{
	@Resource
	private ReleaseLibService releaseLibService;
	
	@Override
	protected BaseService getBaseService() {
		return releaseLibService;
	}

	@Override
	protected String getBaseViewName() {
		return "release";
	}

	@Override
	protected Map<String, Object> getWhereMap() {
		Map<String,Object> whereMap = new HashMap<String,Object>();
		return whereMap;
	}
	
}
