

import java.util.ArrayList;
import java.util.Iterator;
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
    
    private int MinScaleValue;
    
    public int getMinScaleValue()
    {
        return MinScaleValue;
    }
    
    private int MaxScaleValue;

    public int getMaxScaleValue()
    {
        return MaxScaleValue;
    }
    
    @Override
    public Iterator<String> getQuestionOptions()
    {
        ArrayList<String> options = new ArrayList<String>(2);
        options.add(String.valueOf(MinScaleValue));
        options.add(String.valueOf(MaxScaleValue));
        
        return options.iterator();
    }
    
    public ScalarQuestion(String scalarValues,
                          String questionText)
    {
        super(questionText);
        Scanner scanner = new Scanner(scalarValues);
        
        int 
        
        if(scanner.hasNextInt())
        {
            scanner.
        }
        MinScaleValue = scanner.nextInt();
        MaxScaleValue = scanner.nextInt();
    }
}
