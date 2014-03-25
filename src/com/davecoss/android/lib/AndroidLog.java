package com.davecoss.android.lib;

import android.util.Log;

import java.io.OutputStream;

import com.davecoss.java.LogHandler;
import com.davecoss.java.Logger;

public class AndroidLog implements LogHandler {
	
	private Level level = Level.ERROR;
	private String tag = "AndroidLog";
	
	protected AndroidLog() {
	}
	
	public static Logger getInstance(String prefix) {
		Logger logger = Logger.getInstance();
		AndroidLog androidLog = new AndroidLog();
		androidLog.tag = prefix;
		Logger.setLog(androidLog);
		return logger;
	}
	
	
	@Override
	public void setLevel(Level newlevel) {
		level = newlevel;
	}

	@Override
	public Level getLevel() {
		return level;
	}

	@Override
	public OutputStream setLogStream(OutputStream stream) {
		throw new UnsupportedOperationException("Cannot open output stream for android log.");
	}

	@Override
	public void fatal(String msg) {
		Log.wtf(tag, msg);
	}

	@Override
	public void fatal(String msg, Throwable throwable) {
		Log.wtf(tag, throwable);
	}

	@Override
	public void error(String msg) {
		Log.e(tag, msg);
	}

	@Override
	public void error(String msg, Throwable throwable) {
		Log.e(tag, msg, throwable);
	}

	@Override
	public void debug(String msg) {
		Log.d(tag, msg);
	}

	@Override
	public void debug(String msg, Throwable throwable) {
		Log.d(tag, msg, throwable);
	}

	@Override
	public void warn(String msg) {
		Log.w(tag, msg);
	}

	@Override
	public void warn(String msg, Throwable throwable) {
		Log.w(tag, throwable);
	}

	@Override
	public void info(String msg) {
		Log.i(tag, msg);
	}

	@Override
	public void info(String msg, Throwable throwable) {
		Log.i(tag, msg, throwable);
	}
	
	@Override
	public void verbose(String msg) {
		Log.v(tag, msg);
	}

	@Override
	public void verbose(String msg, Throwable throwable) {
		Log.v(tag, msg, throwable);
	}

}
