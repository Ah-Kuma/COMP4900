package ca.bcit.comp4900;

import java.util.List;
import java.util.Random;

import ca.bcit.comp4900.Comment;
import ca.bcit.comp4900.CommentDataSource;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class TestDatabaseActivity extends ListActivity
{
    private CommentDataSource datasource;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        datasource = new CommentDataSource(this);
        datasource.open();
        
        List<Comment> values = datasource.getAllComments();
        
        //Use the SimpleCursorAdapter to show the elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
            android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
    
    //Will be called via the onClick attribute of the buttons in main.xml
    public void onClick(View view)
    {
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        switch (view.getId())
        {
            case R.id.add:
                    String[] comments = new String[] {"Kevin", "Tim", "Will", "Raj"};
                    int nextInt = new Random().nextInt(3);
                    //Save the new comment to the database
                    comment = datasource.createComment(comments[nextInt]);
                    adapter.add(comment);
                    break;
            case R.id.delete:
                if(getListAdapter().getCount() > 0)
                {
                    comment = (Comment) getListAdapter().getItem(0);
                    datasource.deleteCommnet(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
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
