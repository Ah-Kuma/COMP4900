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
/**
 * The purpose of this class is to provide methods that interact with the database indirectly.
 * This class also manages the HealthyDroidQuizHelper.
 * @author Rajpreet Sidhu
 *
 */
public class QuestionDataSource 
{
	//Database fields
	private SQLiteDatabase database;
	private HealthyDroidQuizHelper dbHelper;
	/**
	 * The constructor instantiates a HealthyDroidQuizHelper object with the activity's context.
	 * @param context is the activity this class is instantiated in.
	 */
	public QuestionDataSource(Context context)
	{
		dbHelper = new HealthyDroidQuizHelper(context);
	}
	/**
	 * The open method uses the new database helper (dbHelper) to obtain a database that can be written to.
	 * @throws SQLException
	 */
	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}
	/**
	 *The close method simply closes the database. 
	 */
	public void close()
	{
		dbHelper.close();
	}
	/**
	 * Used to store a question number (questionId) and a question (questionText) into the question table. The question number becomes the
	 * primary key of the table - it cannot be duplicated.
	 * @param questionId the primary key of the table and the number of the question.
	 * @param questionText the actual question.
	 */
	private void storeQuestion(int questionId, String questionText)
	{
		ContentValues values = new ContentValues();
		Cursor cursor = database.rawQuery("SELECT * FROM " + HealthyDroidQuizHelper.TABLE_QUESTION + " WHERE " 
		+ HealthyDroidQuizHelper.COLUMN_ID + " = " + questionId, null);
		
		if(cursor.getCount() == 0)
		{
			values.put(HealthyDroidQuizHelper.COLUMN_ID, questionId);
			values.put(HealthyDroidQuizHelper.COLUMN_QUESTION, questionText);
			database.insert(HealthyDroidQuizHelper.TABLE_QUESTION, null, values);
		}
	}
	/**
	 * Used to store a question number (questionId) and an answer to the question (answer) as well as the date the question was answered into the 
	 * answer table. The table has a primary key that is incremented automatically upon every insert. This method calls the storeQuestion method 
	 * before storing in the answer table. This way, the questionId will be referenced properly.
	 * @param questionId the question number passed in by the user and referenced from the question table.
	 * @param answer the answer to the question.
	 */
	public void storeAnswer(int questionId, String questionText, String answer)
	{
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String dateTime = dateformat.format(date);
		ContentValues values = new ContentValues();
		values.put(HealthyDroidQuizHelper.COLUMN_QUESTION_ID, questionId);
		values.put(HealthyDroidQuizHelper.COLUMN_ANSWER, answer);
		values.put(HealthyDroidQuizHelper.COLUMN_DATETIME, dateTime);
		storeQuestion(questionId, questionText);
		database.insert(HealthyDroidQuizHelper.TABLE_ANSWER, null, values);
		updateAssociation(questionId, answer, dateTime);
		
	}
	/**
	 * Inserts into a question number (questionId), answer to the question (answer) and the date it was answered (date) into the association table.
	 * The table has a composite key based on the questionId and the date.
	 * @param questionId the question number referenced from the question table, part of the composite key. 
	 * @param answer the answer to the question referenced from the answer table.
	 * @param date the date the question was answered referenced from the answer table, part of the composite key.
	 */
	private void updateAssociation(int questionId, String answer, String date)
	{
		ContentValues values = new ContentValues();
		values.put(HealthyDroidQuizHelper.COLUMN_QUESTION_ID, questionId);
		values.put(HealthyDroidQuizHelper.COLUMN_ANSWER, answer);
		values.put(HealthyDroidQuizHelper.COLUMN_DATETIME, date);
		database.insert(HealthyDroidQuizHelper.TABLE_ASSOCIATION, null, values);
	}
	/**
	 * This method is used to query the database. It takes a question number (questionId) and a range of dates (startDate, endDate). If the dates
	 * are empty then all answers for the specified question are returned. Otherwise, all answers answered within the date range are returned.
	 * @param questionId the question number.
	 * @param startDate the beginning of the range.
	 * @param endDate the end of the range.
	 * @return an array list containing the date and answers is returned.
	 */
	public ArrayList<String> getAnswer(int questionId, String startDate, String endDate)
	{
		String query;
		
		if(startDate.equals("") && endDate.equals(""))
		{
			 query = "SELECT " + HealthyDroidQuizHelper.COLUMN_ANSWER + ", "
					+ HealthyDroidQuizHelper.COLUMN_DATETIME + " FROM " + HealthyDroidQuizHelper.TABLE_ASSOCIATION
					+ " WHERE " + HealthyDroidQuizHelper.COLUMN_QUESTION_ID + " = " + questionId;
		}
		else
		{
			 query = "SELECT " + HealthyDroidQuizHelper.COLUMN_ANSWER + ", "
					+ HealthyDroidQuizHelper.COLUMN_DATETIME + " FROM " + HealthyDroidQuizHelper.TABLE_ASSOCIATION
					+ " WHERE " + HealthyDroidQuizHelper.COLUMN_QUESTION_ID + " = " + questionId + " AND "
					+ HealthyDroidQuizHelper.COLUMN_DATETIME + " BETWEEN " + startDate + " AND " + endDate;
		}
		
		ArrayList<String> QuizResults = new ArrayList<String>();
		String ans, currDate, oldDate = null;
		
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		oldDate = cursor.getString(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_DATETIME));
		QuizResults.add(oldDate);
		while(!cursor.isAfterLast())
		{
			currDate = cursor.getString(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_DATETIME));
			if(!oldDate.equalsIgnoreCase(currDate))
			{
				QuizResults.add(currDate);
				oldDate = currDate;
			}
				
			ans = cursor.getString(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_ANSWER));
			QuizResults.add(ans);
			cursor.moveToNext();
		}

		return QuizResults;
	}
}
