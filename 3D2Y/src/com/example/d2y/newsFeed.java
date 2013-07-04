package com.example.d2y;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;

public class newsFeed extends Activity {
	iuHttpHelper iuHttp = new iuHttpHelper();
	String[][] resBundle;
	final iuOpenHelper iuHelper = new iuOpenHelper(this,
			iuOpenHelper.DB_TABLE_USER, null, 1);
	final idusUserHelper idusHelper = new idusUserHelper();
	final String[] snItems = { "搜索活动", "消息", "附近的活动", "我的活动",
			"我的好友" };
	final int[] snImages = { R.drawable.newsfeedicon, R.drawable.messageicon,
			R.drawable.nearbyicon, R.drawable.todolisticon,
			R.drawable.friendsicon };
	ListView snList;
	snListAdapter snAdapter = new snListAdapter(newsFeed.this);
	String _username = "";
	String _sessionid = "";
	int _localOrNot;
	String newsFeedUrl = "";
	ListView newsFeedList;
	TextView newsFeedText;
	String res = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newsfeed);
		final Animation anim = AnimationUtils.loadAnimation(this, R.anim.refresh_rotate);
		newsFeedText = (TextView) findViewById(R.id.newsFeedText);
		final Button newsRefresh = (Button) findViewById(R.id.newsRefresh);
//		final TextView snUserName = (TextView) findViewById(R.id.snUserName);
		Intent gi = getIntent();
		_username = gi.getStringExtra("_USERNAME");
//		snUserName.setText(_username);
		_sessionid = gi.getStringExtra("_SESSIONID");
		_localOrNot = gi.getExtras().getInt("_LOCALORNOT");
//		System.out.println("ga" + _username);
//		System.out.println("ga" + _sessionid);
		iuHttpGetTask getTask = new iuHttpGetTask();
		newsFeedUrl = "http://www.iiyouyou.com/activity/getAtyDb2.php?myaty=1&sessionid="
				+ _sessionid;
		getTask.execute();
		newsFeedList = (ListView) findViewById(R.id.newsFeedList);
		// 声明侧滑菜单===================================================================================
		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.snmenu);
		snList = (ListView) findViewById(R.id.snList);
		snList.setAdapter(snAdapter);
		snList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				Intent i;
				switch (position) {
				case 0:
					i = new Intent(newsFeed.this, newsFeed.class);
					i.putExtra("_USERNAME", _username);
					i.putExtra("_LOCALORNOT", _localOrNot);
					if (_localOrNot == 1) {
						i.putExtra("_SESSIONID", _sessionid);
					}
					startActivity(i);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
					finish();
					break;
				case 3:
					i = new Intent(newsFeed.this, toDoList.class);
					i.putExtra("_USERNAME", _username);
					i.putExtra("_LOCALORNOT", _localOrNot);
					if (_localOrNot == 1) {
						i.putExtra("_SESSIONID", _sessionid);
					}
					startActivity(i);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
					break;
				/*case 1:
					i = new Intent(newsFeed.this, message.class);
					startActivity(i);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
					break;
				case 2:
					i = new Intent(newsFeed.this, nearBy.class);
					startActivity(i);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
					break;
				case 4:
					i = new Intent(newsFeed.this, friends.class);
					startActivity(i);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
					break;*/
				}

				// toDoList.this.overridePendingTransition(R.anim.zoomin,
				// R.anim.zoomout);

				// finish();

			}
		});
