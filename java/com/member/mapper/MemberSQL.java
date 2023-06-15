package com.member.mapper;

import org.apache.ibatis.jdbc.SQL;

public class MemberSQL {

	//1회원목록
	public static final String List_MEMBERS = 
				
		"""
		
		select * from t_member
		
		""";
	//1-1
	//public static final String List_MEMBERS ="select * from t_member";
	//1-2

	public String listMember() {
		return new SQL() {{
			SELECT("*");
			FROM("t_member");
		}}.toString();
			
		}
	
	//회원등록
	
	public String insertMember() {
		  String sql = new SQL()
		    .INSERT_INTO("t_member")
		    .VALUES("id, pwd,name,email", "#{id}, #{pwd}, #{name}")
		    .VALUES("email", ",#{email}")
		    .toString();
		  return sql;
		}
	
	public static final String INSERT_MEMBER =
			 """
			 insert into)
			""";
			
	
	
	
	
	
	
	//조건검색
	
	public String findMembersName(String name) {
		StringBuilder query = new StringBuilder();
		
		query.append("select * from t_member ");
		
//		if(name != null && name.length() > 0) {
//			query.append(" where ");
//			query.append("name like "+name);
//		}
//		
//		return query.toString();
		
		return new SQL() {{
			SELECT("*");
			FROM("t_member");
		WHERE("name like #{name} || '%'");
		ORDER_BY(name);
        	}}.toString();
		
//		return "";
		
	}
	
}