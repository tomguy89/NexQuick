package com.nexquick.service.parsing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class DistanceCheckServiceImpl implements DistanceCheckService {
	
	/* (non-Javadoc)
	 * @see com.nexquick.service.parsing.DistanceCheckService#singleDistanceCheck(java.util.Map)
	 */
	@Override
	public double singleDistanceCheck(Map<String, Object> point) {
		double result = 0;
		Map<String, Object> params = new LinkedHashMap<>(); // 파라미터 세팅
		params.put("startX", point.get("startX"));
		params.put("startY", point.get("startY"));
		params.put("endX", point.get("endX"));
		params.put("endY", point.get("endY"));
		params.put("reqCoordType", "WGS84GEO");
		params.put("resCoordType", "EPSG3857");
		params.put("searchOption", 0);
		
        try {
            String apiURL = "https://api2.sktelecom.com/tmap/routes?version=1&format=json"; 
            URL url = new URL(apiURL);
            StringBuilder postData = new StringBuilder();
            for(Map.Entry<String,Object> param : params.entrySet()) {
                if(postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");


            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            con.setRequestProperty("appKey","5096fcfd-6200-419f-b367-a37263afc3cc");
            con.setDoOutput(true);
            con.getOutputStream().write(postDataBytes); // POST 호출

            int responseCode = con.getResponseCode();
            
			InputStreamReader isr;
			
			if (responseCode == 200) { 
				isr = new InputStreamReader(con.getInputStream());
			} else { 
				isr = new InputStreamReader(con.getErrorStream());
			}
			
			JSONObject object = (JSONObject)JSONValue.parse(isr);
			JSONArray features = (JSONArray) object.get("features");
			JSONObject feature;
			for (int i = 0; i < 1/*features.size()*/; i++){
				feature = (JSONObject) features.get(i);
				
				JSONObject properties = (JSONObject) feature.get("properties");
				result = (Math.round(Integer.parseInt(properties.get("totalDistance").toString())/10.))/100.;
				
//				System.out.printf("거리 : %.2f km\n",Integer.parseInt(properties.get("totalDistance").toString())/1000.);
//				System.out.printf("시간 : %.1f 분",Integer.parseInt(properties.get("totalTime").toString())/60.);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
        return result;
	}
}
