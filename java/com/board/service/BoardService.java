package com.board.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.board.dao.BoardDAO;
import com.board.domain.BoardVO2;
import com.board.dto.BoardDTO2;
import com.member.Util.MapperUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public enum BoardService {
	
    INSTANCE;	
	
	private BoardDAO boardDAO;
	private ModelMapper modelMapper;
	
	  
    private BoardService() {
		
          boardDAO = new BoardDAO();
	      modelMapper = MapperUtil.INSTANCE.get();
		
	  }
	
public List<BoardDTO2> boardList( Map<String, Integer> pageingMap){
		
		
		// 게시글 목록
		List<BoardVO2> bordList = boardDAO.boardList(pageingMap);
		
		// VO -> DTO
		List<BoardDTO2> dtoList = bordList.stream()
						.map(vo -> modelMapper.map(vo, BoardDTO2.class))
						.collect(Collectors.toList());
		
		return dtoList;
	};
	
	
	  //게시글 총 갯수 
	public int selectTotArticles() {
		int totArticles = boardDAO.selectTotArticles();
		return totArticles;
	}
	
	//게시글 조회
	public BoardDTO2 selectArticleOne(int articleNO) {

		 BoardVO2 vo = boardDAO.selectArticleOne(articleNO);
		 BoardDTO2 dto = modelMapper.map(vo, BoardDTO2.class);
		
		  return dto;
		
	}

		    


//게시글 등록

public int insertArticleNO(BoardDTO2 dto) {
	
	int articleNO =boardDAO.getNewArticleNO();
	
	dto.setArticleNO(articleNO);
	
	
	BoardVO2 vo = modelMapper.map(dto, BoardVO2.class);
	int rs =boardDAO.insertNewArticle(vo);
	
	return rs;
	
}

//게시글 수정
public int updateArticleNO(BoardDTO2 dto) {
	
	BoardVO2  vo =modelMapper.map(dto, BoardVO2.class);
	int rs = boardDAO.updateArticle(vo);
	return rs;
	
}





//게시글 삭제 
public List<Integer> deleteArticleNO(int articleNO) {
    //특정 글번호 기준으로 자식 게시글 추출(이미지 첨부파일 폴더를 찾아서 삭제하기 위해)
	
	List<Integer> articleNOList = boardDAO.selectRemoveArticles(articleNO);//부모로 쓰고 있는현재게시글
	
	
	// db에 있는 특정 글 게시글 번호 삭제 (답글게시글 포함)
	int rs =boardDAO.deleteArticle(articleNO);
	
	return articleNOList;
}









}//main


