package com.nexquick.service.parsing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.nexquick.model.vo.Address;

public class AddressTransServiceImpl implements AddressTransService {
	
	@Override
	public Address getAddress(String addr) {
		List<Address> list;
		Address address = null;
		try {
			String input = URLEncoder.encode(addr, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/map/geocode?query=" + input;
			list = parsing(apiURL);
			if(list.size()==1) {
				address = list.get(0);
			}else if(list.size()>1) {
				for(Address a : list) {
					address = a;
					if(a.isRoadAddress()) break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(address.getLongitude() + " / " + address.getLatitude());
		Map<String, String> localCode = getCode(address.getLongitude(), address.getLatitude());
		address.setbCode(localCode.get("bCode"));
		address.sethCode(localCode.get("hCode"));
		return address;
	}
	
	@Override
	public Address getAddress(String longitude, String latitude) {
		List<Address> list;
		Address address = null;
		String addr = longitude+","+latitude;
		try {
			String input = URLEncoder.encode(addr, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/map/reversegeocode?query=" + input;
			list = parsing(apiURL);
			if(list.size()==1) {
				address = list.get(0);
			}else if(list.size()>1) {
				for(Address a : list) {
					address = a;
					if(a.isRoadAddress()) break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String, String> localCode = getCode(address.getLongitude(), address.getLatitude());
		address.setbCode(localCode.get("bCode"));
		address.sethCode(localCode.get("hCode"));
		return address;
	}
	
	
	public Map<String, String> getCode(String longitude, String latitude){
		Map<String, String> result = new LinkedHashMap<>();
        StringBuilder addr = new StringBuilder();
        try {
            addr.append(URLEncoder.encode("x", "UTF-8")).append('=');
            addr.append(URLEncoder.encode(longitude, "UTF-8"));
            addr.append('&');
            addr.append(URLEncoder.encode("y", "UTF-8")).append('=');
            addr.append(URLEncoder.encode(latitude, "UTF-8"));
            String input = addr.toString();
            String apiURL = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?"+input;
            
            URL url = new URL(apiURL);
           
           HttpURLConnection con = (HttpURLConnection)url.openConnection();
           con.setRequestMethod("GET");
            con.setRequestProperty("appkey", "KakaoAK bf6d5cb912dc2b49976ad265e4520f2b");
           con.setRequestProperty("Authorization", "KakaoAK bf6d5cb912dc2b49976ad265e4520f2b");
           con.setRequestProperty("Host", "dapi.kakao.com");
            
           int responseCode = con.getResponseCode();
            InputStreamReader isr;
            
            if (responseCode == 200) { 
                isr = new InputStreamReader(con.getInputStream());
            } else { 
                isr = new InputStreamReader(con.getErrorStream());
            }
            
            JSONObject object = (JSONObject)JSONValue.parse(isr);
            JSONObject meta = (JSONObject) object.get("meta");
            int count = Integer.parseInt(meta.get("total_count").toString());
            JSONArray documents = (JSONArray) object.get("documents");
            JSONObject document;
            for (int i = 0; i < count; i++){
                document = (JSONObject) documents.get(i);
                
                if(document.get("region_type").equals("B")) {
                    result.put("bCode", document.get("code").toString());
                }else if(document.get("region_type").equals("H")) {
                    result.put("hCode", document.get("code").toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
	}
	
	
	private List<Address> parsing(String apiURL) {
		
		List<Address> list = new ArrayList<>();
		String clientId = "EdwOZRQLVu6eR5GlvIiU";
		String clientSecret = "Y1bKEkJIxY";
		
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			InputStreamReader isr;
			
			if (responseCode == 200) { 
				isr = new InputStreamReader(con.getInputStream());
			} else { 
				isr = new InputStreamReader(con.getErrorStream());
			}
			
			JSONObject object = (JSONObject)JSONValue.parse(isr);
			JSONObject result = (JSONObject) object.get("result");
			JSONArray items = (JSONArray) result.get("items");
			Address address;
			for (int i = 0; i < items.size(); i++){
				address = new Address();
				JSONObject item = (JSONObject) items.get(i);
				
				address.setFullAddr(item.get("address").toString());

				JSONObject detail = (JSONObject) item.get("addrdetail");
				address.setCountry(detail.get("country").toString());
				address.setSido(detail.get("sido").toString());
				address.setSigugun(detail.get("sigugun").toString());
				address.setDongmyun(detail.get("dongmyun").toString());
				address.setRi(detail.get("ri").toString());
				address.setRest(detail.get("rest").toString());
				
				if(item.get("isRoadAddress").toString().equals("true")) {
					address.setRoadAddress(true);
				}else {
					address.setRoadAddress(false);
				}
				
				JSONObject point = (JSONObject) item.get("point");
				address.setLongitude(point.get("x").toString());
				address.setLatitude(point.get("y").toString());
				
				list.add(address);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
