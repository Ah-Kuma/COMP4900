

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;


/**
 *
 * @author A00764761
 * @version 1.0
 */
public class MCQuestion
    extends Question
{
    private ArrayList<String> optionSelectionText;
    public static final int QuestionTypeID = MCQuestion.class.hashCode();

    @Override
    public ListIterator<String> getQuestionOptions()
    {
        return optionSelectionText.listIterator();
    }

    /**
     *
     * @param options
     * @param questionText
     */
    public MCQuestion(String options,
                      String questionText)
    {
        super(questionText);

        optionSelectionText = new ArrayList<String>();
        
        Scanner scanner = new Scanner(options);
        while(scanner.hasNext())
        {
            optionSelectionText.add(scanner.next());
        }
    }
}
