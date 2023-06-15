package com.member.mapper;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisManager {
	private static SqlSessionFactory instance;
	private MybatisManager() {}		// 생성자 (외부에서 생성할 수 없음) : 싱글톤
	
	// 생성자 대신 제공하는 메소드(클래스이름으로 메소드 접근: 클래스단위 메소드)
	public static SqlSessionFactory getInstance() {
		Reader reader = null;
		try {
			// mybatis환경 설정 읽음
			reader = Resources.getResourceAsReader("mapper/SqlConfigMapper.xml");
			// SqlSessionFactory객체를 생성
			instance = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return instance;
	}
}
