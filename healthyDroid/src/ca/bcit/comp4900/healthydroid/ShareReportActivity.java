package ca.bcit.comp4900.healthydroid;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ca.bcit.comp4900.R;
/**
 * This class pops open a window with all applications installed onto the phone which can send data. Once an app is chosen (ie an email app)
 * the report will be attached and ready to be sent out to a recipient of the user's choice.
 * @author Rajpreet Sidhu
 *
 */
public class ShareReportActivity extends Activity{
    public static final String FILE_LOCATION = "PdfAndroid";
    public static final String FILE_NAME = "healthyDroidReport.pdf";
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.sharereport);
		
    	// Call the sendReport method when the Share button is clicked
		Button back = (Button) findViewById(R.id.shareReport_shareReportButton);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendReport();
			}
		});	
    }
    /**
     * The method which attaches the report for sending to an application of the user's choice.
     */
	private void sendReport()
	{
		//create the send intent  
		Intent shareIntent =   
		 new Intent(android.content.Intent.ACTION_SEND);  

		//set the type  
		shareIntent.setType("image/*");  
		  
		//add a subject  
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,   
		 "Healthy Droid Report");  
		  
		//build the body of the message to be shared  
		String shareMessage = "Here is my Healthy Droid health report.";  
		  
		//add the message  
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Healthy Droid report");		
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/" + FILE_LOCATION + File.separator + FILE_NAME));
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage); 
		  
		//start the chooser for sharing  
		startActivity(Intent.createChooser(shareIntent,   
		 "Sharing Healthy Droid Report"));
	}
}
