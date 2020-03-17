package com.jt;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.config.HttpClientConfig;
import com.jt.util.HttpClientService;


@SpringBootTest
@RunWith(SpringRunner.class)//容器启动
public class TestHttpClient {
	@Autowired
	private CloseableHttpClient client;
	
	
	/**
	 *     测试HttpClient    
	 *  1.实例化httpClient对象
	 *  2.定义http请求路径 url/uri
	 *  3.定义请求方式 get/post
	 *  4.利用API发起http请求
	 *  5.获取返回值以后判断状态信息 200
	 *  6.获取响应数据.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void testGet() throws ClientProtocolException, IOException {
		//CloseableHttpClient client = HttpClients.createDefault();//创建客户端
		String url = "https://www.baidu.com";
		CloseableHttpResponse response = client.execute(new HttpGet(url));//客户端执行，需要定义请求方式
		if(response.getStatusLine().getStatusCode()==200) {//得到状态量再得到状态码
			System.out.println("请求成功");
			HttpEntity entity = response.getEntity();//获取响应属性
			String result = EntityUtils.toString(entity);
			System.out.println(result);
		}else {
			throw new RuntimeException();
		}
	}
	@Autowired
	private HttpClientService service;
	@Test
	public void testHttpClientService() {
		String result = service.doGet("https://www.baidu.com");
		System.out.println("获取结果"+result);
	}
}
