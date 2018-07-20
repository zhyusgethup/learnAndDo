

/**   
 * @Title: HttpClientUtil.java
 * @Package com.tydic.twcs.common
 * @author yzb yangzb@tydic.com,yzhengbin@gmail.com
 * @date 2013-5-28 下午12:34:01
 * @version 
 */
package zhyu.common.objectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.omg.CORBA.portable.ApplicationException;

import com.fasterxml.jackson.databind.util.JSONPObject;



/**
 * 
 * @ClassName: HttpClientUtil
 * @author yzb yangzb@tydic.com,yzhengbin@gmail.com
 * @date 2013-5-29
 *
 */
public class HttpClientUtil {
	protected static final Logger logger = Logger.getLogger(HttpClientUtil.class);
	private HttpClientUtil(){}
	
	/**
	 * 发送HTTP_GET请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @param requestURL    请求地址(含参数)
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendGetRequest(String reqURL, String decodeCharset){
		long responseLength = 0;       //响应长度
		String responseContent = null; //响应内容
		HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例
		HttpGet httpGet = new HttpGet(reqURL);           //创建org.apache.http.client.methods.HttpGet
		try{
			HttpResponse response = httpClient.execute(httpGet); //执行GET请求
			HttpEntity entity = response.getEntity();            //获取响应实体
			if(null != entity){
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity); //Consume response content
			}
			System.out.println("请求地址: " + httpGet.getURI());
			System.out.println("响应状态: " + response.getStatusLine());
			System.out.println("响应长度: " + responseLength);
			System.out.println("响应内容: " + responseContent);
		}catch(ClientProtocolException e){
			logger.error("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下", e);
		}catch(ParseException e){
			logger.error(e.getMessage(), e);
		}catch(IOException e){
			logger.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
		}finally{
			httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源
		}
		return responseContent;
	}
	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 该方法为<code>sendPostRequest(String,String,boolean,String,String)</code>的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 * @param isEncoder 用于指明请求数据是否需要UTF-8编码,true为需要
	 */
	public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder){
		return sendPostRequest(reqURL, sendData, isEncoder, null, null);
	}
	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL        请求地址
	 * @param sendData      请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,传入该参数中
	 * @param isEncoder     请求数据是否需要encodeCharset编码,true为需要
	 * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder, String encodeCharset, String decodeCharset){
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(reqURL);
		//httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
		try{
			if(isEncoder){
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for(String str : sendData.split("&")){
					formParams.add(new BasicNameValuePair(str.substring(0,str.indexOf("=")), str.substring(str.indexOf("=")+1)));
				}
				httpPost.setEntity(new StringEntity(URLEncodedUtils.format(formParams, encodeCharset==null ? "UTF-8" : encodeCharset)));
			}else{
				httpPost.setEntity(new StringEntity(sendData));
			}
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		}catch(Exception e){
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL        请求地址
	 * @param params        请求参数
	 * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset){
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(reqURL);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列
		for(Map.Entry<String,String> entry : params.entrySet()){
			formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset==null ? "UTF-8" : encodeCharset));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		}catch(Exception e){
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	/**
	 * 只有一个参数并无名称
	 * @Title: sendPostRequest
	 * @param reqURL
	 * @param param
	 * @param encodeCharset
	 * @param decodeCharset
	 * @return
	 * @return String
	 */
	public static String sendPostRequest(String reqURL, String param, String encodeCharset, String decodeCharset){
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(reqURL);
		try{
			StringEntity myEntity = new StringEntity(param, encodeCharset); 
			myEntity.setContentType("application/x-www-form-urlencoded");
//			httpPost.addHeader("Content-Type", "text/xml"); 
			httpPost.setEntity(myEntity);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		}catch(Exception e){
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	/**
	 * 发送HTTPS_POST请求
	 * @see 该方法为<code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>方法的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params){
		return sendPostSSLRequest(reqURL, params, null, null);
	}
	
	
	/**
	 * 发送HTTPS_POST请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL        请求地址
	 * @param params        请求参数
	 * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset){
		String responseContent = "";
		HttpClient httpClient = new DefaultHttpClient();
		X509TrustManager xtm = new X509TrustManager(){
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public X509Certificate[] getAcceptedIssuers() {return null;}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[]{xtm}, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			
			HttpPost httpPost = new HttpPost(reqURL);
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			for(Map.Entry<String,String> entry : params.entrySet()){
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset==null ? "UTF-8" : encodeCharset));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 若发送的<code>sendData</code>中含有中文,记得按照双方约定的字符集将中文<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @see 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL   请求地址
	 * @param sendData 发送到远程主机的正文数据
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJava2(String reqURL, String sendData,String cmd_code){
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; //写
		BufferedReader reader = null;   //读
		int httpStatusCode = 0;  //远程主机响应的HTTP状态码
		try{
			URL sendUrl = new URL(reqURL);
			httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);        //指示应用程序要将数据写入URL连接,其值默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestProperty("content-type",
					"application/x-java-serialized-object");
			httpURLConnection.setRequestProperty("CMD_CODE",cmd_code);
			httpURLConnection.setConnectTimeout(10000); //30秒连接超时
			httpURLConnection.setReadTimeout(10000);    //30秒读取超时
			
			out = httpURLConnection.getOutputStream();
			out.write(sendData.toString().getBytes());
			
			//清空缓冲区,发送数据
			out.flush();
			
			//获取HTTP状态码
			httpStatusCode = httpURLConnection.getResponseCode();
			
			
			//该方法只能获取到[HTTP/1.0 200 OK]中的[OK]
			//若对方响应的正文放在了返回报文的最后一行,则该方法获取不到正文,而只能获取到[OK],稍显遗憾
			//respData = httpURLConnection.getResponseMessage();
			
//			//处理返回结果
//			BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//			String row = null;
//			String respData = "";
//			if((row=br.readLine()) != null){ //readLine()方法在读到换行[\n]或回车[\r]时,即认为该行已终止
//				respData = row;              //HTTP协议POST方式的最后一行数据为正文数据
//			}
//			br.close();
//			if(httpStatusCode!=200){
//				throw new ApplicationException("接口调用出错");
//			}
//			new InputStreamReader(in, cs).r
			if(httpStatusCode==200){
				reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));             
			}else{
				reader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream(),"UTF-8"));
			}
			String line = null;
			StringBuilder buffer = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口调用出错",e);
			throw new RuntimeException("接口调用出错："+e.getMessage());
		}finally{
			if(out != null){
				try{
					out.close();
				}catch (Exception e){
					logger.error("关闭输出流时发生异常,堆栈信息如下", e);
				}
			}
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e){
					logger.error("关闭输入流时发生异常,堆栈信息如下", e);
				}
			}
			if(httpURLConnection != null){
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}
	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 若发送的<code>sendData</code>中含有中文,记得按照双方约定的字符集将中文<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @see 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL   请求地址
	 * @param sendData 发送到远程主机的正文数据
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJava(String reqURL, String sendData){
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; //写
		BufferedReader reader = null;   //读
		int httpStatusCode = 0;  //远程主机响应的HTTP状态码
		try{
			URL sendUrl = new URL(reqURL);
			httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);        //指示应用程序要将数据写入URL连接,其值默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestProperty("content-type",
					"application/x-java-serialized-object");
			httpURLConnection.setRequestProperty("CMD_CODE","INTF_QRY_REPORT_DATA_NEW");
			httpURLConnection.setConnectTimeout(10000); //30秒连接超时
			httpURLConnection.setReadTimeout(10000);    //30秒读取超时
			
			out = httpURLConnection.getOutputStream();
			out.write(sendData.toString().getBytes());
			
			//清空缓冲区,发送数据
			out.flush();
			
			//获取HTTP状态码
			httpStatusCode = httpURLConnection.getResponseCode();
			
			
			//该方法只能获取到[HTTP/1.0 200 OK]中的[OK]
			//若对方响应的正文放在了返回报文的最后一行,则该方法获取不到正文,而只能获取到[OK],稍显遗憾
			//respData = httpURLConnection.getResponseMessage();
			
//			//处理返回结果
//			BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//			String row = null;
//			String respData = "";
//			if((row=br.readLine()) != null){ //readLine()方法在读到换行[\n]或回车[\r]时,即认为该行已终止
//				respData = row;              //HTTP协议POST方式的最后一行数据为正文数据
//			}
//			br.close();
//			if(httpStatusCode!=200){
//				throw new ApplicationException("接口调用出错");
//			}
//			new InputStreamReader(in, cs).r
			if(httpStatusCode==200){
				reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));             
			}else{
				reader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream(),"UTF-8"));
			}
			String line = null;
			StringBuilder buffer = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口调用出错",e);
			throw new RuntimeException("接口调用出错："+e.getMessage());
		}finally{
			if(out != null){
				try{
					out.close();
				}catch (Exception e){
					logger.error("关闭输出流时发生异常,堆栈信息如下", e);
				}
			}
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e){
					logger.error("关闭输入流时发生异常,堆栈信息如下", e);
				}
			}
			if(httpURLConnection != null){
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}
	
	/**
	 * 发送HTTP_POST请求
	 * @see 若发送的<code>sendData</code>中含有中文,记得按照双方约定的字符集将中文<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @see 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL   请求地址
	 * @param sendData 发送到远程主机的正文数据
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJavaSoap(String reqURL, String sendData){
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; //写
		BufferedReader reader = null;   //读
		int httpStatusCode = 0;  //远程主机响应的HTTP状态码
		try{
			URL sendUrl = new URL(reqURL);
			httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);        //指示应用程序要将数据写入URL连接,其值默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestProperty("content-type",
					"application/x-java-serialized-object");
			httpURLConnection.setRequestProperty("CMD_CODE","INTF_QRY_REPORT_DATA_NEW");
			httpURLConnection.setConnectTimeout(10000); //30秒连接超时
			httpURLConnection.setReadTimeout(10000);    //30秒读取超时
			
			out = httpURLConnection.getOutputStream();
			out.write(sendData.toString().getBytes());
			
			//清空缓冲区,发送数据
			out.flush();
			
			//获取HTTP状态码
			httpStatusCode = httpURLConnection.getResponseCode();
			
			if(httpStatusCode==200){
				reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"GBK"));             
			}else{
				reader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream(),"GBK"));
			}
			String line = null;
			StringBuilder buffer = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			return (buffer.toString().replaceAll("&lt;", "<")).replaceAll("&gt;", ">");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口调用出错",e);
			throw new RuntimeException("接口调用出错："+e.getMessage());
		}finally{
			if(out != null){
				try{
					out.close();
				}catch (Exception e){
					logger.error("关闭输出流时发生异常,堆栈信息如下", e);
				}
			}
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e){
					logger.error("关闭输入流时发生异常,堆栈信息如下", e);
				}
			}
			if(httpURLConnection != null){
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}
	
	 public static String sendHttpPost(String url, String body) throws Exception {
		  CloseableHttpClient httpClient = HttpClients.createDefault();
		  HttpPost httpPost = new HttpPost(url);
		  httpPost.addHeader("Content-Type", "application/json");
		  httpPost.setEntity(new StringEntity(body,"UTF-8"));
		  
		  CloseableHttpResponse response = httpClient.execute(httpPost);
		  //System.out.println(response.getStatusLine().getStatusCode() + "\n");
		  HttpEntity entity = response.getEntity();
		 String responseContent = EntityUtils.toString(entity, "UTF-8");
		 response.close();
		 httpClient.close();
		// System.out.println("responseContent====" + responseContent);
		 return responseContent;
		 }
	
	
public static void main(String[] args){
//    String url="http://60.173.195.124/intf/gridmanager!gridBaseInfo.action?staff_id=7600004298&latn_id=560&this_page=1&page_num=10";
//  String ret=   HttpClientUtil.sendGetRequest(url, null);
	String url="http://134.64.110.91:9999/oip/rest?sender=MPPS&servCode=1001.ServiceBus_custView_cust_cust003.SynReq&msgId=crm_20130718_10001";
//    String sendData = "{\"PUB_REQ\":{\"TYPE\":\"QRY_CUST_INFO\",\"SVC_CONT_VER\":\"1.0\",\"PAGE_INDEX\":\"1\",\"PAGE_SIZE\":\"5\"},\"CUST_REQ\":{\"CUST_ID\":\"1231\",\"SYSTEM_USER_ID\":\"46546\",\"PASSWORD\":\"11111\",\"LATN_ID\":\"551\"}}";
    String sendData = 
//    		"{\n" +
//    				"    \"SvcCont\": {\n" + 
//    				"        \"SOO\": [\n" + 
//    				"            {\n" + 
//    				"                \"CUST_REQ\": {\n" + 
//    				"                    \"ACC_NBR\": \"3594260\",\n" + 
//    				"                    \"CUST_ID\": \":getCustId('3594260', '4', '',  '563')\",\n" + 
//    				"                    \"LATN_ID\": \"563\"\n" + 
//    				"                },\n" + 
//    				"                \"PUB_REQ\": {\n" + 
//    				"                    \"TYPE\": \"QRY_CUST_INFO\",\n" + 
//    				"                       \"PAGE_INDEX\":1,\n" + 
//    				"        \"PAGE_SIZE\":\"9999999999\"\n" + 
//    				"\n" + 
//    				"                },\n" + 
//    				"                \"RETURN_OBJECTS\": {\n" + 
//    				"                    \"ACCOUNT\": {\n" + 
//    				"                        \"FIELDS\": \"\"\n" + 
//    				"                    },\n" + 
//    				"                    \"CONTACT\": {\n" + 
//    				"                        \"FIELDS\": \"\"\n" + 
//    				"                    },\n" + 
//    				"                    \"CUST\": {\n" + 
//    				"                        \"FIELDS\": \"\"\n" + 
//    				"                    },\n" + 
//    				"                    \"CUST_BRAND_LABEL\": {\n" + 
//    				"                        \"FIELDS\": \"\"\n" + 
//    				"                    },\n" + 
//    				"                    \"PARTY_CENTIFICATION\": {\n" + 
//    				"                        \"FIELDS\": \"\"\n" + 
//    				"                    }\n" + 
//    				"                }\n" + 
//    				"            }\n" + 
//    				"        ]\n" + 
//    				"    },\n" + 
//    				"    \"TcpCont\": {\n" + 
//    				"        \"ServiceCode\": \"/ServiceBus/custView/cust/cust001\",\n" + 
//    				"        \"SrcSysID\": \"100000\",\n" + 
//    				"        \"TransactionID\": \"65dcd445-eb6a-4391-a581-f491d901ec03\"\n" + 
//    				"    }\n" + 
//    				"}";

"\n" +
"{\n" + 
"    \"SvcCont\": {\n" + 
"        \"SOO\": [\n" + 
"           {\n" + 
"    \"PUB_REQ\":{\n" + 
"        \"TYPE\":\"QRY_CUST_LIST\",\n" + 
"        \"SVC_CONT_VER\":\"1.0\",\n" + 
"        \"PAGE_INDEX\":1,\n" + 
"        \"PAGE_SIZE\":\"5\"\n" + 
"    },\n" + 
"    \"CUST_REQ\":{\n" + 
"\n" + 
"        \"LATN_ID\":\"551\"\n" + 
"    }\n" + 
",\n" + 
"                \"RETURN_OBJECTS\": {\n" + 
"                    \"ACCOUNT\": {\n" + 
"                        \"FIELDS\": \"\"\n" + 
"                    },\n" + 
"                    \"CONTACT\": {\n" + 
"                        \"FIELDS\": \"\"\n" + 
"                    },\n" + 
"                    \"CUST\": {\n" + 
"                        \"FIELDS\": \"\"\n" + 
"                    },\n" + 
"                    \"CUST_BRAND_LABEL\": {\n" + 
"                        \"FIELDS\": \"\"\n" + 
"                    },\n" + 
"                    \"PARTY_CENTIFICATION\": {\n" + 
"                        \"FIELDS\": \"\"\n" + 
"                    }\n" + 
"                }\n" + 
"            }\n" + 
"        ]\n" + 
"    },\n" + 
"    \"TcpCont\": {\n" + 
"        \"ServiceCode\": \"/ServiceBus/custView/cust/cust003\",\n" + 
"        \"SrcSysID\": \"100000\",\n" + 
"        \"TransactionID\": \"65dcd445-eb6a-4391-a581-f491d901ec03\"\n" + 
"    }\n" + 
"}";
   		
    		String ret=   HttpClientUtil.sendPostRequestByJava(url,sendData);
  System.out.print(ret);
}
}
