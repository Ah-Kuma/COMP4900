package ca.bcit.comp4900.healthydroid.chart;

import java.util.ArrayList;
import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
/**
 * 
 * @author Kevin, William
 * @version 1.0
 */
public class LineChart {
	private ArrayList<Integer> xData = new ArrayList<Integer>(0);
	private ArrayList<Integer> yData = new ArrayList<Integer>(0);
	private ArrayList<String> xDataLabel;
	
	/**
	 * Store the x labels
	 * @param xLabel An arrayList that contains all the labels for x axis
	 */
	public void setXDataLabel(ArrayList<String> xLabel){
		xDataLabel = xLabel;	
	}
	
	public void setData(int x, int y){
		xData.add(x);
		yData.add(y);
	}
	
	public GraphicalView getView(Context context, int questionNum){
		TimeSeries series = new TimeSeries("Question " + Integer.toString(questionNum));
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
		mRenderer.setChartTitle("Question " + Integer.toString(questionNum));
		mRenderer.setXLabelsColor(Color.YELLOW);
		mRenderer.setYLabelsColor(0, Color.YELLOW);
		mRenderer.setXLabels(0);
		for(int i = 0; i < xData.size(); i++){
			mRenderer.addXTextLabel(i, xDataLabel.get(i));
		}
		
		GraphicalView view = ChartFactory.getLineChartView(context, dataset, mRenderer);
		//Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Question 4 Report");
		return view;
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
