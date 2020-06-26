package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")
public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("./gbc --> doGet()"); //테스트 메세지
		String action = request.getParameter("action");
		
		if ("list".equals(action)){
			System.out.println("list");//테스트 메세지
			
			BookDao bookDao = new BookDao();
			List<GuestVo> gList = bookDao.select();
			
			//포워드 리퀘스트에 값 넣기
			request.setAttribute("guestlist", gList);
			
			//forword 하는 방법
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		
		} else if("insert".equals(action)) {
			System.out.println("insert");//테스트 메세지
			
			
			String name = request.getParameter("name");
			String pw = request.getParameter("password");
			String content = request.getParameter("content");
			
			BookDao bookDao = new BookDao();
			GuestVo gVo = new GuestVo(name, pw, content);
			bookDao.insert(gVo);

			WebUtil.redirect(response, "/mysite2/gbc?action=list");
			
		} else if("dform".equals(action)) {
			System.out.println("go deleteform");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			//포워드 리퀘스트에 값 넣기
			request.setAttribute("no", no);
			
			//forword 하는 방법
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		} else if("delete".equals(action)) {
			System.out.println("delete");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("password");
			
			BookDao bookDao = new BookDao();
			int result = bookDao.delete(no, pw);
			
			if(result==0) {//비밀번호 불일치
				System.out.println("불일치");
				
				WebUtil.redirect(response, "/mysite2/gbc?no="+no+"&action=dform&result=fail");
				
			} else { //비밀번호 일치
				System.out.println("비밀번호 일치");
				
				WebUtil.redirect(response, "/mysite2/gbc?action=list");
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
