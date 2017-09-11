package alexa_nextbus;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONObject;

public class NextbusRequest {
	
	public static String predictions(String busRoute, String busStop) {
		//this is a test URL which works for everytime
		String testUrl = "http://webservices.nextbus.com/service/publicJSONFeed?command=predictions&a=rutgers&r=wknd1&s=livingston";
		String requestUrl = "";
		String route = "";
		String stop = "";
		List<String> time = new ArrayList<String>(); 
		
		if(busRoute.equalsIgnoreCase("campus connect"))
			route = "connect";
		
		if(busStop.equalsIgnoreCase("washington park"))
			stop = "washpark";
		
		requestUrl = ("http://webservices.nextbus.com/service/publicJSONFeed?command=predictions&a=rutgers-newark").concat("&r=").concat(route).concat("&s=").concat(stop);
		
		if(busRoute.equalsIgnoreCase("test") && busStop.equals("test")) {
			route = "test";
			stop = "test";
			requestUrl = testUrl;
		}
			
		GetMethod method = new GetMethod(requestUrl);
		
		method.addRequestHeader("Accept", "application/json");
		
		String response = "";
		HttpClient client = new HttpClient();
		try {
			Integer statusCode = client.executeMethod(method);

			if(statusCode == 200){
				response = method.getResponseBodyAsString();
				
				JSONObject res = new JSONObject(response);
				JSONArray pred = new JSONArray();
				
				JSONObject data = res.getJSONObject("predictions");

				if(data.has("direction"))
					pred = data.getJSONObject("direction").getJSONArray("prediction"); 
				else
					return "No predictions found. Please try again later.";
				
				pred.forEach(item -> {
					JSONObject minute = (JSONObject)item;
					time.add(minute.getString("minutes"));
				});
				
				System.out.println(time.toString());
				
				String result = "The expected ETA for the shuttle at ".concat(stop).concat(" for ").concat(route).concat(" is ").concat(time.toString());
				System.out.println(result);
				return result;
			}

			else{
				return "Error! Please try again";
			}
		} catch (Exception e) {
			response = null;
			return "Error!";
		}
	}
	
}
