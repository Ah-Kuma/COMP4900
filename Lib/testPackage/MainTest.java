package testPackage;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author A00764761
 * @version 1.0
 */
public class MainTest
{
    public static void main(String[] args)
    {
        System.out.println(MCQuestion.class.hashCode());
        System.out.println(ScalarQuestion.class.hashCode());
        System.out.println(MultipleAnswerQuestion.class.hashCode());

        System.out.println();
        
        try
        {
            MCQuestion mcQuestion = new MCQuestion("\"hello\"",
                                                   "\"world\"");
            ScalarQuestion scalarQuestion = new ScalarQuestion("12",
                                                               "\"12\"");
            MultipleAnswerQuestion maQuestion = new MultipleAnswerQuestion("\"hello\"",
                                                                           "\"world\"");

            System.out.println("MCQ: " + mcQuestion.getQuestionTypeID());
            System.out.println("SQ: " + scalarQuestion.getQuestionTypeID());
            System.out.println("MAQ: " + maQuestion.getQuestionTypeID());

            if(mcQuestion.getQuestionTypeID() == MCQuestion.class.hashCode())
            {
                System.out.println(true);
            }
            if(maQuestion.getQuestionTypeID() == MultipleAnswerQuestion.class.hashCode())
            {
                System.out.println(true);
            }
            if(scalarQuestion.getQuestionTypeID() == ScalarQuestion.class.hashCode())
            {
                System.out.println(true);
            }
        }
        catch(Exception ex)
        {
            System.out.println("Exception caught" + ex.getMessage());
        }
    }
}
