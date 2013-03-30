package com.davecoss.android.lib;

import java.util.Date;

import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

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
		return "Version " + app_ver + "_" + app_code;
	}
}
