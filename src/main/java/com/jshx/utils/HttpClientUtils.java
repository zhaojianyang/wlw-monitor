package com.jshx.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import org.apache.log4j.Logger;

public class HttpClientUtils {

	private static Logger logger = Logger.getLogger(HttpClientUtils.class);

	public static final String REQUEST_POST = "POST";
	public static final String REQUEST_GET = "GET";
	public static final String REQUEST_PUT = "PUT";
	public static final String REQUEST_DELETE = "DELETE";

	static HostnameVerifier hv = new HostnameVerifier() {
		public boolean verify(String urlHostName, SSLSession session) {
			return true;
		}
	};

	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	
	
	static class miTM implements javax.net.ssl.TrustManager,
			javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}



	/**
	 * 请求restful的接口
	 * 
	 * @param url
	 *            请求的地址
	 * @param params
	 *            请求的参数
	 * @param requestType
	 *            请求的类型 ( post get put delete)
	 * @return
	 * @throws MalformedURLException
	 */
	public static String requestForRestful(String urlStr, String params,
			String requestType) throws Exception {
		//以下两行用于绕过安全证书
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		URL url = new URL(urlStr);
		// 打开restful链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 提交模式
		conn.setRequestMethod(requestType);
		// 设置访问提交模式，表单提交
		conn.setRequestProperty("Content-Type",
				"application/json; charset=UTF-8");
		conn.setRequestProperty("Content-Length",
				String.valueOf(params.getBytes().length));
		
		conn.setConnectTimeout(2000);// 连接超时 单位毫秒
		conn.setReadTimeout(2000);// 读取超时 单位毫秒
		conn.setDoInput(true);
		conn.setDoOutput(true);// 是否输入参数
		conn.connect();

		// 写入请求的XML字符串到数据流
		OutputStreamWriter outr = new OutputStreamWriter(conn.getOutputStream());
		outr.write(params);
		outr.flush();
		outr.close();
		// 获取返回的结果
		// 返回"Y"表示调用接口创建热线-急修流程成功，"N"表示调用接口创建热线-急修流程失败
		InputStream is = conn.getInputStream();
		InputStreamReader sre = new InputStreamReader(is,"UTF-8");
		BufferedReader bre = new BufferedReader(sre);
		String lineTxt = null;
		String result = "";
		while ((lineTxt = bre.readLine()) != null) {
			result += lineTxt;
		}
		bre.close();
		sre.close();
		is.close();

		return result;
	}



	
	public static void main(String[] args) throws InterruptedException {
		try {
			System.out.println(requestForRestful("https://ioe.thyssen.com.cn/extend/elevator/elevator!dnss_handleAccessReq.do","","POST"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
