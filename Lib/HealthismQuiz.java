

import java.util.Iterator;
import java.util.List;


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

    private List<Question> QuizQuestions;
    
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
    
}
