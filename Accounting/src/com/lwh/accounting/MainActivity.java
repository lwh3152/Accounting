package com.lwh.accounting;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Context context;
	private TextView mDateDisplay;
	private int mYear;
    private int mMonth;
    private int mDay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		context = MainActivity.this;
		
		mDateDisplay=(TextView)findViewById(R.id.dateDisplay);
		init(context);
		mDateDisplay.setOnClickListener(new ChangeDataClick());
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
    }
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() 
    {                
        public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
        {                    
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

}
