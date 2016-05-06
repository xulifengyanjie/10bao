package cn.com.twsm.newstopic.task;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import cn.com.twsm.newstopic.model.BbsNews;
import cn.com.twsm.newstopic.model.BbsNewsResult;
import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.ChinaNews;
import cn.com.twsm.newstopic.model.ChinaNewsResult;
import cn.com.twsm.newstopic.model.ForeignNews;
import cn.com.twsm.newstopic.model.ForeignNewsResult;
import cn.com.twsm.newstopic.model.Weibo;
import cn.com.twsm.newstopic.model.WeiboResult;
import cn.com.twsm.newstopic.model.Weixin;
import cn.com.twsm.newstopic.model.WeixinResult;
import cn.com.twsm.newstopic.service.BbsNewsResultService;
import cn.com.twsm.newstopic.service.BbsNewsService;
import cn.com.twsm.newstopic.service.CatalogService;
import cn.com.twsm.newstopic.service.ChinaNewsResultService;
import cn.com.twsm.newstopic.service.ChinaNewsService;
import cn.com.twsm.newstopic.service.ForeignNewsResultService;
import cn.com.twsm.newstopic.service.ForeignNewsService;
import cn.com.twsm.newstopic.service.WeiboResultService;
import cn.com.twsm.newstopic.service.WeiboService;
import cn.com.twsm.newstopic.service.WeixinResultService;
import cn.com.twsm.newstopic.service.WeixinService;
import cn.com.twsm.newstopic.util.RpcUtil;
import cn.com.twsm.newstopic.vo.BBSNewsVo;
import cn.com.twsm.newstopic.vo.NewsVo;
import cn.com.twsm.newstopic.vo.OverseasVo;
import cn.com.twsm.newstopic.vo.WeiboVo;
import cn.com.twsm.newstopic.vo.WeixinVo;

import com.alibaba.fastjson.JSONArray;


public class ChinaNewsTask {
	private static final String INTERFACE_NAME = "BasicDatasHandler.getBasicDatas";
	
	@Resource
	private ChinaNewsService chinaNewsService;
	
	@Resource
	private CatalogService catalogService;
	
	@Resource
	private ChinaNewsResultService chinaNewsResultService;
	
	@Resource
	private BbsNewsResultService bbsNewsResultService;
	
	@Resource
	private BbsNewsService bbsNewsService;
	
	@Resource
	private WeixinResultService weixinResultService;
	
	@Resource
	private WeixinService weixinService;
	
	@Resource
	private ForeignNewsService foreignNewsService;
	
	@Resource
	private ForeignNewsResultService foreignNewsResultService;
	
	@Resource
	private WeiboResultService weiboResultService;
	
	@Resource
	private WeiboService weiboService;
	
