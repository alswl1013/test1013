package com.member.dispacher;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;
@Log4j2
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{
	
	private HandlerMapping handlerMapping;
	private ViewResolver viewResolver;
	private  String ctxPath ;
	

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("=====service()");
		System.out.println("=====service()");
		
		
		//1.요청 uri분석 및 컨텍스트 경로 추출
		ctxPath = req.getContextPath();
		
		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));
		
		
	
		
		
		//Path에 해당되는 Controller검색(추출)
		Controller controller = handlerMapping.getController(path);

		
		//추출한Controller 실행
		String viewName = controller.handleRequest(req, resp);
			
		System.out.println("viewName:" +viewName);
		
		
		// viewResolver통해 View 
		String view = null;
		/*
		 * if(!viewName.contains(".do") ) { if(viewName.equals("index")) { view =
		 * viewName+".jsp";
		 * 
		 * }else { view = viewResolver.getView(viewName);
		 * 
		 * } }else { view = viewName; }
		 */
		log.info("view:" +view);
		
		System.out.println("view:" +view);
		
		viewName = controller.handleRequest(req, req);
		
		view = getNextViewPage(viewName);	
		
		//5.검색된 경로 view페이지로 이동하기
		RequestDispatcher dispatcher = req.getRequestDispatcher(view);
		dispatcher.forward(req, resp);
	
	}
	

	@Override
	public void init() throws ServletException {
		log.info("=====init()");
		System.out.println("=====init()");
		//url에 해당되는 Controller객체 생성
		handlerMapping = new HandlerMapping();
		//view 페이지이동을 위한 view페이지 사전 설정하는 객체  
		viewResolver = new ViewResolver();
		
		viewResolver.setPrefix(ctxPath+"./WEB-INF/member/");
		viewResolver.setSuffix(".jsp");
		
	}
	//viewName에서 이동할 페이지 경로 파일 및 최종 추출
		public String getNextViewPage(String viewName) {
			String view="";
			
			if(!viewName.contains(".do") ) {
				if(viewName.equals("index")) {
					view = viewName+".jsp";
					
				}else {
					view = viewResolver.getView(viewName);
					
				}
			}else {
				view = viewName;
			}
			
			
			
			
			return "";
		}
	
	
	}
