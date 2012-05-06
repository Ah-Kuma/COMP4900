package QuizBuilder;


import generated.ObjectFactory;
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

        Unmarshaller unmarshaller = context.createUnmarshaller();

        HealthismQuiz quiz = (HealthismQuiz)unmarshaller.unmarshal(new File("GenericQuizSample.xml"));

        printQuizObject(quiz);
    }

    public static void printQuizObject(HealthismQuiz quiz)
    {
        List<Question> questions = quiz.getQuestion();

        for(Question question : questions)
        {
            System.out.println("Question #" + question.getNumber());
            System.out.println("QuestionType = " + question.getType());
            List<String> options = question.getOptions().getOption();

            int i = 0;
            for(String option : options)
            {
                System.out.print("Option" + ++i + ": " + option);
            }
        }
    }

    private Main()
    {
    }
}
