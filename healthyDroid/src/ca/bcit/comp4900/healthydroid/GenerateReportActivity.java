package ca.bcit.comp4900.healthydroid;

import ca.bcit.comp4900.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Activity that lets the user to select range of dates for generating a report.
 * 
 * @author Kevin, William
 *
 */
public class GenerateReportActivity extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.generatereport);
		//Report report = new Report(this.getBaseContext());
		//ImageView imageView = (ImageView) findViewById(R.id.generateReport_imageView);
		//imageView.setImageBitmap(report.getLineBitMap(3));
    }
}
