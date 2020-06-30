package com.javaex.dao;

import java.util.List;

import com.javaex.vo.GuestVo;
import com.javaex.vo.ListVo;
import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {

		UserDao userDao = new UserDao();
		
		//UserVo vo = new UserVo("id", "1234", "name", "female");
		//userDao.insert(vo);
		//userDao.modify(1, "1234", "박이름", "male");
		//System.out.println(vo.toString());
		
		UserVo u1 = userDao.getUser(7);
		System.out.println(u1.toString());
		UserVo u2 = userDao.getUser("id", "1234");
		System.out.println(u2.toString());
		
		
		///////////////////////////////////////////////////
		
		BookDao bDao = new BookDao();
		//GuestVo gVo = new GuestVo("유이름", "1234", "왔다감미당!");
		bDao.delete(5, "1234");
		
		//bDao.insert(gVo);
		
		List<GuestVo> gList = bDao.select();
		
		//bDao.delete(3, "1234");
		for(GuestVo vo: gList) {
			System.out.println(vo.toString());
		}
		
		//////////////////////////////////////////////////////
		
		BoardDao boardDao = new BoardDao();
		//boardDao.write("제목쓰", "내용쓰", 1);
		
		boardDao.modify(7, "수정제목쓰", "수정내용쓰");
		boardDao.delete(7);
		
		

		List<ListVo> lList = boardDao.select();
		for(ListVo vo: lList) {
			System.out.println(vo.toString());
		}
		
		
		
		
		
	}

}
