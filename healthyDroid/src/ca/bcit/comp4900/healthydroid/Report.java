package ca.bcit.comp4900.healthydroid;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.sql.DataSource;

import org.achartengine.GraphicalView;

import android.content.Context;
import android.graphics.Bitmap;

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
	ArrayList<String> result;
	QuestionDataSource dataSource;
	Context context;
	//Constructor
	public Report(Context c){
		context = c;
		dataSource = new QuestionDataSource(c);
		dataSource.open();
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
		LinkedHashMap<String, ArrayList<Integer>> answers = dataSource.getAnswer(questionNum, "", "");
		ArrayList<String> label = new ArrayList<String>(0);
		LineChart line = new LineChart();
		int count = 0;
		for(String key : answers.keySet()){
			line.setData(count, answers.get(key).get(0));
			label.add(key);
			count++;
		}
		line.setXDataLabel(label);
		GraphicalView view = line.getView(context, questionNum);
		return view.toBitmap();
	}
	
	public Bitmap getPieBitMap(int questionId){
		dataSource.getAnswer(questionId, "", "");
		return null;
	}
}
