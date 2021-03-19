<%@page import="com.webjjang.board.vo.BoardVO"%>
<%@page import="com.webjjang.board.service.BoardWriteService"%>
<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//순수한 자바 코드 부분입니다.---------------

// 한글처리
System.out.println("EncodingFilter에서 한글처리 완료" + request.getCharacterEncoding());
// 1. 데이터 수집
String title = request.getParameter("title");
String content = request.getParameter("content");
String startDate = request.getParameter("startDate");
String endDate = request.getParameter("endDate");
//vo객체를 만들어서 넣는다.
BoardVO vo = new BoardVO();
vo.setTitle(title);
vo.setContent(content);
vo.setWriter(startDate);
vo.setWriter(endDate);

//DB에 데이터를 저장하는 처리를 한다.
String url = request.getServletPath();
Integer result = (Integer)ExeService.execute(Beans.get(url), vo);

//처리를 한 후에 자동 리스트로 페이지 이동시킴.
response.sendRedirect("list.do");
%>
<%=vo%>