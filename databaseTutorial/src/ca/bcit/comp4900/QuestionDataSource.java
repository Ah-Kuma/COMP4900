package ca.bcit.comp4900;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class QuestionDataSource 
{
	//Database fields
	private SQLiteDatabase database;
	private HealthyDroidQuizHelper dbHelper;
	private String[] allQuestionTableColumns = {HealthyDroidQuizHelper.COLUMN_ID, HealthyDroidQuizHelper.COLUMN_QUESTION};
	//private String[] allAnswerTableColumns = {HealthyDroidQuizHelper.COLUMN_ID, HealthyDroidQuizHelper.COLUMN_QUESTION_ID, HealthyDroidQuizHelper.COLUMN_ANSWER, HealthyDroidQuizHelper.COLUMN_DATETIME};
	//private String[] allAssociationTableColumns = {HealthyDroidQuizHelper.COLUMN_QUESTION_ID, HealthyDroidQuizHelper.COLUMN_ANSWER, HealthyDroidQuizHelper.COLUMN_DATETIME};
	
	public QuestionDataSource(Context context)
	{
		dbHelper = new HealthyDroidQuizHelper(context);
	}
	
	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close()
	{
		dbHelper.close();
	}
	
	public void storeQuestion(int questionId, String questionText)
	{
		/*ContentValues values = new ContentValues();
		values.put(HealthyDroidQuizHelper.COLUMN_QUESTION, questionText);
		long insertId = database.insert(HealthyDroidQuizHelper.TABLE_QUESTION, null, values);
		Cursor cursor = database.query(HealthyDroidQuizHelper.TABLE_QUESTION, allQuestionTableColumns,
				HealthyDroidQuizHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Question newQuestion = cursorToQuestion(cursor);
		cursor.close();
		return newQuestion;*/
		
		String sql = "INSERT INTO " + HealthyDroidQuizHelper.TABLE_QUESTION + " (" + HealthyDroidQuizHelper.COLUMN_ID + ", "
				+ HealthyDroidQuizHelper.COLUMN_QUESTION + ")" + " VALUES (" + questionId + ", " + questionText + ");";
		//database.execSQL(sql); 
		ContentValues values = new ContentValues();
		values.put(HealthyDroidQuizHelper.COLUMN_ID, questionId);
		values.put(HealthyDroidQuizHelper.COLUMN_QUESTION, questionText);
		database.insert(HealthyDroidQuizHelper.TABLE_QUESTION, null, values);
		//database.execSQL(sql);
	}
	
	public void storeAnswer(int questionId, String answer)
	{
		/*ContentValues values = new ContentValues();
		values.put(HealthyDroidQuizHelper.COLUMN_ANSWER, questionAnswer);
		long insertId = database.insert(HealthyDroidQuizHelper.TABLE_ANSWER, null, values);
		Cursor cursor = database.query(HealthyDroidQuizHelper.TABLE_ANSWER, allAnswerTableColumns,
				HealthyDroidQuizHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Question newQuestion = cursor.ToQuestion(cursor);*/
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String dateTime = dateformat.format(date);
		String sql = "INSERT INTO " + HealthyDroidQuizHelper.TABLE_ANSWER + " VALUES ("
				+ questionId + ", " + answer + ", " + dateTime + ")";
		database.execSQL(sql);
		updateAssociation(questionId, answer, dateTime);
		
	}
	
	public String getRaj()
	{
		//Cursor cursor = database.query(HealthyDroidQuizHelper.TABLE_QUESTION, allQuestionTableColumns, null, null, null, null, null);
		String stuff = "SELECT * FROM " + HealthyDroidQuizHelper.TABLE_QUESTION;
		Cursor cursor = database.rawQuery(stuff, null);
		cursor.moveToFirst();
		String ans = "not changed";
		int numOfCol = cursor.getColumnCount();
		int numOfRows = cursor.getCount();
		ArrayList<Integer> blah = new ArrayList<Integer>();
		blah.add(numOfCol);
		blah.add(numOfRows);
		if(cursor.getCount() > 0)
		{
			ans =  cursor.getString(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_QUESTION));
		}
		return ans;
	}
	private void updateAssociation(int questionId, String answer, String date)
	{
		String sql = "INSERT INTO " + HealthyDroidQuizHelper.TABLE_ASSOCIATION + " VALUES ("
				+ questionId + ", " + answer + ", " + date + ")";
		database.execSQL(sql);
	}
	
}
