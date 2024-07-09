package com.tenco.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {
	// DBUtil에서 META-INF 의 context.xml 파일에 있는 것을 당겨 쓴다.
	
//	private static DataSource dataSource;
//	
//	// 정적 초기화 블록
//	static {
//				// TODO - 삭제 예정
//				System.out.println("11111111111111111111111");
//		try {
//			// InitialContext 객체를 생성하여 JNDI API 기술을 통해 존재하는 리소스를 찾는 방법
//			InitialContext ctx = new InitialContext(); // 객체 생성
//			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
//			// java:comp/env --> 루트 
//			// jdbc/MyDB --> META-INF 의 context.xml 안에 있는 name을 가지고 왔다. 
//			
//		} catch (NamingException e) {
//			e.printStackTrace();
//		} 
//		
//	}
//	
//	public static Connection getConnection() throws SQLException {
//		return dataSource.getConnection();
//		
//	}
//	

}