	public static void main(String[] args) throws Exception{
		String url = "http://192.168.118.52:8080/adapter/XmlRpcServer";
		//String url = "http://192.168.118.126:8080/adapter/XmlRpcServer";
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(url));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		List<String> list = new ArrayList<String>();
		/*list.add("http://news.sina.com.cn/o/2016-04-02/doc-ifxqxcnr5195964.shtml");
		//新闻聚合接口
		String result=(String)client.execute("SimilarNewsData.getNews",list);
		NewsAggregation  newsAggregation = JSONArray.parseObject(result, NewsAggregation.class);
		List<SubNews> newsList = newsAggregation.getNews();
		if(newsList != null){
			for(SubNews news : newsList){
				System.out.println(news.getTitle() + "\t" + news.getCollectSource() + "\t" + news.getCollectTime() + "\t" + news.getUrl()  );
			}
		}*/
		//新闻列表接口
		list.clear();
		list.add("news");
		String newsResult =(String)client.execute("BasicDatasHandler.getBasicDatas",list);
		System.out.println(newsResult);
		RpcNews  rpcNews = JSONArray.parseObject(newsResult, RpcNews.class);
		List<NewsVo> nList = rpcNews.getNews();
		if(nList != null){
			for(NewsVo news : nList){
				//System.out.println(news.getId() + "\t" + news.getWeight() + "\t" + news.getTitle() + "\t" + news.getCollectSource() + "\t" + news.getCollectTime() + "\t" + news.getNewsLink()  );
			}
		}
	}
	
	
	/**
	 * 通过RPC接口获取分析结果 
	 */
	public void getAnalysisResult(){
			XmlRpcClient client = null;
			try {
				client = RpcUtil.getXmlRpcClient(RpcUtil.INTERNAL_NEWS_RPC_URL);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			List<String> list = new ArrayList<String>();
			
			//获取国内新闻分析结果
			try {
				getNewsResult(client, list ,"news");
			} catch (Exception e) {
				System.out.println("门户媒体接口数据导入失败！");
				e.printStackTrace();
			}
			
			//获取境外新闻分析结果
			try {
				getOverseasNewsResult(client, list ,"overseas");
			} catch (Exception e) {
				System.out.println("境外媒体接口数据导入失败！");
				e.printStackTrace();
			}
			
			//获取微博分析结果
			try {
				getWeiboResult(client, list,"weibo");
			} catch (Exception e) {
				System.out.println("微博接口数据导入失败！");
				e.printStackTrace();
			}
			
			//获取微信分析结果
			try {
				getWeixinResult(client, list,"weixin");
			} catch (Exception e) {
				System.out.println("微信接口数据导入失败！");
				e.printStackTrace();
			}
			
			//获取移动客户端新闻分析结果
			//getAppNewsResult(client, list,"app");
			
			//获取微博评论分析结果
			//getWeiboCommentResult(client, list,"weibo_comment");
			
			//获取论坛分析结果
			try {
				getBbsNewsResult(client, list,"bbs");
			} catch (Exception e) {
				System.out.println("论坛接口数据导入失败！");
				e.printStackTrace();
			}
			
	}


	private void getBbsNewsResult(XmlRpcClient client, List<String> list,
			String column) throws XmlRpcException {
		RpcNews rpcResult = parseJson(client, list, column);
		String crawlTime = rpcResult.getBatchNumber();
		Catalog catalog = new Catalog();
		catalog = catalogService.getByCatalogCode(column);
		if(catalog != null && StringUtils.isNotEmpty(crawlTime) && !crawlTime.equals(catalog.getCrawlTime())){
			catalog.setCrawlTime(crawlTime);
			saveBbsNews(catalog,rpcResult,column);
		}
	}


	private void saveBbsNews(Catalog catalog, RpcNews rpcResult, String column) {
		List<BbsNews> newsList = new ArrayList<BbsNews>();
		List<BbsNewsResult> newsResultList = new ArrayList<BbsNewsResult>();
		if(rpcResult != null && rpcResult.getBbs() != null){
			BbsNews bbs = null;
			BbsNewsResult bbsResult = null;
			BbsNews tmp = null;
			BBSNewsVo news = null;
			for(int i=0;i<rpcResult.getBbs().size();i++){
				buildBBS(catalog, rpcResult, newsList, newsResultList, i,bbs,bbsResult,tmp,news);
			}
			bbsNewsService.batchAddBbsNews(newsList, catalog, newsResultList);
		}
	}


	private void buildBBS(Catalog catalog, RpcNews rpcResult,
			List<BbsNews> newsList, List<BbsNewsResult> newsResultList, int i,BbsNews bbs,BbsNewsResult bbsResult,BbsNews tmp,BBSNewsVo news) {
		news = rpcResult.getBbs().get(i);
		bbs = new BbsNews();
		bbs.setWeight(news.getWeight());
		bbs.setBigType(news.getBigType());
		bbs.setCollectSource(news.getCollectSource());
		bbs.setCollectTime(news.getCollectTime());
		bbs.setCommentsCount(Integer.valueOf(news.getCommentsCount()));
		bbs.setForumLink(news.getForumLink());
		bbs.setHitsCount(Integer.valueOf(news.getHitsCount()));
		bbs.setNewsNum(news.getId());
		bbs.setPoster(news.getPoster());
		bbs.setPostTime(news.getPostTime());
		bbs.setSmallType(news.getSmallType());
		bbs.setSubType(news.getSubType());
		bbs.setTitle(news.getTitle());
		tmp = bbsNewsService.getByNewsNum(news.getId());
		if(tmp == null){
			newsList.add(bbs);
		}
		bbsResult = new BbsNewsResult();
		bbsResult.setWeight(news.getWeight());
		bbsResult.setBatchDate(rpcResult.getBatchNumber().substring(0, 8));
		bbsResult.setBatchNum(rpcResult.getBatchNumber());
		bbsResult.setCatalogId(catalog.getId());
		bbsResult.setNewsNum(news.getId());
		bbsResult.setSort(i+1);
		newsResultList.add(bbsResult);
	}


	private void getWeiboCommentResult(XmlRpcClient client, List<String> list,String column) throws XmlRpcException {
		RpcNews rpcResult = parseJson(client, list, column);
		String crawlTime = rpcResult.getBatchNumber();
		Catalog catalog = new Catalog();
		catalog = catalogService.getByCatalogCode(column);
		if(catalog != null && StringUtils.isNotEmpty(crawlTime) && !crawlTime.equals(catalog.getCrawlTime())){
			catalog.setCrawlTime(crawlTime);
			saveWeiboComment(catalog,rpcResult,column);
		}
	}


	private void saveWeiboComment(Catalog catalog, RpcNews rpcResult, String column) {
		
	}


	private void getAppNewsResult(XmlRpcClient client, List<String> list,String column) throws XmlRpcException {
		RpcNews rpcResult = parseJson(client, list, column);
		String crawlTime = rpcResult.getBatchNumber();
		Catalog catalog = new Catalog();
		catalog = catalogService.getByCatalogCode(column);
		if(catalog != null && StringUtils.isNotEmpty(crawlTime) && !crawlTime.equals(catalog.getCrawlTime())){
			catalog.setCrawlTime(crawlTime);
			saveAppNews(catalog,rpcResult,column);
		}
	}


	private void saveAppNews(Catalog catalog, RpcNews rpcResult, String column) {
		
	}


	private void getWeixinResult(XmlRpcClient client, List<String> list,String column) throws XmlRpcException {
		RpcNews rpcResult = parseJson(client, list, column);
		String crawlTime = rpcResult.getBatchNumber();
		Catalog catalog = new Catalog();
		catalog = catalogService.getByCatalogCode(column);
		if(catalog != null && StringUtils.isNotEmpty(crawlTime) && !crawlTime.equals(catalog.getCrawlTime())){
			catalog.setCrawlTime(crawlTime);
			saveWeixin(catalog,rpcResult,column);
		}
	}


	private void saveWeixin(Catalog catalog, RpcNews rpcResult, String column) {
		List<Weixin> newsList = new ArrayList<Weixin>();
		List<WeixinResult> newsResultList = new ArrayList<WeixinResult>();
		if(rpcResult != null && rpcResult.getWeixin() != null){
			Weixin weixin = null;
			WeixinResult wxResult = null;
			Weixin tmp = null;
			WeixinVo news = null;
			for(int i=0;i<rpcResult.getWeixin().size();i++){
				buildWeixin(catalog, rpcResult, newsList, newsResultList, i, weixin ,wxResult,tmp,news);
			}
			weixinService.batchAddWeixinNews(newsList, catalog, newsResultList);
		}
	}


	private void buildWeixin(Catalog catalog, RpcNews rpcResult,
			List<Weixin> newsList, List<WeixinResult> newsResultList, int i,
			Weixin wx, WeixinResult wxResult, Weixin tmp, WeixinVo news) {
		news = rpcResult.getWeixin().get(i);
		wx = new Weixin();
		wx.setWeight(news.getWeight());
		wx.setBigType(news.getBigType());
		wx.setCollectSource(news.getCollectSource());
		wx.setCollectTime(news.getCollectTime());
		wx.setCommentsCount(news.getCommentsCount());
		wx.setNewsLink(news.getNewsLink());
		wx.setHitsCount(news.getHitsCount());
		wx.setNewsNum(news.getId());
		wx.setSmallType(news.getSmallType());
		wx.setSubType(news.getSubType());
		wx.setTitle(news.getTitle());
		wx.setSupportCount(news.getSupportCount());
		wx.setTransmitCount(news.getTransmitCount());
		wx.setCommentsCount(news.getCommentsCount());
		wx.setSmallType(news.getSmallType());
		wx.setNewsSource(news.getNewsSource());
		wx.setAuthor(news.getAuthor());
		wx.setPublishTime(news.getPublishTime());
		tmp = weixinService.getByNewsNum(news.getId());
		if(tmp == null){
			newsList.add(wx);
		}
		wxResult = new WeixinResult();
		wxResult.setWeight(news.getWeight());
		wxResult.setBatchDate(rpcResult.getBatchNumber().substring(0, 8));
		wxResult.setBatchNum(rpcResult.getBatchNumber());
		wxResult.setCatalogId(catalog.getId());
		wxResult.setNewsNum(news.getId());
		wxResult.setSort(i+1);
		newsResultList.add(wxResult);
	}


	private void getWeiboResult(XmlRpcClient client, List<String> list,String column) throws XmlRpcException {
		RpcNews rpcResult = parseJson(client, list, column);
		String crawlTime = rpcResult.getBatchNumber();
		Catalog catalog = new Catalog();
		catalog = catalogService.getByCatalogCode(column);
		if(catalog != null && StringUtils.isNotEmpty(crawlTime) && !crawlTime.equals(catalog.getCrawlTime())){
			catalog.setCrawlTime(crawlTime);
			saveWeibo(catalog,rpcResult,column);
		}
	}


	private void saveWeibo(Catalog catalog, RpcNews rpcResult, String column) {
		List<Weibo> newsList = new ArrayList<Weibo>();
		List<WeiboResult> newsResultList = new ArrayList<WeiboResult>();
		if(rpcResult != null && rpcResult.getWeibo() != null){
			for(int i=0;i<rpcResult.getWeibo().size();i++){
				buildWeiboNews(catalog, rpcResult, newsList, newsResultList, i);
			}
		}
		weiboService.batchAddWeiboNews(newsList, catalog, newsResultList);
	}


	private void buildWeiboNews(Catalog catalog, RpcNews rpcResult,
			List<Weibo> newsList, List<WeiboResult> newsResultList, int i) {
		Weibo cn = null;
		WeiboResult result= null;
		Weibo tmp= null;
		WeiboVo news= null;
		news = rpcResult.getWeibo().get(i);
		
		cn = new Weibo();
		cn.setCommentsCount(news.getCommentsCount());
		cn.setRepostsCount(news.getRepostsCount());
		cn.setPublisher(news.getPublisher());
		cn.setCreatedAt(news.getCreatedAt());
		cn.setSource(news.getSource());
		cn.setText(news.getText());
		cn.setLink(news.getLink());
		cn.setWeiboId(news.getWeiboId());
		cn.setWeight(news.getWeight());
		tmp = weiboService.getByNewsNum(news.getWeiboId());
		if(tmp == null){
			newsList.add(cn);
		}
		result = new WeiboResult();
		result.setWeight(news.getWeight());
		result.setBatchDate(rpcResult.getBatchNumber().substring(0, 8));
		result.setBatchNum(rpcResult.getBatchNumber());
		result.setCatalogId(catalog.getId());
		result.setNewsNum(news.getWeiboId());
		result.setSort(i+1);
		newsResultList.add(result);
	}


	private void getOverseasNewsResult(XmlRpcClient client, List<String> list,String column) throws XmlRpcException {
		RpcNews rpcResult = parseJson(client, list, column);
		String crawlTime = rpcResult.getBatchNumber();
		Catalog catalog = new Catalog();
		catalog = catalogService.getByCatalogCode(column);
		if(catalog != null && StringUtils.isNotEmpty(crawlTime) && !crawlTime.equals(catalog.getCrawlTime())){
			catalog.setCrawlTime(crawlTime);
			saveOverseasNews(catalog,rpcResult,column);
		}
	}

	private void saveOverseasNews(Catalog catalog, RpcNews rpcResult,
			String column) {
		List<ForeignNews> newsList = new ArrayList<ForeignNews>();
		List<ForeignNewsResult> newsResultList = new ArrayList<ForeignNewsResult>();
		if(rpcResult != null && rpcResult.getOverseas() != null){
			for(int i=0;i<rpcResult.getOverseas().size();i++){
				buildOverseasNews(catalog, rpcResult, newsList, newsResultList, i);
			}
		}
		foreignNewsService.batchAddForeignNews(newsList,catalog,newsResultList);
	}



	private void buildOverseasNews(Catalog catalog, RpcNews rpcResult,
			List<ForeignNews> newsList, List<ForeignNewsResult> newsResultList,
			int i) {
		ForeignNews cn = null;
		ForeignNewsResult result= null;
		ForeignNews tmp= null;
		OverseasVo news= null;
		news = rpcResult.getOverseas().get(i);
		
		cn = new ForeignNews();
		cn.setNewsIndex(new BigDecimal(news.getWeight()));
		cn.setTitle(news.getTitle());
		cn.setPublishTime(news.getPublishTime());
		cn.setPublishMedia(news.getCollectSource());
		cn.setNewsLink(news.getNewsLink());
		cn.setCatalogId(catalog.getId());
		cn.setCrawlTime(catalog.getCrawlTime());
		cn.setNewsNum(news.getId());
		tmp = foreignNewsService.getByNewsNum(news.getId());
		if(tmp == null){
			newsList.add(cn);
		}
		result = new ForeignNewsResult();
		result.setWeight(news.getWeight());
		result.setBatchDate(rpcResult.getBatchNumber().substring(0, 8));
		result.setBatchNum(rpcResult.getBatchNumber());
		result.setCatalogId(catalog.getId());
		result.setNewsNum(news.getId());
		result.setSort(i+1);
		newsResultList.add(result);
	}


	private RpcNews parseJson(XmlRpcClient client, List<String> list, String column)
			throws XmlRpcException {
		RpcNews rpcResult = getRpcResult(client, list, column);
		return rpcResult;
	}


	private RpcNews getRpcResult(XmlRpcClient client, List<String> list,
			String column) throws XmlRpcException {
		list.clear();
		list.add(column);
		String result = (String)client.execute(INTERFACE_NAME, list);
		RpcNews rpcResult = JSONArray.parseObject(result, RpcNews.class);
		return rpcResult;
	}


	private void getNewsResult(XmlRpcClient client, List<String> list,String column) throws XmlRpcException {
		RpcNews rpcResult = parseJson(client, list, column);
		String crawlTime = rpcResult.getBatchNumber();
		Catalog catalog = new Catalog();
		catalog = catalogService.getByCatalogCode(column);
		if(catalog != null && StringUtils.isNotEmpty(crawlTime) && !crawlTime.equals(catalog.getCrawlTime())){
			catalog.setCrawlTime(crawlTime);
			saveColumnNews(catalog,rpcResult,column);
		}
	}
	

	private void saveColumnNews(Catalog catalog,RpcNews rpcResult,String column) {
		List<ChinaNews> newsList = new ArrayList<ChinaNews>();
		List<ChinaNewsResult> newsResultList = new ArrayList<ChinaNewsResult>();
		if(rpcResult != null && rpcResult.getNews() != null){
			for(int i=0;i<rpcResult.getNews().size();i++){
				buildNewsObj(catalog, rpcResult, newsList, newsResultList, i);
			}
		}
		chinaNewsService.batchAddChinaNews(newsList,catalog,newsResultList);
	}


	private void buildNewsObj(Catalog catalog, RpcNews rpcResult,
			List<ChinaNews> newsList, List<ChinaNewsResult> newsResultList,
			int i) {
		ChinaNews cn = null;
		ChinaNewsResult result= null;
		ChinaNews tmp= null;
		NewsVo news= null;
		news = rpcResult.getNews().get(i);
		
		cn = new ChinaNews();
		cn.setNewsIndex(new BigDecimal(news.getWeight()));
		cn.setTitle(news.getTitle());
		cn.setPublishTime(news.getPublishTime());
		cn.setPublishMedia(news.getNewsSource());
		cn.setNewsLink(news.getNewsLink());
		cn.setCatalogId(catalog.getId());
		cn.setCrawlTime(catalog.getCrawlTime());
		cn.setSort(i+1);
		cn.setNewsNum(news.getId());
		cn.setCrawlDate(catalog.getCrawlTime().substring(0, 8));
		tmp = chinaNewsService.getByNewsNum(news.getId());
		if(tmp == null){
			newsList.add(cn);
		}
		result = new ChinaNewsResult();
		result.setWeight(news.getWeight());
		result.setBatchDate(rpcResult.getBatchNumber().substring(0, 8));
		result.setBatchNum(rpcResult.getBatchNumber());
		result.setCatalogId(catalog.getId());
		result.setNewsNum(news.getId());
		result.setSort(i+1);
		newsResultList.add(result);
	}
	private String getUrlSource(String url){
		if(StringUtils.isNotEmpty(url)){
			int start_index = url.indexOf("http://");
			url = url.substring(start_index + 7);
			return url.split("/")[0];
		}else{
			return "";
		}
	}
}
