package com.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.dto.TMemberDTO;

import lombok.extern.log4j.Log4j2;



@Log4j2
@WebServlet("/tmember/checkID.do")
public class MemberIDcheckcontroller extends HttpServlet {
	
	@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		doHandler(req, resp);
		}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
	}
	
	protected void doHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
		
		String action = req.getPathInfo();
		String nextPage = null;
		
		log.info("/list/list.do");
		//log.info("getPathInfo(): "+action);
		
		
	}
}
