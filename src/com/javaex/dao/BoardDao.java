package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.ListVo;

public class BoardDao {

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

	private void close() {
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

	public List<ListVo> select(int page) {
		List<ListVo> lList = new ArrayList<>();
    	getConnect();
    	
		try {
			String query = "";
			query += "SELECT ro, no, title, hit, reg_date, user_no, name ";
			query += " FROM (SELECT ROWNUM ro, no, title, hit, reg_date, user_no, name ";
			query += "       FROM (SELECT b.no no, b.title title, b.hit hit, ";
			query += " 			   to_char(b.reg_date,'yy-mm-dd hh24:mi') reg_date, ";
			query += " 			   b.user_no user_no, u.name name ";
			query += " 		 From board b left outer join users u on b.user_no =  u.no ";
			query += " 		 order by no desc) ";
			query += " 		 where ROWNUM <= ?) ";
			query += " where ro >= ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, (1+(page-1)*5)+4);
			pstmt.setInt(2, 1+(page-1)*5);
			
			rs = pstmt.executeQuery();
		    
		    // 4.결과처리
		    while(rs.next()) {
		    	int ro = rs.getInt("ro");
		    	int no = rs.getInt("no");
		    	String title = rs.getString("title");
		    	int hit = rs.getInt("hit");
		    	String regDate = rs.getString("reg_date");
		    	int userNo = rs.getInt("user_no");
		    	String name = rs.getString("name");
		    	
		    	//리스트에 추가
		    	ListVo vo = new ListVo(ro, no, title, hit, regDate, userNo, name);
		    	lList.add(vo);
		    }
	
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		return lList;
	}
	
	public ListVo read(int no){
    	getConnect();
    	ListVo vo = null;
    	
		try {
			conn.setAutoCommit(false);
			
			hitUp(no);
			
			String query = "";
			query += "SELECT b.no,";
			query += "       u.name,";
			query += "       b.hit,";
			query += "       to_char(b.reg_date, 'yyyy-mm-dd') reg_date,";
			query += "       b.title,";
			query += "       b.content,";
			query += "       b.user_no";
			query += " FROM board b left outer join users u";
			query += " ON b.user_no = u.no";
			query += " where b.no = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			// 4.결과처리
			rs = pstmt.executeQuery();
		    while(rs.next()) {
		    	String name = rs.getString("name");
		    	int hit = rs.getInt("hit");
		    	String regDate = rs.getString("reg_date");
		    	String title = rs.getString("title");
		    	String content = rs.getString("content");
		    	int userNo = rs.getInt("user_no");
		    	
		    	vo = new ListVo(no, name, hit, regDate, title, content, userNo);
		    }
		    
		    conn.commit();
	
		} catch (SQLException e1) {
		    System.out.println("error:" + e1);
		    try {
		    	System.out.println("게시물 읽기 오류");
		    	conn.rollback();
		    	
		    } catch(SQLException e2) {
		    	e2.printStackTrace();
		    }
		}
		
		close();
		return vo;
	}

	public void modify(int no, String title, String content) {
		getConnect();
		
		try {
			String query = "";
			query += " update board ";
			query += " set title = ?,";
			query += "     content   = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query); 
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, no);
			
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}
	
	public void write(String title, String content, int no) {
		getConnect();
		
		System.out.println(title+content+no);
		
		try {
			String query = "insert into board values(seq_board_no.nextval, ?, ?, 0, sysdate, ?)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, no);
			
			pstmt.executeUpdate();

		} catch(SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		
	}
	
	public void delete(int no) {
		getConnect();
		try {
		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from board";
			query += " where no=?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		    
	    // 4.결과처리
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
	}
	
	public void hitUp(int no) {
		getConnect();
		
		try {
			String query = "";
			query += " update board ";
			query += " set hit= hit+1";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query); 
			
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		//close() 하면 안됨! 오류남!!
	}

	public List<ListVo> select(String keyword){
		List<ListVo> resultList = new ArrayList<>();
    	getConnect();
    	
		try {
			String query = "";
			query += " SELECT rownum ro, no, title, hit, reg_date, user_no, name ";
			query += " FROM (select b.no no, b.title title, b.hit hit, ";
			query += " 		to_char(b.reg_date,'yy-mm-dd hh24:mi') reg_date, ";
			query += " 		b.user_no user_no, u.name name ";
			query += "		from board b left outer join users u on b.user_no =  u.no";
			query += " 		order by b.no desc) ";
			query += " Where title like ? ";
			query += " order by rownum asc";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, '%'+keyword+'%');
			
			rs = pstmt.executeQuery();
		    
		    // 4.결과처리
		    while(rs.next()) {
		    	int ro = rs.getInt("ro");
		    	int no = rs.getInt("no");
		    	String title = rs.getString("title");
		    	int hit = rs.getInt("hit");
		    	String regDate = rs.getString("reg_date");
		    	int userNo = rs.getInt("user_no");
		    	String name = rs.getString("name");
		    	
		    	//리스트에 추가
		    	ListVo vo = new ListVo(ro, no, title, hit, regDate, userNo, name);
		    	resultList.add(vo);
		    }
	
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		return resultList;
	}
	
	public int count(String keyword) {
		getConnect();
		int no=0;
		
		try {
			String query = "SELECT count(no) no FROM board WHERE title like ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, '%'+keyword+'%');
			
			rs = pstmt.executeQuery();
		    
		    // 4.결과처리
		    while(rs.next()) {
		    	no = rs.getInt("no");
		    }
	
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		return no;
		
	}
}
