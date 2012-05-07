package ca.bcit.comp4900.healthydroid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import ca.bcit.comp4900.R;
import ca.bcit.comp4900.healthydroid.question.MCQuestion;
import ca.bcit.comp4900.healthydroid.question.Question;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

/**
 * A class that displays and processes the quiz.
 * It also stores all the result in an arrayList and passes it to the database adapter class
 * @author Kevin
 *
 */
public class QuizActivity extends Activity {
	private ArrayList<LinearLayout> viewList;
	private LinearLayout lLayout;
	private LayoutInflater inflater;
	private int currentView = 1;
	private int viewSize;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		
		viewList = new ArrayList<LinearLayout>(0);
		HealthismQuiz quiz = new HealthismQuiz();
		quiz.addQuestion(new MCQuestion("A B C D", "Your fav letter?"));
		quiz.addQuestion(new MCQuestion("E F G H", "Your least fav letter?"));
		quiz.addQuestion(new MCQuestion("I J K", "Letter?"));
		ListIterator<Question> it;
		it = quiz.getQuestions();
		
		for (int i = 0; i < quiz.getQuizSize(); i++) {
			Question q = it.next();
			loadQuiz(q.getQuestionTypeID(), q.getQuestionText(), q.getQuestionOptions());
		}
		
		
		viewSize = viewList.size();
		
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

			childLayout = (LinearLayout) inflater.inflate(R.layout.scalarquestion, null);
			lLayout.addView(childLayout);
			
			// Set the question
			scalarText = (TextView) findViewById(R.id.scalarQuestionText);
			scalarText.setText(questionText);

			// Set the max value of the seekbar
			seekBar = (SeekBar) findViewById(R.id.scalarSeekBar);
			seekBar.setMax(Integer.parseInt(it.next()));

			viewList.add(childLayout);
			lLayout.removeAllViews();
			break;

		case 2:
			TextView checklistText;
			CheckBox checkBox;

			childLayout = (LinearLayout) inflater.inflate(R.layout.checklistquestion, null);
			lLayout.addView(childLayout);
			// Set the question
			checklistText = (TextView) findViewById(R.id.checklistQuestionText);
			checklistText.setText(questionText);

			viewList.add(childLayout);
			lLayout.removeAllViews();
			break;
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
		if(currentView == viewSize){	
			// Store results and pass them to database adapter class
			// ......

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
	 * Change the view to the previous view after user clicks the back button
	 * 
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
}
