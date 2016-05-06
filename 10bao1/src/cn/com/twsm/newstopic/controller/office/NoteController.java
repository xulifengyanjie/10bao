package cn.com.twsm.newstopic.controller.office;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;

import cn.com.twsm.newstopic.model.CollectionType;
import cn.com.twsm.newstopic.model.Note;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.service.CollectionTypeService;
import cn.com.twsm.newstopic.service.NoteService;
import cn.com.twsm.newstopic.service.UserService;
import cn.com.twsm.newstopic.util.Constant;
import cn.com.twsm.newstopic.util.General;

/**
 * 备忘录
 * @author WuHao
 *
 */
@Controller
@RequestMapping("/note")
public class NoteController {
	
	@Resource
	private NoteService noteService;
	@Resource
	private CollectionTypeService collectionTypeService;
	@Resource
	private UserService userService;
	
	/**
	 * 工作任务查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/noteList")
	public ModelAndView queryNoteWork(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize){
		ModelAndView modelAndView = new ModelAndView();
		Map<String , Object> whereMap = new HashMap<String, Object>();
		whereMap.put("type", Constant.NOTE_TYPE_WORK);
		whereMap.put("type1", Constant.NOTE_TYPE_ONESELF);
		PageInfo<Note> page = noteService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.setViewName("note/noteList");
		modelAndView.addObject("page",page);
		return modelAndView;
	}
	
	
	/**
	 * 工作任务查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/remindList")
	public ModelAndView remindList(@RequestParam(value="pageNum", defaultValue="1") int pageNum,@RequestParam(value="pageSize", defaultValue="10") int pageSize){
		ModelAndView modelAndView = new ModelAndView();
		Map<String , Object> whereMap = new HashMap<String, Object>();
		whereMap.put("type", Constant.NOTE_TYPE_REMIND);
		PageInfo<Note> page = noteService.getByPage(pageNum, pageSize, whereMap);
		modelAndView.setViewName("note/remindList");
		modelAndView.addObject("page",page);
		return modelAndView;
	}
	
	/**
	 * 添加备忘录页面
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView add(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/note/addNote");
		return modelAndView;
	}
	
	/**
	 * 备忘录添加
	 * @return
	 */
	@RequestMapping("/doAdd")
	@ResponseBody
	public String doAdd(Note note){
		note.setAddTime(General.getNow());
		note.setStatus(Constant.NOTE_STATUS_PLAYING);
		note.setType(Constant.NOTE_TYPE_WORK);
		noteService.insert(note);
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 显示备忘录修改页面
	 * @return
	 */
	@RequestMapping(value="/edit/{id}" , method=RequestMethod.GET)
	public ModelAndView editNote(@PathVariable Integer id){
		Note note = noteService.selectByPrimaryKey(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("note", note);
		modelAndView.setViewName("/note/noteDetail");
		return modelAndView;
	}
	
	/**
	 * 备忘录修改
	 * @return
	 */
	@RequestMapping("/doEdit")
	public ModelAndView doEditNote(Note note){
		note.setReminderTime(General.getNow());
		noteService.updateByPrimaryKey(note);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/note/noteList");
		return modelAndView;
	}
	
	/**
	 * 备忘录删除
	 * @return
	 */
	@RequestMapping(value="/delete/{id}" , method=RequestMethod.GET)
	@ResponseBody
	public String delNote(@PathVariable Integer id){
		noteService.deleteByPrimaryKey(id);
		//modelAndView.setViewName("redirect:/note/noteList");
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 备忘录标记完成
	 * @return
	 */
	@RequestMapping(value="/complete/{id}" , method=RequestMethod.GET)
	public ModelAndView completeNote(@PathVariable Integer id){
		Note note = noteService.selectByPrimaryKey(id);
		note.setStatus(Constant.NOTE_STATUS_OVER);
		noteService.updateByPrimaryKey(note);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/note/noteList");
		return modelAndView;
	}
	
	/**
	 * 备忘录批量删除
	 * @return
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public String delNoteAll(@RequestParam("ids[]") List<Integer> ids){
		noteService.deleteByIds(ids);
		return JSONArray.toJSONString("success");
	}
	
	/**
	 * 备忘录收藏
	 * @return
	 */
	@RequestMapping(value="/collectNote")
	@ResponseBody
	public String collectNote(@RequestParam("ids[]") List<Integer> ids){
		Note note = null;
		Map<String , Object> whereMap = new HashMap<String, Object>();
		List<Note> list;
		if(ids!=null&&ids.size()>0){
			for (Integer id : ids) {
				whereMap.put("newsId", id);
				whereMap.put("type", "note");
				list = collectionTypeService.getByWhere(whereMap);
				if(list.size()>0){
					continue;
				}
				note = noteService.selectByPrimaryKey(id); 
				if(note==null)
					continue;
				CollectionType collectionType = new CollectionType();
				collectionType.setAddTime(General.getNow());
				collectionType.setNewsId(id);
				collectionType.setTitle(note.getTitle());
				collectionType.setType(Constant.COLLECTION_TYPE_NOTE);
				String loginName = SecurityUtils.getSubject().getPrincipal().toString();
				User user = userService.getByLoginName(loginName);
				collectionType.setUserId(user.getId());
				collectionTypeService.insert(collectionType);
			}
		}
		return JSONArray.toJSONString("success");
	}
}
