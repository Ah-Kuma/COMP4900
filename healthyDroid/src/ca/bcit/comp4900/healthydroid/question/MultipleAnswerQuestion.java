package ca.bcit.comp4900.healthydroid.question;

import java.util.ArrayList;
import java.util.ListIterator;


/**
 *
 * @author A00764761
 * @version 1.0 
 */
public class MultipleAnswerQuestion extends Question {

    private static final int QuestionTypeID = 2;
    
    public int getQuestionOptionsSize()
    {
        return QuestionOptions.size();
    }
    
    private ArrayList<String> QuestionOptions;
    
    @Override
    public ListIterator<String> getQuestionOptions()
    {
        return QuestionOptions.listIterator();
    }

    @Override
    public int getQuestionTypeID()
    {
        return QuestionTypeID;
    }

    /**
     * MultipleAnswerQuestion Constructor. Takes two strings as arguments. First string consists of a string of 
     * question options. Each option string must be enclosed in " " characters and separate by a comma. The contents 
     * of each option can be multi-worded.  
     * @param rawQuestionOptions The options for the question response.
     * @param questionText The text portion of the question query.
     * @throws InstantiationException if malformed option syntax is encountered.
     */
    public MultipleAnswerQuestion(String rawQuestionOptions, String questionText) throws InstantiationException
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
            //trim off any extra whitespace.
            s.trim();
            
            //test proper option syntax enclosed in "".
            if(!(s.startsWith("\"") && s.endsWith("\"")))
            {
                throw new InstantiationException("Invalid option format: not enclosed in \" marks.");
            }
            //remove "" and pass as valid option.
            String option = s.substring(1, s.length() - 1);
            
            QuestionOptions.add(option);
        }
    }
}
