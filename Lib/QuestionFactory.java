

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


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
     * @param args constructor arguments.
     * @param classType the string representation of the class name to create.
     * @return a subclass of Question
     * @throws ClassNotFoundException if the type indicated in classType cannot be found.
     */
    public <T extends Question>T Create(String classType, String questionTypeOptions, String questionText) throws ClassNotFoundException
    {
       T question = null;
        
       try
       {
           //Class<T> handle
           Class<T> clazz;
           //Create a class of <T> from the string classType
           clazz = (Class<T>)Class.forName(classType);
           //get the constructor from the class that takes two strings as parameters
           Constructor<T> constructor = clazz.getConstructor(new Class[]{String.class, String.class});
           //create an object from the constructor, passing in the arguments
           question = constructor.newInstance(questionTypeOptions, questionText);
       }
       catch (InstantiationException ex)
       {
           //This should never happen
       }
       catch(IllegalAccessException ex)
       {
           //This should never happen
       }
       catch(NoSuchMethodException ex)
       {
           //This will only happen if there is no constructor of this type in the class.
           //Therefore should never happen if the class follows appropriate design patterns.
       }
       catch(InvocationTargetException ex)
       {
           //This should never happen - will only happen if there is no constructor of this type in the class.
       }
    
       return question;
    }
}
