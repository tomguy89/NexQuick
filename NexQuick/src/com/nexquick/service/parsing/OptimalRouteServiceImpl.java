package com.nexquick.service.parsing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.nexquick.model.vo.Coordinate;

public class OptimalRouteServiceImpl implements OptimalRouteService {
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Coordinate> optimization(List<Coordinate> list){
		
		List<Coordinate> result = new ArrayList<>();
		int z = list.size()-1;
		JSONObject param = new JSONObject();
		param.put("reqCoordType", "WGS84GEO");
		param.put("resCoordType", "EPSG3857");
		param.put("startName", list.get(0).getLongitude());
		param.put("startX", list.get(0).getLongitude());
		param.put("startY", list.get(0).getLatitude());
		param.put("endName", list.get(0).getLongitude());
		param.put("endX", list.get(z).getLongitude());
		param.put("endY", list.get(z).getLatitude());
		JSONArray viaPoints = new JSONArray();
		JSONObject viaPoint;
		for(int i=0; i<=z; i++) {
			System.out.println(list.get(i).getType()+list.get(i).getNumber());
		}
		for(int i=1; i<z; i++) {
			viaPoint = new JSONObject();
			viaPoint.put("viaPointId", list.get(i).getType()+list.get(i).getNumber());
			viaPoint.put("viaPointName", list.get(i).getType()+list.get(i).getNumber());
			viaPoint.put("viaX", list.get(i).getLongitude());
			viaPoint.put("viaY", list.get(i).getLatitude());
			viaPoint.put("viaTime", 300);
			viaPoints.add(viaPoint);
		}
		param.put("viaPoints", viaPoints);
		System.out.println(param.toJSONString());
        try {
            String apiURL = "https://api2.sktelecom.com/tmap/routes/routeOptimization10?version=1&format=json"; 
            URL url = new URL(apiURL);
            byte[] postDataBytes = param.toString().getBytes("UTF-8");


            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            con.setRequestProperty("appKey","5096fcfd-6200-419f-b367-a37263afc3cc");
            con.setRequestProperty("appKey","2c831aee-8c6e-444b-82ed-1a23b76e504c");
            con.setDoOutput(true);
            con.getOutputStream().write(postDataBytes); // POST 호출

            int responseCode = con.getResponseCode();
            
			InputStreamReader isr;
			
			if (responseCode == 200) { 
				isr = new InputStreamReader(con.getInputStream(), "UTF-8");
			} else { 
				isr = new InputStreamReader(con.getErrorStream(), "UTF-8");
			}
			
			JSONObject object = (JSONObject)JSONValue.parse(isr);
			System.out.println(responseCode);
			System.out.println(object.toString());
			JSONArray features = (JSONArray) object.get("features");
			if (features == null) return null;
			JSONObject feature;
			JSONObject geometry;
			JSONObject properties;
			Coordinate coor;
			JSONArray jarr;
			for (int i = 0; i < features.size(); i++){
				coor = new Coordinate();
				feature = (JSONObject) features.get(i);
				geometry = (JSONObject) feature.get("geometry");
				if(geometry.get("type").toString().equals("LineString")) continue;
				properties = (JSONObject) feature.get("properties");
				jarr = (JSONArray) geometry.get("coordinates");
				coor.setLongitude(Double.parseDouble(jarr.get(0).toString()));
				coor.setLatitude(Double.parseDouble(jarr.get(1).toString()));
				coor.setType((properties.get("viaPointId").toString()).substring(0, 1));
				coor.setNumber(Integer.parseInt((properties.get("viaPointId").toString()).substring(1)));
				result.add(coor);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
}
