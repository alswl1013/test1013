package com.ex01.ajaxcontroller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lombok.extern.log4j.Log4j2;


@Log4j2

@WebServlet("/jsonSend")
public class AjaxJsonSendTest extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//응답기능(JSON객체 생성하여 보내기)
		resp.setContentType("text.html;charset=utf-8");
		PrintWriter writer = resp.getWriter();
		
		
		JSONObject totalObject = new JSONObject();
		
		JSONArray memberArray = new JSONArray();
		
		JSONObject memberInfo = new JSONObject();
		
		//첫번째 JSON객체에 데이터 초기화
		memberInfo.put("name", "홍길동");
		memberInfo.put("age", "10");
		memberInfo.put("gender", "남자");
		memberInfo.put("nickname", "날센돌이");
		
		//배열에 추가
		memberArray.add(memberInfo);
		
		
		memberInfo = new JSONObject();
		//두번째 JSON객체에 데이터 초기화
		memberInfo.put("name", "동순이");
		memberInfo.put("age", "12");
		memberInfo.put("gender", "여자");
		memberInfo.put("nickname", "날쎈순이");
		
		memberArray.add(memberInfo);
		
		
		//배열객체에 저장
		totalObject.put("member", memberArray);
		
		String jsonInfo = totalObject.toJSONString();
		//클라이언트에게 전송하기(데이터보내기)
		
		writer.print(jsonInfo);
		
		
		log.info("totalObject:"+totalObject);
		System.out.println("totalObject:"+totalObject);
		log.info("jsonInfo:"+jsonInfo);
		System.out.println("jsonInfo:"+jsonInfo);
		
		
	
		
		
	}
}
