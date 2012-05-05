package testPackage;




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
    
    private static final int QuestionTypeID = ScalarQuestion.class.hashCode();        
    private int MaxScaleValue;

    @Override
    public int getQuestionTypeID()
    {
        return ScalarQuestion.QuestionTypeID;
    }
    
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
            throw new InstantiationException("No integer value for scale.");
        }

        MaxScaleValue = scanner.nextInt();
    }
}
