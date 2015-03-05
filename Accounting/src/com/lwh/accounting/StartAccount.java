package com.lwh.accounting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.lwh.accounting.MainActivity.ChangeDataClick;
import com.lwh.accounting.entity.SerializableMap;
import com.lwh.accounting.util.DBHelper;
import com.lwh.accounting.util.SQLiteHelper;

import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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

	Button btnBack,btnSave,btnEdit;
	RadioGroup radioGroup;
	private TextView mDateDisplay;
	private EditText editPrice,editNote;
	private int mYear;
    private int mMonth;
    private int mDay;
    private int type = 0 ;
	private int price = 0;
	private String note;
	private boolean saveOrUpdate = false;
	private int accountId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_account);
		
		btnBack = (Button) findViewById(R.id.btnBack);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnEdit = (Button) findViewById(R.id.btnEdit);
		radioGroup = (RadioGroup) findViewById(R.id.radioType);
		mDateDisplay=(TextView)findViewById(R.id.dateDisplay);
		editPrice = (EditText) findViewById(R.id.editPrice);
		editNote = (EditText) findViewById(R.id.editContent);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			saveOrUpdate = true;
			SerializableMap serializableMap = (SerializableMap) bundle.get("accountInfo");  
			Map<String, Object> map = serializableMap.getMap();
			
			// 设置控件的隐藏,显示,不可点击
			btnSave.setVisibility(View.GONE);
			btnEdit.setVisibility(View.VISIBLE);
			//radioGroup.setClickable(false);// 类型不可选择
			editPrice.setEnabled(false);
			editNote.setEnabled(false);
			
			findViewById(R.id.income).setClickable(false);
			findViewById(R.id.spending).setClickable(false);
			mDateDisplay.setEnabled(false);
			
			// 设置控件显示的值
			String type = map.get("type").toString();
			if(type.equals("收入")){
				radioGroup.check(R.id.income);
			}else {
				radioGroup.check(R.id.spending);
			}
			// id
			String index = map.get("index").toString();
			index = index.replace(".", "");
			accountId = Integer.parseInt(index);
			
			// 金额
			String price = map.get("price").toString();
			editPrice.setText(price);
			editPrice.setSelection(price.length());// 设置光标的位置
			// 日期
			String date = map.get("date").toString();
			mDateDisplay.setText(date);
			// 将日期的年月日赋给对应的变量
			String strDateArr[] = date.split("-");
			mYear = Integer.parseInt(strDateArr[0]);
			mMonth = Integer.parseInt(strDateArr[1]);
			mDay = Integer.parseInt(strDateArr[2]);
			
			// 备注
			String note = map.get("note").toString();
			editNote.setText(note);
			
//			System.out.println("禁止软键盘");
//			editText.setInputType(InputType.TYPE_NULL);
//			System.out.println("开启软键盘");
//			editText.setInputType(InputType.TYPE_CLASS_TEXT);
		}else {
			Calendar c=Calendar.getInstance();
	        mYear=c.get(Calendar.YEAR);
	        mMonth=c.get(Calendar.MONTH);
	        mDay=c.get(Calendar.DAY_OF_MONTH);
	        updateDisplay();
		}
		
		mDateDisplay.setOnClickListener(new ChangeDataClick());
		btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnEdit.setVisibility(View.GONE);
				btnSave.setVisibility(View.VISIBLE);
				findViewById(R.id.income).setClickable(true);
				findViewById(R.id.spending).setClickable(true);
				mDateDisplay.setEnabled(true);
				editPrice.setEnabled(true);
				editNote.setEnabled(true);
			}
		});
		
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
				
				
				note = editNote.getText().toString().trim();
				
				DBHelper dbHelper = new DBHelper(StartAccount.this);
				dbHelper.openDatabase();
				String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				long result ;
				if (saveOrUpdate) {	// 保存编辑
					result = dbHelper.updateAccount(accountId, type, price, year, month, day, note,d);
				}else {
					result = dbHelper.insertData(price, type, 1, year, month, day, note, d, d);
				}
				if (result > 0) {
					Toast.makeText(StartAccount.this, "保存成功", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(StartAccount.this, "保存失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		
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
