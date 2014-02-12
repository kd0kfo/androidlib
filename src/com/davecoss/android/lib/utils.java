package com.davecoss.android.lib;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

public class utils {

	public static int unixtime()
	{
		Date now = new Date();  	
		Long longTime = Long.valueOf(now.getTime()/1000);
		return longTime.intValue();
	}
	
	public static String get_app_version(ContextWrapper act) throws NameNotFoundException
	{
		PackageInfo pi = act.getPackageManager().getPackageInfo(act.getPackageName(), 0);
		String app_ver = pi.versionName;
		String app_code = Integer.toString(pi.versionCode);
		return "Version " + app_ver + " Build " + app_code;
	}
	
	public static ExternalFile get_external_dir(Activity parent) throws ExternalFileUnavailable {
		String state = ExternalFile.get_external_state();
    	
    	if (!Environment.MEDIA_MOUNTED.equals(state) && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
    	    // In this case, it is neither RW or RO. Therefore, fail.
    		throw new ExternalFileUnavailable("External Directory for " + parent.getTitle() + " is unavailable.");
    	} 
    	
    	File dir = parent.getExternalFilesDir(null);
    	if(dir == null)
    		throw new ExternalFileUnavailable("Unable to get external file for: " + parent.getLocalClassName());
    	return new ExternalFile(dir.getAbsolutePath());
	}
}
