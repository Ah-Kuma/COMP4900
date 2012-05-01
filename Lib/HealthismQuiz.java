

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

    private ArrayList<Question> QuizQuestions;
    
    public void addQuestion(Question question)
    {
        if(question != null)
        {
            QuizQuestions.add(question);
        }
    }
    
    public Iterator<Question> getQuestions()
    {
        return QuizQuestions.iterator();
    }
    
    public Question getQuestion(int index)
    {
        //boundary testing
        if(index > QuizQuestions.size())
        {
            //returns the beginning of the list if the index is greater than the size of the list
            return QuizQuestions.get(QuizQuestions.size() - 1);
        }
        else if(index < 0)
        {
            //returns the start of the list if the index is greater 
            return QuizQuestions.get(0);
        }
        return QuizQuestions.get(index);
    }
    
    public HealthismQuiz()
    {
        QuizQuestions = new ArrayList<Question>();
    }
}
