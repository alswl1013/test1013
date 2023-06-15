package com.ex01.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.board.dto.TMemberDTO2;
import com.ex01.dao.TMemberDAO;
import com.ex01.domain.TMemberVO;
import com.ex01.dto.TMemberDTO;
import com.member.Util.MapperUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public enum TMemberService {
	INSTANCE;
	
	private TMemberDAO tmemberDao;
	private ModelMapper modelMapper;
	
	private TMemberService() {
		tmemberDao = new TMemberDAO();
		modelMapper = MapperUtil.INSTANCE.get();
	}
	
	// 회원목록 서비스
	public List<TMemberDTO> memberList(){
		List<TMemberVO> memberList = tmemberDao.memberList();
		// vo-> dto
		List<TMemberDTO> dtoList = memberList.stream()
							.map(vo -> modelMapper.map(vo, TMemberDTO.class))
							.collect(Collectors.toList());
		return dtoList;
	}
	
	// 회원등록
	public int addMember(TMemberDTO dto) {
		TMemberVO vo = modelMapper.map(dto, TMemberVO.class);
		int result = tmemberDao.addMember(vo);
		
		return result;
	}
	// 회원조회
	public TMemberDTO findMember(String id) {
		TMemberVO vo = tmemberDao.findMember(id);
		TMemberDTO dto = modelMapper.map(vo, TMemberDTO.class);
		return dto;
	}
	// ID중복 체크
	public Boolean checkID(String id) {
		
		Boolean isCheck = tmemberDao.checkID(id);
		
		return isCheck;
	}	
	
	// 회원수정
	public int  modifyMember(TMemberDTO dto) {
		log.info("service dto:"+dto);
		int result = 0;
		
		TMemberVO vo = modelMapper.map(dto, TMemberVO.class);
		log.info("controller vo:"+vo);
		result = tmemberDao.modifyMember(vo);
		
		return result;// 0:비정상, 1:정상처리
	}
	// 회원삭제
	public int deleteMember(String id) {
		int result = 0;
		result = tmemberDao.deleteMember(id);
		
		return result;
	}

	

	TMemberDTO getByUUID(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}
	 

	
	
}










