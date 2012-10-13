package com.davecoss.android.lib;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Notifier {
	private Context context;
	
	public Notifier(Context _context)
	{
		context = _context;
	}

   public void toast_message(String msg)
    {
    	int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }
   
   public void write_message(View view, String msg)
   {
	   TextView text = (TextView) view;
       text.setText(msg);
   }
 
   
}