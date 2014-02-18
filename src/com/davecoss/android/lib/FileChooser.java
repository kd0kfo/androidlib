package com.davecoss.android.lib;

/**
 * FileChooser
 * @author David Coss, PhD
 * 
 * List Activity used to select a file from the external file system of the Android device.
 * 
 * To use, pass a start directory as an extra in the intent used to start the activity. Then,
 * when a file has been selected the path will be placed in the result intent. There are START_DIRECTORY_KEY
 * and RETURN_VALUE_KEY static strings used to identify the start directory and return value, respectively.
 */

import java.io.File;
import java.io.IOException;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FileChooser extends ListActivity {
	public static final int TYPE_NOT_SPECIFIED = 0;
	public static final int FILE_TYPE = 1;
	public static final int DIRECTORY_TYPE = 2;
	
	public static final String START_DIRECTORY_KEY = "directory";
	public static final String RETURN_VALUE_KEY = "file";
	
	private File currdir = null;
	private Notifier notifier = null;
	private int target_type = FILE_TYPE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_file_chooser);
		
		notifier = new Notifier(getApplicationContext());
		
		Intent intent = getIntent();
		String startdir = intent.getStringExtra(START_DIRECTORY_KEY);
		target_type = intent.getFlags();
		if(target_type == TYPE_NOT_SPECIFIED)
			target_type = FILE_TYPE;
		try {
			if(startdir == null)
				currdir = utils.get_external_dir(this);
			else
				currdir = new File(startdir);
			update_dir_list();
		} catch (IOException ioe) {
			notifier.log_exception("FileChooser", "Error getting file listing for '" + currdir.getAbsolutePath() + "'", ioe);
		} catch (ExternalFileUnavailable e) {
			notifier.log_exception("FileChooser", "Error getting file listing for application. ", e);
		}
		
		
		ListView filelist = getListView();
		filelist.setTextFilterEnabled(true);
		 
		filelist.setOnItemClickListener(new OnItemClickListener() {
			public void return_path(File path) {
				Intent intent = new Intent();
				try {
					intent.putExtra(RETURN_VALUE_KEY, path.getCanonicalPath());
					setResult(RESULT_OK, intent);
				} catch (IOException e) {
					notifier.log_exception("FileChooser", "Error getting canonical path to: '" + path.getAbsolutePath() + "'", e);
					setResult(RESULT_CANCELED, intent);
				}
				finish();
			}
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    String filename = ((TextView) view).getText().toString();
			    if(currdir == null)
			    	currdir = new File(".");
			    
			    if(target_type == DIRECTORY_TYPE && filename == "SELECT")
			    	return_path(currdir);
			    
			    File path = new File(currdir, filename);
			    
			    if(path.isDirectory()) {
			    	try {
						currdir = path.getCanonicalFile();
						update_dir_list();
					} catch (IOException e) {
						notifier.log_exception("FileChooser.onItemClick", "Error entering directory: '" + currdir.getAbsolutePath() + "'", e);
					}
			    	return;
			    }
			    
			    return_path(path);
			}
		});
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_chooser, menu);
		return true;
	}
	
	public String[] get_files(File dir) throws IOException {
		if(!dir.exists() || !dir.isDirectory()) {
			throw new IOException("Not a directory: '" + dir.getAbsolutePath() + "'");
		}
		
		File[] files = dir.listFiles();
		int entry_count = 0;
		for(File file : files) {
			if(target_type == DIRECTORY_TYPE && file.isDirectory())
				entry_count++;
			if(target_type == FILE_TYPE)
				entry_count++;
		}
		
		int extras = 1;
		if(target_type == DIRECTORY_TYPE)
			extras++;
		String[] retval = new String[entry_count + extras];
		entry_count = 0;
		if(target_type == DIRECTORY_TYPE)
			retval[entry_count++] = "SELECT";
		retval[entry_count++] = "..";
		for(File file : files) {
			if(target_type == FILE_TYPE)
				retval[entry_count++] = file.getName();
			else if(target_type == DIRECTORY_TYPE && file.isDirectory())
				retval[entry_count++] = file.getName();
		}
		
		return retval;
	}
	
	public String[] get_files(String dirpath) throws IOException {
		return get_files(new File(dirpath));
	}
	
	public void update_dir_list() throws IOException {
		if(currdir == null)
			return;
		
		this.setTitle(currdir.getPath());
		String[] files = null;
		files = get_files(currdir);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_file_chooser, files));
	}
}
