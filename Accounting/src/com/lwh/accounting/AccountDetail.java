package com.lwh.accounting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lwh.accounting.entity.Accounting;
import com.lwh.accounting.util.DBHelper;

import android.R.anim;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AccountDetail extends Activity{

	TextView textDetail;
	ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acount_detail);
		
		findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AccountDetail.this.finish();
			}
		});
		
		Intent intent = getIntent();
		int type = intent.getExtras().getInt("type");
		int year = intent.getExtras().getInt("year");
		int month = intent.getExtras().getInt("month");
		int day = intent.getExtras().getInt("day");
		
		textDetail = (TextView) findViewById(R.id.textDetail);
		if (type == 1) {
			textDetail.setText("收入详情");
		}else if (type == 2){
			textDetail.setText("支出详情");
		}
		
		DBHelper dbHelper = new DBHelper(AccountDetail.this);
		dbHelper.openDatabase();
		List<Accounting> accountList = dbHelper.getAccounting(year, month, day, type, 1);
		
		listview = (ListView) findViewById(R.id.listView);
		SimpleAdapter adapter = new SimpleAdapter(this,getData(accountList),R.layout.listview_detail,
                new String[]{"index","date","type","price"},
                new int[]{R.id.textIndex,R.id.textDate,R.id.textType,R.id.textPrice});
		listview.setAdapter(adapter);
	}
	
	private List<Map<String, Object>> getData(List<Accounting> accountList) {
		
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (accountList != null && accountList.size() > 0) {
			for (int i = 0;i<accountList.size();i++) {
				Accounting accounting = accountList.get(i);
				int year = accounting.getYear();
				int month = accounting.getMonth();
				int day = accounting.getDay();
				int type = accounting.getType();
				int price = accounting.getPrice();
				
				String date = year + "-" + month + "-" + day;
				String strType = type == 1 ? "收入" : "支出" ;
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("index", i+1+".");
		        map.put("date", date);
		        map.put("type", strType);
		        map.put("price", price);
		        list.add(map);
			}
		}

        return list;

    }
}
