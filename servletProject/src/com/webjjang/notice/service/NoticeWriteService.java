package com.webjjang.notice.service;

import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.main.controller.Service;


public class NoticeWriteService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	private NoticeDAO dao;

	// 기본 생성자 만들기 -> 확인 시 필요
	public NoticeWriteService() {
		System.out.println("NoticeWriteService.NoticeWriteService() - 생성 완료");
	}
	
	
	@Override
	public void setDAO(Object dao) {
		System.out.println("NoticeWriteService.setDAO().dao : " + dao);
		this.dao = (NoticeDAO) dao;
	}
	
	// url 요청에 따른 처리
	// 넘어오는 데이터가 NoticeVO ==> obj
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		// 넘어오는 데이터 확인
		System.out.println("NoticeWriteService.obj : " + obj);
		return dao.write((NoticeVO)obj);
	}

}
