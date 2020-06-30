package com.javaex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.ListVo;

@WebServlet("/bc")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		BoardDao boardDao = new BoardDao();
		int no;
		
		System.out.println("/bc --> doGet()"); //테스트 메세지
		String action = request.getParameter("action");
		
		if ("list".equals(action)){
			System.out.println("게시판으로 이동");//테스트 메세지
			
			List<ListVo> lList = boardDao.select();
			
			//포워드 리퀘스트에 값 넣기
			request.setAttribute("list", lList);
			
			//forword 하는 방법
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		
		} else if("read".equals(action)) {
			System.out.println("게시물 클릭");
			
			no = Integer.parseInt(request.getParameter("no"));
			
			boardDao.hitUp(no);
			ListVo readVo = boardDao.read(no);
			
			//리퀘스트에 게시글 정보 넣기
			request.setAttribute("readvo", readVo);
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		} else if("modifyForm".equals(action)) {
			System.out.println("수정폼으로 이동");
			
			no = Integer.parseInt(request.getParameter("no"));
			
			ListVo vo = boardDao.read(no);
			
			// 리퀘스트에 값 넣기
			request.setAttribute("modifyvo", vo);
			//포워드 하는 방법
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		} else if("modify".equals(action)) {
			System.out.println("수정");
			
			int listNo = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			boardDao.modify(listNo, title, content);
			
			WebUtil.redirect(response, "/mysite2/bc?action=list");
			
		} else if("writeForm".equals(action)) {
			System.out.println("글쓰기폼");
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		} else if("write".equals(action)) {
			System.out.println("글쓰기");
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int userNo = Integer.parseInt(request.getParameter("userno"));
			
			boardDao.write(title, content, userNo);
			
			WebUtil.redirect(response, "/mysite2/bc?action=list");
			
		} else if("delete".equals(action)) {
			System.out.println("삭제");
			
			int listNo = Integer.parseInt(request.getParameter("no"));
			boardDao.delete(listNo);
			
			WebUtil.redirect(response, "/mysite2/bc?action=list");
			
		} else if("search".equals(action)) {
			String keyword = request.getParameter("keyword");
			//전체 리스트 가져오기
			List<ListVo> lList = boardDao.select();
			//결과 담을 리스트
			List<ListVo> resultList = new ArrayList<>();
			
			for(ListVo vo: lList) {
				if(vo.getTitle().contains(keyword)){
					resultList.add(vo);
				}//if
				
	    	}

				//포워드 리퀘스트에 값 넣기
			request.setAttribute("list", resultList);
			
			//forword 하는 방법
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
			
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}