package com.webjjang.message.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.webjjang.main.controller.Beans;
import com.webjjang.main.controller.Controller;
import com.webjjang.main.controller.ExeService;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.message.vo.MessageVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class MessageController implements Controller {

		private final String MODULE = "message";
		private String jspInfo = null;
		
		@Override
		public String execute(HttpServletRequest request) throws Exception {
			System.out.println("MessageController.execute()");
			
			// 페이지 처리를 한다.
			PageObject pageObject = PageObject.getInstance(request);
			request.setAttribute("pageObject", pageObject); // 페이지를 보여주기 위해 서버객체에 담는다.
		
			// url에 맞는 처리를 한다.
			// switch case를 이용한다.
			switch (AuthorityFilter.url) {
			// 1. 메시지 리스트
			case "/" + MODULE +"/list.do":
				// service - dao --> request에 저장까지 해준다.
				list(request,pageObject);
			// "notice/list" 넘긴다. -> /WEB-INF/views/ + notice/list + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/list";			
			break;

			// 2. 메시지 보기
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

			// 3-2. 메시지 보내기 처리
			case "/" + MODULE +"/write.do":
				// service - dao --> request에 저장까지 해준다.
				write(request);
			// "notice/view" 넘긴다. -> /WEB-INF/views/ + notice/view + .jsp를 이용해서 HTML을 만든다.
			jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
			break;
			
			//5. 메시지 글삭제 처리
			case "/" + MODULE +"/delete.do":
				// service - dao --> request에 저장까지 해준다.
				delete(request);
			// list.do 로 자동이동
			
			jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
			break;
			
			//5. 새로운 메시지 개수 가져오기 처리
			case "/ajax/getMessageCnt.do":
				// service - dao --> request에 저장까지 해준다.
			// list.do 로 자동이동
//			jspInfo = "123";
			jspInfo = "" + getMessageCnt(request);
				
			break;
			
		default:
			throw new Exception("페이지 오류 404 - 존재하지 않는 페이지 입니다.");
		}

			return jspInfo;
			
		}
			// 1. 메시지 리스트 처리
			private void list(HttpServletRequest request, PageObject pageObject) throws Exception {
				//DB에서 데이터 가져오기
				pageObject.setAccepter(((LoginVO) request.getSession().getAttribute("login")).getId());
				@SuppressWarnings("unchecked")
				List<MessageVO> list = (List<MessageVO>) ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);

				//서버 객체에 데이터 저장하기
				request.setAttribute("list", list);
				request.setAttribute("pageObject", pageObject);
				
	}
			//2. 메시지 보기 처리
			private void view (HttpServletRequest request) throws Exception {
				// 넘어오는 데이터 받기 - 글번호
				String strNo = request.getParameter("no");
				// 내 아이디 정보를 꺼내야 한다.
				String id= ((LoginVO) request.getSession().getAttribute("login")).getId();

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
			}

			//3. 메시지 쓰기 처리
			private void write (HttpServletRequest request) throws Exception {

				// 자바
				// 넘어오는 데이터 수집 - 받는 사람 아이디, 내용
				String accepter = request.getParameter("accepter");
				String content = request.getParameter("content");

				// session에서 내 아이디 가져오기
				// session의 내용은 /member/login.jsp 확인. 이 때 key = login이라는 것이 다르면 null이 나온다.
				LoginVO vo = (LoginVO) request.getSession().getAttribute("login");
				String sender = vo.getId();

				// vo 객체 생성 후 데이터를 넣는다.
				MessageVO messageVO = new MessageVO();
				messageVO.setContent(content);
				messageVO.setSender(sender);
				messageVO.setAccepter(accepter);

				// db 처리 : jsp - service - dao - db
				// ExeService.execute(실행할 Service, Service에 전달되는 데이터)
				ExeService.execute(Beans.get(AuthorityFilter.url), messageVO);
			}
			//5. 메시지 글삭제
			private void delete(HttpServletRequest request) throws Exception{

			// 자바
			// 넘어오는 데이터 수집 - 번호
			String strNo = request.getParameter("no");
			Long no = Long.parseLong(strNo);

			// db 처리 : jsp - service - dao - db
			// ExeService.execute(실행할 Service, Service에 전달되는 데이터)
			ExeService.execute(Beans.get(AuthorityFilter.url), no);
			
		}
		// 6. 새로운 메시지 개수를 가져오기 
			private Long getMessageCnt(HttpServletRequest request) throws Exception {
				// session에 있는 id를 꺼낸다.
				LoginVO vo = (LoginVO)request.getSession().getAttribute("login");
				if(vo == null) throw new Exception("MessageController.getMessageCnt() - 로그인이 되지 않았습니다.");
				String id = ((LoginVO)request.getSession().getAttribute("login")).getId();
				return (Long)ExeService.execute(Beans.get(AuthorityFilter.url), id);
			}
}
