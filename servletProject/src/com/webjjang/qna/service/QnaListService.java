package com.webjjang.qna.service;

import com.webjjang.qna.dao.QnaDAO;
import com.webjjang.main.controller.Service;

import com.webjjang.util.PageObject;

public class QnaListService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	QnaDAO dao;

	// 기본 생성자 만들기 -> 확인 시 필요
	public QnaListService() {
		System.out.println("QnaListService.QnaListService() - 생성 완료");
	}
	
	
	@Override
	public void setDAO(Object dao) {
		System.out.println("QnaListService.setDAO().dao : " + dao);
		this.dao = (QnaDAO) dao;
	}
	
	// url 요청에 따른 처리
	// 넘어오는 데이터가 PageObject ==> obj
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		// 넘어오는 데이터 확인
		System.out.println("QnaListService.obj : " + obj);
		// 전체 데이터를 가져오기
		long totalRow = dao.getTotalRow();
		// 넘어오는 obj가 Object - (pageObject) obj;
		PageObject pageObject = (PageObject)obj;
		pageObject.setTotalRow(totalRow);
		// 전체 페이지 세팅 후 페이지 객체 출력
		System.out.println("QnaListService.pageObject : " + pageObject);
		return dao.list(pageObject);
	}

}
