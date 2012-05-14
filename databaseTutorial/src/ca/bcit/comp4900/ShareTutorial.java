package ca.bcit.comp4900;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShareTutorial extends Activity
{
	Button start;
	
	 public void onCreate(Bundle savedInstanceState)
	 {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.share);	       	        
	 }
	 
	private void doStuff()
	{
		//create the send intent  
		Intent shareIntent =   
		 new Intent(android.content.Intent.ACTION_SEND);  
		
		//create a folder for our app
		File reportDirectory = new File("/sdcard/HeathlyDroid/Report/");
		reportDirectory.mkdir();
		//set the type  
		shareIntent.setType("image/*");  
		  
		//add a subject  
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,   
		 "Testing share thing");  
		  
		//build the body of the message to be shared  
		String shareMessage = "This is the message body";  
		  
		//add the message  
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,   
		 shareMessage);
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");		
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/roms/test.pdf"));
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage); 
		  
		//start the chooser for sharing  
		startActivity(Intent.createChooser(shareIntent,   
		 "This is the title of the share thing"));
	}
	
	public void onClick(View view)
	{
		if(view.getId() == R.id.startActivity)
		{
			doStuff();
		}
			
	}
}
