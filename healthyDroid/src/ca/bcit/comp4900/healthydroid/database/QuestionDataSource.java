package ca.bcit.comp4900.healthydroid.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

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
	 * Used to store a question number (questionId) a question (questionText) and a type (questionType) 
	 * into the question table. The question number becomes the primary key of the table - it cannot be duplicated.
	 * @param questionId the primary key of the table and the number of the question.
	 * @param questionText the actual question.
	 * @param questionType the type of the question inserted.
	 */
	private void storeQuestion(int questionId, String questionText, String questionType)
	{
		ContentValues values = new ContentValues();
		Cursor cursor = database.rawQuery("SELECT * FROM " + HealthyDroidQuizHelper.TABLE_QUESTION + " WHERE " 
		+ HealthyDroidQuizHelper.COLUMN_ID + " = " + questionId, null);

		if(cursor.getCount() == 0)
		{
			values.put(HealthyDroidQuizHelper.COLUMN_ID, questionId);
			values.put(HealthyDroidQuizHelper.COLUMN_QUESTION, questionText);
			values.put(HealthyDroidQuizHelper.COLUMN_QUESTION_TYPE, questionType);
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
	public void storeAnswer(int questionId, String questionText, String questionType, int answer)
	{
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String dateTime = dateformat.format(date);
		ContentValues values = new ContentValues();
		values.put(HealthyDroidQuizHelper.COLUMN_QUESTION_ID, questionId);
		values.put(HealthyDroidQuizHelper.COLUMN_ANSWER, answer);
		values.put(HealthyDroidQuizHelper.COLUMN_DATETIME, dateTime);
		storeQuestion(questionId, questionText, questionType);
		database.insert(HealthyDroidQuizHelper.TABLE_ANSWER, null, values);
		updateAssociation(questionId, answer, dateTime);
	}
	/**
	 * Method to store all question options into the options table.
	 * @param questionId the question number.
	 * @param option the option (ie. one of the potential answers).
	 */
	public void storeOption(int questionId, String option)
	{
		ContentValues values = new ContentValues();
		values.put(HealthyDroidQuizHelper.COLUMN_QUESTION_ID, questionId);
		values.put(HealthyDroidQuizHelper.COLUMN_OPTION, option);
		database.insert(HealthyDroidQuizHelper.TABLE_OPTION, null, values);
	}
	/**
	 * Inserts into a question number (questionId), answer to the question (answer) and the date it was answered (date) into the association table.
	 * The table has a composite key based on the questionId and the date.
	 * @param questionId the question number referenced from the question table, part of the composite key. 
	 * @param answer the answer to the question referenced from the answer table.
	 * @param date the date the question was answered referenced from the answer table, part of the composite key.
	 */
	private void updateAssociation(int questionId, int answer, String date)
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
	 * @return an array list of an array list containing the date and answers is returned.
	 */
	public LinkedHashMap<String, ArrayList<Integer>> getAnswer(int questionId, String startDate, String endDate)
	{
		String query;
		int ans;
		ArrayList<Integer> QuizResults = new ArrayList<Integer>();
		LinkedHashMap<String, ArrayList<Integer>> totalResults = new LinkedHashMap<String, ArrayList<Integer>>();
		String currDate = "", oldDate = null;
		
		//Change query based on empty or null dates
		if("".equals(startDate) && "".equals(endDate))
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
		
		Cursor cursor = database.rawQuery(query, null);
		//Only pull information if the query obtained results
		if(cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			//Set the oldDate to the date in the first row.
			oldDate = cursor.getString(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_DATETIME));
			while(!cursor.isAfterLast())
			{
				//If the data has changed, store the contents of QuizResults into totalResults. Clear QuizResults and set the oldDate as the 
				//new date.
				if(!oldDate.equalsIgnoreCase(currDate))
				{
					totalResults.put(oldDate, QuizResults);
					QuizResults.clear();
					oldDate = currDate;
				}
				//Pull the answer from the current row and add it into QuizResults
				ans = cursor.getInt(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_ANSWER));
				QuizResults.add(ans);
				//Move current date to the next date
				currDate = cursor.getString(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_DATETIME));
				cursor.moveToNext();
			}
		}
		return totalResults;
	}
	/**
	 * This method queries the question table for all inserted questions and retrieves the type. The types are 
	 * stored as an string in the database and are returned in an array list by this method.
	 * @return an array list of strings containing all question types in the order they were inserted into the database.
	 */
	public ArrayList<String> getQuestionTypes()
	{
		String sql = "SELECT " + HealthyDroidQuizHelper.COLUMN_QUESTION_TYPE + " FROM "
		+ HealthyDroidQuizHelper.TABLE_QUESTION;
		ArrayList<String> questionTypes = new ArrayList<String>();
		
		Cursor cursor = database.rawQuery(sql, null);
		//Only pull information if the query yielded results
		if(cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			while(!cursor.isAfterLast())
			{
				questionTypes.add(cursor.getString(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_QUESTION_TYPE)));
				cursor.moveToNext();
			}
		}
		return questionTypes;
	}
	/**
	 * Method to query the options table in the database which obtains all potential answers for questions.
	 * It takes an question number as an integer and returns all options for that question.
	 * @param questionNumber the question number.
	 * @return an array list of strings which holds all options for a question in the order they were inserted
	 * into the database.
	 */
	public ArrayList<String> getOptions(int questionNumber)
	{
		ArrayList<String> options = new ArrayList<String>();
		String sql = "SELECT " + HealthyDroidQuizHelper.COLUMN_OPTION + " FROM "
		+ HealthyDroidQuizHelper.TABLE_OPTION + " WHERE " 
		+ HealthyDroidQuizHelper.COLUMN_QUESTION_ID + " = " + questionNumber;
		
		Cursor cursor = database.rawQuery(sql, null);
		//Only pull information if the query yielded results
		if(cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			while(!cursor.isAfterLast())
			{
				//Pull the option from the option column and add it to the array list
				options.add(cursor.getString(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_OPTION)));
				cursor.moveToNext();
			}
		}
		return options;
	}
	/**
	 * Method to query the Association table for all question numbers inserted into it. The number of numbers returned is the number of 
	 * questions answered.
	 * @return an array list of the question numbers
	 */
	public ArrayList<Integer> getQuestions()
	{
		ArrayList<Integer> results = new ArrayList<Integer>();
		String sql = "SELECT " + HealthyDroidQuizHelper.COLUMN_QUESTION_ID + " FROM " + HealthyDroidQuizHelper.TABLE_ASSOCIATION;		
		Cursor cursor = database.rawQuery(sql, null);
		//Only pull out information if the query yielded results
		if(cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			while(!cursor.isAfterLast())
			{
				//Add the question number in this row into the array list
				results.add(cursor.getInt(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_QUESTION_ID)));
				cursor.moveToNext();
			}
		}
		return results;
	}
	/**
	 * Find the question text of a question that is passed in.
	 * @param questionNumber - the number of the question
	 * @return A string which is the text of the question passed in
	 */
	public String getQuestionText(int questionNumber)
	{
		String questionText = "";
		String sql = "SELECT " + HealthyDroidQuizHelper.COLUMN_QUESTION + " FROM " + HealthyDroidQuizHelper.TABLE_QUESTION
				+ " WHERE " + HealthyDroidQuizHelper.COLUMN_ID + " = " + questionNumber;
		Cursor cursor = database.rawQuery(sql, null);
		//Only pull out information if the query yielded results
		if(cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			//Store the question text of the passed in question into a string
			questionText = cursor.getString(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_QUESTION));
		}
		return questionText;
	}
	/**
	 * Method that takes no arguments and returns all dates and answers from the answer table in a linked list. The format of the linked lis
	 * is the answer followed by the date.
	 * @return A linked list containing the answers and the dates within the table.
	 */
	public LinkedList<String> getAnswer()
	{
		String query = "SELECT " + HealthyDroidQuizHelper.COLUMN_ANSWER + ", "
					+ HealthyDroidQuizHelper.COLUMN_DATETIME + " FROM " + HealthyDroidQuizHelper.TABLE_ASSOCIATION;
		LinkedList<String> totalResults = new LinkedList<String>();
		int ans;
		String date;
		Cursor cursor = database.rawQuery(query, null);
		//Only pull out information if the query yielded results
		if(cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			while(!cursor.isAfterLast())
			{
				//Store the Question's answer and the date answered into variables
				ans = cursor.getInt(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_ANSWER));
				date = cursor.getString(cursor.getColumnIndex(HealthyDroidQuizHelper.COLUMN_DATETIME));
				//Add the answer and date into the linked list
				totalResults.add(Integer.toString(ans));
				totalResults.add(date);
				cursor.moveToNext();
			}
		}
		return totalResults;
	}
}
