package com.webjjang.message.service;

import com.webjjang.main.controller.Service;
import com.webjjang.message.dao.MessageDAO;

public class MessageGetMessageCntService implements Service{

	MessageDAO dao;
	
	@Override
	// 넘어 오는 데이터 : 아이디 - 타입: String
	public void setDAO(Object dao) {
		System.out.println("MessageGetMessageCntService.dao" + dao);
		this.dao = (MessageDAO) dao;
	}
	
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MessageGetMessageCntService.obj " + obj);
		return dao.getMessageCnt((String) obj);
	}

}
