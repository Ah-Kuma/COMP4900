package com.nolifefool.pdfandroid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity that creates pdf. implements runnable because it needs to run a thread
 * for the loading animation
 * 
 * @author William
 *
 */
public class PdfAndroidActivity extends Activity implements Runnable {
    public static final String FILE_LOCATION = "PdfAndroid";
    public static final String FILE_NAME = "test.pdf";
    private EditText textTitle;
    private EditText textContent;
    private ProgressDialog pd;
    
    private boolean errorResult;
    private String errorMsg = "";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textTitle = (EditText) findViewById(R.id.editText0);
        textContent = (EditText) findViewById(R.id.editText1);
    }
    
    /**
     * Handler for the button
     * 
     * @param v
     */
    public void buttonClick(View v) {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "Error: can't write to media.", 600).show();
            return;
        }
  
        pd = ProgressDialog.show(this, "PDF Creator", "Creating pdf...", true, false);
        Thread t = new Thread(this);
        t.start();
       
    }
    
    @Override
    public void run() {
        
        errorResult = createPdf();
        handler.sendEmptyMessage(0);
    }
    
    /**
     * Handles when the action is completed.
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                pd.dismiss();
                if(errorResult)
                    Toast.makeText(getApplicationContext(), "PDF successfully created.", 600).show();
                else
                    Toast.makeText(getApplicationContext(), errorMsg, 600).show();
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
            addPageOneContent(doc);
            doc.newPage();
            addPageTwoContent(doc);
            
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
        document.addTitle("test pdf - title");
        document.addSubject("test pdf - subject");
        document.addKeywords("test pdf - keywords");
        document.addAuthor("test pdf - author");
        document.addCreator("test pdf - creator");
    }

    /**
     * add text content for page one. content is the user entered values.
     * 
     * @param doc
     * @throws DocumentException
     */
    private void addPageOneContent(Document doc) throws DocumentException {
        Font FontTitle = new Font(Font.TIMES_ROMAN, 28, Font.UNDERLINE);
        Font FontContent = new Font(Font.TIMES_ROMAN, 18, Font.NORMAL);
        
        // add title
        Paragraph p = new Paragraph(textTitle.getText().toString(), FontTitle);
        // add empty line
        p.add(new Paragraph(" "));
        // add content
        p.add(new Paragraph(textContent.getText().toString(), FontContent));
        
        doc.add(p);
    }
    
    /**
     * add predefined table stuff into page two
     * 
     * @param doc
     * @throws DocumentException 
     */
    private void addPageTwoContent(Document doc) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        
        PdfPCell c1 = new PdfPCell(new Phrase("header 1"));
        PdfPCell c2 = new PdfPCell(new Phrase("header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table.addCell(c1);
        table.addCell(c2);
        for(int i = 0; i < 10; i++) {
            table.addCell(Integer.toString(i));
        }
        
        doc.add(table);
    }


}
