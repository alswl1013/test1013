package com.ex01.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import lombok.extern.log4j.Log4j2;


@Log4j2
@WebServlet("/fileupload")
public class FileUploadTestController extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("fileupload");
		System.out.println("fileupload");
		req.getParameter("param1");
			String encoding= "utf-8";
			
			//1. 업로드 할 파일 경로를 지정(게시물 공지사항에 많이쓰임)
			File currenPath = new File("d:/file_repo");
			
			//2.라이브러리에서 지정한 업로드 저장소 지정
			DiskFileItemFactory factory = new DiskFileItemFactory();
		
			factory.setRepository(currenPath);
			factory.setSizeThreshold(1024*1024);//최대 업로드 파일크기 지정(바이트단위)
		
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			try {
				
				//request객체의 정보를 upload객체로 받아와서 처리
				List<FileItem> item = upload.parseRequest(req);// req객체 정보를 ServletFileUpload객체가 처리
				
				for(int i=0; i<item.size(); i++) {
					FileItem fileItem = item.get(i);
					
					
					
					//form요소안에 있는 요소인지(매개변수인지 file인지 구분) 판별
					
					if(fileItem.isFormField()) {
					
						System.out.println(fileItem.getFieldName()+"="+fileItem.getString(encoding));   
					}else {
						//file이면 처리
						System.out.println("매개변수:"+fileItem.getFieldName());
						System.out.println("파일이름:"+fileItem.getName());
						System.out.println("파일크기:"+fileItem.getSize()+"bytes");
					
						//조건처리
						
						if (fileItem.getSize()>0) {
							//경로와 파일명을 분리:"c:/f/tbc/abc.txt"
							//운영체제별 파일경로 기호 추출: "/", "\\",....
							String  separator = File.separator;
							
							int idx = fileItem.getName().lastIndexOf(separator);
							String fileName = fileItem.getName().substring(idx+1);
							
							
							
							//파일 저장 
							File uploadFile = new File(currenPath+separator+fileName);
							
							
							fileItem.write(uploadFile);
							
						}
					}
				
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
	}

}











