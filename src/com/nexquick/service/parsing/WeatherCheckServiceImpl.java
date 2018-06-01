package com.nexquick.service.parsing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.nexquick.model.vo.Address;

public class WeatherCheckServiceImpl implements WeatherCheckService {
	
	
	/* (non-Javadoc)
	 * @see com.nexquick.service.parsing.WeatherCheckService#simpleWeather(com.nexquick.model.vo.Address)
	 */
	@Override
	public int simpleWeather(Address addr) {
		String latitude = addr.getLatitude();
		String longitude = addr.getLongitude();
		StringBuilder getData = new StringBuilder();
		
		try {
			getData.append("latitude=").append(URLEncoder.encode(latitude, "UTF-8"));
			getData.append("&longitude=").append(URLEncoder.encode(longitude, "UTF-8"));
			String param = getData.toString();
			String apiURL = "https://apis.sktelecom.com/v1/weather/status?"+param;

	        JSONObject object = parsing(apiURL);
	        
	        int weatherStatus = Integer.parseInt(object.get("weatherStatusCode").toString());
	        return weatherStatus;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see com.nexquick.service.parsing.WeatherCheckService#detailWeather(com.nexquick.model.vo.Address)
	 */
	@Override
	public void detailWeather(Address addr) {
		
		Map<String, Object> params = new LinkedHashMap<>(); // 파라미터 세팅
		params.put("lat", addr.getLatitude());
		params.put("lon", addr.getLongitude());
/*		params.put("city", addr.getSido());
		params.put("county", addr.getSigugun());
		params.put("village", addr.getDongmyun());*/
		
        StringBuilder getData = new StringBuilder();
        for(Map.Entry<String,Object> param : params.entrySet()) {
            try {
            	getData.append('&');
				getData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				getData.append('=');
				getData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        }
        String param = getData.toString();
        
        String apiURL = "https://api2.sktelecom.com/weather/current/minutely?version=1"+param;

        JSONObject object = parsing(apiURL);
        
		JSONObject weather = (JSONObject) object.get("weather");
		JSONArray minutely = (JSONArray) weather.get("minutely");
		JSONObject data = (JSONObject) minutely.get(0);
		System.out.println(data.toJSONString());
	}
	
	
	private JSONObject parsing(String apiURL) {

		try {
            URL url = new URL(apiURL);
            
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("appKey","2c831aee-8c6e-444b-82ed-1a23b76e504c");
            con.setRequestProperty("TDCProjectKey","2c831aee-8c6e-444b-82ed-1a23b76e504c");
			con.setRequestProperty("Accept", "application/json");
			
            int responseCode = con.getResponseCode();
			InputStreamReader isr;
			if (responseCode == 200) { 
				isr = new InputStreamReader(con.getInputStream());
			} else { 
				isr = new InputStreamReader(con.getErrorStream());
			}
			
			JSONObject object = (JSONObject)JSONValue.parse(isr);
			return object;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
