package com.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.modelmapper.ModelMapper;

import com.board.dao.TMemberDAO;

import com.member.Util.ConnectionOracleDBUtil;
import com.member.Util.MapperUtil;
import com.member.dto.TMemberDTO;
import com.member.mapper.MembersqlMapper;
import com.member.vo.TMemberVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
public enum MemberService {
	INSTANCE;
	

	private ModelMapper modelMapper;
	private SqlSessionFactory  sqlSessionFactory;
	private SqlSession session;
	private MembersqlMapper memberMapper;
	
	
			//생성자
			private MemberService() {
			
				
				try {
						modelMapper = MapperUtil.INSTANCE.get();
						sqlSessionFactory = ConnectionOracleDBUtil.INSTANCE.getSqlSessionFactory();
						session = sqlSessionFactory.openSession();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
			
			
			//회원목록서비스 
			public List<TMemberDTO> memberList() {
				
				
				 memberMapper = session.getMapper(MembersqlMapper.class);
				
				List<TMemberVO> memberList = memberMapper.selectAll();
				
				
				List<TMemberDTO>  dtoList = memberList.stream()
													.map(vo -> modelMapper.map(vo, TMemberDTO.class))
													.collect(Collectors.toList());
				
				
				log.info("mybatis mapper memberDTO:"+ dtoList);
				session.commit();
				return dtoList;
				
				
			
			}
			
			
				
			
			//회원등록서비스
			public int addMember(TMemberDTO dto) {
				
				memberMapper = session.getMapper(MembersqlMapper.class);
				
				TMemberVO vo = modelMapper.map(dto, TMemberVO.class);
				int  insertOK = memberMapper.addMember(vo);
				session.commit();
				
				return insertOK;
			}
			
			
			
				//회원 조회 서비스
			
			public TMemberDTO findMember(String id) {
				
				memberMapper = session.getMapper(MembersqlMapper.class);
				
				TMemberVO vo = memberMapper.memberSelectOne(id);
				TMemberDTO dto = modelMapper.map(vo, TMemberDTO.class);
				
				session.commit();
				return dto;
				
			}
			
				//회원 수정 서비스
			public int modifyMember(TMemberDTO dto) {
				
				memberMapper = session.getMapper(MembersqlMapper.class);
				int updateOK = 0;
				
				TMemberVO vo = modelMapper.map(dto, TMemberVO.class);
				updateOK = memberMapper.memberUpdate(vo);
				
				session.commit();
				return updateOK ;
				
				
			}
			
			
				
				
					//회원 삭제 서비스
			public int deleteMember(String id) {
				memberMapper = session.getMapper(MembersqlMapper.class);
				int deleteOK = 0;
				deleteOK = memberMapper.memberDelete(id);
				
				return deleteOK;
					
					
				
				
			}
			
			//ID중복 체크
			public Boolean checkID(String id) {
			
			memberMapper = session.getMapper(MembersqlMapper.class);
			String isCheck = memberMapper.checkID(id);
				
				return Boolean.parseBoolean(isCheck);
		    	}	
			}
			
			
			
			
			
				
			
				
			
