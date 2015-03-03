package com.lwh.accounting;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class StartAccount extends Activity {

	Button btnBack,btnSave;
	
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
				Toast.makeText(StartAccount.this, "save", 1000).show();
			}
		});
	}
	
}
