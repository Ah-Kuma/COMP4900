package ca.bcit.comp4900.healthydroid.chart;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class BarChart {
	public Intent getIntent(Context context){
		int[] y = {0,10,20,30,40};
		CategorySeries series = new CategorySeries("Demo Bar Graph");
		for(int i =0; i < y.length; i++){
			series.add("Bar " + (i+1), y[i]);
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());
		
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing((float)0.5);
		renderer.setColor(Color.WHITE);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("Demo Graph Title");
		mRenderer.setXTitle("X Values");
		mRenderer.setYTitle("Y Values");
		
		mRenderer.addSeriesRenderer(renderer);
		
		Intent intent = ChartFactory.getBarChartIntent(context, dataset, mRenderer, Type.DEFAULT);
		
		return intent;
	}
}
