package com.example.d2y;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;

public class toDoList extends Activity {
	iuActivity[] iuActivity;
	iuHttpHelper iuHttp = new iuHttpHelper();
	final iuOpenHelper iuHelper = new iuOpenHelper(this,
			iuOpenHelper.DB_TABLE_ACTIVITY, null, 1);
	final idusActivityHelper idusHelper = new idusActivityHelper();
	final String[] snItems = { "搜索活动", "消息", "附近的活动", "我的活动",
			"我的好友"  };
	final int[] snImages = { R.drawable.newsfeedicon, R.drawable.messageicon,
			R.drawable.nearbyicon, R.drawable.todolisticon,
			R.drawable.friendsicon };

	String _username;
	String _sessionid;
	int _localOrNot;

	ListView snList;
	snListAdapter snAdapter = new snListAdapter(toDoList.this);

	ListView activityList;
	ListView activityListByTag;

	BaseAdapter adapter_activityList = new BaseAdapter() {
		@Override
		public int getCount() {
			if (iuActivity != null) {
				return iuActivity.length;
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
				convertView = LayoutInflater.from(toDoList.this).inflate(
						R.layout.todoitem, null);
				itemViewCache itemView = new itemViewCache();
				itemView.toDoTag = (ImageView) convertView
						.findViewById(R.id.toDoTag);
				itemView.toDoItem = (TextView) convertView
						.findViewById(R.id.toDoItem);
				itemView.toDoDate = (TextView) convertView
						.findViewById(R.id.toDoDate);
				convertView.setTag(itemView);
			}
			itemViewCache cache = (itemViewCache) convertView.getTag();
			switch (iuActivity[position].getTag()) {
			case 0:
				cache.toDoTag.setImageResource(R.drawable.typeall);
				break;
			case 1:
				cache.toDoTag.setImageResource(R.drawable.typea);
				break;
			case 2:
				cache.toDoTag.setImageResource(R.drawable.typeb);
				break;
			case 3:
				cache.toDoTag.setImageResource(R.drawable.typec);
				break;
			case 4:
				cache.toDoTag.setImageResource(R.drawable.typed);
				break;
			}
			cache.toDoItem.setText(iuActivity[position].getActivity());
			cache.toDoDate.setText(iuActivity[position].getDate());
			return convertView;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.todolist);
//		final TextView snUserName = (TextView) findViewById(R.id.snUserName);
		Intent gi = getIntent();
		_localOrNot = gi.getExtras().getInt("_LOCALORNOT");
		_username = gi.getStringExtra("_USERNAME");
//		snUserName.setText(_username);
		if (_localOrNot == 1) {
			_sessionid = gi.getStringExtra("_SESSIONID");
		}
		System.out.println(_localOrNot);
		activityList = (ListView) findViewById(R.id.activityList);
		activityList.setAdapter(adapter_activityList);
		if (_localOrNot == 1) {
			// 声明侧滑菜单
			SlidingMenu menu = new SlidingMenu(this);
			menu.setMode(SlidingMenu.LEFT);
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			menu.setShadowWidthRes(R.dimen.shadow_width);
			menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
			menu.setShadowDrawable(R.drawable.shadow);
			menu.setFadeDegree(0.35f);
			menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
			menu.setMenu(R.layout.snmenu);
			Log.v("trouble", "123123");
			snList = (ListView) findViewById(R.id.snList);
			snList.setAdapter(snAdapter);
			snList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						final int position, long id) {
					Intent i = new Intent(toDoList.this, toDoList.class);
					switch (position) {
					case 0:
						i = new Intent(toDoList.this, newsFeed.class);
						i.putExtra("_LOCALORNOT", _localOrNot);
						i.putExtra("_USERNAME", _username);
						if (_localOrNot == 1){
							i.putExtra("_SESSIONID", _sessionid);
						}
						startActivity(i);
						overridePendingTransition(android.R.anim.slide_in_left,
								android.R.anim.slide_out_right);
						break;
//					case 1:
//						i = new Intent(toDoList.this, message.class);
//						startActivity(i);
//						overridePendingTransition(android.R.anim.slide_in_left,
//								android.R.anim.slide_out_right);
//						break;
//					case 2:
//						i = new Intent(toDoList.this, nearBy.class);
//						startActivity(i);
//						overridePendingTransition(android.R.anim.slide_in_left,
//								android.R.anim.slide_out_right);
//						break;
					case 3:
						i = new Intent(toDoList.this, toDoList.class);
						i.putExtra("_LOCALORNOT", _localOrNot);
						i.putExtra("_USERNAME", _username);
						if (_localOrNot == 1){
							i.putExtra("_SESSIONID", _sessionid);
						}
						startActivity(i);
						overridePendingTransition(android.R.anim.slide_in_left,
								android.R.anim.slide_out_right);
						finish();
						break;
//					case 4:
//						i = new Intent(toDoList.this, friends.class);
//						startActivity(i);
//						overridePendingTransition(android.R.anim.slide_in_left,
//								android.R.anim.slide_out_right);
//						break;
					}
					
					// toDoList.this.overridePendingTransition(R.anim.zoomin,
					// R.anim.zoomout);
					
					// finish();

				}
			});
		}

		// 声明activityTag
		final Button typeAll = (Button) findViewById(R.id.typeAll);
		final Button typeA = (Button) findViewById(R.id.typeA);
		final Button typeB = (Button) findViewById(R.id.typeB);
		final Button typeC = (Button) findViewById(R.id.typeC);
		final Button typeD = (Button) findViewById(R.id.typeD);
		final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
		final LayoutInflater inflater = LayoutInflater.from(this);
		Log.v("trouble", "123123");
		typeAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				typeAll.setBackgroundResource(R.drawable.selector_list_all);
				typeA.setBackgroundResource(R.color.A);
				typeB.setBackgroundResource(R.color.B);
				typeC.setBackgroundResource(R.color.C);
				typeD.setBackgroundResource(R.color.D);
				RelativeLayout rltag = (RelativeLayout) inflater.inflate(
						R.layout.todolistbytag, null).findViewById(R.id.rltag);
				rl.removeAllViews();
				rl.addView(rltag);
				activityList = (ListView) findViewById(R.id.activityListByTag);
				activityList.setAdapter(adapter_activityList);
				iuActivity = idusHelper.getActivities(iuHelper, 0);
				// 绑定删除活动事件
				activityList
						.setOnItemLongClickListener(new OnItemLongClickListener() {
							@Override
							public boolean onItemLongClick(AdapterView<?> arg0,
									View view, final int position, long id) {
								AlertDialog.Builder delDialog = new AlertDialog.Builder(
										toDoList.this);
								delDialog.setTitle("删除");
								delDialog.setMessage("是否要删除活动 "
										+ iuActivity[position].getActivity());
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
												long res = idusHelper
														.deleteActivity(
																iuHelper,
																iuActivity[position]
																		.getActivity());
												if (res == -1) {
													Toast.makeText(
															toDoList.this,
															"对不起，删除活动失败！",
															Toast.LENGTH_SHORT)
															.show();
												} else {
													Toast.makeText(
															toDoList.this,
															"删除活动成功！",
															Toast.LENGTH_SHORT)
															.show();
													updateActivityList(iuHelper);
												}
											}
										});
								delDialog.show();
								return true;
							}
						});

				// 绑定更新活动事件
				activityList.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long id) {
						int requestCode = 2;
						Intent i = new Intent(toDoList.this,
								updateActivity.class);
						i.putExtra("_ACTIVITY",
								iuActivity[position].getActivity());
						startActivityForResult(i, requestCode);
						toDoList.this.overridePendingTransition(R.anim.zoomin,
								R.anim.zoomout);
					}
				});
				iuActivity = idusHelper.getActivities(iuHelper, 0);
			}
		});
		typeA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				typeAll.setBackgroundResource(R.color.All);
				typeA.setBackgroundResource(R.drawable.selector_list_a);
				typeB.setBackgroundResource(R.color.B);
				typeC.setBackgroundResource(R.color.C);
				typeD.setBackgroundResource(R.color.D);
				RelativeLayout rltag = (RelativeLayout) inflater.inflate(
						R.layout.todolistbytag, null).findViewById(R.id.rltag);
				System.out.println("A");
				rl.removeAllViews();
				rl.addView(rltag);
				activityList = (ListView) findViewById(R.id.activityListByTag);
				activityList.setAdapter(adapter_activityList);
				iuActivity = idusHelper.getActivities(iuHelper, 1);
				// 绑定删除活动事件
				activityList
						.setOnItemLongClickListener(new OnItemLongClickListener() {
							@Override
							public boolean onItemLongClick(AdapterView<?> arg0,
									View view, final int position, long id) {
								AlertDialog.Builder delDialog = new AlertDialog.Builder(
										toDoList.this);
								delDialog.setTitle("删除");
								delDialog.setMessage("是否要删除活动 "
										+ iuActivity[position].getActivity());
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
												long res = idusHelper
														.deleteActivity(
																iuHelper,
																iuActivity[position]
																		.getActivity());
												if (res == -1) {
													Toast.makeText(
															toDoList.this,
															"对不起，删除活动失败！",
															Toast.LENGTH_SHORT)
															.show();
												} else {
													Toast.makeText(
															toDoList.this,
															"删除活动成功！",
															Toast.LENGTH_SHORT)
															.show();
													updateActivityList(iuHelper);
												}
											}
										});
								delDialog.show();
								return true;
							}
						});

				// 绑定更新活动事件
				activityList.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long id) {
						int requestCode = 2;
						Intent i = new Intent(toDoList.this,
								updateActivity.class);
						i.putExtra("_ACTIVITY",
								iuActivity[position].getActivity());
						startActivityForResult(i, requestCode);
						toDoList.this.overridePendingTransition(R.anim.zoomin,
								R.anim.zoomout);
					}
				});
				iuActivity = idusHelper.getActivities(iuHelper, 1);
			}
		});
		typeB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				typeAll.setBackgroundResource(R.color.All);
				typeA.setBackgroundResource(R.color.A);
				typeB.setBackgroundResource(R.drawable.selector_list_b);
				typeC.setBackgroundResource(R.color.C);
				typeD.setBackgroundResource(R.color.D);
				RelativeLayout rltag = (RelativeLayout) inflater.inflate(
						R.layout.todolistbytag, null).findViewById(R.id.rltag);
				System.out.println("B");
				rl.removeAllViews();
				rl.addView(rltag);
				activityList = (ListView) findViewById(R.id.activityListByTag);
				activityList.setAdapter(adapter_activityList);
				iuActivity = idusHelper.getActivities(iuHelper, 2);

				// 绑定删除活动事件
				activityList
						.setOnItemLongClickListener(new OnItemLongClickListener() {
							@Override
							public boolean onItemLongClick(AdapterView<?> arg0,
									View view, final int position, long id) {
								AlertDialog.Builder delDialog = new AlertDialog.Builder(
										toDoList.this);
								delDialog.setTitle("删除");
								delDialog.setMessage("是否要删除活动 "
										+ iuActivity[position].getActivity());
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
												long res = idusHelper
														.deleteActivity(
																iuHelper,
																iuActivity[position]
																		.getActivity());
												if (res == -1) {
													Toast.makeText(
															toDoList.this,
															"对不起，删除活动失败！",
															Toast.LENGTH_SHORT)
															.show();
												} else {
													Toast.makeText(
															toDoList.this,
															"删除活动成功！",
															Toast.LENGTH_SHORT)
															.show();
													updateActivityList(iuHelper);
												}
											}
										});
								delDialog.show();
								return true;
							}
						});

				// 绑定更新活动事件
				activityList.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long id) {
						int requestCode = 2;
						Intent i = new Intent(toDoList.this,
								updateActivity.class);
						i.putExtra("_ACTIVITY",
								iuActivity[position].getActivity());
						startActivityForResult(i, requestCode);
						toDoList.this.overridePendingTransition(R.anim.zoomin,
								R.anim.zoomout);
					}
				});
				iuActivity = idusHelper.getActivities(iuHelper, 2);
			}
		});
		typeC.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				typeAll.setBackgroundResource(R.color.All);
				typeA.setBackgroundResource(R.color.A);
				typeB.setBackgroundResource(R.color.B);
				typeC.setBackgroundResource(R.drawable.selector_list_c);
				typeD.setBackgroundResource(R.color.D);
				RelativeLayout rltag = (RelativeLayout) inflater.inflate(
						R.layout.todolistbytag, null).findViewById(R.id.rltag);
				System.out.println("C");
				rl.removeAllViews();
				rl.addView(rltag);
				activityList = (ListView) findViewById(R.id.activityListByTag);
				activityList.setAdapter(adapter_activityList);
				iuActivity = idusHelper.getActivities(iuHelper, 3);
				// 绑定删除活动事件
				activityList
						.setOnItemLongClickListener(new OnItemLongClickListener() {
							@Override
							public boolean onItemLongClick(AdapterView<?> arg0,
									View view, final int position, long id) {
								AlertDialog.Builder delDialog = new AlertDialog.Builder(
										toDoList.this);
								delDialog.setTitle("删除");
								delDialog.setMessage("是否要删除活动 "
										+ iuActivity[position].getActivity());
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
												long res = idusHelper
														.deleteActivity(
																iuHelper,
																iuActivity[position]
																		.getActivity());
												if (res == -1) {
													Toast.makeText(
															toDoList.this,
															"对不起，删除活动失败！",
															Toast.LENGTH_SHORT)
															.show();
												} else {
													Toast.makeText(
															toDoList.this,
															"删除活动成功！",
															Toast.LENGTH_SHORT)
															.show();
													updateActivityList(iuHelper);
												}
											}
										});
								delDialog.show();
								return true;
							}
						});

				// 绑定更新活动事件
				activityList.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long id) {
						int requestCode = 2;
						Intent i = new Intent(toDoList.this,
								updateActivity.class);
						i.putExtra("_ACTIVITY",
								iuActivity[position].getActivity());
						startActivityForResult(i, requestCode);
						toDoList.this.overridePendingTransition(R.anim.zoomin,
								R.anim.zoomout);
					}
				});
				iuActivity = idusHelper.getActivities(iuHelper, 3);
			}
		});
		typeD.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				typeAll.setBackgroundResource(R.color.All);
				typeA.setBackgroundResource(R.color.A);
				typeB.setBackgroundResource(R.color.B);
				typeC.setBackgroundResource(R.color.C);
				typeD.setBackgroundResource(R.drawable.selector_list_d);
				RelativeLayout rltag = (RelativeLayout) inflater.inflate(
						R.layout.todolistbytag, null).findViewById(R.id.rltag);
				rl.removeAllViews();
				rl.addView(rltag);
				activityList = (ListView) findViewById(R.id.activityListByTag);
				activityList.setAdapter(adapter_activityList);
				iuActivity = idusHelper.getActivities(iuHelper, 4);
				// 绑定删除活动事件
				activityList
						.setOnItemLongClickListener(new OnItemLongClickListener() {
							@Override
							public boolean onItemLongClick(AdapterView<?> arg0,
									View view, final int position, long id) {
								AlertDialog.Builder delDialog = new AlertDialog.Builder(
										toDoList.this);
								delDialog.setTitle("删除");
								delDialog.setMessage("是否要删除活动 "
										+ iuActivity[position].getActivity());
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
												long res = idusHelper
														.deleteActivity(
																iuHelper,
																iuActivity[position]
																		.getActivity());
												if (res == -1) {
													Toast.makeText(
															toDoList.this,
															"对不起，删除活动失败！",
															Toast.LENGTH_SHORT)
															.show();
												} else {
													Toast.makeText(
															toDoList.this,
															"删除活动成功！",
															Toast.LENGTH_SHORT)
															.show();
													updateActivityList(iuHelper);
												}
											}
										});
								delDialog.show();
								return true;
							}
						});

				// 绑定更新活动事件
				activityList.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long id) {
						int requestCode = 2;
						Intent i = new Intent(toDoList.this,
								updateActivity.class);
						i.putExtra("_ACTIVITY",
								iuActivity[position].getActivity());
						startActivityForResult(i, requestCode);
						toDoList.this.overridePendingTransition(R.anim.zoomin,
								R.anim.zoomout);
					}
				});
				iuActivity = idusHelper.getActivities(iuHelper, 4);
			}
		});

		// 绑定删除活动事件
		activityList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				AlertDialog.Builder delDialog = new AlertDialog.Builder(
						toDoList.this);
				delDialog.setTitle("删除");
				delDialog.setMessage("是否要删除活动 "
						+ iuActivity[position].getActivity());
				delDialog.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
							}
						});
				delDialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								long res = idusHelper.deleteActivity(iuHelper,
										iuActivity[position].getActivity());
								if (res == -1) {
									Toast.makeText(toDoList.this,
											"对不起，删除活动失败！", Toast.LENGTH_SHORT)
											.show();
								} else {
									Toast.makeText(toDoList.this, "删除活动成功！",
											Toast.LENGTH_SHORT).show();
									updateActivityList(iuHelper);
								}
							}
						});
				delDialog.show();
				return true;
			}
		});

		// 绑定更新活动事件
		activityList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				int requestCode = 2;
				Intent i = new Intent(toDoList.this, updateActivity.class);
				i.putExtra("_ACTIVITY", iuActivity[position].getActivity());
				startActivityForResult(i, requestCode);
				toDoList.this.overridePendingTransition(R.anim.zoomin,
						R.anim.zoomout);
			}
		});
		Log.v("trouble", "12312");
		updateActivityList(iuHelper);
		Log.v("trouble", "123123");
		// 绑定 菜单 按钮

		// 绑定 添加事件 按钮
		Button addActivitybtn = (Button) findViewById(R.id.addActivity);
		addActivitybtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int requestCode = 1;
				Intent i = new Intent(toDoList.this, addActivity.class);
				i.putExtra("_LOCALORNOT", _localOrNot);
				i.putExtra("_USERNAME", _username);
				if (_localOrNot == 1){
					i.putExtra("_SESSIONID", _sessionid);
				}
				startActivityForResult(i, requestCode);
				toDoList.this.overridePendingTransition(R.anim.zoomin,
						R.anim.zoomout);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent i) {
		super.onActivityResult(requestCode, resultCode, i);
		if (requestCode == 1) {
			updateActivityList(iuHelper);
		} else if (requestCode == 2) {
			updateActivityList(iuHelper);
		}
	}

	// 更新活动列表
	public void updateActivityList(iuOpenHelper helper) {
		activityList.setAdapter(adapter_activityList);
		iuActivity = idusHelper.getActivities(iuHelper, 0);
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

	public static class itemViewCache {
		public TextView snTextView;
		public ImageView snImageView;
		public ImageView toDoTag;
		public TextView toDoItem;
		public TextView toDoDate;
	}

}