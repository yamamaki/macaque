package com.example.d2y;

import static com.example.d2y.iuOpenHelper.*;
import java.util.ArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class toDoList extends Activity {
	iuOpenHelper iuHelper;
	String [] _activity;
	String [] _date;
	String [] _tag;
	ListView itemList;
	BaseAdapter adapter_itemList = new BaseAdapter() {
       	@Override 
       	public int getCount() {
       		if (_activity != null) {
       			return _activity.length;
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
       		tv.setText(_activity[position]);
       		tv.setTextSize(32);
       		tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
       				LayoutParams.WRAP_CONTENT));
       		tv.setGravity(Gravity.CENTER_VERTICAL);
       		TextView tv2 = new TextView(toDoList.this);
       		tv2.setText("["+_date[position]+"]");
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
        iuHelper = new iuOpenHelper(this, DB_TABLE, null, 1);
        
        itemList = (ListView)findViewById(R.id.itemList);
        itemList.setAdapter(adapter_itemList);
        
        //绑定删除活动事件
        itemList.setOnItemLongClickListener(new OnItemLongClickListener() {
        	@Override
        	public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long id) {
        		AlertDialog.Builder delDialog = new AlertDialog.Builder(toDoList.this);
        		delDialog.setTitle("删除");
        		delDialog.setMessage("是否要删除活动 "+_activity[position]);
        		delDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {}
				});
        		delDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						long res = delActivity(iuHelper, _activity[position]);
						
						if (res == 0) {
							Toast.makeText(toDoList.this, "对不起，删除活动失败！",
				        			Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(toDoList.this, "删除活动成功！",
				        			Toast.LENGTH_SHORT).show();
							getActivity(iuHelper);
						}
					}
				});
        		delDialog.show();
        		return true;
        	}
        });
        
        //绑定更新活动事件
        itemList.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
        		int requestCode = 2;
        		Intent i = new Intent(toDoList.this, updateItem.class);
        		i.putExtra("_ACTIVITY", _activity[position]);
        		startActivityForResult(i, requestCode);
        	}
        });
        getActivity(iuHelper);
        // 绑定 添加事件 按钮
        Button addItem = (Button)findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int requestCode = 1;
				Intent i = new Intent(toDoList.this, addItem.class);
				startActivityForResult(i, requestCode);
			}
		});    
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == 1) {
    		getActivity(iuHelper);
    	}
    	else if (requestCode == 2) {
    		getActivity(iuHelper);
    	}
/*    	if (resultCode == RESULT_OK) {
//    		String receive_item = data.getExtras().getString("receive_item");
//    		array_itemList.add(0, receive_item);
    		getActivity(iuHelper);
    	}*/
    	
    }
    
    //更新活动列表
    public void getActivity(iuOpenHelper helper) {
    	SQLiteDatabase _db = helper.getWritableDatabase();
    	itemList = (ListView)findViewById(R.id.itemList);
        itemList.setAdapter(adapter_itemList);
    	Cursor c = _db.query(DB_TABLE, new String[] {KEY_ACTIVITY, KEY_DATE, KEY_TAG},
    			null, null, null, null, KEY_ACTIVITY);
    	int activityIndex = c.getColumnIndex(KEY_ACTIVITY);
    	int dateIndex = c.getColumnIndex(KEY_DATE);
    	int tagIndex = c.getColumnIndex(KEY_TAG);
    	_activity = new String[c.getCount()];
    	_date = new String[c.getCount()];
    	_tag = new String[c.getCount()];
    	int i = 0;
    	for (c.moveToFirst(); !(c.isAfterLast()); c.moveToNext()) {
    		_activity[i] = c.getString(activityIndex);
    		_date[i] = c.getString(dateIndex);
    		_tag[i] = c.getString(tagIndex);
    		++i;
    	}
    	c.close();
    	_db.close();
    }
    
    //删除活动
    public long delActivity(iuOpenHelper helper, String activity) {
    	long res = 0;
    	String [] arg = {activity};
    	SQLiteDatabase _db = helper.getWritableDatabase();
    	res = _db.delete(DB_TABLE, KEY_ACTIVITY + "=?", arg);
    	_db.close();
    	return res;
    }
}