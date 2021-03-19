package com.webjjang.message.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.webjjang.message.vo.MessageVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.db.DBInfo;
import com.webjjang.util.db.DBSQL;

public class MessageDAO {
		
	//필요한 객체 선언
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 메시지 리스트
	public List<MessageVO> list(PageObject pageObject) throws Exception {
		System.out.println("MessageDAO.list().pageObject : " + pageObject );

		List<MessageVO> list = null;
		
		try {
			con = DBInfo.getConnection();
			System.out.println("MessageDAO.list().con: " + con);
			//3.4.
			System.out.println("MessageDAO.list().DBSQL.MESSAGE_LIST: " + DBSQL.MESSAGE_LIST);
			pstmt = con.prepareStatement(DBSQL.MESSAGE_LIST);
			System.out.println("MessageDAO.list().pstmt: " + pstmt);
			
			pstmt.setString(1, pageObject.getAccepter());
			pstmt.setString(2, pageObject.getAccepter());
			pstmt.setLong(3, pageObject.getStartRow());
			pstmt.setLong(4, pageObject.getEndRow());
			//5. 실행
			System.out.println("MessageDAO.list().rs: " + rs);
			rs = pstmt.executeQuery();
			//6. 표시 
			if(rs != null) {
				while(rs.next()) {
					if(list == null) list = new ArrayList<>();
					MessageVO vo = new MessageVO();
					vo.setNo(rs.getLong("no"));
					vo.setSender(rs.getString("sender"));
					vo.setSendDate(rs.getString("sendDate"));
					vo.setAccepter(rs.getString("accepter"));
					vo.setAcceptDate(rs.getString("acceptDate"));
					list.add(vo);
					System.out.println("MessageDAO.list().while().vo : " + vo );
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("메시지 리스트 DB처리 중 오류");
		}finally {
			DBInfo.close(con, pstmt, rs);
		}
		System.out.println("MessageDAO.list().: " + list );
		return list;
	}
	// 메시지 전체 데이터 개수
	public long getTotalRow() throws Exception {
		long result = 0;
		
		try {
			System.out.println("MessageDAO.list().con: " + con);
			con = DBInfo.getConnection();
			//3.4.
			System.out.println("MessageDAO.list().DBSQL.MESSAGE_LIST: " + DBSQL.MESSAGE_GET_TOTALROW);
			pstmt = con.prepareStatement(DBSQL.MESSAGE_GET_TOTALROW);
			//5. 실행
			System.out.println("MessageDAO.list().rs: " + rs);
			rs = pstmt.executeQuery();
			//6. 표시 
			if(rs != null && rs.next()) {
				result = rs.getLong(1); 
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("메시지 리스트 DB처리 중 오류");
		}finally {
			DBInfo.close(con, pstmt, rs);
		}
		System.out.println("MessageDAO.list().: " + result );
		return result;

	}

	// 메시지 보내기 - 쓰기
	public int write(MessageVO vo) throws Exception {
		int result = 0;
		
		try {
			System.out.println("MessageDAO.list().con: " + con);
			con = DBInfo.getConnection();
			//3.4.
			System.out.println("MessageDAO.list().DBSQL.MESSAGE_LIST: " + DBSQL.MESSAGE_WRITE);
			pstmt = con.prepareStatement(DBSQL.MESSAGE_WRITE);
			pstmt.setString(1, vo.getSender());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getAccepter());
			//5. 실행
			System.out.println("MessageDAO.list().rs: " + rs);
			result = pstmt.executeUpdate();
			//6. 표시 
			System.out.println("MessageDAO.write() - 메시지 보내기 완료");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("메시지 보내기 DB처리 중 오류");
		}finally {
			DBInfo.close(con, pstmt);
		}
		System.out.println("MessageDAO.list().: " + result );
		return result;
	}// end of write()
	
	//메시지 보기의 읽음 처리
	public int viewUpdateRead(MessageVO vo) throws Exception{
		 int result = 0;
		
		try {
		con = DBInfo.getConnection();
		//3.4.
		System.out.println(DBSQL.MESSAGE_VIEW_READ);
		pstmt = con.prepareStatement(DBSQL.MESSAGE_VIEW_READ);
		pstmt.setLong(1, vo.getNo());
		pstmt.setString(2, vo.getAccepter());
		//5. 실행
		result = pstmt.executeUpdate();
		//6.데이터 표시
		System.out.println("messageDAO.viewUpdateRead(): - 메시지 읽음 표시 완료 ");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("메시지 보기 DB 처리 중 오류 발생");
		}finally {
			DBInfo.close(con, pstmt);
		}
		return result;
	}
	// 메시지 읽기 -view
	public MessageVO view(long no) throws Exception {
		MessageVO vo = null;
		
		try {
			//1.2.
			con = DBInfo.getConnection();
			System.out.println("MessageDAO.view().con: " + con);
			//3.4.
			System.out.println("MessageDAO.view().DBSQL.MESSAGE_LIST: " + DBSQL.MESSAGE_VIEW);
			pstmt = con.prepareStatement(DBSQL.MESSAGE_VIEW);
			System.out.println("MessageDAO.view().pstmt: " + pstmt);
			pstmt.setLong(1,no);
			
			//5. 실행
			System.out.println("MessageDAO.view().rs: " + rs);
			rs = pstmt.executeQuery();
			//6. 표시 
			if(rs != null && rs.next()) {
					vo = new MessageVO();
					vo.setNo(rs.getLong("no"));
					vo.setContent(rs.getString("content"));
					vo.setSender(rs.getString("sender"));
					vo.setSendDate(rs.getString("sendDate"));
					vo.setAccepter(rs.getString("accepter"));
					vo.setAcceptDate(rs.getString("acceptDate"));
				}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("메시지 보기 DB처리 중 오류");
		}finally {
			DBInfo.close(con, pstmt, rs);
		}
		return vo;
	}

	// 메시지 삭제
	public int delete(long no) throws Exception {
		int result = 0;
		
		try {
			System.out.println("MessageDAO.delete().con: " + con);
			con = DBInfo.getConnection();
			//3.4.
			System.out.println("MessageDAO.delete().DBSQL.MESSAGE_LIST: " + DBSQL.MESSAGE_DELETE);
			pstmt = con.prepareStatement(DBSQL.MESSAGE_DELETE);
			pstmt.setLong(1, no);
			//5. 실행
			System.out.println("MessageDAO.delete().rs: " + rs);
			result = pstmt.executeUpdate();
			//6. 표시 
			System.out.println("MessageDAO.write() - 메시지 보내기 완료");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("메시지 삭제 DB처리 중 오류");
		}finally {
			DBInfo.close(con, pstmt);
		}
		System.out.println("MessageDAO.list().: " + result );
		return result;
	}// end of write()
	
	// 6. 새로운 메시지 개수 가져오기
	public Long getMessageCnt(String id) throws Exception{
		// TODO Auto-generated method stub
		
		Long cnt = 0L;
		
		try {
			
			// 1.2.
			con = DBInfo.getConnection();
			//3.4.
			pstmt = con.prepareStatement(DBSQL.MESSAGE_GET_MESSAGE_CNT);
			pstmt.setString(1, id);
			
			// 5.
			rs = pstmt.executeQuery();
			//6.
			if(rs != null && rs.next()) {
				cnt = rs.getLong(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("MessageDAO - 새로운 메시지 개수 DB처리 중 오류");
		} finally {
			DBInfo.close(con, pstmt, rs);
			
		}
		System.out.println("MessageDAO.getMessageCnt().cnt : " + cnt);
		return cnt;
	}


}

