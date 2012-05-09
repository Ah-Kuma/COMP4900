package ca.bcit.comp4900;
/**
 * author: Rajpreet Sidhu
 * 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.zip.Inflater;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestDatabaseActivity extends Activity
{
    private QuestionDataSource datasource;
    EditText quesNum, ques, ans, startingDate, endingDate;
    TextView textview;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        datasource = new QuestionDataSource(this);
        datasource.open();     
        
    }
    
    public void changeText()
    {
    	
    	quesNum = (EditText)findViewById(R.id.idBox);
    	ques = (EditText)findViewById(R.id.questionBox);
    	ans = (EditText)findViewById(R.id.answerBox);
        String questionNumber, question, answer;
        
        questionNumber = quesNum.getText().toString();
        question = ques.getText().toString();
        answer = ans.getText().toString();

        datasource.storeAnswer(Integer.parseInt(questionNumber), question, answer);

    }
    
    public void query()
    {
    	quesNum = (EditText)findViewById(R.id.idBox);
    	startingDate = (EditText)findViewById(R.id.startDate);
    	endingDate = (EditText)findViewById(R.id.endDate);
    	textview = (TextView)findViewById(R.id.myText);
    	String num, start, end, textView;
    	
    	num = quesNum.getText().toString();
    	start = startingDate.getText().toString();
    	end = endingDate.getText().toString();
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        result.add(datasource.getAnswer(Integer.parseInt(num), start, end));
        textview.setText(result.get(0).toString());
    }

    
    //Will be called via the onClick attribute of the buttons in main.xml
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.add:
                    changeText();
                    break;
            case R.id.delete:
            	query();
                break;
                
        }
    }
   
    @Override
    protected void onResume()
    {
        datasource.open();
        super.onResume();
    }
    
    @Override
    protected void onPause()
    {
        datasource.close();
        super.onPause();
    }
    
}
