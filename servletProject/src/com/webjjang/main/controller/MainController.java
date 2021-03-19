package com.webjjang.main.controller;

import javax.servlet.http.HttpServletRequest;

import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class MainController implements Controller {

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		String jspInfo = null;
		switch (AuthorityFilter.url) {
		case "/main.do":
			jspInfo = "redirect:/main/main.do";
			break;
		
		case "/main/main.do":
			list(request);
			jspInfo = "main/main";
			break;
			
		default:
			throw new Exception("MainController - 페이지 오류 404- 존재하지 않는 페이지입니다.");
		}
		return jspInfo;
	}
	// 메인으로 데이터 가져오기
	private void list(HttpServletRequest request) throws Exception {
		PageObject pageObject = PageObject.getInstance(request); // page:1, perPgeNum : 10
		//perPageNum = 7로 변경
		pageObject.setPerPageNum(7);
		request.setAttribute("noticeList", ExeService.execute(Beans.get("/notice/list.do"), pageObject));
		request.setAttribute("boardList", ExeService.execute(Beans.get("/board/list.do"), pageObject));
		
	}
}
