package cn.com.twsm.newstopic.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class RpcUtil {
	public static final String TEST_NEWS_RPC_URL = "test_rpc_url";
	
	public static final String INTERNAL_NEWS_RPC_URL = "INTERNAL_NEWS_RPC_URL";
	
	public static final String NEWS_AGGREGATION_RPC_URL = "NEWS_AGGREGATION_RPC_URL";
	
	public static XmlRpcClient getXmlRpcClient(String key) throws IOException{
		String url = (String) getProp().get(key);
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(url));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		return client;
	}
	
	public static Properties getProp() throws IOException{
		Resource resource = new ClassPathResource("/config.properties");
		Properties p = PropertiesLoaderUtils.loadProperties(resource);
		return p;
	}

}
