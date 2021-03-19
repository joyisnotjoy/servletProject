<%@page import="com.webjjang.board.vo.BoardVO"%>
<%@page import="com.webjjang.board.service.BoardWriteService"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//순수한 자바 코드 부분입니다.---------------

// 한글처리
request.setCharacterEncoding("utf-8");

// 1. 데이터 수집
String strNo = request.getParameter("no");
long no = Long.parseLong(strNo);

// 2. DB처리를 한다. -> delete.jsp
String url = request.getServletPath();
Integer result = (Integer)ExeService.execute(Beans.get(url), no);

//처리를 한 후에 자동 리스트로 페이지 이동시킴.
response.sendRedirect("list.jsp");
%>
