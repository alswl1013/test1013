package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.dto.TMemberDTO;
import com.member.service.MemberService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet("/tmember/insert.do")

public class MemberRegisterController extends HttpServlet {

	private MemberService memberService = MemberService.INSTANCE;
	
	String action = null;
	String nextPage =null;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
		//입력폼
		action = req.getPathInfo();
		nextPage ="/Member/memberForm.jsp";
		req.getRequestDispatcher(nextPage).forward(req, resp);
	 
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
	 //insert db처리
	
		TMemberDTO dto = new TMemberDTO();
		dto.setName(req.getParameter("name"));
		dto.setId(req.getParameter("id"));
		dto.setPwd(req.getParameter("pwd"));
		dto.setEmail(req.getParameter("email"));
		
		int result = memberService.addMember(dto);
		
		nextPage ="/list/list.do";
		resp.sendRedirect(req.getContextPath()+nextPage);
		
	
		
	
	
	}
	

}