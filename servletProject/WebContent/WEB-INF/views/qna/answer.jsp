<%@page import="com.webjjang.main.controller.Beans"%>
<%@page import="com.webjjang.util.filter.AuthorityFilter"%>
<%@page import="com.webjjang.main.controller.ExeService"%>
<%@page import="com.webjjang.qna.vo.QnaVO"%>
<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 자바
// 넘어오는 데이터 받기
String strNo = request.getParameter("no");
String title = request.getParameter("title");
String content = request.getParameter("content");
// 아이디 session에서 받아오기
String id = ((LoginVO)session.getAttribute("login")).getId();
String strRefNo = request.getParameter("refNo");
String strOrdNo = request.getParameter("ordNo");
String strLevNo = request.getParameter("levNo");
// VO 객체를 생성하고 저장해 놓는다.
QnaVO vo = new QnaVO();
//번호(no)는 insert 실행 시 시퀀스를 사용한다. 넘겨받은 번호는 부호번호로 세팅한다.
vo.setTitle(title);
vo.setContent(content);
vo.setId(id);
vo.setRefNo(Long.parseLong(strRefNo));
vo.setOrdNo(Long.parseLong(strOrdNo) + 1); // 관련글 번호가 같고 순서번호가 같거나 큰 것의 순서번호는 1증가 우선한다.
vo.setLevNo(Long.parseLong(strLevNo) + 1);
vo.setParentNo(Long.parseLong(strNo)); // 번호(no)는 insert 실행 시 시퀀스를 사용한다. 넘겨받은 번호는 부호번호로 세팅한다.
// DB 저장 처리 : jsp - service - dao
ExeService.execute(Beans.get(AuthorityFilter.url), vo);
// 처리가 다 끝나면 리스트로 자동이동 시킨다.
response.sendRedirect("list.jsp");
%>
