package LoginController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.board.dao.TMemberDAO;
import com.board.dto.TMemberDTO2;
import com.board.service.MemberService;
import com.ex01.service.TMemberService;

//import com.ex01.dto.MemberDTO;
//import com.ex01.service.MemberService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	String nextPage ="";
	
	private Logger logger = LogManager.getLogger(LoginController.class);// 로그컨드롤에 대해서 작동시키겠다.
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("/login Get....한글");
	
		
		
	
		// 로그인 페이지 전환
		//req.getRequestDispatcher("login/login.jsp").forward(req, resp);
		
	
		nextPage ="/login/login.jsp	";
		resp.sendRedirect(req.getContextPath()+nextPage);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		log.info("/login Post....");
		
		
		req.setCharacterEncoding("utf-8");
	
		
		 String user_id = req.getParameter("user_id"); 
		 String user_pw =req.getParameter("user_pw");
		 String login_auto = req.getParameter("auto");
		 
		
		
		log.info(" user_id => "+user_id);
		log.info(" user_pw => "+user_pw);
		log.info(" login_auto => "+login_auto);
		
		
		
		
		// 클라이언트에서 json구조형식 데이터를 ajax()으로 보냈을 경우
		String logindata = req.getParameter("logindata");
		log.info("login data(JSON):" +logindata);
		
		JSONParser jsonParser =  new JSONParser();
		JSONObject jsonObject =  new JSONObject();
		
		 try {
			jsonObject  =(JSONObject) jsonParser.parse("longindata");
			log.info("login data:"+jsonObject);
			log.info("login data:"+jsonObject.get("user_id"));
			log.info("login data:"+jsonObject.get("user_pw"));
			log.info("login data:"+jsonObject.get("auto"));
			
	
		} catch (Exception e) {
			e.printStackTrace();}
		
		
		user_id =  (String) jsonObject.get("user_id");
		user_pw =  (String)  jsonObject.get("user_pw");
		login_auto = (String) jsonObject.get("auto");
		
		// 자동로그인 체크항목이 on일 경우 처리
		
		 boolean rememberMe = (login_auto != null) && (login_auto.equals("on"));
		  log.info("rememberMe status : "+rememberMe);
		  
		
		  
		  
		  
		  //  db에 회원여부 확인후 => 로그인 상태로 전환 => 세션작업
		  
		  TMemberDTO2 member = new TMemberDTO2();
		  String memberId ="";
		  String memberPwd= "";
          int isOK =0;
          
		  try {
		
			  TMemberDTO2 memberDto2 = MemberService.INSTANCE.login(user_id);
		
			  memberId = member.getId();
			  memberPwd = member.getPwd();
			  
	
		 
		
		  }catch (Exception e) {
			
		}
		 
		 if(memberId.equals(user_id)) {
			 
			 //아이디 일치
			 
			 if(memberPwd.equals(user_pw)) {
				 
				 //등록회원으로 로그인 승인
				 isOK =1;
				 
				 //자동 로그인 체크시 처리부분
				 if (rememberMe) {
						// rememberMe true면 난수발생 
					String uuid = UUID.randomUUID().toString();
						
				   //사용자가 로그인할때 임의의 문자열을 생성하고 이를 데이터베이스에 보관
						try {
							MemberService.INSTANCE.updateUUid(memberId, uuid);
						} catch (Exception e) {e.printStackTrace();
							member.setUuid(uuid);
							
						}
							
			
				  // DB에 저장된 난수와 같은 문자열을 쿠키값 생성 Cookie rememberCookie = new
				  Cookie rememberCookie = new Cookie("remember-me",uuid);  
						  rememberCookie.setMaxAge(7*24*60*60);//쿠키 유효기간7일
						  rememberCookie.setPath("/");
				 
				//   클라이언트에게 쿠키전송 resp.addCookie(rememberCookie); }
				  	}
				  
				 // HttpSession session = req.getSession(); session.setAttribute("loginInfo",
				//  memeberDto);
				  
				//  resp.sendRedirect(req.getContextPath()+"/todo/list");
				  
			// } catch (Exception e) {
			//  resp.sendRedirect(req.getContextPath()+"/login?result=error");
			  				 
				
				 HttpSession session =req.getSession();
				 session.setAttribute("loginInfo",member.getName());

					
				 
				
			 }else {
				 //아이디 일치, 패스워드 불일치
				 isOK =2;
			 }
		 }else {
			 //아이디 불일치 
			 isOK=-1;
		 }
		 
		 //클라이언트에게 메세지 전송 응답
		 //1.text 타입으로 응답
			//resp.setContentType("text/plane; charset=utf-8");
			
		 //2.html 타입으로 응답
		 resp.setContentType("text/html; charset=utf-8");
			PrintWriter pw = resp.getWriter();
			
			//JSONArray messageArray = new JSONArray(); 
			JSONObject sendData =new JSONObject();
			//sendData.put(key, value);
		
			log.info("isOK:"+isOK);
			if (isOK == 1) {
				
			} else if (isOK == 2 ) {
				pw.print("비밀번호가 일치하지 않습니다.");
				sendData.put("code","pw_fail");
				sendData.put("message","비밀번호가 일치하지않습니다");
			}else if(isOK== -1) {
				
				sendData.put("code","id_fail");
				sendData.put("message","비밀번호가 일치하지않습니다");
				pw.print(sendData);
			}else {
				resp.sendRedirect(req.getContextPath()+"/login");
				
			
			}
			

	

		  
		  // 자동로그인 체크시 처리부분 
			if (rememberMe) {
				// rememberMe true면 난수발생 
				String uuid =
		  UUID.randomUUID().toString();
		  }
		  // 사용자가 로그인할때 임의의 문자열을 생성하고 이를 데이터베이스에 보관
	//	  MemberService.INSTANCE.updateUuid(user_id, uuid); memeberDto.setUuid(uuid);
		  
		  // DB에 저장된 난수와 같은 문자열을 쿠키값 생성 Cookie rememberCookie = new
		//  Cookie("remember-me", uuid); rememberCookie.setMaxAge(7*24*60*60);//쿠키 유효기간7일
		//   rememberCookie.setPath("/");
		 
		  // 클라이언트에게 쿠키전송 resp.addCookie(rememberCookie); }
		  
		  
		 // HttpSession session = req.getSession(); session.setAttribute("loginInfo",
		//  memeberDto);
		  
		 // resp.sendRedirect(req.getContextPath()+"/todo/list");
		  
	//  } catch (Exception e) {
	 // resp.sendRedirect(req.getContextPath()+"/login?result=error");
	//  
	  }
		 
  }
 

	


/*
쿠키와 세션을 같이 활용하기

자동 로그인 준비
 - 사용자가 로그인할때 임의의 문자열을 생성하고 이를 데이터베이스에 보관
 - 쿠키에는 생성된 문자열을 값으로 삼고 유효기간은 1주일로 지정

로그인 체크 방식
 - 현재 사용자의 HttpSession에 로그인 정보가 없는 경우에만 쿠키를 확인
 - 쿠키의 값과 데이터베이스의 값을 비교하고 같으면 사용자의 정보를 읽어와서 
   HttpSession에 사용자 벙보를 추가




*/


