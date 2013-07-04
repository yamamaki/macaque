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
import org.json.JSONArray;
import org.json.JSONObject;

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
		} else {
		}
		return res;
	} // www.iiyouoyu.com/activity/getAtyDb.php?myaty=1&sessionid=...

	// Post
	public String requestByPost(String httpUrl, String[] attrs, String[] vals)
			throws Exception {
		String res = "";
		HttpPost httpPost = new HttpPost(httpUrl);
		HttpClient httpClient = new DefaultHttpClient();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i = 0; i < attrs.length; ++i) {
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

	// login request by post
	public String[] iuLogin(String[] attrs, String[] vals) throws Exception {
		String httpUrl = "http://www.iiyouyou.com/i/loginDb.php";
		String res = "";
		String[] resBundle = new String[3];
		HttpPost httpPost = new HttpPost(httpUrl);
		HttpClient httpClient = new DefaultHttpClient();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i = 0; i < attrs.length; ++i) {
			params.add(new BasicNameValuePair(attrs[i], vals[i]));
		}
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				res = EntityUtils.toString(httpResponse.getEntity());
				System.out.println(res);
				JSONObject jsonRes = new JSONObject(res);
				resBundle[0] = jsonRes.getString("login");
				if (resBundle[0].equals("true")) {
					resBundle[1] = jsonRes.getString("userid");
					resBundle[2] = jsonRes.getString("sessionid");
				} else {
					resBundle[1] = null;
					resBundle[2] = null;
				}
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
		return resBundle;
	}

	// register request by post
	public String[] iuRegister(String[] attrs, String[] vals) throws Exception {
		String httpUrl = "http://www.iiyouyou.com/i/signUpDb.php";
		String res = "";
		String[] resBundle = new String[2];
		HttpPost httpPost = new HttpPost(httpUrl);
		HttpClient httpClient = new DefaultHttpClient();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i = 0; i < attrs.length; ++i) {
			params.add(new BasicNameValuePair(attrs[i], vals[i]));
		}
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				res = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonRes = new JSONObject(res);
				resBundle[0] = jsonRes.getString("register");
				if (resBundle[0].equals("true")) {
					resBundle[1] = jsonRes.getString("userid");
				} else {
					resBundle[1] = null;
				}
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
		return resBundle;
	}

	// setting request by post
	public String[] iuSetting(String[] attrs, String[] vals) throws Exception {
		String httpUrl = "http://www.iiyouyou.com/i/setting.php";
		String res = "";
		String[] resBundle = new String[2];
		HttpPost httpPost = new HttpPost(httpUrl);
		HttpClient httpClient = new DefaultHttpClient();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i = 0; i < attrs.length; ++i) {
			params.add(new BasicNameValuePair(attrs[i], vals[i]));
		}
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				res = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonRes = new JSONObject(res);
				resBundle[0] = jsonRes.getString("login");
				if (resBundle[0].equals("true")) {
					resBundle[1] = jsonRes.getString("userid");
				} else {
					resBundle[1] = null;
				}
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
		return resBundle;
	}

	// getActivity request by get
	public String[][] iuGetActivity(String sessionid) throws Exception {
		String httpUrl = "http://www.iiyouyou.com/activity/getAtyDb.php?&sessionid="
				+ sessionid;
		String res = "";
		String[][] resBundle;
		HttpGet httpGet = new HttpGet(httpUrl);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			res = EntityUtils.toString(httpResponse.getEntity());
			JSONArray jsonRes = new JSONArray(res);
			int len = jsonRes.length();
			resBundle = new String[len + 1][7];
			resBundle[0][0] = String.valueOf(len);
			for (int i = 1; i < len + 1; ++i) {
				resBundle[i][0] = jsonRes.getJSONObject(i - 1).getString(
						"atyname");
				resBundle[i][1] = jsonRes.getJSONObject(i - 1).getString(
						"atyaddress");
				resBundle[i][2] = jsonRes.getJSONObject(i - 1).getString(
						"atystarttime");
				resBundle[i][3] = jsonRes.getJSONObject(i - 1).getString(
						"atytype");
				resBundle[i][4] = jsonRes.getJSONObject(i - 1).getString("lat");
				resBundle[i][5] = jsonRes.getJSONObject(i - 1).getString("lng");
				resBundle[i][6] = jsonRes.getJSONObject(i - 1).getString(
						"atyid");
			}
		} else {
			resBundle = null;
		}
		return resBundle;
	}

	// pushActivity request by post
	public String iuPushActivity(String[] attrs, String[] vals)
			throws Exception {
		String httpUrl = "http://www.iiyouyou.com/activity/holdActivityDb.php";
		String res = "";
		String resBundle = "";
		HttpPost httpPost = new HttpPost(httpUrl);
		HttpClient httpClient = new DefaultHttpClient();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i = 0; i < attrs.length; ++i) {
			params.add(new BasicNameValuePair(attrs[i], vals[i]));
		}
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				res = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonRes = new JSONObject(res);
				resBundle = jsonRes.getString("holdaty");
				if (resBundle.equals("true")) {
				} else {
				}
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
		return resBundle;
	}
	
	// takeActivity request by post
	public String iuAttendActivity(String[] attrs, String[] vals)
			throws Exception {
		String httpUrl = "http://www.iiyouyou.com/activity/attendAtyDb.php";
		String res = "";
		String resBundle = "";
		HttpPost httpPost = new HttpPost(httpUrl);
		HttpClient httpClient = new DefaultHttpClient();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i = 0; i < attrs.length; ++i) {
			params.add(new BasicNameValuePair(attrs[i], vals[i]));
		}
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				res = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonRes = new JSONObject(res);
				resBundle = jsonRes.getString("attendaty");
				if (resBundle.equals("true")) {
				} else {
				}
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
		return resBundle;
	}
}