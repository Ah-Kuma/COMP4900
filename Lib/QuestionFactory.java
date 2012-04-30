/**
 * QuestionFactory is a factory pattern class that creates objects that inherit directly from Question. 
 * @author A00764761
 * @version 1.0 
 */
public class QuestionFactory {

    public QuestionFactory()
    {
    }
    
    /**
     * Create creates an object of the type passed in as the value of classType.
     * @param <T> Generic subclass of Question
     * @param classType the string representation of the class name to create.
     * @return a subclass of Question
     * @throws ClassNotFoundException if the type indicated in classType cannot be found.
     */
    public <T extends Question>T Create(String classType) throws ClassNotFoundException
    {
       T question = null;
       
       try
       {
           Class<T> clazz;
           clazz = (Class<T>)Class.forName(classType);
           //create a new class of the type "classType"
           //question = clazz.getConstructor(Class<T>, string);
           question = clazz.newInstance();
       }
       
       catch (InstantiationException ex)
       {
           //This should never happen
       }
       catch(IllegalAccessException ex)
       {
           //This should never happen
       }
    
       return question;
    }
}
