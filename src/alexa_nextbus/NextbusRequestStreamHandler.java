package alexa_nextbus;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class NextbusRequestStreamHandler extends SpeechletRequestStreamHandler{
	private static final Set<String> supportedApplicationIds = new HashSet<String>();
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.add("amzn1.ask.skill.46773ba7-aee4-4789-8560-08b9e25592c7");
    }

    public NextbusRequestStreamHandler() {
        super(new NextbusSpeechlet(), supportedApplicationIds);
    }
}
