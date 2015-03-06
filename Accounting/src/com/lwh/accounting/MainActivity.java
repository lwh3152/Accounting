package com.lwh.accounting;

import java.util.Calendar;

import com.lwh.accounting.util.DBHelper;

import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Context context;
	private TextView mDateDisplay;
	private int mYear;
    private int mMonth;
    private int mDay;
    private RelativeLayout layoutIncome,layoutSpend;
    private TextView textIncome,textSpend,textRemain;
    private Button btnStartAccount,btnSelectAccount,btnSetAccount;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		context = MainActivity.this;
		
		mDateDisplay=(TextView)findViewById(R.id.dateDisplay);
		mDateDisplay.setOnClickListener(new ChangeDataClick());
		btnStartAccount = (Button) findViewById(R.id.btnStartAccount);
		btnSelectAccount = (Button) findViewById(R.id.btnSelectAccount);
		btnSetAccount = (Button) findViewById(R.id.btnSetAccount);
		textIncome = (TextView) findViewById(R.id.textViewIncome);
		textSpend = (TextView) findViewById(R.id.textViewSpend);
		textRemain = (TextView) findViewById(R.id.textViewRemain);
		layoutIncome = (RelativeLayout) findViewById(R.id.layoutIncome);
		layoutSpend = (RelativeLayout) findViewById(R.id.layoutSpending);
		init(context);
		
		// 记一笔
		btnStartAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context,AddAccount.class);
				startActivityForResult(intent,1);
			}
		});
		// 设置
		btnSetAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,AccountSet.class);
				startActivity(intent);
			}
		});
		// 查看流水
		btnSelectAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
			}
		});
		
		layoutIncome.setOnClickListener(new LayoutOnClick(1));	// 收入详情
		layoutSpend.setOnClickListener(new LayoutOnClick(2));	// 支出详情
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			switch (requestCode) {	// requestCode对应启动Activity时传的code
			case 0:
			case 1:	
				// 刷新数据
				updateDisplay();
				break;
			default:
				break;
			}
		}
	}
	
	class LayoutOnClick implements OnClickListener{
		int type = 0;
		public LayoutOnClick(int mType){
			this.type = mType;
		}
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this,AccountDetail.class);
			intent.putExtra("type", type);
			intent.putExtra("year", mYear);
			intent.putExtra("month", mMonth+1);
			intent.putExtra("day", mDay);
			startActivityForResult(intent, 0);
		}
	}
	
	private void init(final Context context){
		final Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
	}
	
	class ChangeDataClick implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			showDialog(0);
		}
	}
	private void updateDisplay(){
        mDateDisplay.setText(
                new StringBuilder()
                    .append(mYear).append("年")
                    .append(mMonth+1).append("月")
                    .append(mDay).append("日"));
        int income = changeAccountData(mYear,mMonth+1,mDay,1,1);
        int spending = changeAccountData(mYear, mMonth+1, mDay, 2,1);
        System.out.println("income="+income+",spending"+spending);
        textIncome.setText(Integer.toString(income));
        textSpend.setText(Integer.toString(-spending));
        textRemain.setText(Integer.toString(income+spending));

    }
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){                
        public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth){                    
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }            
    };
	protected Dialog onCreateDialog(int id){    
        switch (id){    
            case 0:       
                return new DatePickerDialog(this,mDateSetListener, mYear, mMonth, mDay);
        }    
        return null;
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// 根据选择的日期,实时更新对应的收入,支出情况
	private int changeAccountData(int year,int month,int day,int type,int status){
		DBHelper dbHelper = new DBHelper(MainActivity.this);
		dbHelper.openDatabase();
		return dbHelper.getTotalInCome(year, month, day, type, status);
	}

}
