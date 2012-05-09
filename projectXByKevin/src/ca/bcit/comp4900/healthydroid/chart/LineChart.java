package ca.bcit.comp4900.healthydroid.chart;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

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
			series.add(xData.get(i), yData.get(i));
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.WHITE);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setChartTitle("Question 4");
		mRenderer.setLabelsColor(Color.YELLOW);
		/*
		for(int i = 0; i < yData.size(); i++){
			mRenderer.addXTextLabel(i, Integer.toString(xData.get(i)));
		}
		*/
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Question 4 Report");
		return intent;
	}
}
