package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	
	String forwardPath = "/WEB-INF/views/user";
	String goMain = "/mysite2/main";
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) {
			System.out.println("joinForm");
			
			WebUtil.forward(request, response, forwardPath+"/joinForm.jsp");
		} else if("join".equals(action)) {
			System.out.println("join");
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo(id, password, name, gender);
			UserDao dao = new UserDao();
			dao.insert(vo);
			
			WebUtil.forward(request, response, forwardPath+"/joinOk.jsp");
			
		} else if("loginForm".equals(action)) {
			WebUtil.forward(request, response, forwardPath+"/loginForm.jsp");
			
		} else if("login".equals(action)) {
			System.out.println("login");
			
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			UserDao userdao = new UserDao();
			UserVo authVo = userdao.getUser(id, password);
			
			if(authVo==null) {//로그인 실패
				System.out.println("로그인실패");
				
				WebUtil.redirect(response, "/mysite2/user?action=loginForm&result=fail");
				
			} else { //로그인 성공
				//세션영역에 값을 추가
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);
				
				WebUtil.redirect(response, goMain);
			}
			
		} else if("logout".equals(action)) {// 로그아웃
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(response, goMain);
			
		} else if("modifyForm".equals(action)) {
			System.out.println("모디파이도착");
			
			WebUtil.forward(request, response, forwardPath+"/modifyForm.jsp");
			
		} else if("modify".equals(action)) {
			System.out.println("수정성공");
			
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserDao dao = new UserDao();
			UserVo authVo = dao.modify(id, password, name, gender);
			
			//세션영역에 값을 추가
			HttpSession session = request.getSession();
			session.setAttribute("authUser", authVo);
			
			WebUtil.redirect(response, goMain);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
