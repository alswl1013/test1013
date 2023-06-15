package com.member.Util;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.member.mapper.MembersqDivlMapper;
import com.member.mapper.MembersqlMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.log4j.Log4j2;

@Log4j2
public enum ConnectionOracleDBUtil {

	INSTANCE;
	
	private HikariDataSource ds;
	
	private ConnectionOracleDBUtil(){
		
		HikariConfig config = new HikariConfig();
		
		
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521/xe");
		config.setUsername("user2");
		config.setPassword("1234");
		
		
		config.addDataSourceProperty("cachePrepStmts", true);
		config.addDataSourceProperty("prepStmtCachesize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		 
		
		
		ds = new HikariDataSource(config);

	
	}
	public Connection getConnection()throws Exception{
		return ds.getConnection();
	
	 }
	
//----------------------------------------------------------------//	
	public SqlSessionFactory getSqlSessionFactory() {
		
		DataSource hDs= ds;
		
		log.info("hikariDataSource : "+hDs); 
		
		TransactionFactory  transactionFatory = new JdbcTransactionFactory();
		
		Environment enviroment = new Environment("development", transactionFatory, hDs);
		Configuration configuration = new Configuration(enviroment);
	
		
		configuration.addMapper(MembersqlMapper.class);
		//configuration.addMapper(MembersqDivlMapper.class);
		
		
		
		
		log.info("SqlSessionFactory:" + new SqlSessionFactoryBuilder().build(configuration));
		
		return new SqlSessionFactoryBuilder().build(configuration);
	
	
    }
	 

	
	
	//-----------------------------------------------------------//
	
	
	// iBatis(XML)
//	public SqlSessionFactory getSqlSessionFactoryXML() {
//		
//		Reader reader = null;
//		try {
//			reader = Resources.getResourceAsReader("mapper/SqlConfigMapper.xml");
//		} catch (Exception e) { e.printStackTrace();
//		}
//		finally {
//			try {
//				if(reader != null) reader.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		
//		log.info("SqlSessionFactoryXML"+new SqlSessionFactoryBuilder().build(reader));
//		return new SqlSessionFactoryBuilder().build(reader);
//	}
	
}
