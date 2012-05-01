

import java.util.ArrayList;
import java.util.Iterator;
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

    public Iterator<String> getOptionSelectionText()
    {
        return optionSelectionText.iterator();
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

        Scanner scanner = new Scanner(options);
        while(scanner.hasNext())
        {
            optionSelectionText.add(scanner.next());
        }
    }
}
