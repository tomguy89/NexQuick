package com.nexquick.service.parsing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
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
	public Map<String, String> simpleWeather(Address addr) {
		String latitude = addr.getLatitude();
		String longitude = addr.getLongitude();
		StringBuilder getData = new StringBuilder();
		Map<String, String> map = new HashMap<>();
		
		try {
			getData.append("latitude=").append(URLEncoder.encode(latitude, "UTF-8"));
			getData.append("&longitude=").append(URLEncoder.encode(longitude, "UTF-8"));
			String param = getData.toString();
			String apiURL = "https://apis.sktelecom.com/v1/weather/status?"+param;

	        JSONObject object = parsing(apiURL);
	        System.out.println(object.toString());
	        String weatherStatus = object.get("weatherStatusCode").toString();
	        String weatherModify = object.get("weatherModifyCode").toString();
	        String word = null;
	        switch(Integer.parseInt(weatherStatus)) {
	        case 0:word="알수 없음"; break; case 1:word="맑음"; break;case 2:word="흐림"; break;case 3:word="안개"; break;case 4:word="구름"; break;case 5:word="비"; break;
	        case 6:word="눈"; break;case 7:word="비/눈"; break;case 8:word="폭우"; break;case 9:word="폭설"; break;case 10:word="폭우/폭설"; break;}
	        map.put("weatherStatus", word);
	        switch(Integer.parseInt(weatherModify)) {
	        case 0:word="알수 없음"; break; case 1:word="강추위"; break;case 2:word="쌀쌀하다"; break;case 3:word="쌀쌀하다"; break;case 4:word="포근하다"; break;
	        case 5:word="따듯하다"; break; case 6:word="선선하다"; break;case 7:word="덥다"; break;case 8:word="무더위"; break;}
	        map.put("weatherModify", word);
	        return map;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
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
