/**
 * File Class for files in the External Directory for an Android Application
 * 
 * @author David Coss, PhD
 */
package com.davecoss.android.lib;

import java.io.File;
import java.net.URI;

import android.os.Environment;

public class ExternalFile extends File {

	private static final long serialVersionUID = 4922627385323582513L;
	
	public ExternalFile(String path) {
		super(path);
	}

	public ExternalFile(URI uri) {
		super(uri);
	}

	public ExternalFile(File dir, String name) {
		super(dir, name);
	}

	public ExternalFile(String dirPath, String name) {
		super(dirPath, name);
	}
	
	public boolean mounted_read_only() {
		return get_external_state().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
	}
	
	public static String get_external_state() {
		return Environment.getExternalStorageState();
	}

}
