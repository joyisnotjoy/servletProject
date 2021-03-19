package com.webjjang.member.service;

import com.webjjang.main.controller.Service;
import com.webjjang.member.dao.MemberDAO;

public class MemberViewService implements Service{

	MemberDAO dao;
	
	@Override
	// 넘어 오는 데이터 : 아이디 - 타입: String
	public void setDAO(Object dao) {
		this.dao = (MemberDAO) dao;
	}
	
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MemberViewService.setDAO().obj " + obj);
		System.out.println("MemberViewService.setDAO().dao " + dao);
		return dao.view((String) obj);
	}

}
