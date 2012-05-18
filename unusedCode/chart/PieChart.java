package ca.bcit.comp4900.healthydroid.chart;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class PieChart {
	
	public Intent getIntent(Context context)
	{
		int [] values = {1,2,3,4,5}; 
		
		CategorySeries series = new CategorySeries("Pie Graph");
		
		int k = 0;
		for (int value : values){
			series.add("Section " + ++k, value);
		}
		int [] colors = new int[] {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN};
		
		DefaultRenderer renderer = new DefaultRenderer();
		
		for(int color : colors){
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		
		Intent intent = ChartFactory.getPieChartIntent(context, series, renderer, "Pie");
		return intent;
	}
}
