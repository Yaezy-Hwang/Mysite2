package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
	// 0. import java.sql.*;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	//회원 추가
	public int insert(UserVo vo) {
		int count = 0;
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " INSERT INTO users ";
			query += " VALUES (seq_users_no.nextval, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate(); // 쿼리문 실행
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return count;
	}
	
	public UserVo getUser(String id, String password) {//로그인한 사용자 정보 가져오기
		UserVo vo = null;
		getConnection();
		
		try {
			String query = "";
			query += " select  no, ";
			query += "         name, ";
			query += "         gender ";
			query += " from    users";
			query += " where id= ? ";
			query += " and password= ? ";
			
			pstmt = conn.prepareStatement(query); 
			
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				vo = new UserVo(no, id, password, name, gender);
			}
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return vo;
	}
	
	public void modify(int no, String password, String name, String gender) {
		UserVo vo = null;
		getConnection();
		
		try {
			String query = "";
			query += " update users ";
			query += " set name = ?,";
			query += "     password     = ?,";
			query += "     gender   = ? ";
			query += " where no= ? ";
			
			pstmt = conn.prepareStatement(query); 
			
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.setString(3, gender);
			pstmt.setInt(4, no);
			
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}
	
	public UserVo getUser(int no) {//로그인한 사용자 정보 가져오기
		UserVo vo = null;
		getConnection();
		
		try {
			String query = "";
			query += " select  no, ";
			query += "         id, ";
			query += "         password, ";
			query += "         name, ";
			query += "         gender ";
			query += " from    users";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query); 
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int rNo = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				vo = new UserVo(rNo, id, password, name, gender);
			}
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return vo;
	}
	
	
	
}
