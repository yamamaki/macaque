package com.example.d2y;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class iuHttpHelper {
	public iuHttpHelper() {
		
	}
	
	// Get
	public String requestByGet(String httpUrl) throws Exception {
		String res = "";
		HttpGet httpGet = new HttpGet(httpUrl);
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			res = EntityUtils.toString(httpResponse.getEntity());
		}
		else {
		}
		return res;
	}
	
	// Post
	public String requestByPost(String httpUrl, String[] attrs, String[] vals) throws Exception {
		String res = "";
		HttpPost httpPost = new HttpPost(httpUrl);
		HttpClient httpClient = new DefaultHttpClient();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i=0; i<attrs.length; ++i) {
			params.add(new BasicNameValuePair(attrs[i], vals[i]));
		}
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				res = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonRes = new JSONObject(res);  
				return jsonRes.getString("login");
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
				httpClient = null;
			}
		}
		return res;
	}
}