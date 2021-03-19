<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="login_error.jsp" %>
<%
// 자바부분
// 로그인 정보를 가져오는데 성공하는 로그인 처리를 한다.
// 데이터 받기
String id = request.getParameter("id");
String pw = request.getParameter("pw");

// 한 개의 데이터를 넘겨야 하기 때문에 받은 데이터를 VO 객체에 저장 한다.
LoginVO vo = new LoginVO();
vo.setId(id);
vo.setPw(pw);

// DB 처리 - 아이디, 이름, 등급번호, 등급이름을 가져온다.
// JSP - service -dao


if(id.equals("test")){
	vo.setId(id);
	vo.setName("홍길동");
	vo.setGradeNo(1);
} else if(id.equals("admin")){
	vo.setId(id);
	vo.setName("박소현");
	vo.setGradeNo(9);
}else {
	throw new Exception(" 로그인 정보를 확인해 주세요.");
}
session.setAttribute("login", vo);
response.sendRedirect("main.jsp");
%>