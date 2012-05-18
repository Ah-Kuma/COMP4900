package ca.bcit.comp4900.healthydroid;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.sql.DataSource;

import org.achartengine.GraphicalView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

import ca.bcit.comp4900.healthydroid.chart.LineChart;
import ca.bcit.comp4900.healthydroid.database.QuestionDataSource;

/**
 *Generates charts with data from the database.
 *Then converts the charts into images and generate a pdf file with these
 *images. 
 * @author Kevin, William
 * @version 1.0 
 */
public class Report {
	//ArrayList<String> result;
	QuestionDataSource dataSource;
	Context context;
	//Constructor
	public Report(Context c){
		context = c;
		dataSource = new QuestionDataSource(c);
		dataSource.open();
	}
	
	public void generateTables(){
		LinkedHashMap<String, ArrayList<Integer>> answers = dataSource.getAnswer(0, "", "");
		int count = 0;
		for(String key : answers.keySet()){
			answers.get(key).get(0);
			count++;
		}
		
	}
	
	
	public void generateCharts() throws IllegalArgumentException{
		ArrayList<Integer> questionType = dataSource.getQuestionTypes();
		for(int i = 0; i < questionType.size(); i++){
			switch(questionType.get(i)){
				case 0:
					//getLineBitMap(i);
					break;
				case 1:
					getLineBitMap(i);
					break;
				case 2:
					//getPieBitMap(i);
					break;
				default:
					throw new IllegalArgumentException("Unknown view type");
			}
		}
	}
	
	public Bitmap getLineBitMap(int questionNum){
		Log.d("tag", "getLineBitMap");
		LinkedHashMap<String, ArrayList<Integer>> answers = dataSource.getAnswer(questionNum, "", "");
		ArrayList<String> label = new ArrayList<String>(0);
		LineChart line = new LineChart();
		int count = 0;
		for(String key : answers.keySet()){
			Log.d("tag", Integer.toString(answers.get(key).get(0)));
			line.setData(count, answers.get(key).get(0));
			label.add(key);
			count++;
		}
		line.setXDataLabel(label);
		GraphicalView view = line.getView(context, questionNum);
		
		dataSource.close();

		view.setDrawingCacheEnabled(true);
		view.layout(0,0, 800, 600);
		view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		view.buildDrawingCache(true);
		//view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		//view.layout(0, 0,view.getMeasuredWidth(),view.getMeasuredHeight());
		
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		if(bitmap == null){
			Log.d("bitmap", "null");
		}
		return bitmap;
	}
	
	public Bitmap getPieBitMap(int questionId){
		dataSource.getAnswer(questionId, "", "");
		return null;
	}
}
