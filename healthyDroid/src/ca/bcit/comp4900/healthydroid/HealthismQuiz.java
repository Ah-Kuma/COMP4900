package ca.bcit.comp4900.healthydroid;



import java.util.ArrayList;
import java.util.ListIterator;

import ca.bcit.comp4900.healthydroid.question.Question;


/**
 *
 * @author A00764761
 * @version 1.0 
 */
public class HealthismQuiz {

    private ArrayList<Question> quizQuestions;
    
    public int getQuizSize(){
    	return quizQuestions.size();
    }
    
    public void addQuestion(Question question)
    {
        if(question != null)
        {
            quizQuestions.add(question);
        }
    }
    
    public ListIterator<Question> getQuestions()
    {
        return quizQuestions.listIterator();
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
            //returns the first question if the index is smaller than 0;
            return quizQuestions.get(0);
        }
        return quizQuestions.get(index);
    }
    
    public HealthismQuiz()
    {
        quizQuestions = new ArrayList<Question>();
    }
}
