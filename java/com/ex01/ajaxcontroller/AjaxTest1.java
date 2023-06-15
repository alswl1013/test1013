package com.ex01.ajaxcontroller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;


@Log4j2
@WebServlet("/ajaxTest1")
public class AjaxTest1 extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doHandler(req, resp);
	
}

@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			doHandler(req, resp);
	
}

protected void doHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			//한글 인코딩
		//req.setCharacterEncoding("utf-8");
		

	
		String data = req.getParameter("param");
		log.info("ajax get: " +data);
		System.out.println("ajax get: " +data);
		//클라이언트에게 응답(데이터 전송)
	
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print("안녕하세요 ! 서버입니다.");
		

	}
}






