<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.message.vo.MessageVO"%>
<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 자바
// 넘어오는 데이터 수집 - 번호
String strNo = request.getParameter("no");
Long no = Long.parseLong(strNo);

// db 처리 : jsp - service - dao - db
// ExeService.execute(실행할 Service, Service에 전달되는 데이터)
ExeService.execute(Beans.get(AuthorityFilter.url), no);
// 리스트로 자동이동
response.sendRedirect("list.do");
%>
