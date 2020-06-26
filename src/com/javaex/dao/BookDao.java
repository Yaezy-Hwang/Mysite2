package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class BookDao {
	// 0. import java.sql.*;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private void getConnect() {
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

	public int insert(GuestVo vo) {

		getConnect();
		int count = 0;
		
		try {
			String query = "insert into guestbook values(seq_no.nextval, ?, ?, ?, sysdate)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());
			
			count = pstmt.executeUpdate();
			

		} catch(SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		return count;
	}
	
	public List<GuestVo> select() {
		List<GuestVo> gList = new ArrayList<>();
    	getConnect();
    	
		try {
			String query = "";
			query += "SELECT no,";
			query += "       name,";
			query += "       password,";
			query += "       content,";
			query += "       reg_date";
			query += " FROM guestbook";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
		    
		    // 4.결과처리
		    while(rs.next()) {
		    	int no = rs.getInt("no");
		    	String name = rs.getString("name");
		    	String pw = rs.getString("password");
		    	String content = rs.getString("content");
		    	String regDate = rs.getString("reg_date");
		    	
		    	//리스트에 추가
		    	GuestVo guestVo = new GuestVo(no, name, pw, content, regDate);
		    	gList.add(guestVo);
		    }
	
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		return gList;
	}
	
	public int delete(int no, String pw) {
		getConnect();
		int printResult = 0;
		try {
		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from guestbook";
			query += " where no=? and password = ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			pstmt.setString(2, pw);
			printResult = pstmt.executeUpdate();
		    
	    // 4.결과처리
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return printResult;
	}
	
}

