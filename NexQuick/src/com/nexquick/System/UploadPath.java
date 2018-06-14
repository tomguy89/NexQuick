package com.nexquick.System;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class UploadPath {
	
	public static String attach_path ="uploadPicture/";
	
	
	public static String path(HttpServletRequest request){	
		String uploadPath ="";
		
		try{
			String root_path =request.getServletContext().getRealPath("/");
			
			System.out.println("root Path는 "+root_path);
			
			uploadPath =root_path+attach_path.replace('/', File.separatorChar);
			
			System.out.println("uploadPath는 "+uploadPath);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return uploadPath;
	}
}
