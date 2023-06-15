package com.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.member.vo.TMemberVO;

@Mapper
public interface MembersqDivlMapper {

	//동적쿼리 추가
	

	@Select (MemberSQL.List_MEMBERS)
		public List<TMemberVO> selectAll();

	@SelectProvider(type = MemberSQL.class, method = "listMember")
	public List<TMemberVO> selectAll2();
	
	
	
	// java객체의 속성명과 매개변수 #{}부분의 필드명이 일치햐애 값이 전달됨,	
	//@InsertProvider(type=MembersqDivlMapper.class, method="insertMember")
	@Insert(MemberSQL.INSERT_MEMBER)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int addMember(TMemberVO vo);

	
	@Select("select * from t_member where id = #{id}")
	public TMemberVO memberSelectOne(String id);
	
	String update_sql ="""
			update t_member set pwd=#{pwd}, name=#{name}, email=#{email} 
	    where id =#{id}
			""";
			
	@Update(update_sql)	
	public int memberUpdate(TMemberVO vo);

	
	@Delete("delete from t_member where id= #{id}")
	public int memberDelete(@Param("id") String id);

	//ID중복 체크
	String CheckID="""
			select decode( count(*),1,'true','false') as isCheck  
			
			from t_member where id=?
			""";
	
	@Select(CheckID)
		public String checkID(@Param("id") String id);
	
	
	
		/*
		 * @Select (MemberSQL.FIND_MEMBERS) public List<TMemberVO> findMemberAll();
		 * 
		 * @SelectProvider(type = MemberSQL.class, method = "findMembersName") public
		 * List<TMemberVO> findMembersByName(@Param("name")String name);
		 */

}














/*
 * //마이바티스 프레임 워크 특징 SQL실행결과를 자바빈즈 또는 Map객체에 매핑해주는 Persustence솔루션으로 관리 (SQL용
 * 소스코드가 아닌 XML(Mapper 인터페이스 자바코드 )로 분리 SQL문과 프로그래밍 코드를분리해서 구현 데이터소스(DataSource)
 * 처리기능 제공
 */