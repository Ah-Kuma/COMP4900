

import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author A00764761
 * @version 1.0 
 */
public class MultipleAnswer extends Question {

    private static final int QuestionTypeID = 2;
    
    private ArrayList<String> QuestionOptions;
    
    @Override
    public ListIterator<String> getQuestionOptions()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getQuestionTypeID()
    {
        return QuestionTypeID;
    }

    public MultipleAnswer(String rawQuestionOptions, String questionText) throws InstantiationException
    {
        super(questionText);
        
        //MultipleAnswer options cannot be null.
        if(rawQuestionOptions == null)
        {
            throw new IllegalArgumentException("QuestionOptions cannot be null");
        }
        
        //Initialize QuestionOptions collection.
        QuestionOptions = new ArrayList<String>();
        
        //Split the rawOptions string by comma seperators.
        String[] questionOptions = rawQuestionOptions.split(",");
        
        for(String s: questionOptions)
        {
            if(!(s.startsWith("\"") && s.endsWith("\"")))
            {
                throw new InstantiationException("Invalid option format: not enclosed in \" marks.");
            }
            String option = s.substring(1, s.length() - 2);
            
            QuestionOptions.add(option);
        }
    }
}
