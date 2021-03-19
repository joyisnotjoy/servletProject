package com.webjjang.notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.webjjang.main.controller.Beans;
import com.webjjang.main.controller.Controller;
import com.webjjang.main.controller.ExeService;
import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class NoticeController implements Controller {

	private final String MODULE = "notice";
	private String jspInfo = null;

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		System.out.println("NoticeController.execute()");
		
		// 페이지 처리를 한다.
		PageObject pageObject = PageObject.getInstance(request);
		request.setAttribute("pageObject", pageObject); // 페이지를 보여주기 위해 서버객체에 담는다.

		// url에 맞는 처리를 한다.
		// switch case를 이용한다.
		switch (AuthorityFilter.url) {
		// 1. 공지 리스트
		case "/" + MODULE +"/list.do":
			// service - dao --> request에 저장까지 해준다.
			list(request,pageObject);
		// "notice/list" 넘긴다. -> /WEB-INF/views/ + notice/list + .jsp를 이용해서 HTML을 만든다.
		jspInfo = MODULE + "/list";			
		break;

		// 2. 공지 보기
		case "/" + MODULE +"/view.do":
			// service - dao --> request에 저장까지 해준다.
			view(request);
		
		// "notice/view" 넘긴다. -> /WEB-INF/views/ + notice/view + .jsp를 이용해서 HTML을 만든다.
		jspInfo = MODULE + "/view";			
		break;
		
		//3-1.  쓰기 폼
		case "/" + MODULE +"/writeForm.do":
		// "notice/view" 넘긴다. -> /WEB-INF/views/ + notice/view + .jsp를 이용해서 HTML을 만든다.
		jspInfo = MODULE + "/writeForm";			
		break;
		
		// 3-2. 공지 글쓰기 처리
		case "/" + MODULE +"/write.do":
			// service - dao --> request에 저장까지 해준다.
			write(request);
		jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
		break;
		
		//4-1. 공지사항 글수정 폼
		case "/" + MODULE +"/updateForm.do":
			// service - dao --> request에 저장까지 해준다.
			updateForm(request);
		// "board/view" 넘긴다. -> /WEB-INF/views/ + board/view + .jsp를 이용해서 HTML을 만든다.
		jspInfo = MODULE + "/updateForm";			
		break;
		
		//4-2. 공지사항 글수정 처리
		case "/" + MODULE +"/update.do":
			// service - dao --> request에 저장까지 해준다.
			Long no = update(request);

//		 "board/view" 넘긴다. -> /WEB-INF/views/ + board/view + .do로 자동 이동.
		jspInfo = "redirect:view.do?no=" + no + "&inc=0&page="
				+ pageObject.getPage() + "&perPageNum=" + pageObject.getPerPageNum();			
		break;

		
		default:
			throw new Exception("페이지 오류 404 - 존재하지 않는 페이지 입니다.");
		}
		return jspInfo;
		}
	//1.공지사항 리스트 처리
		private void list(HttpServletRequest request, PageObject pageObject) throws Exception {
			// 여기가 자바 코드입니다. servlet-controller(*)-Service-DAO

		// DB에서 데이터 가져오기
			
		@SuppressWarnings("unchecked")
		List<NoticeVO> list = (List<NoticeVO>) ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);

		// 서버 객체에 데이터 저장하기
		request.setAttribute("list", list);

		}
		//2. 공지사항 글보기 처리
		private void view (HttpServletRequest request) throws Exception {
			// 여기가 자바 코드입니다. servlet - Controller - Service - DAO -> /notice/view.do

			// 넘어오는 데이터 받기 - 글번호

			
			// 자바 부분 데이터 가져오기 - 페이지 처리 : 페이지와 한 페이지당 표시 데이터의 개수를 전달 받아야 한다.
			// 여기가 자바 코드입니다. JSP ~> Service ~> DAO -> /notice/view.jsp

			// 넘어오는 데이터 받기 - 글 번호
			String strNo = request.getParameter("no");
			long no = Long.parseLong(strNo);


			NoticeVO vo = (NoticeVO)ExeService.execute(Beans.get(AuthorityFilter.url), no);
			// 서버객체 request에 담는다.
			//<% ~~ 자바처리, <%= ~ 화면표시
			request.setAttribute("vo", vo);

		}
		//3. 공지사항 글쓰기 처리
		private void write(HttpServletRequest request) throws Exception{

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");

			// vo 객체에 생성하고 저장한다.
			NoticeVO vo = new NoticeVO();
			vo.setTitle(title);
			vo.setContent(content);
			vo.setStartDate(startDate);
			vo.setEndDate(endDate);

			// vo객체 데이터 확인
			System.out.println("/notice/write.jsp [vo] : " + vo);

			// DB에 데이터 저장 jsp(controller) - NoticeWriteService - NoticeDAO - notice table
			ExeService.execute(Beans.get(AuthorityFilter.url), vo);

		}
		//4-1. 공지사항 글수정 폼
		private void updateForm(HttpServletRequest request) throws Exception{
			// 자바 부분
			// 1.넘어오는 데이터 받기 - 글번호
			String strNo = request.getParameter("no");
			long no = Long.parseLong(strNo);
			// 2. 글번호에 맞는 데이터 가져오기 -> BoardViewService => /board/view.jsp
			String url = "/notice/view.do"; // 현재 URL과 다르므로 강제 세팅함.
			NoticeVO vo = (NoticeVO)ExeService.execute(Beans.get(url), new Long[]{no,0L});
			// 3. 서버 객체에 넣기
			request.setAttribute("vo", vo);

		}
		
		//4-2. 공지사항 글수정 처리
		private Long  update(HttpServletRequest request) throws Exception{
			//순수한 자바 코드 부분입니다.---------------

			// 1. 데이터 수집
			String strNo = request.getParameter("no");
			long no = Long.parseLong(strNo);

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String startDate= request.getParameter("startDate");
			String endDate= request.getParameter("endDate");
			// 2. DB처리 - update.jsp ->service -> dao
			NoticeVO vo = new NoticeVO();
			vo.setNo(no);
			vo.setTitle(title);
			vo.setContent(content);
			vo.setStartDate(startDate);
			vo.setEndDate(endDate);

			//DB에 데이터를 저장하는 처리를 한다.
			String url = request.getServletPath();
			Integer result = (Integer)ExeService.execute(Beans.get(url), vo);

			if(result < 1) throw new Exception("공지사항 글수정 - 수정할 데이터가 존재하지 않습니다.");
		
			return no;
		}

}

