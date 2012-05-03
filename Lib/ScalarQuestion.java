

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;


/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author A00764761
 * @version 1.0
 */
public class ScalarQuestion
    extends Question
{
    
    private static final int QuestionTypeID = 1;
    
    @Override
    public int getQuestionTypeID()
    {
        return QuestionTypeID;
    }
        
    private int MaxScaleValue;

    public int getMaxScaleValue()
    {
        return MaxScaleValue;
    }
    
    @Override
    public ListIterator<String> getQuestionOptions()
    {
        ArrayList<String> options = new ArrayList<String>(1);
        options.add(String.valueOf(MaxScaleValue));
        
        return options.listIterator();
    }
    
    public ScalarQuestion(String scalarValues,
                          String questionText) throws InstantiationException
    {
        super(questionText);
        Scanner scanner = new Scanner(scalarValues);
                    
        if(!scanner.hasNextInt())
        {
            throw new InstantiationException();
        }

        MaxScaleValue = scanner.nextInt();
    }
}
