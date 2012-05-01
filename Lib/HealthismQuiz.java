

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
        if(index > QuizQuestions.size())
        {
            return QuizQuestions.get(index);
        }
        return QuizQuestions.get(QuizQuestions.size() - 1);
    }
}
