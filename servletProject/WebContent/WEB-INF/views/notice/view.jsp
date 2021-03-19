<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.notice.vo.NoticeVO"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지 보기</title>

  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script type="text/javascript">
// 객체 선택에 문제가 있다. 아래  Document가 다 로딩이 되면 실행되는 스크립트 작성
// jquery -> $(function(){처리문 만들기;}) = jquery(function(){처리문 만들기;})
$(function(){ // jquery에서 익명함수를 전달해서 저장해놨다가 Document가 로딩이 되면 호출해서 처리한다.
	//삭제 버튼을 클릭하면 실제적으로 삭제를 진행할 건지에 대한 여부를 물어본다.
	$("#deleteBtn").click(function(){
		if(!confirm("정말로 삭제하시겠습니까?")) return false;
	});
});
</script>
</head>

<body>
<div class="container">
<h1>공지 글 보기</h1>
<table class="table">
	<tbody>
		<tr>
			<th>글 번호</th>
			<td class="no">${vo.no }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${vo.title }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><pre style="background: #fff; border: none; padding: 0px;">${vo.content }</pre></td>
		</tr>
		<tr>
			<th>공지 시작일</th>
			<td>${vo.startDate }</td>
		</tr>
		<tr>
			<th>공지 종료일</th>
			<td>${vo.endDate }</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${vo.writeDate }</td>
		</tr>
		<tr>
			<th>최근 수정일</th>
			<td>${vo.updateDate }</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="2">
				<a href="updateForm.do?no=${vo.no }&page=${pageObject.page }&perPageNum=${pageObject.perPageNum}" class="btn btn-default">공지수정</a>
				<a href="delete.do?no=${vo.no }&perPageNum=${pageObject.perPageNum}" class="btn btn-default"
				id="deleteBtn">공지 삭제</a>	
				<a href="list.do?page=${pageObject.page }&perPageNum=${pageObject.perPageNum}" class="btn btn-default">리스트</a>

			</td>
		</tr>
	</tfoot>
</table>
</div>
</body>
</html>