package com.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.dto.TMemberDTO;
import com.member.service.MemberService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet("/tmember/list.do")
public class MemberListcontroller extends HttpServlet {

	private MemberService memberService = MemberService.INSTANCE;
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
		
		
			try {
			
				List<TMemberDTO> memberList = memberService.memberList();
				
				log.info("memberList:"+memberList);
				
				req.setAttribute("memberList", memberList);
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			nextPage ="/Member3/list.jsp";
			req.getRequestDispatcher(nextPage).forward(req, resp);
			
		
		
		

	
	

    }
}
