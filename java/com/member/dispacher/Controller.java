package com.member.dispacher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.filters.ExpiresFilter.XHttpServletResponse;

public interface Controller {
//뷰페이지 용
		String handleRequest(HttpServletRequest req, HttpServletRequest req);

		

	

		
}
