package com.davecoss.android.lib;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationActivity extends Activity {
	public static final String MESSAGE = "message";
	public static final String OK_BTN_TEXT = "ok_text";
	public static final String CANCEL_BTN_TEXT = "cancel_text";
	public static final String RESULT = "result";
	
	public static final boolean CONFIRM = true;
	public static final boolean CANCEL = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmation);
		
		TextView txtMessage = (TextView)findViewById(R.id.txtMessage);
		Intent intent = getIntent();
		String msg = intent.getStringExtra(MESSAGE);
		if(msg == null)
			txtMessage.setText("Confirm?");
		else
			txtMessage.setText(msg);
		
		Button btn = null;
		if(intent.hasExtra(OK_BTN_TEXT))
		{
			btn = (Button)findViewById(R.id.btnConfirm);
			btn.setText(intent.getStringExtra(OK_BTN_TEXT));
		}
		if(intent.hasExtra(CANCEL_BTN_TEXT))
		{
			btn = (Button)findViewById(R.id.btnCancel);
			btn.setText(intent.getStringExtra(CANCEL_BTN_TEXT));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public void doConfirm(View view) {
		Intent intent = new Intent();
		intent.putExtra(RESULT, CONFIRM);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	public void doCancel(View view) {
		Intent intent = new Intent();
		intent.putExtra(RESULT, CANCEL);
		setResult(RESULT_CANCELED, intent);
		finish();
	}

}
