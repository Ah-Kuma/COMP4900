package ca.bcit.comp4900.healthydroid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import ca.bcit.comp4900.R;
import ca.bcit.comp4900.healthydroid.chart.LineChart;
import ca.bcit.comp4900.healthydroid.database.QuestionDataSource;
import ca.bcit.comp4900.healthydroid.question.MCQuestion;
import ca.bcit.comp4900.healthydroid.question.MultipleAnswerQuestion;
import ca.bcit.comp4900.healthydroid.question.Question;
import ca.bcit.comp4900.healthydroid.question.ScalarQuestion;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A class that displays and processes the quiz.
 * It also stores all the result in array and arrayLists.
 * Results will be stored when the quiz is finished.
 * @author Kevin
 */
public class QuizActivity extends Activity {
	
	private QuestionDataSource dataSource;
	private AlarmManager am;
	
	private ArrayList<LinearLayout> viewList;
	private ArrayList[] resultList;
	private int [] viewTypeArray;
	private String [] questionTextArray;
	private LinearLayout lLayout;
	private LayoutInflater inflater;
	private int currentView = 1;
	private int viewSize;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		
		//Open the database
		dataSource = new QuestionDataSource(this);
		dataSource.open();
		
		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		viewList = new ArrayList<LinearLayout>(0);

