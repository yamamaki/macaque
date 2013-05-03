package com.example.d2y;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class iuGeoCoder {
	
	public iuGeoCoder() {
		
	}
	public String getGeoInfo(double lat, double lng) throws Exception {
		String res = "";
		String urlString = String.format("http://maps.google.cn/maps/geo?key=abcdefg&q=%s,%s",
							lat, lng);
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urlString);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			BufferedReader buffReader = new BufferedReader(
						new InputStreamReader(httpEntity.getContent()));
			StringBuffer strBuff = new StringBuffer();
			String result = null;
			while ((result = buffReader.readLine()) != null) {
				strBuff.append(result);
			}
			res = strBuff.toString();
			
			if (res != null && res.length() > 0) {
				JSONObject jsonObject = new JSONObject(res);
				JSONArray jsonArray = new JSONArray(jsonObject.get("Placemark").toString());
				res = "";
				for (int i = 0; i < jsonArray.length(); ++i) {
					res = jsonArray.getJSONObject(i).getString("address");
				}
			}
		} catch (Exception e) {
			throw new Exception("获取地理位置出现错误：" + e.getMessage());
		} finally {
			httpGet.abort();
			httpClient = null;
		}
		return res;
	}
}