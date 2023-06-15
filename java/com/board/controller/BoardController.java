package com.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;

import com.board.dto.BoardDTO2;
import com.board.service.BoardService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet("/boardlist/*")
public class BoardController extends HttpServlet{
	
	// 글에 첨부한 이미지 저장위치
	private static String ARTICLE_IMAGE_REPO = "d:\\file_repo";
	
	// 게시글 서비브 객체 생성
	private BoardService boardService = BoardService.INSTANCE;

	//session 부모글 보관 
	HttpSession session;

	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
	}
	protected void doHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("---  요청 테스트 ");
		// 공통된 경로를 제외한 url을 작업구분자로 적용
		String action = req.getPathInfo();
		String nextPage ="";
	

		String _section = req.getParameter("pageBlock");
		String _pageNum = req.getParameter("pageNum");
		
		log.info("section:" +_section);
		log.info("page Number"+_pageNum);
		
		int section =Integer.parseInt((_section == null) ? "1" : _section);
		int pageNum =Integer.parseInt((_pageNum == null) ? "1" : _pageNum);
			
	
		
		Map<String,Integer> pageingMap = new HashMap<>();
		pageingMap.put("section", section);
		pageingMap.put("pageNum",pageNum);
		
	
		// action : 목록, 등록, 조회, 삭제,...
		if (action==null || action.equals("/list.do")) {
			try {
					
				List<BoardDTO2> boardList= boardService.boardList(pageingMap);
				
				// 게시글 총 개수
			 int totArticles =boardService.selectTotArticles();
				
				// 총페이지 블럭계산(1블록: 10개 페이지)
				// 총블럭 = 총페이수/10(블럭묶음단위) + 1
				// 991페잊/10페이지 = 99.1블럭 => (99.1)+1 => 100.1 => 100(정수만 처리)
				
				// 전체 페이지수 = 전체레코드수/10(1page: 10개묶음) + 1
				int totalPage = (int)Math.ceil(totArticles*1.0/10); // 소수점이하기 있으면 자리올림(10.1=>11)
				int totalPageBlock =(int)Math.ceil(totalPage*1.0/10);
				
				log.info("totArticles:"+totArticles);
				log.info("totalPage:"+totalPage);
				log.info("totalPageBlock:"+totalPageBlock);
				
				
				 // 마지막 페이지 계산 : 현재페이지 카운터에서 마지막페이지 초과하지않게 설정
				 // 	페이지계산은 1-10형식으로 반복처리됨.
				 int lastPage = 1;
				 for (int i=1; i<=10; i++){
					 // 블럭에 대한 페이지번호 생성
					int endPage = (section-1)*10 + i;
					 
					log.info("("+section+"-1)*10) + "+i+": "+(endPage));
					
					if (pageNum==i) {
						log.info("현재페이지와 동일 번호: "+i);
					}
					
					if (endPage <=  totalPage){
						
						lastPage = i;
					}
				 }
				 log.info("section: "+section);
				 log.info("lastPage: "+lastPage);
				
				
				
//				articlesMap.put("section", section);
//				articlesMap.put("pageNum", pageNum);
				
				
				// mybatis 적용 : xml 또는 java query문 연결
				//List<BoardDTO> boardList = boardService.boardList3(pageingMap);
			
			 req.setAttribute("list", boardList);
				
			 
				
			 req.setAttribute("totArticles", totArticles);
				req.setAttribute("section", section);
				req.setAttribute("pageNum", pageNum);
				req.setAttribute("totSection", totalPageBlock);
				// 페이지 블럭에서 마지막페이지 값 보관
				req.setAttribute("lastPage", lastPage);
			
			} catch (Exception e) {
				
			}

			nextPage ="/board/listboard.jsp";
			req.getRequestDispatcher(nextPage).forward(req, resp);
			
			} else if (action.equals("/viewArticle.do")) {
		
				int articleNO = Integer.parseInt( req.getParameter("articleNO"));
				// 서비스 요청
				try {
				BoardDTO2 article = boardService.selectArticleOne(articleNO);
				
				
				req.setAttribute("dto", article);
				
				req.setAttribute("section", section);
				req.setAttribute("pageNum", pageNum);
			
			} catch (Exception e) {
			}
			
				nextPage ="/board/viewBoard.jsp";
				req.getRequestDispatcher(nextPage).forward(req, resp);
			
		    } else if (action.equals("/register.do")) { // 입력 폼
			
//			   int articleNO = Integer.parseInt( req.getParameter("articleNO"));
			// 서비스 요청
			   
			
			
			   	nextPage ="/board/boardform.jsp";
			   	resp.sendRedirect(req.getContextPath()+nextPage);
			
		    } else if (action.equals("/insert.do")) { // db 입력
		    	log.info("=== insert.do ");
		    	BoardDTO2 dto = new BoardDTO2();
			
//			업로드 기능이 없을 경우 처리
//			dto.setId(req.getParameter("id")) ;
//			dto.setTitle(req.getParameter("title"));
//			dto.setContent(req.getParameter("content"));
//			dto.setImageFileName(req.getParameter("imageFileName"));
			
			// 업로드 기능이 있을 경우 처리
			Map<String, String> articleMap = upload(req, resp);// 업로드기능 호출
			
			dto.setId(articleMap.get("id")) ;
			dto.setTitle(articleMap.get("title"));
			dto.setContent(articleMap.get("content"));
			dto.setImageFileName(articleMap.get("imageFileName"));
			log.info(dto);
			
			// db에 게시글 등록 서비스 요청
			try {
				int isOK = boardService.insertArticleNO(dto);
				
				// 첨부파일 경우만 처리
				if (dto.getImageFileName()!= null && dto.getImageFileName().length() != 0) {
					// temp폴더에 임시로 보관된 파일경로 설정
					File srcFile = 
						new File(ARTICLE_IMAGE_REPO+"\\"+"Temp"+"\\"+dto.getImageFileName());
					
					//  ARTICLE_IMAGE_REPO 하위 경로에 글 번호 폴더를 생성: "d:\\file_repo\글번호폴더
					File descFile = new File(ARTICLE_IMAGE_REPO+"\\"+dto.getArticleNO());
					descFile.mkdirs();
					
					//temp폴더의 이미지 첨부파일을 글번호이름으로하는 폴더로 이동
					FileUtils.moveFileToDirectory(srcFile, descFile, true);
					
					
				}
				
				// 클라이언트에게  메시지 전송 응답
				resp.setContentType("text/html; charset=utf-8");
				PrintWriter pw = resp.getWriter();
				
				log.info("isOK:"+isOK);
				if (isOK != 0) {
					pw.print("<script>" +
								"alert('새글 등록되었습니다.'); "+
								"location.href='" + 
								 req.getContextPath()+"/boardlist/list.do';"+
							"</script>"
							);
					
				}else {
					pw.print("<script>" +
							"alert('글을 등록할 수 없습니다.');"+
							"location.href='"+req.getContextPath()+"/boardlist/list.do';"+
						"</script>"
						);
				}
				
			} catch (Exception e) {}
			
			return;
			
			
			//nextPage ="/boardlist/list.do";
			//resp.sendRedirect(req.getContextPath()+nextPage);
			
		} else if (action.equals("/modify.do")) {// 수정 폼
			BoardDTO2 dto = null;
			
			// view페이지에서 넘겨받은 글번호을 가지고 db에접속
			//req.setCharacterEncoding("utf-8");
			
			int articleNO =  Integer.parseInt( req.getParameter("articleNO")) ;
			
		
			
			
			// db에 게시글 조회 서비스 요청
			try {
				// 수정할 데이터 요청
				dto = boardService.selectArticleOne(articleNO);
				
				// 수정할 데이터 저장
				req.setAttribute("dto", dto);
				
			} catch (Exception e) {}
			
			log.info("=== 수정폼: "+dto);
			
			
			// 수정 페이지 요청
			nextPage ="/board/modifyform.jsp";
			req.getRequestDispatcher(nextPage).forward(req, resp);
			
		    } else if (action.equals("/update.do")) {// db 수정
		
			// 필터에서 인코딩 안될 경우 명시적으로 선언
			req.setCharacterEncoding("utf-8");
			BoardDTO2 dto = new BoardDTO2();
			
//			dto.setArticleNO( Integer.parseInt( req.getParameter("articleNO")) );
//			dto.setTitle(req.getParameter("title"));
//			dto.setContent(req.getParameter("content"));
//			dto.setImageFileName(req.getParameter("imageFileName"));
			
			
			// 업로드 기능이 있을 경우 처리
			Map<String, String> articleMap = upload(req, resp);// 업로드기능 호출
			
			// 게시글 번호 
			// request객체의 정보는 file upload라이브러러로 통해 데이터를 추출해야함.
			// form => "enctype="multipart/form-data" => request작동안됨
			// 데이터읽어올수 없음: int articleNO = Integer.parseInt(req.getParameter("articleNO"));
			
			dto.setArticleNO(Integer.parseInt(articleMap.get("articleNO")));
			dto.setId(articleMap.get("id")) ;
			dto.setTitle(articleMap.get("title"));
			dto.setContent(articleMap.get("content"));
			dto.setImageFileName(articleMap.get("imageFileName"));

			log.info("before update:"+dto);
			
			// db에 게시글 등록 서비스 요청
			try {
				
				// db 수정 서비스 요청
				int isOK = boardService.updateArticleNO(dto);
				
				log.info("== update.do :"+dto+", isOK="+isOK);
				
				// 첨부파일 경우만 처리
				if (dto.getImageFileName()!= null && dto.getImageFileName().length() != 0) {
					// temp폴더에 임시로 보관된 파일경로 설정
					File srcFile = 
						new File(ARTICLE_IMAGE_REPO+"\\"+"Temp"+"\\"+dto.getImageFileName());
					
					//  ARTICLE_IMAGE_REPO 하위 경로에 글 번호 폴더를 생성: "d:\\file_repo\글번호폴더
					File descFile = new File(ARTICLE_IMAGE_REPO+"\\"+dto.getArticleNO());
					descFile.mkdirs();
					
					//temp폴더의 이미지 첨부파일을 글번호이름으로하는 폴더로 이동
					FileUtils.moveFileToDirectory(srcFile, descFile, true);
					
					
					// ----------------- //
					// 추가 : 수정전 이미지 삭제 
					// ----------------- //
					// request객체의 정보는 file upload라이브러러로 통해 데이터를 추출해야함.
					String originalFileName = articleMap.get("originalFileName");
					File oldFile = 
							new File(ARTICLE_IMAGE_REPO+"\\"+dto.getArticleNO()+"\\"+originalFileName);
					oldFile.delete();
					
				}
				
				// 클라이언트에게  메시지 전송 응답
				resp.setContentType("text/html; charset=utf-8");
				PrintWriter pw = resp.getWriter();
				
				log.info("isOK:"+isOK);
				if (isOK != 0) {
					pw.print("<script>" +
								"alert('게시글 수정되었습니다.'); "+
								"location.href='" + 
								 req.getContextPath()+"/boardlist/list.do';"+
							"</script>"
							);
					
				}else {
					pw.print("<script>" +
							"alert('게시글글을 수정할 수 없습니다.');"+
							"location.href='"+req.getContextPath()+"/boardlist/list.do';"+
						"</script>"
						);
				}
				
			} catch (Exception e) {}
			
			return;
			
		} else if (action.equals("/delete.do")) {// db 수정
			
			int articleNO =  Integer.parseInt( req.getParameter("articleNO"));
			
			log.info("=== articleNO: "+articleNO);
			
			int isOk = 0;
			int isOK2 =0;
			
			List<Integer> articleNOList = null;
			// db 삭제 서비스 요청
			try {
				articleNOList = boardService.deleteArticleNO(articleNO);
				isOk= 1;
			} catch (Exception e) {}
			
			// 이미지폴더 및 파일 삭제
			for(int _articleNO : articleNOList) {
				
				File imgDir = new File(ARTICLE_IMAGE_REPO+"\\"+_articleNO);//번호 폴더 삭제  요청
				
				if (imgDir.exists()) {// 폴더가 존제하면 폴더삭제
					FileUtils.deleteDirectory(imgDir);
				}
			}
			
			// 삭제 성공 메시지 클라이언트에게 보내기
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter pw = resp.getWriter();
			
			
			  log.info("isOK:"+isOk);
			  
			  if (isOk!=0) { pw.print("<script>" +
			  "alert('"+articleNO+"번 게시글 삭제되었습니다.'); "+ "location.href='" +
			  req.getContextPath()+"/boardlist/list.do';"+ "</script>" );
			  
			  }else { pw.print("<script>" + "alert('"+articleNO+"번 게시글 삭제 거부!!!');"+
			  "location.href='"+req.getContextPath()+"/boardlist/list.do';"+ "</script>" );
			  }
			 
			
			return;
			
//			nextPage ="/board/listboard.jsp";
//			req.getRequestDispatcher(nextPage).forward(req, resp);
//			
		 } else if (action.equals("/reply.do")) {
			int parentNO = Integer.parseInt(req.getParameter("parentNO"));
//			int articleNO = Integer.parseInt( req.getParameter("articleNO"));
			// 서비스 요청
			
			//session =req.getSession();
			//session = setAttribute("parentNO",parentNO);
			
			req.setAttribute("parentNO", parentNO);
			nextPage ="/boardlist/replyform.jsp";
			
			req.getRequestDispatcher(nextPage).forward(req, resp);
			//resp.sendRedirect(req.getContextPath()+nextPage);
	
		 } else if (action.equals("/insert.do")) { // db 입력
			log.info("=== insert.do ");
			BoardDTO2 dto = new BoardDTO2();
			
//			업로드 기능이 없을 경우 처리
//			dto.setId(req.getParameter("id")) ;
//			dto.setTitle(req.getParameter("title"));
//			dto.setContent(req.getParameter("content"));
//			dto.setImageFileName(req.getParameter("imageFileName"));
			
			// 업로드 기능이 있을 경우 처리
			Map<String, String> articleMap = upload(req, resp);// 업로드기능 호출
			
			dto.setId(articleMap.get("id")) ;
			dto.setParentNO(Integer.parseInt(articleMap.get("parentNO")));
			dto.setTitle(articleMap.get("title"));
			dto.setContent(articleMap.get("content"));
			dto.setImageFileName(articleMap.get("imageFileName"));
			log.info(dto);
			
			// db에 게시글 등록 서비스 요청
			try {
				int isOK = boardService.insertArticleNO(dto);
				
				// 첨부파일 경우만 처리
				if (dto.getImageFileName()!= null && dto.getImageFileName().length() != 0) {
					// temp폴더에 임시로 보관된 파일경로 설정
					File srcFile = 
						new File(ARTICLE_IMAGE_REPO+"\\"+"Temp"+"\\"+dto.getImageFileName());
					
					//  ARTICLE_IMAGE_REPO 하위 경로에 글 번호 폴더를 생성: "d:\\file_repo\글번호폴더
					File descFile = new File(ARTICLE_IMAGE_REPO+"\\"+dto.getArticleNO());
					descFile.mkdirs();
					
					//temp폴더의 이미지 첨부파일을 글번호이름으로하는 폴더로 이동
					FileUtils.moveFileToDirectory(srcFile, descFile, true);
					
					
				}
				
				// 클라이언트에게  메시지 전송 응답
				resp.setContentType("text/html; charset=utf-8");
				PrintWriter pw = resp.getWriter();
				
				log.info("isOK:"+isOK);
				if (isOK != 0) {
					pw.print("<script>" +
								"alert('새글 등록되었습니다.'); "+
								"location.href='" + 
								 req.getContextPath()+"/boardlist/list.do';"+
							"</script>"
							);
					
				}else {
					pw.print("<script>" +
							"alert('글을 등록할 수 없습니다.');"+
							"location.href='"+req.getContextPath()+"/boardlist/list.do';"+
						"</script>"
						);
				}
				
			} catch (Exception e) {}
			
			return;
			
			
			
		} else {
			try {
				
				List<BoardDTO2> boardList= boardService.boardList(pageingMap);
				
				// 게시글 총 개수
			 int totArticles =boardService.selectTotArticles();
				
				// 총페이지 블럭계산(1블록: 10개 페이지)
				// 총블럭 = 총페이수/10(블럭묶음단위) + 1
				// 991페잊/10페이지 = 99.1블럭 => (99.1)+1 => 100.1 => 100(정수만 처리)
				
				// 전체 페이지수 = 전체레코드수/10(1page: 10개묶음) + 1
				int totalPage = (int)Math.ceil(totArticles*1.0/10); // 소수점이하기 있으면 자리올림(10.1=>11)
				int totalPageBlock =(int)Math.ceil(totalPage*1.0/10);
				
				log.info("totArticles:"+totArticles);
				log.info("totalPage:"+totalPage);
				log.info("totalPageBlock:"+totalPageBlock);
				
				
				 // 마지막 페이지 계산 : 현재페이지 카운터에서 마지막페이지 초과하지않게 설정
				 // 	페이지계산은 1-10형식으로 반복처리됨.
				 int lastPage = 1;
				 for (int i=1; i<=10; i++){
					 // 블럭에 대한 페이지번호 생성
					int endPage = (section-1)*10 + i;
					 
					log.info("("+section+"-1)*10) + "+i+": "+(endPage));
					
					if (pageNum==i) {
						log.info("현재페이지와 동일 번호: "+i);
					}
					
					if (endPage <=  totalPage){
						
						lastPage = i;
					}
				 }
				 log.info("section: "+section);
				 log.info("lastPage: "+lastPage);
				
				
				
//				articlesMap.put("section", section);
//				articlesMap.put("pageNum", pageNum);
				
				
				// mybatis 적용 : xml 또는 java query문 연결
				//List<BoardDTO> boardList = boardService.boardList3(pageingMap);
			
			 req.setAttribute("list", boardList);
				
			 
				
			 req.setAttribute("totArticles", totArticles);
				req.setAttribute("section", section);
				req.setAttribute("pageNum", pageNum);
				req.setAttribute("totSection", totalPageBlock);
				// 페이지 블럭에서 마지막페이지 값 보관
				req.setAttribute("lastPage", lastPage);
			
			} catch (Exception e) {
				
			}

			nextPage ="/boardlist/listboard.jsp";
			req.getRequestDispatcher(nextPage).forward(req, resp);
		}
		
	}// end doHandle()
	
	// 이미지 업로드 메서드 선언
	private Map<String, String> upload(
						HttpServletRequest req,
						HttpServletResponse resp) throws ServletException, IOException {
		
		log.info("=== upload() ");
		
		Map<String, String> articleMap = new HashMap<String, String>();
		String encoding = "utf-8";
		
		// 문자열-> 시스템 파일로 변환
		File currentPath = new File(ARTICLE_IMAGE_REPO);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentPath);
		factory.setSizeThreshold(1024*1024);// 1024byte = 1KB * 1024 = 1MB
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		log.info("ServletFileUpload :"+upload);
		try {
			// request에 저장되어 있는 매개변수(정보)를 List에 저장
			log.info("before List<FileItem> :");
			List<FileItem> items = upload.parseRequest(req);
			log.info("after List<FileItem> :"+items);
			
			for(int i=0; i<items.size(); i++ ) {
				
				FileItem fileItem = items.get(i);
				if (fileItem.isFormField()) {// form요소이면
					log.info(fileItem.getFieldName()+"="+fileItem.getString(encoding));
					
					// 게시글 등록 => title, content,...
					articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				}else {
					// file객체이면 처리
					log.info("파라미터이름:"+fileItem.getFieldName());
					log.info("파일이름:"+fileItem.getName());
					log.info("파일크기:"+fileItem.getSize()+"bytes");
					
					articleMap.put(fileItem.getFieldName(), fileItem.getName());
					
					if (fileItem.getSize() > 0) {
						// 운영체제별 파일 경로 기호 추출 : "/" , "\\",..
						String separator = File.separator;
						
						
						// "d:\abc\ccc\image.jpg"
						int idx = fileItem.getName().lastIndexOf(separator);
						if (idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}
						
						String fileName = fileItem.getName().substring(idx+1);
						// currentPath = "d:\\file_repo",
						//File uploadFile = new File(currentPath +separator+ fileName);
						// "d:\\file_repo\temp\파일" 
						File uploadFile = new File(currentPath +separator+"Temp"+separator+ fileName);
						
						// 파일시스템으로 전환된 파일경로+파일이름을 업로드 실행
						fileItem.write(uploadFile);
						
					}// end if (file size)
				}// end if (file객체 구분)
			}// end for (form 요소 불러오기)
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return articleMap;
	}
}









