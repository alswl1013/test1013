package com.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.board.domain.BoardVO2;
import com.board.util.ConnectionOracleUtil;

import lombok.Cleanup;

public class BoardDAO {


	public List<BoardVO2> boardList(Map<String,Integer> pageingMap){
		int section = pageingMap.get("section");
		int pageNum = pageingMap.get("pageNum");
		
		

		
		List<BoardVO2> boardList = new ArrayList<>();
		
		System.out.println(8);
		
		try {
			String sql_backup = "select level, "
					+ "articleNO, "
					+ "parentNO, "
					+ "title, "
					+ "content, "
					+ "writeDate, "
					+ "id "
					+ "from t_board "
					+ "start with parentNO=0 "
					+ "connect by prior articleNO = parentNO "
					+ "order siblings by articleNO Desc ";
			//jdk버전 17이상
		
			String sql =
					
					"""
					select * 
							from (
							
						select
							rownum as  recNum,
							LVL,
							articleNO,
							parentNO,
							 title,
							content,
							writeDate,
							 id
							
							from (
							
							    select
							    level as LVL,
							    articleNO,
							    parentNO,
							    title,
							    content,
							    writeDate,
							    id
							
							     from t_board
							     start with parentNO =0
							     connect by prior articleNO = parentNO
							     order siblings by articleNO Desc
							    )
							   )
							      where recNum between (?-1)*100+(?-1)*10+1 and  (?-1)*100+ (?)*10 
							    
						        """;
			@Cleanup Connection conn = ConnectionOracleUtil.INSTANCE.getConnection();
			   
			@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
			    
		
				 pstmt.setInt(1, section);
			     pstmt.setInt(2, pageNum);
			     pstmt.setInt(3, section);
			     pstmt.setInt(4, pageNum);
			     
			@Cleanup ResultSet rs = pstmt.executeQuery();
			      System.out.println(11);
			
			while(rs.next()) {
		
				 BoardVO2 vo = BoardVO2.builder()
						.level(rs.getInt("LVL"))
						.articleNO(rs.getInt("articleNO"))
						.ParentNO(rs.getInt("parentNO"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.writeDate(rs.getDate("writeDate").toLocalDate())
						.id(rs.getString("id"))
						.build();
				
				 boardList.add(vo);
				 
				   
				 
	}//while
	
					
	}catch (Exception e) {}
	
	   return boardList;
	   
	   
		
		
		
	}
	// 1-1. 게시글 전체 개수 
		public int selectTotArticles() {
				String sql = "select count(articleNO) from t_board ";
				
				try {
					@Cleanup Connection conn = ConnectionOracleUtil.INSTANCE.getConnection();
					@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
					@Cleanup ResultSet rs = pstmt.executeQuery();
					
					if (rs.next()) {
						return (rs.getInt(1));
					}
				
				} catch (Exception e) {}
				
				return 0;
			}




	

	

//  2.게시글 등록시 게시글번호 생성하기
  public int getNewArticleNO() {
	String sql="select max(articleNO) from t_board ";
	try {
	
		
	@Cleanup Connection conn = ConnectionOracleUtil.INSTANCE.getConnection();
	@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
	@Cleanup ResultSet rs = pstmt.executeQuery();
	
	if(rs.next()) {
		return(rs.getInt(1)+1);
	}
	
	
	} catch (Exception e) {}
		
		return 0;
	}
	
	
	



//3.게시글 등록
public int insertNewArticle(BoardVO2 boardvo) {
	int isOk= 0;
	
	String sql="insert into t_board(articleNO, parentNO, title,content, imageFileName, id) "
			+ "values(?, ?, ?, ?, ?, ?)";
			
	try {
	
		
		@Cleanup Connection conn = ConnectionOracleUtil.INSTANCE.getConnection();
		@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
	
		pstmt.setInt(1, boardvo.getArticleNO());
		pstmt.setInt(2, boardvo.getParentNO());
		pstmt.setString(3, boardvo.getTitle());
		pstmt.setString(4, boardvo.getContent());
		pstmt.setString(5, boardvo.getImageFileName());
		pstmt.setString(6, boardvo.getId());
	

	
		isOk =pstmt.executeUpdate();
	
		} catch (Exception e) {}
		
			return isOk;	
		}
	
//3. 게시글 조회

	public BoardVO2 selectArticleOne(int articleNO) {
		BoardVO2 vo =  null;


		String sql= "select * from t_board where articleno = ?";
		
		try {
		
		@Cleanup Connection conn = ConnectionOracleUtil.INSTANCE.getConnection();
		@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, articleNO);
		
		@Cleanup ResultSet rs = pstmt.executeQuery();
			
		
		
		rs.next();
			vo = BoardVO2.builder()
				.articleNO(rs.getInt("articleNO"))
				.ParentNO(rs.getInt("parentNO"))
				.title(rs.getString("title"))
				.content(rs.getString("content"))
				.imageFileName(rs.getString("imageFileName"))
				.id(rs.getString("id"))
				.writeDate(rs.getDate("writeDate").toLocalDate())
				.build();
		
		
		} catch (Exception e) {}
				
	
		return vo;
	
	
		}

	

//4.게시글 수정
	public int updateArticle(BoardVO2 vo) {
	
			int isOk= 0;
	
   String sql= """ 
		update t_board set title =?, content =?, imagefilename=?
			where articleno=?
	""";
   	
        try {
	
	
        	@Cleanup Connection conn = ConnectionOracleUtil.INSTANCE.getConnection();
        	@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
	
        	pstmt.setString(1, vo.getTitle());
        	pstmt.setString (2, vo.getContent());
        	pstmt.setString(3, vo.getImageFileName());
        	pstmt.setInt(4, vo.getArticleNO());
	
        	isOk =pstmt.executeUpdate();
		
	} catch (Exception e) {}	

	
    return isOk;
	}

//5-1.게시글 삭제(DB정보 삭제)

	public int deleteArticle(int articleNO) {

		int isOk = 0;
	
		String sql=    " delete from t_board where articleno in("+
		       " select articleNO from t_board  " + ////부모로 쓰고 있는 현재게시글
			   " start with parentNO=?     "+                  //번호지정이 되지않아 물음표로 지정 
			   " connect by prior articleNO = parentNO )";     //부모노드를 쓰고있는 레코드(자식게시글)이 있는지 검색 ->부모로 삼고있는 자식게시글 검색(뽑인 레코드번호 대상으로 또 검색 뽑힌기준으로 계속 검색이 되어서 삭제됨계층구조 트리구조) 
			
		try {
			
			@Cleanup Connection conn = ConnectionOracleUtil.INSTANCE.getConnection();
			@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
	
			pstmt.setInt(1, articleNO);
	
			isOk =pstmt.executeUpdate();
	
			} catch (Exception e) {}
	

			return isOk;
	
	
			}

//5-2.게시글 삭제(이미지파일)

	public List<Integer> selectRemoveArticles(int articleNO){
	
		List<Integer> articleNOList = new ArrayList<>();
   
		int isOk =0;

		String sql=    
	       " select articleNO from t_board  " +
		   " start with parentNO=?     "+                  //번호지정이 되지않아 물음표로 지정 
		   " connect by prior articleNO = parentNO )";     //부모노드를 쓰고있는 레코드(자식게시글)이 있는지 검색 ->부모로 삼고있는 자식게시글 검색(뽑인 레코드번호 대상으로 또 검색 뽑힌기준으로 계속 검색이 되어서 삭제됨계층구조 트리구조) 
		
		try {
				@Cleanup Connection conn = ConnectionOracleUtil.INSTANCE.getConnection();
				@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
	
				pstmt.setInt(1, articleNO);
	
				@Cleanup ResultSet rs = pstmt.executeQuery();
	
				while(rs.next()) {

					articleNO = rs.getInt("articleNO");
		
		
						articleNOList.add(articleNO);
				}

				} catch (Exception e) {}

				return articleNOList;
				}
		

















	

	
	

}//main


