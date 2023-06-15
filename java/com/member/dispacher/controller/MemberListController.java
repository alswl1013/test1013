package com.member.dispacher.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.dispacher.Controller;
import com.member.dto.TMemberDTO;
import com.member.service.MemberService;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class MemberListController implements Controller {

	private MemberService memberService = MemberService.INSTANCE;
	
	public String handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		
		log.info("---요청한 서비스 호출");

		String action = req.getPathInfo();
	
		
		log.info("/list/list.do");
	
		
			try {
			
				List<TMemberDTO> memberList = memberService.memberList();
				
				log.info("memberList:"+memberList);
				
				req.setAttribute("memberList", memberList);
				
				
				
			    } catch (Exception e) {
				     e.printStackTrace();}
			 

				log.info("");
				 return "listMember";
	}

}
