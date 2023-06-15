package com.board.service;

import org.modelmapper.ModelMapper;

import com.board.dao.TMemberDAO;
import com.board.domain.TMemberVO2;
import com.board.dto.TMemberDTO2;
import com.member.Util.MapperUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public enum MemberService {
	INSTANCE;
	
	private TMemberDAO dao;
	private ModelMapper modelMapper;
	
	private MemberService() {
		dao = new TMemberDAO();
		modelMapper = MapperUtil.INSTANCE.get();
	}
	
	// 로그인 서비스
	public TMemberDTO2 login(String id) throws Exception {
		
		TMemberVO2 vo = dao.findMember(id);
		TMemberDTO2 memberDTO = modelMapper.map(vo, TMemberDTO2.class);
		
		return memberDTO;
	}
	// 자동로그인 체크시 uuid에 임의문자열을 저장(수정)
	
	 public void updateUUid(String id, String uuid) throws Exception{
	 dao.updateUuid(id, uuid);
}
	 
	   // 자동 로그인 상태일 경우 쿠키값을 읽어 db정보을 추추하는 메서드
	 
          public TMemberDTO2 getByUUID(String uuid) throws Exception{ TMemberVO2 vo =
		  dao.selectUUID(uuid);
		  
		  TMemberDTO2 TMemberDTO2 = modelMapper.map(vo, TMemberDTO2.class); return TMemberDTO2
		  ; }
		 
	
}
