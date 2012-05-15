package ca.bcit.comp4900.healthydroid.quizBuilder;


import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


/**
 * HealthismQuiz class is the de-serialized representation of a HealthismQuiz object extracted from an XML document using
 * the SimpleXML framework annotations. This object contains a list of questions, and a value for the quiz ID.
 *
 * @author Tim Rapske
 * @version 1.0
 */
@Root
public class HealthismQuiz
{
       
    /**
     * The list of questions in the quiz.
     */
    @ElementList(entry = "Question",
                 inline = true,
                 required = false)
    private List<Question> question;
    /**
     * The ID of the quiz - for identification if necessary.
     */
    @Attribute
    private int id;

    /**
     * Gets the ID of the quiz.
     *
     * @return the quiz ID.
     */
    public int getID()
    {
        return id;
    }

    /**
     * Gets the list of questions from the quiz. If the list is null, returns an empty list.
     *
     * @return the Quiz questions.
     */
    public List<Question> getQuestions()
    {
        if(question == null)
        {
            question = new ArrayList<Question>();
        }
        return question;
    }
    
    /**
     * getQuestion gest a specific question from the list of questions in the quiz.
     * @param index the index of the question to get.
     * @return the question at the index.
     */
    public Question getQuestion(int index)
    {
        // if the list is not initialized.
        if(question == null)
        {
            return new Question();
        }
        
        //if the index is less than 0, returns the first element in the list.
        if(index <0)
        {
            return question.get(0);
        }
        //if index is greater than the size of the list, returns last item.
        if(index > question.size())
        {
            return question.get(question.size()-1);
        }
        // returns the item at the index.
        return question.get(index);
    }

    /**
     * Gets the number of questions in the quiz.
     *
     * @return the number of questions.
     */
    public int numQuestions()
    {
        return question.size();
    }

    /**
     * Public constructor for HealthismQuiz. This constructor is called when an XML document is parsed. In the overall
     * project, only one HealthismQuiz Object should be in memory, however if the application is run multiple times,
     * garbage collection may not occur. Therefore this constructor resets the static counter in the Questions class
     * back to 0 upon instantiating a new quiz object.
     */
    public HealthismQuiz()
    {
        /**
         * Ensures that the static integer questionCounter in the Question class is reset to 0 each time a HealthismQuiz
         * object is created. This resolves some undesired behavior with internalized question number assignments if
         * garbage collection is not performed between instantiations of this object.
         */
        Question.questionCounter = 0;
    }
}