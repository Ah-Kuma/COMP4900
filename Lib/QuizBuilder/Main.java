package QuizBuilder;


import generated.HealthismQuiz;
import generated.HealthismQuiz.Question;
import java.io.File;
import java.util.List;
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

        HealthismQuiz quiz = (HealthismQuiz)unmarshaller.unmarshal(new File("QuizSample.xml"));

        printQuizObject(quiz);
        
    }

    public static void printQuizObject(HealthismQuiz quiz)
    {
        List<Question> questions = quiz.getQuestion();

        for(Question question: questions)
        {
            System.out.println("Question #" + question.getNumber());
            System.out.println("QuestionType = " + question.getType());
            
            List<String> options = question.getOptions().getOption();
            
            System.out.println("Options");
            int optionNumber = 0;
            for(String option : options)
            {
                System.out.println("Option " + ++optionNumber + ": " + option);
            }
            System.out.println(question.getText());
        }
    }

    private Main()
    {
    }
}
