<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@page import="com.webjjang.message.vo.MessageVO"%>
<%@page import="com.webjjang.board.vo.BoardVO"%>
<%@page import="java.util.List"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>   
<%
// 여기가 자바 코드입니다. JSP - Service - DAO -> /board/view.do

// 넘어오는 데이터 받기 - 글번호
String strNo = request.getParameter("no");
// 내 아이디 정보를 꺼내야 한다.
String id= ((LoginVO) session.getAttribute("login")).getId();

// vo객체 생성 - 데이터 세팅
MessageVO vo = new MessageVO();
vo.setNo(Long.parseLong(strNo));
vo.setAccepter(id);// 받는 사람이 나인 데이터를 읽기 표시 하기 위해서

// DB처리 데이터 가져오기
// 1. 받은 사람이 로그인한 사람과 같아야 하고 번호가 같아야 하고(받은 메시지) 받은 날짜가 null인 메시지를 (읽지 않음) 
//읽음 표시 한다.(acceptDate를 현재 날짜로 넣어준다.-update)
//2. 메시지 번호에 맞는 전체 메시지 정보 가져오기
MessageVO viewVO = (MessageVO)ExeService.execute(Beans.get(AuthorityFilter.url), vo);

// 서버 객체에 저장
request.setAttribute("vo", viewVO);
String url = "/message/view.do"; // 현재 URL과 다르므로 강제 세팅한다.
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메시지 보기</title>
  
</head>
<body>
<div class="container">
<h1>메시지 보기</h1>
<table class="table">
	<tbody>
		<tr>
			<th>번호</th>
			<td class="no">${vo.no }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><pre style="background: #fff; border: none; padding: 0px;">${vo.content }</pre></td>
		</tr>
		<tr>
			<th>보낸사람</th>
			<td>${vo.sender }</td>
		</tr>
		<tr>
			<th>보낸날짜</th>
			<td>${vo.sendDate }</td>
		<tr>
			<th>받은사람</th>
			<td>${vo.accepter}</td>
		</tr>
		<tr>
			<th>받은날짜</th>
			<td>${vo.acceptDate }</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="2">
				<c:if test="${! (vo.sender == login && !empty vo.acceptDate) }">
				<a href="delete.do?no=${vo.no }" class="btn btn-default">삭제</a>
				</c:if>
				<a href="list.do" class="btn btn-default">리스트</a>
			</td>
		</tr>
	</tfoot>
</table>
</div>
</body>
</html>