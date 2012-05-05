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
        System.out.println(MCQuestion.QuestionTypeID);
        System.out.println(ScalarQuestion.QuestionTypeID);
        System.out.println(MultipleAnswerQuestion.QuestionTypeID);

        System.out.println();
        
        try
        {
            MCQuestion mcQuestion = new MCQuestion("\"hello\"",
                                                   "\"world\"");
            ScalarQuestion scalarQuestion = new ScalarQuestion("12",
                                                               "\"12\"");
            MultipleAnswerQuestion maQuestion = new MultipleAnswerQuestion("\"hello\"",
                                                                           "\"world\"");

            System.out.println("MCQ: " + mcQuestion.QuestionTypeID);
            System.out.println("SQ: " + scalarQuestion.QuestionTypeID);
            System.out.println("MAQ: " + maQuestion.QuestionTypeID);

            if(mcQuestion.QuestionTypeID == MCQuestion.class.hashCode())
            {
                System.out.println(true);
            }
            if(maQuestion.QuestionTypeID == MultipleAnswerQuestion.class.hashCode())
            {
                System.out.println(true);
            }
            if(scalarQuestion.QuestionTypeID == ScalarQuestion.class.hashCode())
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
