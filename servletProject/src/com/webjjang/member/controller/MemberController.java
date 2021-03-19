package com.webjjang.member.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.main.controller.Beans;
import com.webjjang.main.controller.Controller;
import com.webjjang.main.controller.ExeService;
import com.webjjang.util.PageObject;
import com.webjjang.util.filter.AuthorityFilter;

public class MemberController implements Controller{

	private final String MODULE = "member";
	private String jspInfo = null;
	
	@Override
	public String execute(HttpServletRequest request) throws Exception{
		System.out.println("BoardController.execute()");
		
		//페이지를 위한 처리
		PageObject pageObject = PageObject.getInstance(request);
		request.setAttribute("pageObject", pageObject); // 페이지를 보여주기 위해 서버객체에 담는다.
		
		switch (AuthorityFilter.url) {
		// 1. 회원 리스트
		case "/" + MODULE +"/list.do":
			// service - dao --> request에 저장까지 해준다.
			list(request,pageObject);
		// "member/list" 넘긴다. -> /WEB-INF/views/ + member/list + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/list";			
			break;

		// 2. 회원 보기
		case "/" + MODULE +"/view.do":
			// service - dao --> request에 저장까지 해준다.
			view(request);
		
		// "member/view" 넘긴다. -> /WEB-INF/views/ + member/view + .jsp를 이용해서 HTML을 만든다.
			jspInfo = MODULE + "/view";			
			break;
	
		//3-1. 회원 등록 폼
		case "/" + MODULE +"/joinForm.do":
		// "member/view" 넘긴다. -> /WEB-INF/views/ + member/view + .jsp를 이용해서 HTML을 만든다.
		jspInfo = MODULE + "/joinForm";			
		break;
		
		// 3-2. 회원 등록 처리
		case "/" + MODULE +"/join.do":
			// service - dao --> request에 저장까지 해준다.
			join(request);
		
			// 회원가입이 끝나면 자동으로 로그인 페이지로 이동시킨다.
			jspInfo = "redirect:/member/loginForm.do";			
			break;

		//4-1. 회원 글수정 폼
		case "/" + MODULE +"/updateForm.do":
			// service - dao --> request에 저장까지 해준다.
			updateForm(request);
		// "member/view" 넘긴다. -> /WEB-INF/views/ + member/view + .jsp를 이용해서 HTML을 만든다.
		jspInfo = MODULE + "/updateForm";			
		break;
		
		//5. 회원 글삭제 처리
		case "/" + MODULE +"/delete.do":
			// service - dao --> request에 저장까지 해준다.
			delete(request);
		
		// list.do 로 자동이동
		jspInfo = "redirect:list.do?page=1&perPageNum=" + pageObject.getPerPageNum();
		break;
		
		// 6-1. 로그인 폼
		case "/" + MODULE +"/loginForm.do":
		// list.do 로 자동이동
		jspInfo = MODULE + "/loginForm";
		break;

		// 6-2. 로그인 처리
		case "/" + MODULE +"/login.do":
			login(request);
			// list.do 로 자동이동
			jspInfo = "redirect:/board/list.do";
		break;
		
		// 6-3. 로그아웃 처리
		case "/" + MODULE +"/logout.do":
			logout(request);
			
		// list.do 로 자동이동
		jspInfo = "redirect:/board/list.do";
		break;
		
		
		// 7 아이디 중복 체크
		case "/ajax/checkId.do":
			//DB에서 입력한 아이디를 찾아온다.
			// 찾아온 아이디를 request에 넣는다.
			checkId(request);
			
			//div안에 들어가는 코드만 있는 jsp 생성. 
			jspInfo = "member/checkId";
		break;				
		
		default:
			throw new Exception("페이지 오류 404 - 존재하지 않는 페이지 입니다.");
		}
		
		// jsp의 정보를 가지고 리턴한다.
		return jspInfo;
	}
	
	
	
	
	// 1. 회원 리스트 처리
	private void list(HttpServletRequest request, PageObject pageObject) throws Exception {
		// 여기가 자바 코드입니다. servlet-controller(*)-Service-DAO

		//request.getServletPath(); -> AuthorityFilter 76줄에서 실행한 후 url 저장하도록 하고 있다.
		@SuppressWarnings("unchecked")
		List<MemberVO> list = (List<MemberVO>) ExeService.execute(Beans.get(AuthorityFilter.url), pageObject);
		// 서버객체 request에 담는다.
		request.setAttribute("list", list);

	}
	
