package ca.bcit.comp4900.healthydroid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import ca.bcit.comp4900.R;
import ca.bcit.comp4900.healthydroid.database.QuestionDataSource;
import ca.bcit.comp4900.healthydroid.quizBuilder.Question.QuestionType;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity that lets the user to select range of dates for generating a report.
 * 
 * @author Kevin, William and Rajpreet
 *
 */
public class GenerateReportActivity extends Activity implements Runnable {
    public static final String FILE_LOCATION = "PdfAndroid";
    public static final String FILE_NAME = "healthyDroidReport.pdf";
    private EditText textTitle;
    private EditText textContent;
    private ProgressDialog pd;
    private QuestionDataSource datasource;
    private boolean errorResult;
    private String errorMsg = "", title, content;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.generatereport);
    	textTitle = (EditText) findViewById(R.id.editText0);
        textContent = (EditText) findViewById(R.id.editText1);
		datasource = new QuestionDataSource(this.getBaseContext());
        datasource.open();
    }
    
    /**
     * Handler for the button
     * 
     * @param v
     */
    public void buttonClick(View v) {
        //see if the external storage is mounted (writable) else display a error message
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "Error: can't write to media.", 600).show();
            return;
        }
  
        //show the loading animation dialog and start the thread to generate the pdf
        pd = ProgressDialog.show(this, "Report Builder", "Creating pdf...", true, false);
        Thread t = new Thread(this);
        t.start();
       
    }

    //The new thread runs this
    @Override
    public void run() {
        
        errorResult = createPdf();
        handler.sendEmptyMessage(0);
    }
    
    /**
     * Handles when the pdf generation is completed.
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                pd.dismiss();
                if(errorResult)
                    Toast.makeText(getApplicationContext(), "PDF successfully created.", 600).show();
                else
                    Toast.makeText(getApplicationContext(), errorMsg, 600).show();
                
                File out = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + FILE_LOCATION + File.separator + FILE_NAME);
                Intent i = new Intent();
                i.setAction(android.content.Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(out), "application/pdf");
                try {
                startActivity(i);
                }
                catch(ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "No application available to open file.", 600).show();
                }
        }
    };
    
    /**
     * creates the pdf and calls the fuctions to put content into it
     * 
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    private boolean createPdf() {
        
        //sets the filepaths, creates the file
        File basePath = Environment.getExternalStorageDirectory();
        if(!basePath.canWrite()) {
            errorMsg = "Can't write to storage device";
            return false;
        }
        File dir = new File(basePath.getAbsolutePath() + File.separator + FILE_LOCATION);
        dir.mkdirs();
        File file = new File(dir, FILE_NAME);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            errorMsg = "Can't access file";
            return false;
        }
        
        // writes content into the document
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, fOut);
            doc.open();

            addFileProperties(doc);
            addUserInfo(doc);
            addTableContent(doc);
            
            doc.close();
        } catch (DocumentException e) {
            errorMsg = "Error writing document";
            return false;
        }
        return true;
    }

    /**
     * add properties to the pdf file
     * 
     * @param document
     */
    private void addFileProperties(Document document) {
    	title = textTitle.getText().toString();
    	content = textContent.getText().toString();
        document.addTitle(title);
        document.addSubject(content);
        document.addKeywords("test pdf - keywords");
        document.addAuthor("Healthism Systems Inc");
        document.addCreator("Healthism Systems Inc");
    }

    /**
     * Adds the users name, birthdate and gender into the document.
     * 
     * @param doc
     * @throws DocumentException
     */
    private void addUserInfo(Document doc) throws DocumentException {
        Font FontTitle = new Font(Font.TIMES_ROMAN, 28, Font.NORMAL);
        Font FontContent = new Font(Font.TIMES_ROMAN, 18, Font.NORMAL);
        SharedPreferences prefs = getSharedPreferences(HealthyDroidActivity.NAMESPACE, 0);
        String fname, lname, gender, birthdate;
        // add title
        Paragraph p = new Paragraph("Health Report", FontTitle);
        // add empty line
        p.add(new Paragraph(""));
        // add headers 
        fname = prefs.getString("firstName", null);
        lname = prefs.getString("lastName", null);
        gender = prefs.getString("gender", null);
        birthdate = prefs.getString("birthdate", null);
        // add user name, birthdate and gender
        p.add(new Paragraph(fname + " " + lname, FontContent));
        p.add(new Paragraph(birthdate, FontContent));
        p.add(new Paragraph(gender, FontContent));
        // add a blank line for formatting
        p.add(new Paragraph(""));
        // append all changes to the document
        doc.add(p);
    }
    
    /**
     * adds the questions, answers and date the questions were answered into a table and adds the table onto the pdf document.
     * 
     * @param doc - the pdf documnent that the content is written to
     * @throws DocumentException 
     */
    private void addTableContent(Document doc) throws DocumentException {
    	ArrayList<Integer> questions = new ArrayList<Integer>();
        LinkedList<String> answers = new LinkedList<String>();
        ArrayList<String> options = new ArrayList<String>();
        ArrayList<String> questionTypes = new ArrayList<String>();
    	PdfPTable table = new PdfPTable(4);
        
        PdfPCell questionNumber = new PdfPCell(new Phrase("Question Number"));
        PdfPCell questionText = new PdfPCell(new Phrase("Question"));
        PdfPCell answer = new PdfPCell(new Phrase("Answer"));
        PdfPCell date = new PdfPCell(new Phrase("Date Answered"));
        
        questionNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
        questionText.setHorizontalAlignment(Element.ALIGN_CENTER);
        answer.setHorizontalAlignment(Element.ALIGN_CENTER);
        date.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table.addCell(questionNumber);
        table.addCell(questionText);
        table.addCell(answer);
        table.addCell(date);
        
        questions = datasource.getQuestions();
        answers = datasource.getAnswer();
        questionTypes = datasource.getQuestionTypes();
        
        int qNum;
        Log.d("questions", Integer.toString(questions.size()));
        Log.d("answers", Integer.toString(answers.size()));

        Iterator<String> answersIterator = answers.iterator();
        while(answersIterator.hasNext())
        {
        	for(int j=0; j<questions.size(); j++)
        	{
        		qNum = questions.get(j);
        		//Add the question number to the table
        		table.addCell(Integer.toString(qNum));

        		if((questionTypes.get((qNum-1)).equals(QuestionType.valueOf("ScalarQuestion").toString())))
        		{
        			options = datasource.getOptions(qNum-1);
        			String text = datasource.getQuestionText(qNum);
        			String range = options.get(0);
        			//Add question text
        			table.addCell(text + " (on a scale of 1 to " + range + ")");
        			//Add question answer
        			Log.d("stuff",answers.toString());
        			table.addCell(answersIterator.next());
        		}
        		else
        		{
        			options = datasource.getOptions(qNum-1);
        			String text = datasource.getQuestionText(qNum);
            		//Add question text
        			table.addCell(text);
            		//Add answer
        			table.addCell(options.get(Integer.parseInt(answersIterator.next())));
        		}
        		//Add date
        		table.addCell(answersIterator.next());
        	}
        }
        //Append everything into the document
        doc.add(table);
    }
    /**
     * Overwritten method for when the activity is restarted after pausing.
     */
    @Override
	protected void onResume()
    {
		datasource.open();
		super.onResume();
	}
	/**
	 * Overwritten method to close the database when the activity is left abruptly (ie the home button is pressed).
	 */
	@Override
	protected void onPause()
	{
		datasource.close();
		super.onPause();
	}
}
