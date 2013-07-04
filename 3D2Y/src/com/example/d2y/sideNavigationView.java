package com.example.d2y;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class sideNavigationView extends ListActivity {
	private String[] snItems = {"搜索活动", "消息", "附近的活动", "我的活动",
			"我的好友" };
	private int[] snImages = {R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
	public class sideNavigationAdapter extends BaseAdapter {
		private Context snContext;

		
		public sideNavigationAdapter(Context context) {
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
				convertView = LayoutInflater.from(snContext).inflate(R.layout.snmenu, null);
				itemViewCache itemView = new itemViewCache();
				itemView.snLayout = (RelativeLayout)convertView.findViewById(R.id.snLayout);
				itemView.snTextView = (TextView)convertView.findViewById(R.id.snText);
//				itemView.snImageView = (ImageView)convertView.findViewById(R.id.snImage);
				convertView.setTag(itemView);
			}
			itemViewCache cache = (itemViewCache)convertView.getTag();
			cache.snTextView.setText(snItems[position]);
/*			cache.snImageView.setImageResource(snImages[position]);*/
			return convertView;
		}
	}
	
	public static class itemViewCache {
		public RelativeLayout snLayout;
		public TextView snTextView;
		public ImageView snImageView;
	}
}
