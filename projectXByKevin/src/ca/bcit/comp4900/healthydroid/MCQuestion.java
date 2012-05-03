package ca.bcit.comp4900.healthydroid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


/**
 *
 * @author A00764761
 * @version 1.0
 */
public class MCQuestion
    extends Question
{
    private ArrayList<String> optionSelectionText;
    
    public static final int QuestionTypeID = 0;

    @Override
    public Iterator<String> getQuestionOptions()
    {
        return optionSelectionText.iterator();
    }
    
    @Override
    public int getQuestionTypeID()
    {
        return QuestionTypeID;
    }

    /**
     *
     * @param options
     * @param questionText
     */
    public MCQuestion(String options,
                      String questionText)
    {
    	super(questionText);
    	
    	optionSelectionText = new ArrayList<String>(0);
        
        Scanner scanner = new Scanner(options);
        while(scanner.hasNext())
        {
            optionSelectionText.add(scanner.next());
        }
    }
}
