package ca.bcit.comp4900;

import java.util.ArrayList;
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
    EditText text, text2;
    TextView textview;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        datasource = new QuestionDataSource(this);
        datasource.open();
        
        
    }
    
    public void changeText(){
    	
    	text = (EditText)findViewById(R.id.idBox);
    	text2 = (EditText)findViewById(R.id.questionBox);
        textview = (TextView)findViewById(R.id.myText);
        String questionText, questionNumber = text.getText().toString();
        questionText = text2.getText().toString();
        datasource.storeQuestion(Integer.parseInt(questionNumber), questionText);
        String result = datasource.getRaj();
        textview.setText(result);
    }
    /*
     <ListView 
	    android:layout_height="wrap_content" 
	    android:layout_width="fill_parent" 
	    android:id="@android:id/list" 
	    android:text="@string/hello"/> 
     */
    
    //Will be called via the onClick attribute of the buttons in main.xml
    public void onClick(View view)
    {
        //@SuppressWarnings("unchecked")
		//ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        //Comment comment = null;
        switch (view.getId())
        {
            case R.id.add:
                    changeText();
            		//String[] comments = new String[] {"Kevin", "Tim", "Will", "Raj"};
                    //int nextInt = new Random().nextInt(3);
                    //Save the new comment to the database
                    //comment = datasource.createComment(comments[nextInt]);
                    //adapter.add(comment);
                    break;
            /*case R.id.delete:
                if(getListAdapter().getCount() > 0)
                {
                    comment = (Comment) getListAdapter().getItem(0);
                    datasource.deleteCommnet(comment);
                    adapter.remove(comment);
                }
                break;
                */
        }
        //adapter.notifyDataSetChanged();
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
