<%@page import="com.webjjang.main.controller.Service"%>
<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@page import="com.webjjang.member.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>   

<%
// session의 login에 id가 들어가 있기 때문에 거기서 가져오면 된다.
LoginVO loginVO = (LoginVO)session.getAttribute("login");
// null 이면 로그인이 안된상태 - 서버가 리스타트 되면 자동 로그아웃 된다.
System.out.println("view.jsp[loginVO]-" + loginVO);
// 로그인이 안되 었으면 login.jsp로 가라
if(loginVO == null) {
	response.sendRedirect("loginForm.jsp");
 return;
 
 }
 
String id = loginVO.getId();
System.out.println("/member/view.jsp [id] : " + id);

// 요청 url 가져오기
String url = request.getServletPath();
Service service = Beans.get(url);
System.out.println("/member/view.jsp [service] : " + service);

MemberVO vo = (MemberVO)ExeService.execute(Beans.get(url), id);
System.out.println("/member/view.jsp [vo] : " + vo);

// Html 안에 쉽게 사용하는 EL을 쓰려면 서버 저장객체에 넣어야 한다. 주로 사용하는 것이 , request 이다.
request.setAttribute("vo", vo);

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 정보 보기</title>

	<!-- 부트스트랩 라이브러리 등록 - CDN 방식 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  

</head>
<body>
<div class="container">
<h1>내 정보 보기</h1>
<table class="table">
	<tbody>
		<tr>
			<th>아이디</th>
			<td>${vo.id }</td>
		</tr>
		<tr>
			<th>이름</th>
			<td>${vo.name }</td>
		</tr>
		<tr>
			<th>성별</th>
			<td>${vo.gender }</td>
		</tr>
		<tr>
			<th>생년월일</th>
			<td>${vo.birth }</td>
		</tr>
		<tr>
			<th>연락처</th>
			<td>${vo.tel }</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${vo.email }</td>
		</tr>
		<tr>
			<th>등록일</th>
			<td>${vo.regDate }</td>
		</tr>
		<tr>
			<th>상태</th>
			<td>${vo.status }</td>
		</tr>
		<tr>
			<th>등급번호</th>
			<td>${vo.gradeNo }</td>
		</tr>
		<tr>
			<th>등급명</th>
			<td>${vo.gradeName }</td>
		</tr>
	</tbody>
		<tfoot>
		<tr>
			<td colspan="8">
				<a href="list.jsp" class="btn btn-default">리스트</a>
			</td>
		</tr>
	</tfoot>
	
</table>
</div>
</body>
</html>