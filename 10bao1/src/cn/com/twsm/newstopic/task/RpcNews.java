package cn.com.twsm.newstopic.task;

import java.util.List;

import cn.com.twsm.newstopic.vo.AppVo;
import cn.com.twsm.newstopic.vo.BBSNewsVo;
import cn.com.twsm.newstopic.vo.NewsVo;
import cn.com.twsm.newstopic.vo.OverseasVo;
import cn.com.twsm.newstopic.vo.WeiboCommentVo;
import cn.com.twsm.newstopic.vo.WeiboVo;
import cn.com.twsm.newstopic.vo.WeixinVo;


public class RpcNews {
	private String batchNumber;
	private String column;
	private List<NewsVo> news;
	private List<BBSNewsVo> bbs;
	private List<WeiboVo> weibo;
	private List<WeixinVo> weixin;
	private List<AppVo> app;
	private List<OverseasVo> overseas;
	private List<WeiboCommentVo> weibo_comment;
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public List<NewsVo> getNews() {
		return news;
	}
	public void setNews(List<NewsVo> news) {
		this.news = news;
	}
	public List<BBSNewsVo> getBbs() {
		return bbs;
	}
	public void setBbs(List<BBSNewsVo> bbs) {
		this.bbs = bbs;
	}
	public List<WeiboVo> getWeibo() {
		return weibo;
	}
	public void setWeibo(List<WeiboVo> weibo) {
		this.weibo = weibo;
	}
	public List<WeixinVo> getWeixin() {
		return weixin;
	}
	public void setWeixin(List<WeixinVo> weixin) {
		this.weixin = weixin;
	}
	public List<AppVo> getApp() {
		return app;
	}
	public void setApp(List<AppVo> app) {
		this.app = app;
	}
	public List<OverseasVo> getOverseas() {
		return overseas;
	}
	public void setOverseas(List<OverseasVo> overseas) {
		this.overseas = overseas;
	}
	public List<WeiboCommentVo> getWeibo_comment() {
		return weibo_comment;
	}
	public void setWeibo_comment(List<WeiboCommentVo> weibo_comment) {
		this.weibo_comment = weibo_comment;
	}

}
