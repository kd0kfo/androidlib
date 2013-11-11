/**
 * Android Notification Class
 * 
 * Acts as an communication layer between an application and the user. Uses Toast to send messages 
 * to the user. Also can be used to send messages to the logcat.
 * 
 * @author David Coss, PhD
 */
package com.davecoss.android.lib;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Notifier {
	private Context context;
	
	/**
	 * 
	 * @param _context Context Android Activity Context with which the Notifier should be associated.
	 */
	public Notifier(Context _context)
	{
		context = _context;
	}

	/**
	 * Toasts a message to the user
	 * 
	 * @param msg String Message to be toasted to the user.
	 */
	public void toast_message(String msg)
    {
    	int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }
   
	/**
	 * Writes a message to a Text View
	 * 
	 * @param view TextView
	 * @param msg String
	 */
   public void write_message(View view, String msg)
   {
	   TextView text = (TextView) view;
       text.setText(msg);
   }
 
   /**
    * Logs an exception and toasts the message to the user.
    * 
    * @param tag String Lists the source of the exception within the application
    * @param msg String Message about the exception
    * @param e Exception
    */
   public void log_exception(String tag, String msg, Exception e)
   {
	   toast_message(msg);
	   Log.e("ListDB",msg + "\n" + e.getMessage());
   }
   
}