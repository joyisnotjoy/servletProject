package com.webjjang.notice.service;

import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.main.controller.Service;

public class NoticeViewService implements Service{

	//dao가 필요하다. 밖에서 생성한 후 넣어준다. - 1. 생성자. 2. setter()
	private NoticeDAO dao;

	@Override
	public void setDAO(Object dao) {
		this.dao = (NoticeDAO) dao;
	}
	
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		// 글보기와 글수정도 사용을 한다. 글보기할때(list -> view) 조회수1증가 해야하고 
		// 글수정할때는 (update->view) 증가하지 않는다. 데이터가 2개 넘어오게 된다. (클래스, 배열(O))
		// obj[0] - no /obj[1] - inc
		return dao.view((Long)obj);
	}

}
