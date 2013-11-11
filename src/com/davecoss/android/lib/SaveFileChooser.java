package com.davecoss.android.lib;

import java.io.File;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SaveFileChooser extends Activity {

	public static final int GET_DIRECTORY = 1;
	private File directory = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_file_chooser);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_file_chooser, menu);
		return true;
	}
	
	public void select_directory(View view) {
		Intent savefile_intent = new Intent(getBaseContext(), FileChooser.class);
		savefile_intent.setFlags(FileChooser.DIRECTORY_TYPE);
		startActivityForResult(savefile_intent,GET_DIRECTORY);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        switch(requestCode)
        {
        case GET_DIRECTORY:
        	if (resultCode == RESULT_OK) {
        		String filepath = data.getStringExtra("file");
        		if(filepath != null) {
        			directory = new File(filepath);
        			TextView dir_label = (TextView)findViewById(R.id.txtDirName);
        			dir_label.setText(directory.getName());
        		}
        	}
        	break;
        default:
        	break;
        }
	}
	
	public void select_file(View view) {
		TextView filename = (TextView)findViewById(R.id.txtFileName);
		if(filename == null || filename.getText().length() == 0)
			return;
		if(directory == null)
			return;
		
		File path = new File(directory, filename.getText().toString());
		
		Intent intent = new Intent();
		try {
			intent.putExtra("file", path.getCanonicalPath());
		} catch (IOException e) {
			filename.setText("ERROR");
			return;
		}
		setResult(RESULT_OK, intent);
		finish();
	}

}
