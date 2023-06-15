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
@WebServlet("/tmember/update.do")
public class MemberUpdateController extends HttpServlet{

	 private MemberService memberService;
	 private String nextPage="";

	 public MemberUpdateController() {
		 memberService = MemberService.INSTANCE;
	 }
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    log.info("url:"+ req.getContextPath()+","+memberService);
    

    String id =req.getParameter("id");
	// 수정하고자하는 정보 요청
    
	TMemberDTO memberDTO =  memberService.findMember(id);
	req.setAttribute("member", memberDTO);
	
	// 수정페이지로 전달
			nextPage ="/Member/modifyMember.jsp";
			req.getRequestDispatcher(nextPage).forward(req, resp);
}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.info("db수정요청");
		TMemberDTO dto = new TMemberDTO();
		dto.setName(req.getParameter("name"));
		dto.setId(req.getParameter("id"));
		dto.setPwd(req.getParameter("pwd"));
		dto.setEmail(req.getParameter("email"));
		
		// 수정작업 서비스 요청(DB)
		int result = memberService.modifyMember(dto);
		
		nextPage = "/list/list.do";
		resp.sendRedirect(req.getContextPath()+nextPage);
		

		
		
		
		
		
		
}

}
