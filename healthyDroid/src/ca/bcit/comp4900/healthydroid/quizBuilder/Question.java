package ca.bcit.comp4900.healthydroid.quizBuilder;


import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


/**
 * Question class is the representation of a Question object that exists within the list of questions in a HealthismQuiz
 * object. A question contains query text, and a list of options.
 *
 * @author Tim Rapske
 * @version 1.0
 */
@Root
public class Question
{
     /**
     * The types of Questions possible in a HealthismQuiz object. 
     * This list must be updated if there are any new types implemented.
     */
    public enum QuestionType {MAQuestion, MCQuestion, ScalarQuestion};
    
    //Is the element list of <Option> elements contained within the Element <Options>.
    @ElementList(entry = "Option",
                 empty = true,
                 required = true)
    private List<String> Options;
    //Text of the question query
    @Element
    private String Text;
    //Type of question
    @Attribute
    private String type;
    /**
     * Holds the value of the number of question objects created.
     */
    protected static int questionCounter = 0;
    /**
     * The number of the question instance.
     */
    public final int Number;

    /**
     * getOptions gets the Options list for the question.
     *
     * @return the options for the question.
     */
    public List<String> getOptions()
    {
        return Options;
    }

    /**
     * Gets the text for the question.
     *
     * @return the question query text.
     */
    public String getText()
    {
        return Text;
    }

    /**
     * Gets the Type of question
     *
     * @return a string representing the type of question.
     */
    public QuestionType getType()
    {
        return QuestionType.valueOf(type);
    }

    /**
     * Constructor for Question objects. This constructor dynamically assigns a unique number to the instantiated
     * question objects.
     */
    public Question()
    {
        Number = ++questionCounter;
    }
}
