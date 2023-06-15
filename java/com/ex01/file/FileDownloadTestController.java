package com.ex01.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet("/download")

public class FileDownloadTestController extends HttpServlet {
	
	@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
	
	}
	@Override 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
		
	}


protected void doHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	
	resp.setContentType("text/html;charset=utf-8");
	
	
	String file_repo = "d:/file_repo";
	String fileName = req.getParameter("fileName");
	System.out.println("매개변수 파일명:"+fileName);
	
	OutputStream out = resp.getOutputStream();
	String downFile  = file_repo+"/"+fileName;
	System.out.println("다운로드파일명:"+downFile);
	
	File f = new File(downFile);
	resp.setHeader("Catch-Control", "no-cache");// "no-cache" :파일이름유지
	resp.addHeader("Content-disposition", "attachment; fileName="+fileName);
	
	FileInputStream in = new FileInputStream(f);
	
	byte[] buffer = new byte[1024*8];
	while(true) {
		int count = in.read(buffer);
		if(count == -1) {
			break;
		}
		
			out.write(buffer,0,count);
			
	}
	 in.close(); out.close();
		
	
}

}
