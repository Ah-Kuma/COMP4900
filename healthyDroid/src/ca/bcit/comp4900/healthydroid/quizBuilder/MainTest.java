/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.bcit.comp4900.healthydroid.quizBuilder;


import java.util.List;


/**
 *
 * @author A00764761
 * @version 1.0 
 */
public class MainTest {

    public static void main(String[] args)
    {
        HealthismQuiz quiz;
        quiz = QuizFactory.build();
        
        printQuiz(quiz);
    }
    
    public static void printQuiz(HealthismQuiz quiz)
    {
        List<Question> questions = quiz.getQuestions();

        for(Question question : questions)
        {
            System.out.println("Question #" + question.Number);
            System.out.println("QuestionType = " + question.getType());

            List<String> options = question.getOptions();

            System.out.println("Options");
            int optionNumber = 0;
            for(String option : options)
            {
                System.out.println("Option " + ++optionNumber + ": " + option);
            }
            System.out.println(question.getText());
        }
    }
}