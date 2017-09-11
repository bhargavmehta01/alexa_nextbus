package alexa_nextbus;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONObject;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;

public class NextbusSpeechlet implements Speechlet {

	@Override
	public void onSessionStarted(SessionStartedRequest arg0, Session arg1) throws SpeechletException {
		// TODO Auto-generated method stub

	}

	@Override
	public SpeechletResponse onLaunch(LaunchRequest arg0, Session arg1) throws SpeechletException {
		// TODO Auto-generated method stub
		return SkillResponse.newAskResponse("Welcome to the Nextbus App, how may I help you?", "Sorry! I didn't get you. How may I help you?");
	}

	@Override
	public SpeechletResponse onIntent(final IntentRequest request, final Session session) {
		// TODO Auto-generated method stub
		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;

		if("BUSLOCATIONINTENT".equals(intentName)) {

			String busRoute = intent.getSlot("BUSROUTE").getValue();
			String busStop = intent.getSlot("BUSSTOP").getValue();
			return SkillResponse.newTellResponse(NextbusRequest.predictions(busRoute, busStop)); 
		}
		return null;
	}

	@Override
	public void onSessionEnded(SessionEndedRequest arg0, Session arg1) throws SpeechletException {
		// TODO Auto-generated method stub

	}



}
