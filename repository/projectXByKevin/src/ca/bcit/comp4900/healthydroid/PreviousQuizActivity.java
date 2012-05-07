package ca.bcit.comp4900.healthydroid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import ca.bcit.comp4900.R;
import ca.bcit.comp4900.healthydroid.question.MCQuestion;
import ca.bcit.comp4900.healthydroid.question.Question;
import ca.bcit.comp4900.healthydroid.question.ScalarQuestion;

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
public class PreviousQuizActivity extends Activity {
	
	private ListIterator<Question> it;
	private LinearLayout lLayout;
	private LayoutInflater  inflater;
	private ArrayList<LinearLayout> viewList;
	private int quizSize;
	private int currentQuestion = 1;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
        
        viewList = new ArrayList<LinearLayout>(0);
        
        HealthismQuiz quiz = new HealthismQuiz();
        quiz.addQuestion(new MCQuestion("A B C D D", "Your fav letter?"));
        quiz.addQuestion(new MCQuestion("E F G H", "Your least fav letter?"));
        quiz.addQuestion(new MCQuestion("I J K", "Letter?"));
        try {
			quiz.addQuestion(new ScalarQuestion("2", "Rate your happiness"));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        it = quiz.getQuestions();
        quizSize = quiz.getQuizSize();
        
        Question q = it.next();
        setView(q.getQuestionTypeID(), q.getQuestionText(), q.getQuestionOptions());
        
        //Display the current question number
        TextView curQuestText = (TextView) findViewById(R.id.quiz_curQuestNumText);
        curQuestText.setText(currentQuestion + "/" + quizSize);
        
        //Call the nextButtonOnClick method when the next button is clicked
        Button next = (Button)findViewById(R.id.quiz_nextButton);
        next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextButtonOnClick(v);		
			}
		});
        
        //Call the backButtonOnClick method when the back button is clicked
        Button back = (Button)findViewById(R.id.quiz_backButton);
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
     * @param questionText question
     * @param it iterator pointing to the current question
     */
    private void setView(int questionType, String questionText, Iterator<String> it){
    	LinearLayout childLayout = new LinearLayout(this.getApplicationContext());
    	inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	lLayout = (LinearLayout)findViewById(R.id.quiz_layout);
    	
    	//Hide the back button if the current question is the first question
		//otherwise, show the back button
		if(currentQuestion == 1){
			Button backButton = (Button) findViewById(R.id.quiz_backButton);
			backButton.setVisibility(View.INVISIBLE);
		}
		else{
			Button backButton = (Button) findViewById(R.id.quiz_backButton);
			backButton.setVisibility(View.VISIBLE);
		}
		
		//Change the next button to display "Finish" if the current question is the last question;
		//otherwise, change it to display "Next"
		if(currentQuestion == quizSize){
			Button nextButton = (Button) findViewById(R.id.quiz_nextButton);
			nextButton.setText("Finish");
		}
		else{
			Button nextButton = (Button) findViewById(R.id.quiz_nextButton);
			nextButton.setText("Next");
		}
    	
    	switch(questionType){
    		case 0:
    			TextView mcText;
    			RadioButton b;
    			RadioGroup radioGroup;
    			int buttonCount = 0;
    			
    			childLayout = (LinearLayout)inflater.inflate(R.layout.mcquestion, null);    			
    			lLayout.addView(childLayout);
    			//Add slide out animation to question one
    			if(currentQuestion == 1)
    				childLayout.setAnimation(AnimationUtils.loadAnimation(this.getBaseContext(), R.anim.view_transition_out_left));
    			
    			//Set the question
    			mcText = (TextView)findViewById(R.id.mc_QuestionText);
    			mcText.setText(questionText);
    			radioGroup = (RadioGroup)findViewById(R.id.mcRadioGroup);
    			
    			//Create radio buttons and set their texts
    			while(it.hasNext()){
    				radioGroup.addView(new RadioButton(this.getBaseContext()));
    				b =(RadioButton)radioGroup.getChildAt(buttonCount++);
    				b.setText(it.next());
    			}
    			  			
    			
    			
    			viewList.add(childLayout);
    			break;
    		case 1:
    			SeekBar seekBar;
    			TextView scalarText;
    			
    			childLayout = (LinearLayout)inflater.inflate(R.layout.scalarquestion, null);
    			lLayout.addView(childLayout);
    			//Add slide out animation to question one
    			if(currentQuestion == 1)
    				childLayout.setAnimation(AnimationUtils.loadAnimation(this.getBaseContext(), R.anim.view_transition_out_left));
    			//Set the question
    			scalarText = (TextView) findViewById(R.id.scalarQuestionText);
    			scalarText.setText(questionText);
    			
    			//Set the max value of the seekbar
    			seekBar = (SeekBar) findViewById(R.id.scalarSeekBar);
    			seekBar.setMax(Integer.parseInt(it.next()));
    			
    			viewList.add(childLayout);
    			break;
    		case 2:
    			TextView checklistText;
    			CheckBox checkBox;
    			
    			childLayout = (LinearLayout)inflater.inflate(R.layout.checklistquestion, null);
    			lLayout.addView(childLayout);
    			//Add slide out animation to question one
    			if(currentQuestion == 1)
    				childLayout.setAnimation(AnimationUtils.loadAnimation(this.getBaseContext(), R.anim.view_transition_out_left));
    			
    			//Set the question
    			checklistText = (TextView) findViewById(R.id.checklistQuestionText);
    			checklistText.setText(questionText);
    			
    			viewList.add(childLayout);
    			break;
    	}
    	
    }
    
   /**
    * Change the view after user clicks the next button
    * If all the questions are answered, store the results and return to the main activity
    * @param v
    */
    public void nextButtonOnClick(View v){  	
    	currentQuestion++;
    	
    	if(currentQuestion <= viewList.size()){		
    		//Display the current question number
    		TextView curQuestText = (TextView) findViewById(R.id.quiz_curQuestNumText);
            curQuestText.setText(currentQuestion + "/" + quizSize);
            
            //Show the back button
			Button backButton = (Button) findViewById(R.id.quiz_backButton);
			backButton.setVisibility(View.VISIBLE);
			
			//If the current question is the last question, set the next button to display "Finish"
			if(currentQuestion == quizSize){
				Button nextButton = (Button) findViewById(R.id.quiz_nextButton);
				nextButton.setText("Finish");
			}
			//Add slide out animation to the previous question 
			if(currentQuestion >= 2)
    			viewList.get(currentQuestion-2).setAnimation(AnimationUtils.loadAnimation(this.getBaseContext(), R.anim.view_transition_out_left));
			
    		lLayout.removeAllViews();
    		//Add slide in animation to the current question
    		viewList.get(currentQuestion-1).setAnimation(AnimationUtils.loadAnimation(this.getBaseContext(), R.anim.view_transition_in_left));
    		lLayout.addView(viewList.get(currentQuestion-1));
    		return;
    	}
    	
    	if(it.hasNext()){
    		//Display the current question number
    		TextView curQuestText = (TextView) findViewById(R.id.quiz_curQuestNumText);
            curQuestText.setText(currentQuestion + "/" + quizSize);
            //Add slide out animation to the previous question 
			if(currentQuestion >= 2)
    			viewList.get(currentQuestion-2).setAnimation(AnimationUtils.loadAnimation(this.getBaseContext(), R.anim.view_transition_out_left));
			
            
    		lLayout.removeAllViews();	
    		Question q = it.next();
    		setView(q.getQuestionTypeID(), q.getQuestionText(), q.getQuestionOptions());
    		//Add slide in animation to the current question
    		viewList.get(currentQuestion-1).setAnimation(AnimationUtils.loadAnimation(this.getBaseContext(), R.anim.view_transition_in_left));
    	}
    	else{
    		//Store results and pass them to database adapter class
    		//......
    		
    		//Return to the main activity/screen
    		finish();
    		Intent intent = new Intent(this, HealthyDroidActivity.class);
            startActivity(intent);
    	}
    }
    
	/**
	 * Change the view to the previous view after user clicks the back button
	 * 
	 * @param v
	 */
	public void backButtonOnClick(View v) {
		currentQuestion--;
		// Display the current question number
		TextView curQuestText = (TextView) findViewById(R.id.quiz_curQuestNumText);
		curQuestText.setText(currentQuestion + "/" + quizSize);
		
		//Hide the back button if the current question is the first question
		if(currentQuestion == 1){
			Button backButton = (Button) findViewById(R.id.quiz_backButton);
			backButton.setVisibility(View.INVISIBLE);
		}
		
		//Set the next button to display "Next"
		Button nextButton = (Button) findViewById(R.id.quiz_nextButton);
		nextButton.setText("Next");
		
		lLayout.removeAllViews();
		//Add animation to the view
		viewList.get(currentQuestion-1).setAnimation(AnimationUtils.loadAnimation(this.getBaseContext(), R.anim.view_transition_in_right));
		lLayout.addView(viewList.get(currentQuestion-1));

	}
}