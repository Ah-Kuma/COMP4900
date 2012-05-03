package ca.bcit.comp4900.healthydroid;

import java.util.Iterator;
import java.util.ListIterator;

import ca.bcit.comp4900.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A class that displays the quiz.
 * @author Kevin
 *
 */
public class QuizActivity extends Activity {
	
	public ListIterator<Question> it;
	public LinearLayout lLayout;
	public LinearLayout childLayout;
	public LayoutInflater  inflater;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
        
        HealthismQuiz quiz = new HealthismQuiz();
        quiz.addQuestion(new MCQuestion("A B C D", "Your fav letter?"));
        quiz.addQuestion(new MCQuestion("E F G H", "Your least fav letter?"));
        quiz.addQuestion(new MCQuestion("I J K", "Letter?"));
        
        it = quiz.getQuestions();
        Question q = it.next();
        setView(q.getQuestionTypeID(), q.getQuestionText(), q.getQuestionOptions());
        
        Button next = (Button)findViewById(R.id.quiz_nextButton);
        next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextButtonOnClick(v);		
			}
		});
        
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
    	inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	lLayout = (LinearLayout)findViewById(R.id.quiz_layout);
    	switch(questionType){
    		case 0:
    			TextView mcText;
    			RadioButton b;
    			RadioGroup radioGroup;
    			int buttonCount = 0;
    			
    			childLayout = (LinearLayout)inflater.inflate(R.layout.mcquestion, null);
    			lLayout.addView(childLayout);
    			
    			mcText = (TextView)findViewById(R.id.mc_QuestionText);
    			mcText.setText(questionText);
    			radioGroup = (RadioGroup)findViewById(R.id.mcRadioGroup);
    			
    			while(it.hasNext()){
    				radioGroup.addView(new RadioButton(this.getBaseContext()));
    				b =(RadioButton)radioGroup.getChildAt(buttonCount++);
    				b.setText(it.next());
    			}
    			
    			break;
    		case 2:
    			childLayout = (LinearLayout)inflater.inflate(R.layout.scalarquestion, null);
    			lLayout.addView(childLayout);
    			break;
    		case 3:
    			childLayout = (LinearLayout)inflater.inflate(R.layout.checklistquestion, null);
    			lLayout.addView(childLayout);
    			break;
    	}
    	
    }
    
   /**
    * Change the view after user clicks the next button
    * @param v
    */
    public void nextButtonOnClick(View v){  	
    	//Toast.makeText(getApplicationContext(), "button is pressed", 600).show();
    	if(it.hasNext()){	
    		lLayout.removeAllViews();
    		Question q = it.next();
    		setView(q.getQuestionTypeID(), q.getQuestionText(), q.getQuestionOptions());
    	}
    }
    
    /**
     * Change the view after user clicks the back button
     * @param v
     */
     public void backButtonOnClick(View v){  	
    	 //Toast.makeText(getApplicationContext(), "button is pressed", 600).show();
    	 if(it.hasPrevious()){	
    		 lLayout.removeAllViews();
    		 Question q = it.previous();
    		 setView(q.getQuestionTypeID(), q.getQuestionText(), q.getQuestionOptions());
     	}
     	
     }
}