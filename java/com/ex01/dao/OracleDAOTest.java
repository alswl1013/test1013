package com.ex01.dao;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ex01.domain.TMemberVO;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class OracleDAOTest {
	
	private TMemberDAO dao;
	
	@BeforeEach
	public void read() {
		dao = new TMemberDAO();
	}
	@Test
	public void testTime() throws Exception{
		System.out.println(dao.getTime());
	}
	
	@Test
	public void testMemberListAll() throws Exception{
		List<TMemberVO> list =  dao.memberList();
		list.forEach(vo -> {
			//System.out.println(vo);
			log.info(vo);
			});
	}
	
	@Test
	public void testAddMember() throws Exception{
		// vo -> db전달
		TMemberVO vo = TMemberVO.builder()
				.id("test1")
				.pwd("1234")
				.name("홍길동테스트맨")
				.email("test1@gmail.com")
				.build();
		
		dao.addMember(vo);
	}
	@Test
	public void testSelectOne() throws Exception {
		String id = "dong";
		TMemberVO vo =  dao.findMember(id);
		
		log.info("findMember : "+ vo);
	}
	
	@Test
	public void testModifyMember() throws Exception{
		TMemberVO vo = TMemberVO.builder()
				.id("dong2")
				.pwd("2222")
				.name("DongSun2")
				.email("dong2@gmail.com")
				.build();
		
		int result = dao.modifyMember(vo);
		if (result!=0)
			log.info("수정완료 하였습니다.");
		else 
			log.info("수정작업 실패");
	}
	
	@Test
	public void testDeleteMember() throws Exception{
		String id = "test1";
		int result = dao.deleteMember(id);
		
		if (result==0) 
			log.info("삭제 실패");
		else 
			log.info("삭제되었습니다."); 
			
	}
	
	@Test
	public void UUID_Test() {
	String uuid_test = UUID.randomUUID().toString();
	log.info("\nUUID난수:"+uuid_test);
	

	

 }



}










