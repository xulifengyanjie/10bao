package cn.com.twsm.newstopic.controller.management;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.twsm.newstopic.controller.common.SingleTableController;
import cn.com.twsm.newstopic.model.EditLib;
import cn.com.twsm.newstopic.service.BaseService;
import cn.com.twsm.newstopic.service.EditNewsService;

/**
 * 待编稿库信息Controller
 *
 */
@Controller
@RequestMapping("editNews")
public class EditNewsController extends SingleTableController<EditLib>{
	@Resource
	private EditNewsService editNewsService;
	
	@Override
	protected BaseService getBaseService() {
		return editNewsService;
	}

	@Override
	protected String getBaseViewName() {
		return "editNews";
	}

	@Override
	protected Map<String, Object> getWhereMap() {
		Map<String,Object> whereMap = new HashMap<String,Object>();
		return whereMap;
	}
	
}
