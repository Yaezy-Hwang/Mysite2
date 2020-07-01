<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->
		
		<c:import url="/WEB-INF/views/include/nav.jsp"></c:import>
		<!-- //nav -->

		<c:import url="/WEB-INF/views/include/asideBoard.jsp"></c:import>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="list">
					<form action="/mysite2/bc" method="get">
						<div class="form-group text-right">
							<input type="hidden" name="action" value="search">
							<input type="text" name="keyword" value="">
							<button type="submit" id=btn_search>검색</button>
						</div>
					</form>
					
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${requestScope.list}" var="vo" varStatus="status">
								<tr>
									<td>${vo.ro}</td>
									<td class="text-left"><a href="/mysite2/bc?no=${vo.no}&action=read">${vo.title}</a></td>
									<td>${vo.name}</td>
									<td>${vo.hit}</td>
									<td>${vo.date}</td>
									<td>
										<c:if test="${sessionScope.authUser.no eq vo.userNo}"> <!-- 로그인시 -->
											<a href="/mysite2/bc?no=${vo.no}&action=delete">[삭제]</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
		
					<div id="paging">
						<ul>
							<li><a href="/mysite2/bc?action=list&page=1">◀</a></li>
							<c:forEach items= "${requestScope.arr}" var="page">
								
									<li <c:if test="${param.page eq page}"> class="active" </c:if>>
										<a href="/mysite2/bc?action=list&page=${page}">${page}</a>
									</li>
								
							</c:forEach>
							<li><a href="">▶</a></li>
						</ul>
						
						
						<div class="clear"></div>
					</div>
					
					<c:if test="${!empty sessionScope.authUser}"> <!-- 로그인시 -->
						<a id="btn_write" href="/mysite2/bc?action=writeForm">글쓰기</a>
					</c:if>
				
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>
