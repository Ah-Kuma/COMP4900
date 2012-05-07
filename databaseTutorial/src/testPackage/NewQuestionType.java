/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testPackage;


import java.util.ArrayList;
import java.util.ListIterator;


/**
 *
 * @author A00764761
 * @version 1.0 
 */
public class NewQuestionType extends Question {

    ArrayList<String> options;
    private static final int questionTypeID = NewQuestionType.class.hashCode();
    
    @Override
    public ListIterator<String> getQuestionOptions()
    {
        return options.listIterator();
    }

    @Override
    public int getQuestionTypeID()
    {
        return NewQuestionType.questionTypeID;
    }
    
    public NewQuestionType(String string)
    {
        super(string);
    }

}
