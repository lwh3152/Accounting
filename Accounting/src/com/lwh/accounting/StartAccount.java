package com.lwh.accounting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.lwh.accounting.MainActivity.ChangeDataClick;
import com.lwh.accounting.util.DBHelper;
import com.lwh.accounting.util.SQLiteHelper;

import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class StartAccount extends Activity {

	Button btnBack,btnSave;
	RadioGroup radioGroup;
	int type = 0 ;
	int price = 0;
	String note;
	private TextView mDateDisplay;
	private int mYear;
    private int mMonth;
    private int mDay;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_account);
		
		btnBack = (Button) findViewById(R.id.btnBack);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				StartAccount.this.finish();
			}
		});
		
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(type == 0){
					Toast.makeText(StartAccount.this, "请选择类型", Toast.LENGTH_SHORT).show();
					return;
				}
				EditText editPrice = (EditText) findViewById(R.id.editPrice);
				String strPrice = editPrice.getText().toString().trim();
				if (strPrice.equals("") || strPrice.length() == 0) {
					Toast.makeText(StartAccount.this, "请输入金额", Toast.LENGTH_SHORT).show();
					return;
				}
				price = Integer.parseInt(strPrice);
				if (type == 2) {	// 支出
					price = -price;
				}
				
				String date = mDateDisplay.getText().toString();
				String[] strArr = date.split("-");
				int year = Integer.parseInt(strArr[0]);
				int month = Integer.parseInt(strArr[1]);
				int day = Integer.parseInt(strArr[2]);
				
				EditText editNote = (EditText) findViewById(R.id.editContent);
				note = editNote.getText().toString().trim();
				
				DBHelper dbHelper = new DBHelper(StartAccount.this);
				dbHelper.openDatabase();
				String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				long result = dbHelper.insertData(price, type, 1, year, month, day, note, d, d);
				if (result > 0) {
					Toast.makeText(StartAccount.this, "保存成功", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(StartAccount.this, "保存失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		radioGroup = (RadioGroup) findViewById(R.id.radioType);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.income){	// 收入
					type = 1;
				}else if(checkedId == R.id.spending){	// 支出
					type = 2;
				}
			}
		});
		
		
		mDateDisplay=(TextView)findViewById(R.id.dateDisplay);
		mDateDisplay.setOnClickListener(new ChangeDataClick());
		Calendar c=Calendar.getInstance();
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
	protected Dialog onCreateDialog(int id){
        switch (id){
            case 0:
                return new DatePickerDialog(this,mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth){
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }            
    };
    private void updateDisplay(){
        mDateDisplay.setText(
                new StringBuilder()
                    .append(mYear).append("-")
                    .append(mMonth+1).append("-")
                    .append(mDay));
    }
}
