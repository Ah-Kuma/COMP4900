/**
 * Question.Java
 * 
 * Is the abstract superclass of all other question type objects.
 * All questions of the subtype must inherit from this class.
 * @author A00764761
 * @version 1.0
 */
public abstract class Question 
{
    private String QuestionText;
    
    public String getQuestionText()
    {
        return this.QuestionText;
    }
    
    public void setQuestionText(String text)
    {
        this.QuestionText = text;
    }
    
    public Question(String questionText)
    {
        this.QuestionText = questionText;
    }
    
}
