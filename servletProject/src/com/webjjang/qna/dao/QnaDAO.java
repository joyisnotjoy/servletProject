package com.webjjang.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.webjjang.qna.vo.QnaVO;
import com.webjjang.util.PageObject;
import com.webjjang.util.db.DBInfo;
import com.webjjang.util.db.DBSQL;

public class QnaDAO {

	//필요한 객체 선언
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 1. 게시판 리스트 
		public List<QnaVO> list(PageObject pageObject) throws Exception{
			List<QnaVO> list = null;
				
			try {
				//1. 드라이버 확인 + 2. 연결
				con = DBInfo.getConnection();
				System.out.println("QnaDAO.list().con: " + con);
				// 3. sql -> DBSQL + 4. 실행객체 + 데이터 세팅
				System.out.println("QnaDAO.list().DBSQL.Qna_LIST: " + DBSQL.QNA_LIST);
				pstmt = con.prepareStatement(DBSQL.QNA_LIST);
				pstmt.setLong(1, pageObject.getStartRow()); // 시작 번호
				pstmt.setLong(2, pageObject.getEndRow()); // 끝 번호
				// 5. 실행
				rs = pstmt.executeQuery();
				System.out.println("QnaDAO.list().rs : " + rs);
				// 6. 표시 -> 데이터담기
				if(rs != null) {
					while(rs.next()) {
						if(list == null) list = new ArrayList<>();
						QnaVO vo = new QnaVO();
						vo.setNo(rs.getLong("no"));
						vo.setTitle(rs.getString("title"));
						vo.setId(rs.getString("Id"));
						vo.setName(rs.getString("name"));
						vo.setWriteDate(rs.getString("writeDate"));
						vo.setHit(rs.getLong("hit"));
						vo.setLevNo(rs.getLong("levNo"));
						list.add(vo);
						System.out.println("QnaDAO.list().while().vo: " + vo);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				// 개발자를 위해서 오류를 콘솔에 출력한다.
				e.printStackTrace();
				// 사용자를 위한 오류 처리
				throw new Exception("질문답변 리스트 실행 중 DB 처리 오류");
			} finally {
				// 7. 닫기
				DBInfo.close(con, pstmt, rs);
			}
			System.out.println("QnaDAO.list().list : " + list);
			return list;
		}
		
	// 1-1. getTotalRow - 전체 데이터 개수 가져오기
		public Long getTotalRow() throws Exception{
			Long result = 0L;
				
			try {
				//1. 드라이버 확인 + 2. 연결
				con = DBInfo.getConnection();
				System.out.println("QnaDAO.list().con: " + con);
				// 3. sql -> DBSQL + 4. 실행객체 + 데이터 세팅
				System.out.println("QnaDAO.list().DBSQL.Qna_LIST: " + DBSQL.QNA_GET_TOTALROW);
				pstmt = con.prepareStatement(DBSQL.QNA_GET_TOTALROW);
				
				System.out.println("qnaDAO.list().pstmt: " + pstmt);
				// 5. 실행
				rs = pstmt.executeQuery();
				System.out.println("QnaDAO.list().rs : " + rs);
				// 6. 표시 -> 데이터담기
				if(rs != null && rs.next()) {
					result = rs.getLong(1);
				}
			} catch (Exception e) {
				// TODO: handle exception
				// 개발자를 위해서 오류를 콘솔에 출력한다.
				e.printStackTrace();
				// 사용자를 위한 오류 처리
				throw new Exception("질문답변 전체 데이터 개수 실행 중 DB 처리 오류");
			} finally {
				// 7. 닫기
				DBInfo.close(con, pstmt, rs);
			}
			System.out.println("QnaDAO.list().result : " + result);
			return result;
		}
				
	// 2. 질문답변 보기 - view
			public QnaVO view(Long no) throws Exception{
				QnaVO vo = null;
					
				try {
					//1. 드라이버 확인 + 2. 연결
					con = DBInfo.getConnection();
					System.out.println("QnaDAO.view().con: " + con);
					// 3. sql -> DBSQL + 4. 실행객체 + 데이터 세팅
					System.out.println("QnaDAO.view().DBSQL.Qna_LIST: " + DBSQL.QNA_VIEW);
					pstmt = con.prepareStatement(DBSQL.QNA_VIEW);
					pstmt.setLong(1, no);
					System.out.println("qnaDAO.view().pstmt: " + pstmt);
					// 5. 실행
					rs = pstmt.executeQuery();
					System.out.println("QnaDAO.view().rs : " + rs);
					// 6. 표시 -> 데이터담기
					if(rs != null && rs.next()) {
						vo = new QnaVO();
						vo.setNo(rs.getLong("no"));
						vo.setTitle(rs.getString("title"));
						vo.setContent(rs.getString("content"));
						vo.setName(rs.getString("name"));
						vo.setId(rs.getString("id"));
						vo.setWriteDate(rs.getString("writeDate"));
						vo.setHit(rs.getLong("hit"));
						// 글보기 할 때는 관련글 번호, 순서번호, 들여쓰기 정보를 필요로 하지 않아서 버린다.
						// 답변쓰기 할 때는 이러한 정보가 필요하다. 그래서 일단 담아 놓는다.
						vo.setRefNo(rs.getLong("refNo"));
						vo.setOrdNo(rs.getLong("ordNo"));
						vo.setLevNo(rs.getLong("levNo"));
					}
				} catch (Exception e) {
					// TODO: handle exception
					// 개발자를 위해서 오류를 콘솔에 출력한다.
					e.printStackTrace();
					// 사용자를 위한 오류 처리
					throw new Exception("질문답변 보기 실행 중 DB 처리 오류");
				} finally {
					// 7. 닫기
					DBInfo.close(con, pstmt, rs);
				}
				System.out.println("QnaDAO.view().vo : " + vo);
				return vo;
			}
					
	// 2-1 조회수 1증가
	public int increase(Long no) throws Exception{
		int result = 0;
			
		try {
			//1. 드라이버 확인 + 2. 연결
			con = DBInfo.getConnection();
			System.out.println("QnaDAO.increase().con: " + con);
			// 3. sql -> DBSQL + 4. 실행객체 + 데이터 세팅
			pstmt = con.prepareStatement(DBSQL.QNA_INCREASE);
			pstmt.setLong(1, no);
			
			System.out.println("qnaDAO.increase().pstmt: " + pstmt);
			// 5. 실행
			result = pstmt.executeUpdate();
			System.out.println("QnaDAO.increase().rs : " + result);
			// 6. 표시 -> 데이터담기
			System.out.println("조회수 1증가 완료");
		} catch (Exception e) {
			// TODO: handle exception
			// 개발자를 위해서 오류를 콘솔에 출력한다.
			e.printStackTrace();
			// 사용자를 위한 오류 처리
			throw new Exception("질문답변 보기의 조회수 1증가 실행 중 DB 처리 오류");
		} finally {
			// 7. 닫기
			DBInfo.close(con, pstmt);
		}
		System.out.println("QnaDAO.increase().result : " + result);
		return result;
	}
			
	
// 3-1. 질문하기 - question : 제목, 내용 -> 사용자 입력, 아이디 -> session의 로그인 정보 : JSP에서 수집
public int question(QnaVO vo) throws Exception{
int result = 0;
	
try {
	//1. 드라이버 확인 + 2. 연결
	con = DBInfo.getConnection();
	System.out.println("QnaDAO.list().con: " + con);
	// 3. sql -> DBSQL + 4. 실행객체 + 데이터 세팅
	System.out.println("QnaDAO.list().DBSQL.Qna_LIST: " + DBSQL.QNA_QUESTION);
	pstmt = con.prepareStatement(DBSQL.QNA_QUESTION);
	pstmt.setString(1, vo.getTitle());
	pstmt.setString(2, vo.getContent());
	pstmt.setString(3, vo.getId());
	
	System.out.println("qnaDAO.list().pstmt: " + pstmt);
	// 5. 실행
	result = pstmt.executeUpdate();
	System.out.println("QnaDAO.list().rs : " + result);
	// 6. 표시 -> 데이터담기
	System.out.println("QnaDAO.question() - 질문 등록 완료 ");
	
} catch (Exception e) {
	// TODO: handle exception
	// 개발자를 위해서 오류를 콘솔에 출력한다.
	e.printStackTrace();
	// 사용자를 위한 오류 처리
	throw new Exception("질문 등록 실행 중 DB 처리 오류");
} finally {
	// 7. 닫기
	DBInfo.close(con, pstmt, rs);
}
System.out.println("QnaDAO.list().result : " + result);
return result;
}
	

// 3-2. 답변하기 - answer : 제목, 내용 -> 사용자 입력, 아이디 -> session의 로그인 정보 : JSP에서 수집
// 관련글 번호, 순서번호, 들여쓰기 ,부모글 번호 -> 전달받는다.
public int answer(QnaVO vo) throws Exception{
int result = 0;

try {
//1. 드라이버 확인 + 2. 연결
con = DBInfo.getConnection();
System.out.println("QnaDAO.answer().con: " + con);
// 3. sql -> DBSQL + 4. 실행객체 + 데이터 세팅
System.out.println("QnaDAO.answer().DBSQL.Qna_answer: " + DBSQL.QNA_ANSWER);
pstmt = con.prepareStatement(DBSQL.QNA_ANSWER);
pstmt.setString(1, vo.getTitle());
pstmt.setString(2, vo.getContent());
pstmt.setString(3, vo.getId());
pstmt.setLong(4, vo.getRefNo());
pstmt.setLong(5, vo.getOrdNo());
pstmt.setLong(6, vo.getLevNo());
pstmt.setLong(7, vo.getParentNo());

System.out.println("qnaDAO.answer().pstmt: " + pstmt);
// 5. 실행
result = pstmt.executeUpdate();
System.out.println("QnaDAO.answer().rs : " + result);
// 6. 표시 -> 데이터담기
System.out.println("QnaDAO.answer() - 답변 등록 완료 ");

} catch (Exception e) {
// TODO: handle exception
// 개발자를 위해서 오류를 콘솔에 출력한다.
e.printStackTrace();
// 사용자를 위한 오류 처리
throw new Exception("답변 등록 실행 중 DB 처리 오류");
} finally {
// 7. 닫기
DBInfo.close(con, pstmt);
}
System.out.println("QnaDAO.list().result : " + result);
return result;
}

//3-3 답변하기 순서번호 1증가.
	public int ordNoIncrease(QnaVO vo) throws Exception{
		int result = 0;
			
		try {
			//1. 드라이버 확인 + 2. 연결
			con = DBInfo.getConnection();
			System.out.println("QnaDAO.answer().con: " + con);
			// 3. sql -> DBSQL + 4. 실행객체 + 데이터 세팅
			pstmt = con.prepareStatement(DBSQL.QNA_ANSWER_INCREASE_ORDNO);
			pstmt.setLong(1, vo.getRefNo());
			pstmt.setLong(2, vo.getOrdNo());
			
			System.out.println("qnaDAO.answer().pstmt: " + pstmt);
			// 5. 실행
			result = pstmt.executeUpdate();
			System.out.println("QnaDAO.answer().rs : " + result);
			// 6. 표시 -> 데이터담기
			System.out.println("답변하기 순서번호 1증가 완료");
		} catch (Exception e) {
			// TODO: handle exception
			// 개발자를 위해서 오류를 콘솔에 출력한다.
			e.printStackTrace();
			// 사용자를 위한 오류 처리
			throw new Exception("질문답변 답변하기의 조회수 1증가 실행 중 DB 처리 오류");
		} finally {
			// 7. 닫기
			DBInfo.close(con, pstmt);
		}
		System.out.println("QnaDAO.answer().result : " + result);
		return result;
	}
			
	
//5. 질문답변 삭제
public int delete(long no) throws Exception {
	int result = 0;
	try {
		//1. 확인 2. 연결
		con = DBInfo.getConnection();
		//3. sql . 4. 실행&데이터 세팅
		pstmt = con.prepareStatement(DBSQL.QNA_DELETE);
		pstmt.setLong(1,no);
		//5. 실행
		result = pstmt.executeUpdate();
		//6. 출력
		if(result==1)
			System.out.println("질문답변 삭제 성공");
		else
			System.out.println("삭제하려는 글의 정보를 확인하세요.");
	}catch (Exception e) {
		// TODO: handle exception
		// 개발자를 위한 예외 출력(500) -> 콘솔
		e.printStackTrace();
		// 사용자를 위한 예외처리 -> jsp 까지 전달된다.
		throw new Exception("질문답변 삭제  DB처리 중 오류 발생");
	} finally {
		DBInfo.close(con, pstmt);
	}
	return result;
	}
}
