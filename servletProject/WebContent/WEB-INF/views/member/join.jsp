<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.message.vo.MessageVO"%>
<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 자바
// 넘어오는 데이터 수집 - 받는 사람 아이디, 내용
String accepter = request.getParameter("accepter");
String content = request.getParameter("content");

// session에서 내 아이디 가져오기
// session의 내용은 /member/login.jsp 확인. 이 때 key = login이라는 것이 다르면 null이 나온다.
LoginVO vo = (LoginVO) session.getAttribute("login");
String sender = vo.getId();

// vo 객체 생성 후 데이터를 넣는다.
MessageVO messageVO = new MessageVO();
messageVO.setContent(content);
messageVO.setSender(sender);
messageVO.setAccepter(accepter);

// db 처리 : jsp - service - dao - db
// ExeService.execute(실행할 Service, Service에 전달되는 데이터)
ExeService.execute(Beans.get(AuthorityFilter.url), messageVO);
// 리스트로 자동이동
response.sendRedirect("list.do");
%>
