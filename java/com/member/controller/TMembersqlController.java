package com.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ex01.service.TMemberService;
import com.member.dto.TMemberDTO;
import com.member.service.MemberService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet("/mem/*")
public class TMembersqlController  extends HttpServlet{
	// 서비스 객체를 생성(dao, modelMapper)
	//private TMemberService memberService = TMemberService.INSTANCE;
	private MemberService memberService = MemberService.INSTANCE;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
	}
	protected void doHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 공통된 경로를 제외한 url을 작업구분자로 적용
		String action = req.getPathInfo();
		String nextPage = null;
		
		log.info("/mem/*");
		log.info("getPathInfo(): "+action);
		
		if (action == null || action.equals("/list.do")) {
			try {
				List<TMemberDTO> memberList = memberService.memberList();
				log.info("memberList:"+memberList);
				req.setAttribute("memberList", memberList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			nextPage ="/Member/list.jsp";
			req.getRequestDispatcher(nextPage).forward(req, resp);
			
			
			/*
			
		} else if (action.equals("/viewMember.do")) {
			String id = req.getParameter("id");
			
			TMemberDTO memberDTO =  memberService.findMember(id);
			req.setAttribute("member", memberDTO);//뷰에 보여질 데이터를 저장
			
			nextPage ="/Member/viewMember.jsp";//req.getContextPath()+
			req.getRequestDispatcher(nextPage).forward(req, resp);
			
		} else if (action.equals("/memberForm.do")) {
			// 회원등록 폼 요청시
			nextPage ="/Member/memberForm.jsp";
			req.getRequestDispatcher(nextPage).forward(req, resp);
			
		}else if (action.equals("/checkId.do")) {
			String id = req.getParameter("id");
			
			// id중복체크 서비스 요청
			boolean isCheck = memberService.checkID(id);		
			if (isCheck) {
				log.info("이미 사용중인 아이디입니다. => "+isCheck);
			} else {
				log.info("사용할 수 있는 아이디입니다.=> "+isCheck);
			}
			
			// 클라이언에게 응답
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter writer = resp.getWriter();
			if (isCheck) {
				writer.print("not_usable");
			}else {
				writer.print("usable");
			}
		}else if (action.equals("/insertMember.do")) {
			// 입력할 데이터 전달 받기(아이디, 비번, 이름, 이메일)
			TMemberDTO dto = new TMemberDTO();
			dto.setName(req.getParameter("name"));
			dto.setId(req.getParameter("id"));
			dto.setPwd(req.getParameter("pwd"));
			dto.setEmail(req.getParameter("email"));
			
			// 회원정보등록 서비스 요청
			int result = memberService.addMember(dto);
			
			nextPage ="/member/listMember.do";
			resp.sendRedirect(req.getContextPath()+ nextPage);
			
		}else if (action.equals("/modifyMember.do")) {
			String id = req.getParameter("id");
			
			// 수정하고자하는 정보 요청
			TMemberDTO memberDTO =  memberService.findMember(id);
			req.setAttribute("member", memberDTO);
			
			// 수정페이지로 전달
			nextPage ="/Member/modifyMember.jsp";
			req.getRequestDispatcher(nextPage).forward(req, resp);
			
		}else if (action.equals("/updateMember.do")) {
			// 수정할 데이터 전달 받기(아이디, 비번, 이름, 이메일)
			TMemberDTO dto = new TMemberDTO();
			dto.setName(req.getParameter("name"));
			dto.setId(req.getParameter("id"));
			dto.setPwd(req.getParameter("pwd"));
			dto.setEmail(req.getParameter("email"));
			
			log.info("controller dto:"+dto);
			// 수정작업 서비스 요청(DB)
			int result = memberService.modifyMember(dto);
			
			nextPage ="/member/listMember.do";
			resp.sendRedirect(req.getContextPath()+ nextPage);
			
		}else if (action.equals("/deleteMember.do")) {
			String id = req.getParameter("id");
			int result = memberService.deleteMember(id);
			
			// 삭제서비스 요청
			nextPage ="/member/listMember.do";
			resp.sendRedirect(req.getContextPath()+ nextPage);
			
		} else {
			
			List<TMemberDTO> memberList = memberService.memberList();
			req.setAttribute("memberList", memberList);
			nextPage ="/Member/listMember.jsp";
			req.getRequestDispatcher(nextPage).forward(req, resp);
		}
		
		
		*/
		
		
		// 1.포워딩: request객체에 값을 연속성. 데이터를보관해서 페이지이동
		//req.getRequestDispatcher(nextPage).forward(req, resp);
		
		// 2. request객체에 값을 연속성 없이 사용할경우, 단순히 페이지이동(전달 전송기능없음)
		//resp.sendRedirect(req.getContextPath()+nextPage);
	}
}

}







