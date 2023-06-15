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
@WebServlet("/tmember/view.do")
public class MemberViewController extends HttpServlet {

		private MemberService memberService = MemberService.INSTANCE;
		private String nextPage="";
		
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			 doHandler(req, resp);
		
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			 doHandler(req, resp);		
		
		}
		
		protected void doHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			String id = req.getParameter("id");
			
			
			TMemberDTO memberDTO =  memberService.findMember(id);
			req.setAttribute("member", memberDTO);//뷰에 보여질 데이터를 저장
			
			
			nextPage ="/Member/viewMember.jsp";//req.getContextPath()+
			req.getRequestDispatcher(nextPage).forward(req, resp);
			
			 
		
		}
}
