package com.nexquick.System;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FireBaseMessaging {
	
	
	private static FireBaseMessaging instance = new FireBaseMessaging();
	private FireBaseMessaging() {}
	
	public static FireBaseMessaging getInstance() {
		if(instance == null) {
			synchronized(FireBaseMessaging.class) {
				instance = new FireBaseMessaging();
			}
		}
		return instance;
	}
	
	
	public boolean sendMessage(String token, String msg) {
		 final String apiKey = "AAAAqMvnVrg:APA91bE3yZF2YLilNUtx2AzvAJy_q2EXrgQR-_7tRYwCIQzFwAyV88BcFeeJa3fZZy_-eOBUZ7W6N1LNUmgTV2g8dvjpCx-QW7jtel6blFP1cKG3CWtPtxV-yUI4dmsz_l4IHWE_KueC";
        URL url;
		try {
			url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + apiKey);
			
			conn.setDoOutput(true);
			
			// 이걸로 보내면 특정 토큰을 가지고있는 어플에만 알림을 날려준다  위에 둘중에 한개 골라서 날려주자
			String input = "{\"notification\" : {\"title\" : \" NexQuick \", \"body\" : \""+""+msg+""+"\"}, \"to\":\""+token+"\"}";
			System.out.println(input);
			
			OutputStream os = conn.getOutputStream();
			
			// 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
			os.write(input.getBytes("UTF-8"));
			os.flush();
			os.close();
			
			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + input);
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
