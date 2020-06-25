package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {

		UserDao userdao = new UserDao();
		
		UserVo vo = new UserVo("id", "1234", "name", "female");
		userdao.insert(vo);
		
	}

}
