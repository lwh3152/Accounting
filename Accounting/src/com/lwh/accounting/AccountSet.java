package com.lwh.accounting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lwh.accounting.util.PreferencesUtil;

public class AccountSet extends Activity{
	Context context;
	RadioButton radioButtonYear,radioButtonMonth,radioButtonDay;
	RadioGroup radioGroupDate;
	public static final String PRE_KEY_NAME = "Date";
	public static final int DATE_YEAR  = 1;
	public static final int DATE_MONTH = 2;
	public static final int DATE_DAY   = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings2);
		// what is happen
		
		context = AccountSet.this;
		findViewById(R.id.btSettingBack).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				AccountSet.this.finish();
			}
		});
		
		radioButtonYear = (RadioButton) findViewById(R.id.radioYear);
		radioButtonMonth= (RadioButton) findViewById(R.id.radioMonth);
		radioButtonDay  = (RadioButton) findViewById(R.id.radioDay);
		long setDate = PreferencesUtil.getValue(context, PRE_KEY_NAME);	// 获取preferences中保存的参数配置
		if (setDate == 0 || setDate == 3) {	// 如果默认为0或则3,则是"按日查看"
			radioButtonDay.setChecked(true);
		}else if(setDate == 1){
			radioButtonYear.setChecked(true);
		}else if(setDate == 2){
			radioButtonMonth.setChecked(true);
		}
		
		radioGroupDate = (RadioGroup) findViewById(R.id.radioGroupDate);
		radioGroupDate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radioYear:
					PreferencesUtil.putValue(context, DATE_YEAR, PRE_KEY_NAME);
					break;
				case R.id.radioMonth:
					PreferencesUtil.putValue(context, DATE_MONTH, PRE_KEY_NAME);
					break;
				case R.id.radioDay:
					PreferencesUtil.putValue(context, DATE_DAY, PRE_KEY_NAME);
					break;
				default:
					break;
				}
			}
		});
	}
}
