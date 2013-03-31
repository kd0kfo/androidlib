package com.davecoss.android.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.os.Environment;
import android.util.Log;

public class SDIO {
	
	public static OutputStream open_sdwriter(String filename)throws IOException{return open_sdwriter(filename,true);}
	public static OutputStream open_sdwriter(String filename, boolean should_append) throws IOException
	{
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		
		if(filename == null || filename.length() == 0)
			return null;
		
		String state = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		
		if(mExternalStorageAvailable && mExternalStorageWriteable)
		{
			File dir = Environment.getExternalStorageDirectory();
			if(!dir.exists())
			{
				if(!dir.mkdirs())
				{
					Log.e("SDIO","Could not create directory to write file: " + filename);
					return null;
				}
			}
			File file = new File(dir, filename);
			OutputStream os = new FileOutputStream(file,should_append);
			return os;
		}
		
		return null;
	}
}
