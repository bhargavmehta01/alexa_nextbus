package alexa_nextbus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONObject;

public class NextbusRequest {

	public static String predictions(String busRoute, String busStop) {

		//this is a test URL which works for every time
		//String testUrl = "http://webservices.nextbus.com/service/publicJSONFeed?command=predictions&a=rutgers&r=wknd1&s=livingston";
		String baseUrl = "http://webservices.nextbus.com/service/publicJSONFeed?command=predictions&a=rutgers";
		StringBuilder requestUrl = new StringBuilder(baseUrl);
		String route = "";
		String stop = "";

		System.out.println(busRoute + " " + busStop);
		List<String> time = new ArrayList<String>(); 

		Map<String, String> routes = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		Map<String, String> stops = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		
		routes.put("campus connect", "connect");
		
		stops.put("washington park", "washpark");
		
		route = routes.get(busRoute);
		stop = stops.get(busStop);
		System.out.println(route + " " + stop);
//		if(busRoute.equalsIgnoreCase("campus connect")) {
//			route = "connect";
//		}
//
//		if(busStop.equalsIgnoreCase("washington park")) {
//			stop = "washpark";
//		}

		requestUrl.append("&r=").append(route).append("&s=").append(stop);

		if(busRoute.equalsIgnoreCase("test") && busStop.equals("test")) {
			route = "test";
			stop = "test";
			//requestUrl = testUrl;
		}

		GetMethod method = new GetMethod(requestUrl.toString());

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

				String result = "The ETA for ".concat(busRoute).concat(" at ").concat(busStop).concat(" is ").concat(time.toString()).concat(" minutes");
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
