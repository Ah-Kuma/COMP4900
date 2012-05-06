package QuizBuilder;


import generated.QuizFactory;
import java.util.List;
import generated.HealthismQuiz;
import generated.HealthismQuiz.Question;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author A00764761
 * @version 1.0
 */
public class Main
{
    public static void main(String[] args)
        throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(HealthismQuiz.class);
        QuizFactory qf = new QuizFactory();

        Unmarshaller unmarshaller = context.createUnmarshaller();

        HealthismQuiz quiz = (HealthismQuiz)unmarshaller.unmarshal(new File("GenericQuizSample.xml"));

        printQuizObject(quiz);
    }

    public static void printQuizObject(HealthismQuiz quiz)
    {
        List<Question> questions = quiz.getQuestion();

        for(int i = 0; i < questions.size(); i++)
        {
            System.out.println("Question" + (i + 1));
            System.out.println("QuestionType = " + questions.get(i).
                getType());
            List<String> options = questions.get(i).
                getOptions().
                getOption();

            int j = 0;
            for(String option : options)
            {
                System.out.print("Option" + ++j + ": ");
                System.out.println(option);
            }
        }
    }

    private Main()
    {
    }
}
