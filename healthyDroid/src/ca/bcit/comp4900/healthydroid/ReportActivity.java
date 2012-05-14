package ca.bcit.comp4900.healthydroid;

import ca.bcit.comp4900.R;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

/**
 * Container TabActivity for the GenerateReportActivity and ShareReportActivity.
 * User is able to switch between these two tabs
 * @author Kevin, William
 *
 */
public class ReportActivity extends TabActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, GenerateReportActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("tab1").setIndicator("Generate Report",
                          res.getDrawable(R.drawable.tab1))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tab
        intent = new Intent().setClass(this, ShareReportActivity.class);
        spec = tabHost.newTabSpec("tab2").setIndicator("Share Report",
                          res.getDrawable(R.drawable.tab2))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}

