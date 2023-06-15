package com.board.testcontroller;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.board.dao.BoardDAO;


public class BoardDBConnectTest {

	private BoardDAO boardDAO;
	
	@Test
	public void testConnection() throws Exception {
			
			Class.forName("oracle.jdbc.OracleDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/xe",
					"user2",	
					"1234");
			Assertions.assertNotNull(conn);
		
		System.out.println("======");
		conn.close();
		
		}
	}

