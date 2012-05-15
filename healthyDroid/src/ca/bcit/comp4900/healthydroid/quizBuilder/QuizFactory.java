/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bcit.comp4900.healthydroid.quizBuilder;


import java.io.File;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


/**
 * QuizFactory class builds a HealthismQuiz object from an XML file. This file exists in a standard location with a
 * generic name. Further implementation could modify this hard coded behavior to affix an ID to the filename which is
 * associated with the id of the quiz it contains.
 *
 * @author Tim Rapske
 * @version 1.0
 */
public class QuizFactory
{
    /**
     * String indicating where the quiz document is located. This is a default value.
     */
    private static final String QuizFileName = "QuizTest4.xml";
    private static final String ResourceDir = "./resources/";

    /**
     * For demonstration purposes only. Each quiz file will be used separately.
     *
     * private static final String QuizDemoFileName = "./resources/Quiz1.XML"; private static final String
     * QuizDemoFileName = "./resources/Quiz2.XML"; private static final String QuizDemoFileName =
     * "./resources/Quiz3.XML"; private static final String QuizDemoFileName = "./resources/Quiz4.XML;
     */
    /**
     * Private constructor ensures that this object remains utility only.
     */
    private QuizFactory()
    {
    }

    /**
     * build takes the filename of the quiz to be read and returns a HealthismQuiz object representation of that
     * document. If there is an issue with parsing the document the caught exception will cause the program to terminate
     * with error code 9.
     *
     * @param fileParam the filename of the document to be read.
     * @return the quiz made from the XML document.
     */
    public static HealthismQuiz build(String fileParam)
    {
        String fileName = fileParam == null ? QuizFileName : fileParam;

        HealthismQuiz quiz = null;

        try
        {
            Serializer serializer = new Persister();
            File file1 = new File(ResourceDir + fileName);
            quiz = serializer.read(HealthismQuiz.class,
                                   file1);
        }
        //SimpleXML requires this.
        catch(Exception ex)
        {
            //this should never happen. Problem with xml parsing or file not found.
            System.err.print(ex.getMessage()); //consider error log?
            System.exit(9); //causes full program shutdown. 
        }

        //There is a very small possibility that this could be null, but not reliably testable.
        return quiz;
    }

    public static HealthismQuiz build()
    {
        return build(null);
    }
}
