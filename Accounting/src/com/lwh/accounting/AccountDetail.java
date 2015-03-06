package com.lwh.accounting;

import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.lwh.accounting.adapter.AccountAdapter;
import com.lwh.accounting.entity.Accounting;
import com.lwh.accounting.util.DBHelper;

public class AccountDetail extends Activity{

	TextView textDetail;
	ListView listview;
	List<Map<String, Object>> accountData;
	int type,year,month,day;
	SimpleAdapter adapter;
	AccountAdapter dataAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acount_detail);
		
		findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				AccountDetail.this.finish();
			}
		});
		
		Intent intent = getIntent();
		type = intent.getExtras().getInt("type");
		year = intent.getExtras().getInt("year");
		month = intent.getExtras().getInt("month");
		day = intent.getExtras().getInt("day");
		
		textDetail = (TextView) findViewById(R.id.textDetail);
		if (type == 1) {
			textDetail.setText("收入详情");
		}else if (type == 2){
			textDetail.setText("支出详情");
		}
		
		// 查询收入/支出的列表
		DBHelper dbHelper = new DBHelper(AccountDetail.this);
		dbHelper.openDatabase();
		final List<Accounting> accountList = dbHelper.getAccounting(year, month, day, type, 1);
		
		listview = (ListView) findViewById(R.id.listView);
		dataAdapter = new AccountAdapter(AccountDetail.this, accountList);
		listview.setAdapter(dataAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent intent = new Intent(AccountDetail.this,AddAccount.class);
				Accounting account = accountList.get(arg2);
				intent.putExtra("account", account);
				startActivityForResult(intent, 0);
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			// 编辑成功回来后,刷新列表
			// 刷新listview,首先得是数据源变化,再调用notifyDataSetChanged方法
			// 重新的查询一遍数据
			DBHelper dbHelper = new DBHelper(AccountDetail.this);
			dbHelper.openDatabase();
			List<Accounting> accountList = dbHelper.getAccounting(year, month, day, type, 1);
			
			dataAdapter = new AccountAdapter(AccountDetail.this, accountList);
			dataAdapter.notifyDataSetChanged();
			listview.setAdapter(dataAdapter);
		}
	}
}
