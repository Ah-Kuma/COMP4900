package ca.bcit.comp4900.healthydroid.chart;

import java.util.ArrayList;
import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class LineChart {
	private static ArrayList<Integer> xData = new ArrayList<Integer>(0);
	private static ArrayList<Integer> yData = new ArrayList<Integer>(0);
	
	public void setData(int x, int y){
		xData.add(x);
		yData.add(y);
	}
	
	public Intent getIntent(Context context){
		//int [] x = {1,2,3,4,5,6,7,8,9,10};
		//int [] y = {30,34,45,57,77,89,100,111,123,145};
		
		TimeSeries series = new TimeSeries("Question 4");
		for(int i = 0; i < xData.size(); i++){
			series.add(i, yData.get(i));
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.WHITE);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(false);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setChartTitle("Question 4");
		mRenderer.setXLabelsColor(Color.YELLOW);
		mRenderer.setYLabelsColor(0, Color.YELLOW);
		mRenderer.setXLabels(0);
		for(int i = 0; i < xData.size(); i++){
			mRenderer.addXTextLabel(i, Integer.toString(xData.get(i)));
		}
		
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Question 4 Report");
		return intent;
	}
	
	public static long daysBetween(Calendar startDate, Calendar endDate) {   
		  Calendar date = (Calendar) startDate.clone();   
		  long daysBetween = 0;   
		  while (date.before(endDate)) {   
		    date.add(Calendar.DAY_OF_MONTH, 1);   
		    daysBetween++;   
		  }   
		  return daysBetween;   
		}  
}
