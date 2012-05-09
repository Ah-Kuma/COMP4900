package ca.bcit.comp4900;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * This class is meant to manage the tables of the database. Its responsibilities include creating and dropping tables as well as acting as a
 * medium between the datasource and the database.
 * @author Rajpreet Sidhu
 *
 */
public class HealthyDroidQuizHelper extends SQLiteOpenHelper
{
	/**
	 * The columns and tables used in the database, as well as the database name and version.
	 */
	public static final String TABLE_QUESTION = "question";
	public static final String TABLE_ANSWER = "answerTable";
	public static final String TABLE_ASSOCIATION = "association";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_QUESTION_ID = "questionId";
	public static final String COLUMN_QUESTION = "questionText";
	public static final String COLUMN_ANSWER = "answer";
	public static final String COLUMN_DATETIME = "dateTime";
	
	private static final String DATABASE_NAME = "healthism.db";
	private static final int DATABASE_VERSION = 1;
	
	/**
	 * SQL statements for creating the 3 tables used in the database.
	 */
	private static String CREATE_TABLE_QUESTION = "create table "
			+ TABLE_QUESTION + "( " + COLUMN_ID 
			+ " integer primary key," + COLUMN_QUESTION + " text not null);";
	
	private static String CREATE_TABLE_ANSWER = "create table "
			+ TABLE_ANSWER + "( " + COLUMN_ID
			+ " integer primary key autoincrement, "
			+ COLUMN_QUESTION_ID + " integer, " + COLUMN_ANSWER + " text not null, "
			+ COLUMN_DATETIME + " text not null, FOREIGN KEY (" + COLUMN_QUESTION_ID + ") REFERENCES "
			+ TABLE_QUESTION + "("+COLUMN_ID+"));";
	
	private static String CREATE_TABLE_ASSOCIATION = "create table "	
			+ TABLE_ASSOCIATION + "( " + COLUMN_QUESTION_ID + " integer not null, "
			+ COLUMN_ANSWER + " text not null, " + COLUMN_DATETIME + " text not null, " 
			+ "FOREIGN KEY (" + COLUMN_QUESTION_ID + ") REFERENCES " + TABLE_QUESTION
			+ "(" + COLUMN_ID + "), FOREIGN KEY (" + COLUMN_ANSWER + ") "
			+ "REFERENCES "+ TABLE_ANSWER + "(" + COLUMN_ANSWER + "), " 
			+ "FOREIGN KEY (" + COLUMN_DATETIME + ") REFERENCES " + TABLE_ANSWER
			+ "(" + COLUMN_DATETIME + ") PRIMARY KEY (" + COLUMN_QUESTION_ID + "," + COLUMN_DATETIME + "));";
	
	/**
	 * SQL statements for dropping the tables
	 */
	private static String DROP_TABLE_QUESTION = "DROP TABLE IF EXISTS " + TABLE_QUESTION;
	private static String DROP_TABLE_ANSWER = "DROP TABLE IF EXISTS " + TABLE_ANSWER;
	private static String DROP_TABLE_ASSOCIATION = "DROP TABLE IF EXISTS " + TABLE_ASSOCIATION;
	/**
	 * The constructor taking in the current context of the activity, the database name and version.
	 * It creates a new database.
	 * @param context
	 */
	public HealthyDroidQuizHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DROP_TABLE_QUESTION);
		db.execSQL(DROP_TABLE_ANSWER);
		db.execSQL(DROP_TABLE_ASSOCIATION);
		db.execSQL(CREATE_TABLE_QUESTION);
		db.execSQL(CREATE_TABLE_ANSWER);
		db.execSQL(CREATE_TABLE_ASSOCIATION);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(HealthyDroidQuizHelper.class.getName(),
				"Upgrading database from version "+ oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSOCIATION);
		onCreate(db);
	}
}
