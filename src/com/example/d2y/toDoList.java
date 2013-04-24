package com.example.d2y;

import static com.example.d2y.iuOpenHelper.*;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class toDoList extends Activity {
	iuActivity [] iuActivity;
	final iuOpenHelper iuHelper = new iuOpenHelper(this, DB_TABLE, null, 1);
	final idusActivityHelper idusHelper = new idusActivityHelper(); 
	ListView activityList;
	BaseAdapter adapter_activityList = new BaseAdapter() {
       	@Override 
       	public int getCount() {
       		if (iuActivity != null) {
       			return iuActivity.length;
       		}
       		else return 0;
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
       		LinearLayout ll = new LinearLayout(toDoList.this);
       		ll.setOrientation(LinearLayout.HORIZONTAL);
       		TextView tv = new TextView(toDoList.this);
       		tv.setText(iuActivity[position]._activity);
       		tv.setTextSize(32);
       		tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
       				LayoutParams.WRAP_CONTENT));
       		tv.setGravity(Gravity.CENTER_VERTICAL);
       		TextView tv2 = new TextView(toDoList.this);
       		tv2.setText("["+iuActivity[position]._date+"]");
       		tv2.setTextSize(28);
       		tv2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
       				LayoutParams.WRAP_CONTENT));
       		tv2.setGravity(Gravity.BOTTOM|Gravity.RIGHT);
       		ll.addView(tv);
       		ll.addView(tv2);
       		return ll;
       	}
       };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.todolist);
        activityList = (ListView)findViewById(R.id.activityList);
        activityList.setAdapter(adapter_activityList);
        
        //绑定删除活动事件
        activityList.setOnItemLongClickListener(new OnItemLongClickListener() {
        	@Override
        	public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long id) {
        		AlertDialog.Builder delDialog = new AlertDialog.Builder(toDoList.this);
        		delDialog.setTitle("删除");
        		delDialog.setMessage("是否要删除活动 "+iuActivity[position]._activity);
        		delDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {}
				});
        		delDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						long res = idusHelper.deleteActivity(iuHelper, iuActivity[position]._activity);
						if (res == -1) {
							Toast.makeText(toDoList.this, "对不起，删除活动失败！",
				        			Toast.LENGTH_SHORT).show();
						}
						else {
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
        
        //绑定更新活动事件
        activityList.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
        		int requestCode = 2;
        		Intent i = new Intent(toDoList.this, updateActivity.class);
        		i.putExtra("_ACTIVITY", iuActivity[position]._activity);
        		startActivityForResult(i, requestCode);
        	}
        });
        
        updateActivityList(iuHelper);
        
        // 绑定 添加事件 按钮
        Button addActivitybtn = (Button)findViewById(R.id.addActivity);
        addActivitybtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int requestCode = 1;
				Intent i = new Intent(toDoList.this, addActivity.class);
				startActivityForResult(i, requestCode);
			}
		});    
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent i) {
    	super.onActivityResult(requestCode, resultCode, i);
    	if (requestCode == 1) {
    		updateActivityList(iuHelper);
    	}
    	else if (requestCode == 2) {
    		updateActivityList(iuHelper);
    	}    	
    }
    
    //更新活动列表
    public void updateActivityList(iuOpenHelper helper) {
    	activityList.setAdapter(adapter_activityList);
    	iuActivity = idusHelper.getActivities(iuHelper);
    }
}