//##################################NEW##########################################
		newsFeedList
		.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0,
					View view, final int position, long id) {
				AlertDialog.Builder delDialog = new AlertDialog.Builder(
						newsFeed.this);
				delDialog.setTitle("参加活动");
				delDialog.setMessage("确定要参加该活动？");
				delDialog.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface arg0,
									int arg1) {
							}
						});
				delDialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface arg0,
									int arg1) {
								iuHttpPostTask postTask = new iuHttpPostTask();
								postTask.execute(new String[] { resBundle[position+1][6], _sessionid });
							}
						});
				delDialog.show();
				return true;
			}
		});
		
		
		
		newsRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
		        LinearInterpolator lir = new LinearInterpolator();  
		        anim.setInterpolator(lir);
		        findViewById(R.id.newsRefresh).startAnimation(anim);
				iuHttpGetTask getTask = new iuHttpGetTask();
				newsFeedUrl = "http://www.iiyouyou.com/activity/getAtyDb2.php?myaty=1&sessionid="
						+ _sessionid;
				getTask.execute();
				// newsFeedList = (ListView)findViewById(R.id.newsFeedList);
			}

		});
	}

	public class snListAdapter extends BaseAdapter {
		private Context snContext;

		public snListAdapter(Context context) {
			this.snContext = context;
		}

		@Override
		public int getCount() {
			return snItems.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(snContext).inflate(
						R.layout.snitem, null);
				itemViewCache itemView = new itemViewCache();
				itemView.snTextView = (TextView) convertView
						.findViewById(R.id.snText);
				itemView.snImageView = (ImageView) convertView
						.findViewById(R.id.snImage);
				convertView.setTag(itemView);
			}
			itemViewCache cache = (itemViewCache) convertView.getTag();
			cache.snTextView.setText(snItems[position]);
			cache.snTextView.setTextSize(25);
			cache.snImageView.setImageResource(snImages[position]);
			return convertView;
		}
	}

	// OVER===================================================================================

	public static class itemViewCache {
		public TextView snTextView;
		public ImageView snImageView;
		public TextView newsFeedItemName;
		public TextView newsFeedItemAddress;
		public TextView newsFeedItemDate;
	}

	public class iuHttpGetTask extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				resBundle = iuHttp.iuGetActivity(_sessionid);
			} catch (Exception e) {
				resBundle = null;
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String r) {
			newsFeedList = (ListView) findViewById(R.id.newsFeedList);
			newsFeedList.setAdapter(adapter_newsFeedList);

		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(String... values) {
		}
	}

	BaseAdapter adapter_newsFeedList = new BaseAdapter() {
		@Override
		public int getCount() {
			if (resBundle != null) {
				return Integer.parseInt(resBundle[0][0]);
			} else
				return 0;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(newsFeed.this).inflate(
						R.layout.nfitem, null);
				itemViewCache itemView = new itemViewCache();
				itemView.newsFeedItemName = (TextView) convertView
						.findViewById(R.id.newsFeedItemName);
				itemView.newsFeedItemAddress = (TextView) convertView
						.findViewById(R.id.newsFeedItemAddress);
				itemView.newsFeedItemDate = (TextView) convertView
						.findViewById(R.id.newsFeedItemDate);
				convertView.setTag(itemView);
			}
				itemViewCache cache = (itemViewCache) convertView.getTag();
/*				res = "";
				for (int i = 0; i < 3; ++i) {
					res += resBundle[position+1][i] + '\n';
				}*/
				cache.newsFeedItemName.setText(resBundle[position+1][0]);
				cache.newsFeedItemAddress.setText(resBundle[position+1][1]);
				cache.newsFeedItemDate.setText(resBundle[position+1][2].substring(0,10));
//				System.out.println(String.valueOf(position) + " " + res);
			return convertView;

		}
	};
	
	public class iuHttpPostTask extends AsyncTask<String, String, String> {
		String resBundlePost = "";
		// @Override
		protected String doInBackground(String... params) {
			try {
				resBundlePost = iuHttp.iuAttendActivity(new String[] { "atyid", "sessionid" },
						new String[] { params[0], params[1] });
			} catch (Exception e) {
				resBundlePost = "";
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String res) {
			System.out.println(resBundlePost);
			if (resBundlePost.equals("true")) {
				Toast.makeText(newsFeed.this, "参加成功!", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(newsFeed.this, "参加失败!", Toast.LENGTH_SHORT)
						.show();
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(String... values) {
		}
	}

}