		HealthismQuiz quiz = new HealthismQuiz();
		quiz.addQuestion(new MCQuestion("A B C D", "Your fav letter?"));
		quiz.addQuestion(new MCQuestion("E F G H", "Your least fav letter?"));
		quiz.addQuestion(new MCQuestion("I J K", "Letter?"));
		try {
			quiz.addQuestion(new ScalarQuestion("4", "Scale"));
			quiz.addQuestion(new MultipleAnswerQuestion("\"X\",\"Y\",\"Z\"","Question"));
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

		ListIterator<Question> it;
		it = quiz.getQuestions();
		
		viewSize = quiz.getQuizSize();
		resultList = new ArrayList[viewSize];
		viewTypeArray = new int [viewSize];
		questionTextArray = new String [viewSize];		
		
		for (int i = 0; i < quiz.getQuizSize(); i++) {
			Question q = it.next();
			loadQuiz(q.getQuestionTypeID(), q.getQuestionText(), q.getQuestionOptions());
			questionTextArray[i] = q.getQuestionText();
			viewTypeArray[i] = q.getQuestionTypeID();
		}
		
		
		//Display the first question of the quiz
		initFirstQuestion();
		
		// Call the nextButtonOnClick method when the next button is clicked
		Button next = (Button) findViewById(R.id.quiz_nextButton);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextButtonOnClick(v);
			}
		});

		// Call the backButtonOnClick method when the back button is clicked
		Button back = (Button) findViewById(R.id.quiz_backButton);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				backButtonOnClick(v);
			}
		});	
	}

	/**
	 * Change what is displayed on the screen according to the question.
	 * @param questionType type of question
	 * @param questionTextn question
	 * @param it iterator pointing to the current question
	 */
	public void loadQuiz(int questionType, String questionText, Iterator<String> it) {
		LinearLayout childLayout = new LinearLayout(this.getApplicationContext());
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		lLayout = (LinearLayout) findViewById(R.id.quiz_layout);
		
		switch (questionType) {
		case 0:
			TextView mcText;
			RadioButton b;
			RadioGroup radioGroup;
			int buttonCount = 0;
			
			//Add a question view into the main quiz view
			childLayout = (LinearLayout) inflater.inflate(R.layout.mcquestion, null);
			lLayout.addView(childLayout);
			// Set the question
			mcText = (TextView) findViewById(R.id.mc_QuestionText);
			mcText.setText(questionText);
			radioGroup = (RadioGroup) findViewById(R.id.mcRadioGroup);

			// Create radio buttons and set their texts
			while (it.hasNext()) {
				radioGroup.addView(new RadioButton(this.getBaseContext()));
				b = (RadioButton) radioGroup.getChildAt(buttonCount++);
				b.setText(it.next());
			}

			viewList.add(childLayout);
			lLayout.removeAllViews();
			break;

		case 1:
			SeekBar seekBar;
			TextView scalarText;
			
			//Add a question view into the main quiz view
			childLayout = (LinearLayout) inflater.inflate(R.layout.scalarquestion, null);
			lLayout.addView(childLayout);
			
			// Set the question
			scalarText = (TextView) findViewById(R.id.scalarQuestionText);
			scalarText.setText(questionText);

			// Set the max value of the seekbar
			seekBar = (SeekBar) findViewById(R.id.scalarSeekBar);
			seekBar.setMax(Integer.parseInt(it.next()));

			//Set a listener
			setSeekBar(seekBar);
			
			viewList.add(childLayout);
			lLayout.removeAllViews();
			break;

		case 2:
			TextView checklistText;
			LinearLayout checkBoxView;
			int checkBoxCount = 0;
			
			//Add a question view into the main quiz view
			childLayout = (LinearLayout) inflater.inflate(R.layout.maquestion, null);
			lLayout.addView(childLayout);
			
			//Set the question
			checklistText = (TextView) findViewById(R.id.maQuestionText);
			checklistText.setText(questionText);
			
			checkBoxView = (LinearLayout) findViewById(R.id.ma_checkBoxView);
			//Create checkBoxes and set their texts
			while(it.hasNext()){
				checkBoxView.addView(new CheckBox(this.getBaseContext()));
				((CheckBox) checkBoxView.getChildAt(checkBoxCount++)).setText(it.next());
			}
			viewList.add(childLayout);
			lLayout.removeAllViews();
			break;
		default:
			throw new IllegalArgumentException("Unknown view type");
		}
	}
	
	/**
	 * Display the first question.
	 */
	public void initFirstQuestion(){
		lLayout.addView(viewList.get(0));
		// Display the current question number
		TextView curQuestText = (TextView) findViewById(R.id.quiz_curQuestNumText);
		curQuestText.setText(currentView + "/" + viewSize);
	}
	
	/**
	 * Change the view after user clicks the next button If all the questions
	 * are answered, store the results and return to the main activity
	 * 
	 * @param v
	 */
	public void nextButtonOnClick(View v) {	
		//Store the result of the current view(question)
		try {
			saveResult();
		} catch(IllegalArgumentException ex) {
			Toast.makeText(getApplicationContext(), "Please pick an answer", 300).show();
			return;
		}
		
		//Save the results into the database and exit this activity
		if(currentView == viewSize){	
			// Save results into the database
			for(int i = 0; i < viewTypeArray.length; i++){
				for(int j = 0; j < resultList[i].size(); j++)
					dataSource.storeAnswer(i + 1, questionTextArray[i], (String)resultList[i].get(j));
			}
			
			//Set alarm for notification
			setNotifiAlarm();
			
			// Try to create a line graph base on the results of a scalar question
			final Calendar c = Calendar.getInstance();
			LineChart line = new LineChart();
			line.setData(2012503, 1);
			line.setData(2012508, 1);
			line.setData(2012509, 2);

			Calendar day01 = Calendar.getInstance();
			day01.set(2012, 5, 8);
			Calendar day02 = Calendar.getInstance();
			day02.set(2012, 5, 9);
			for(int i= 0; i < viewTypeArray.length; i++){
				if(viewTypeArray[i] == 1) {
					Calendar date = Calendar.getInstance();
					date.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE) );
					
					line.setData(Integer.parseInt("" + c.get(Calendar.YEAR)+ (c.get(Calendar.MONTH) + 1 )+c.get(Calendar.DATE))
							, Integer.parseInt((String) resultList[i].get(0)));
				}
			}
			
			
			// Return to the main activity/screen
			finish();
			Intent intent = new Intent(this, HealthyDroidActivity.class);
			startActivity(intent);
		}	
		
		currentView++;

		if (currentView <= viewList.size()) {
			// Display the current question number
			TextView curQuestText = (TextView) findViewById(R.id.quiz_curQuestNumText);
			curQuestText.setText(currentView + "/" + viewSize);

			// Show the back button
			Button backButton = (Button) findViewById(R.id.quiz_backButton);
			backButton.setVisibility(View.VISIBLE);

			// If the current question is the last question, set the next button
			// to display "Finish"
			if (currentView == viewSize) {
				Button nextButton = (Button) findViewById(R.id.quiz_nextButton);
				nextButton.setText("Finish");
			}
			// Add slide out animation to the previous question
			if (currentView >= 2)
				viewList.get(currentView - 2).setAnimation(
						AnimationUtils.loadAnimation(this.getBaseContext(),
								R.anim.view_transition_out_left));

			lLayout.removeAllViews();
			// Add slide in animation to the current question
			viewList.get(currentView - 1).setAnimation(
					AnimationUtils.loadAnimation(this.getBaseContext(),
							R.anim.view_transition_in_left));
			lLayout.addView(viewList.get(currentView - 1));
			return;
		}
	}

	/**
	 * Change the view to the previous view after user clicks the back button.
	 * @param v
	 */
	public void backButtonOnClick(View v) {
		currentView--;
		// Display the current question number
		TextView curQuestText = (TextView) findViewById(R.id.quiz_curQuestNumText);
		curQuestText.setText(currentView + "/" + viewSize);

		// Hide the back button if the current question is the first question
		if (currentView == 1) {
			Button backButton = (Button) findViewById(R.id.quiz_backButton);
			backButton.setVisibility(View.INVISIBLE);
		}

		// Set the next button to display "Next"
		Button nextButton = (Button) findViewById(R.id.quiz_nextButton);
		nextButton.setText("Next");

		lLayout.removeAllViews();
		// Add animation to the view
		viewList.get(currentView - 1).setAnimation(
				AnimationUtils.loadAnimation(this.getBaseContext(),
						R.anim.view_transition_in_right));
		lLayout.addView(viewList.get(currentView - 1));

	}
	
	/**
	 * Save results of the current view(question) into the result array.
	 */
	private void saveResult() throws IllegalArgumentException{
		
		switch(viewTypeArray[currentView - 1]){
		case 0:
			RadioGroup radioGroup = (RadioGroup)findViewById(R.id.mcRadioGroup);
			resultList[currentView - 1] = new ArrayList<String>();
			if(radioGroup.getCheckedRadioButtonId() == -1)
				throw new IllegalArgumentException();
			else 
				resultList[currentView - 1].add(((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText());					
			//Toast.makeText(getApplicationContext(), (String)resultList[currentView -1].get(0), 300).show();
			break;
		case 1:
			SeekBar seekBar = (SeekBar)findViewById(R.id.scalarSeekBar);
			resultList[currentView -1] = new ArrayList<String>();
			resultList[currentView -1].add(Integer.toString(seekBar.getProgress() + 1));
			break;
		case 2:
			LinearLayout checkBoxView = (LinearLayout) findViewById(R.id.ma_checkBoxView);
			
			resultList[currentView - 1] = new ArrayList<String>();
			for(int i = 0; i < checkBoxView.getChildCount(); i++){
				if(((CheckBox)checkBoxView.getChildAt(i)).isChecked())				
					resultList[currentView - 1].add(((CheckBox)checkBoxView.getChildAt(i)).getText());
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown view type");
		}
	}
	
	/**
	 * Set a listener that will display the current chosen value for the seekBar in scalar question 
	 */
	private void setSeekBar(SeekBar seekBar){
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				TextView text = (TextView) findViewById(R.id.scalarAnswer);
				text.setText(Integer.toString(seekBar.getProgress()+1));
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				TextView text = (TextView) findViewById(R.id.scalarAnswer);
				text.setText(Integer.toString(seekBar.getProgress()+1));
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextView text = (TextView) findViewById(R.id.scalarAnswer);
				text.setText(Integer.toString(seekBar.getProgress()+1));
			}		
		});
	}
	
	private void setNotifiAlarm(){
		int hour, minute, notifiMinutes, currentTime;
        
        //Get current time
        Time time = new Time();
        time.setToNow();
        hour = time.hour;
        minute = time.minute;
        //convert current hour and minute into minute
        currentTime = hour * 60 + minute; 
        
		//Get the notification minute from shared preferences
		SharedPreferences sp = getSharedPreferences(HealthyDroidActivity.NAMESPACE, 0);
        notifiMinutes = sp.getInt("time", -1);
        if(notifiMinutes == -1)
        	throw new NoSuchElementException("Invalid or missing time value");
        
        //Set the alarm for notification
		Intent intent = new Intent(this, AlarmReciever.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        int days = 1;
        am.set(AlarmManager.RTC, System.currentTimeMillis() 
        		- (currentTime + notifiMinutes)*60000 
        		+ (days * 86400000) , pi);
	}
	
	@Override
	protected void onResume(){
		dataSource.open();
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		dataSource.close();
		super.onPause();
	}
}