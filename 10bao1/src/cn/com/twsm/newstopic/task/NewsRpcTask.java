package cn.com.twsm.newstopic.task;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import cn.com.twsm.newstopic.model.Catalog;
import cn.com.twsm.newstopic.model.IndustryNews;
import cn.com.twsm.newstopic.service.CatalogService;
import cn.com.twsm.newstopic.service.IndustryNewsService;
import cn.com.twsm.newstopic.util.RpcUtil;

import com.alibaba.fastjson.JSONArray;


public class NewsRpcTask {
	
	@Resource
	private IndustryNewsService industryNewsService;
	
	@Resource
	private CatalogService catalogService;
	
	public static void main(String[] args) throws Exception{
		NewsRpcTask newsRpcTask = new NewsRpcTask();
		newsRpcTask.getInternalNews();
	}
	
	/**
	 * 通过RPC接口获取国内栏目的新闻 
	 */
	public void getInternalNews(){
		try {
			XmlRpcClient client = RpcUtil.getXmlRpcClient(RpcUtil.TEST_NEWS_RPC_URL);
			List<String> list = new ArrayList<String>();
			String columnsJson = (String)client.execute("topnews.getColumns", list);
			String crawlTimeJson = (String)client.execute("topnews.getCrawlBegin", list);
			Map<String,List<String>> columnMap = (Map<String,List<String>>)JSONArray.parse(columnsJson);
			Map<String,String> crawlMap = (Map<String,String>)JSONArray.parse(crawlTimeJson);
			String crawlTime = crawlMap.get("start");
			System.out.println(crawlTime);
			List<String> values = columnMap.get("columns");
			Catalog catalog = new Catalog();
			if(values != null){
				for(int i=0;i<values.size();i++){
					System.out.println(values.get(i));
					catalog = catalogService.getByCatalogCode(values.get(i));
					if(catalog != null && StringUtils.isNotEmpty(crawlTime) && !crawlTime.equals(catalog.getCrawlTime())){
						catalog.setCrawlTime(crawlTime);
						saveColumnNews(client, list,catalog);
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过RPC接口获取境外栏目的新闻 
	 */
	public void getOverseasNews(){
		try {
			XmlRpcClient client = RpcUtil.getXmlRpcClient(RpcUtil.INTERNAL_NEWS_RPC_URL);
			List<String> list = new ArrayList<String>();
			String columnsJson = (String)client.execute("topnews.getColumns", list);
			String crawlTimeJson = (String)client.execute("topnews.getCrawlBegin", list);
			Map<String,List<String>> columnMap = (Map<String,List<String>>)JSONArray.parse(columnsJson);
			Map<String,String> crawlMap = (Map<String,String>)JSONArray.parse(crawlTimeJson);
			String crawlTime = crawlMap.get("start");
			System.out.println(crawlTime);
			List<String> values = columnMap.get("columns");
			Catalog catalog = new Catalog();
			if(values != null){
				for(int i=0;i<values.size();i++){
					System.out.println(values.get(i));
					catalog = catalogService.getByCatalogCode(values.get(i));
					if(catalog != null && StringUtils.isNotEmpty(crawlTime) && !crawlTime.equals(catalog.getCrawlTime())){
						catalog.setCrawlTime(crawlTime);
						saveColumnNews(client, list,catalog);
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
	}

	private void saveColumnNews(XmlRpcClient client, List<String> list,Catalog catalog)
			throws XmlRpcException {
		list.clear();
		list.add(catalog.getCatalogCode());
		String result=(String)client.execute("topnews.getColumn", list);
		RpcResult rpcResult = JSONArray.parseObject(result, RpcResult.class);
		List<IndustryNews> newsList = new ArrayList<IndustryNews>();
		IndustryNews cn = null;
		News news = null;
		NewsItem newsItem = null;
		if(rpcResult != null && rpcResult.getNews() != null){
			for(int i=0;i<rpcResult.getNews().size();i++){
				news = rpcResult.getNews().get(i);
				if(news.getList() != null){
					for(int j=0;j<news.getList().size();j++){
						newsItem = news.getList().get(j);
						cn = new IndustryNews();
						cn.setNewsIndex(new BigDecimal(news.getWeight()));
						cn.setTitle(newsItem.getSubtitle());
						cn.setPublishTime(newsItem.getDate());
						cn.setNewsLink(newsItem.getUrl());
						cn.setCatalogId(catalog.getId());
						cn.setCrawlTime(catalog.getCrawlTime());
						cn.setPublishMedia(getUrlSource(newsItem.getUrl()));
						if(j==0){
							cn.setNewsLevel(1);
							cn.setReadNum(i+1);
						}else{
							cn.setNewsLevel(2);
						}
						newsList.add(cn);
					}
				}
			}
		}
		/*List<ChinaNews> newsList2 = new ArrayList<ChinaNews>();
		for(int k=0;k<newsList.size();k++){
			if(k<11){
				newsList2.add(newsList.get(k));
				System.out.println(newsList.get(k).getNewsIndex() +"\t"+ newsList.get(k).getTitle() +"\t"+ newsList.get(k).getNewsLink() + "\t" + newsList.get(k).getPublishTime() );
			}
		}*/
		industryNewsService.batchAddIndustryNews(newsList,catalog);
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
