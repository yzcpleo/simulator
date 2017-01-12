package com.shhxzq.fin.simulator.biz.service.impl.ceb;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * date: 20140225-1030 | author: jinbo_wu | target: 浦发快捷
 * @author Administrator
 *
 */
public class HttpClientSend {
	private HttpConnectionManager connectionManager;

	public HttpClientSend() {
		super();

		// 创建一个线程安全的HTTP连接池
		connectionManager = new MultiThreadedHttpConnectionManager();

		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		// 连接建立超时
		params.setConnectionTimeout(10000);
		// 数据等待超时
		params.setSoTimeout(30000);
		// 默认每个Host最多10个连接
		params.setDefaultMaxConnectionsPerHost(10);
		// 最大连接数（所有Host加起来）
		params.setMaxTotalConnections(200);

		connectionManager.setParams(params);
	}

	/**
	 * HttpClient post请求
	 * 
	 * @param reqXml
	 * @param postURL
	 * @return
	 * @throws Exception
	 */
	public String post(String reqXml, String postURL) throws Exception {
		HttpClient httpClient = new HttpClient(connectionManager);
		PostMethod method = new PostMethod(postURL);
		method.setRequestHeader("Content-Type", "text/xml; charset=GBK");
		try {
			method.setRequestEntity(new StringRequestEntity(reqXml, null,"GBK"));
			httpClient.executeMethod(method);
			// 获得返回报文
			String resXml = method.getResponseBodyAsString();
			return resXml;
		} catch (Exception e) {
			throw new Exception("发送报文失败! 详细描述: " + e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

}
