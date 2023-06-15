package com.ex01.ajaxcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lombok.extern.log4j.Log4j2;



@Log4j2
@WebServlet("/json")
public class AjaxJsonTest extends HttpServlet {

	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	String jsonInfo = req.getParameter("jsonInfo");
	int age = Integer.parseInt(req.getParameter("age"));
	String name =req.getParameter("name");
	
	
	log.info("=====");
	System.out.println("=====");
	log.info("jsonInfo");
	System.out.println("jsonInfo");
	log.info("name");
	System.out.println("name");
	log.info("age");
	System.out.println("age");
	log.info("=====");
	System.out.println("=====");
	//객체생성
	JSONParser jsonParser = new JSONParser();
	
		//문자열을 JSon object로 변환
		try {
			JSONObject jsonObject =(JSONObject)jsonParser.parse(jsonInfo);

			log.info(jsonObject.get("name"));
			System.out.println("name");
			log.info(jsonObject.get("age"));
			System.out.println("age");
			log.info(jsonObject.get("gender"));
			System.out.println("gender");
			log.info(jsonObject.get("nickname"));
			System.out.println("nickname");
		
		
		} catch (ParseException e) {
		
			e.printStackTrace();
		}


		
	
	
	}
	
}
