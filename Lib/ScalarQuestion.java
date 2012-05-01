

import java.util.Scanner;
import org.w3c.dom.ranges.Range;


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
    
    public ScalarQuestion(String scalarValues,
                          String questionText)
    {
        super(questionText);
        Scanner scanner = new Scanner(scalarValues);
        
        MinScaleValue = scanner.nextInt();
        MaxScaleValue = scanner.nextInt();
    }
}
