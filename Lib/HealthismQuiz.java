

import java.util.ArrayList;
import java.util.Iterator;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author A00764761
 * @version 1.0 
 */
public class HealthismQuiz {

    private ArrayList<Question> quizQuestions;
    
    public void addQuestion(Question question)
    {
        if(question != null)
        {
            quizQuestions.add(question);
        }
    }
    
    public Iterator<Question> getQuestions()
    {
        return quizQuestions.iterator();
    }
    
    public Question getQuestion(int index)
    {
        //boundary testing
        if(index > quizQuestions.size())
        {
            //returns the beginning of the list if the index is greater than the size of the list
            return quizQuestions.get(quizQuestions.size() - 1);
        }
        else if(index < 0)
        {
            //returns the start of the list if the index is greater 
            return quizQuestions.get(0);
        }
        return quizQuestions.get(index);
    }
    
    public HealthismQuiz()
    {
        quizQuestions = new ArrayList<Question>();
    }
}