	// 2. 회원 글보기 처리
	private void view (HttpServletRequest request) throws Exception {
		// 여기가 자바 코드입니다. servlet - Controller - Service - DAO -> /member/view.do

		// 넘어오는 데이터 받기 - 글번호

		String strNo = request.getParameter("no");
		long no = Long.parseLong(strNo);

		String strInc = request.getParameter("inc");
		long inc = Long.parseLong(strInc);

		MemberVO vo = (MemberVO)ExeService.execute(Beans.get(AuthorityFilter.url), new Long[]{no,inc});
		// 서버객체 request에 담는다.
		//<% ~~ 자바처리, <%= ~ 화면표시
		request.setAttribute("vo", vo);

	}
	
	
	//4-1. 회원 글수정 폼
	private void updateForm(HttpServletRequest request) throws Exception{
		// 자바 부분
		// 1.넘어오는 데이터 받기 - 글번호
		String strNo = request.getParameter("no");
		long no = Long.parseLong(strNo);
		// 2. 글번호에 맞는 데이터 가져오기 -> BoardViewService => /member/view.jsp
		String url = "/member/view.do"; // 현재 URL과 다르므로 강제 세팅함.
		MemberVO vo = (MemberVO)ExeService.execute(Beans.get(url), new Long[]{no,0L});
		// 3. 서버 객체에 넣기
		request.setAttribute("vo", vo);

	}


	//5. 회원 글삭제
	private void delete(HttpServletRequest request) throws Exception{
		// 1. 데이터 수집
		String strNo = request.getParameter("no");
		long no = Long.parseLong(strNo);

		// 2. DB처리를 한다. -> delete.jsp
		String url = request.getServletPath();
		Integer result = (Integer)ExeService.execute(Beans.get(url), no);
		if(result == 0 ) throw new Exception("회원 글삭제 오류 - 존재하지 않는 글은 삭제 불가합니다.");
		
	}
	//6-1. 로그인 처리
	private void login(HttpServletRequest request) throws Exception{
		// 1. 데이터 수집
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		LoginVO vo = new LoginVO();
		vo.setId(id);
		vo.setPw(pw);
		
		// 2. DB처리를 한다. -> delete.jsp
		String url = request.getServletPath();
		LoginVO loginVO = (LoginVO) ExeService.execute(Beans.get(url), vo);
		//id, pw가 틀린 경우의 처리
		if(loginVO == null)	throw new Exception(" 로그인 정보를 확인해 주세요.");
		
		request.getSession().setAttribute("login", loginVO);
	}
	//6-1. 로그아웃 처리
	private void logout(HttpServletRequest request) throws Exception{
		request.getSession().invalidate();
		System.out.println("로그아웃 처리가 되었습니다.");
	}
	//3.회원 등록 처리
	private void join(HttpServletRequest request) throws Exception{
		
		// 1. 데이터 수집
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String birth = request.getParameter("birth");
		String[] tels = request.getParameterValues("tel");
		String email = request.getParameter("email");

		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPw(pw);
		vo.setName(name);
		vo.setGender(gender);
		vo.setBirth(birth);
		if (tels == null) vo.setTel(Arrays.toString(tels));
		vo.setEmail(email);
		
		// 들어온 데이터 확인
		System.out.println("MemberController.join().vo : " + vo);
		
		// 2. DB 처리 - write.jsp -> service -> dao
		Integer result = (Integer) ExeService.execute(Beans.get(AuthorityFilter.url), vo);

		System.out.println("MemberController.join().result : " + result);
	}

	
	private void checkId(HttpServletRequest request) throws Exception{
		// 넘어오는 아이디 받기
		String id = request.getParameter("id");
		// DB처리 -> id를 가져온다.
		String result = (String)ExeService.execute(Beans.get(AuthorityFilter.url), id);
		//서버 객체에 저장.
		request.setAttribute("id", result);
	}
}