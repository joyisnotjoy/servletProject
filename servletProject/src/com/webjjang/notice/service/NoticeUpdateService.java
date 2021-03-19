package com.webjjang.notice.service;

import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.main.controller.Service;


public class NoticeUpdateService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	NoticeDAO dao;

	@Override
	public void setDAO(Object dao) {
		this.dao = (NoticeDAO)dao;
	}
	
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return dao.update((NoticeVO)obj);
}

}
