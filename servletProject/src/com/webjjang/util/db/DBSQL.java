package com.webjjang.util.db;

public class DBSQL {

	// 게시판 쿼리-----------------------------------------------------------------------
	// 게시판 리스트
	public static final String BOARD_LIST
	= " select rnum, no, title, writer, "
	+ " to_char(writeDate, 'yyyy.mm.dd') writeDate, hit from( "
		+ " select rownum rnum, no, title, writer, writeDate, hit from ( "
			+ " select no, title, writer, writeDate, hit from board "
			+ " order by no desc "
		+ " ) "
	+ " ) where rnum between ? and ? ";
	public static final String BOARD_VIEW  
	= " select no, title, content, writer, "
	+ " to_char(writeDate, 'yyyy.mm.dd') writeDate, hit "
	+ " from board where no = ? ";
	public static final String BOARD_WRITE 
	= " insert into board(no, title, content, writer) "
	+ " values(board_seq.nextval, ?, ?, ?) ";
	public static final String BOARD_UPDATE 
	= " update board set title = ?, content = ?, writer = ? where no = ? ";
	public static final String BOARD_DELETE
	= " delete from board where no = ? ";
	public static final String BOARD_INCREASE 
	= " update board set hit = hit + 1 where no = ? ";
	public static final String BOARD_GET_TOTALROW 
	= " select count(*) from board ";

	//게시판 댓글 리스트
	public static final String BOARD_REPLY_LIST
	= " select rnum, rno, no, content, writer, "
			+ " to_char(writeDate, 'yyyy.mm.dd') writeDate from( "
			+ " select rownum rnum, rno, no, content, writer, writeDate from ( "
			+ " select rno, no, content, writer, writeDate from board_reply "
			+ " where no = ? " 
			+ " order by rno desc "
			+ " ) "
			+ " ) where rnum between ? and ? ";
	
	public static final String BOARD_GET_REPLY_TOTALROW 
	= " select count(*) from board_reply "
			+ " where no = ? ";
	// 댓글 등록
	public static final String BOARD_REPLY_WRITE
	= " insert into board_reply(rno, no, content, writer ) "
			+ " values(board_reply_seq.nextval, ?, ?, ? ) ";

	// 공지 사항 쿼리----------------------------------------------------------------------
	//1. 리스트 - 번호, 제목, 공지시작일, 공지종료일, 최근 수정일
	public static final String NOTICE_LIST
	= " select rnum, no, title, "
	+ " to_char(startDate, 'yyyy.mm.dd') startDate, "
	+ " to_char(endDate, 'yyyy.mm.dd') endDate, "
	+ " to_char(updateDate, 'yyyy.mm.dd') updateDate "
	+ " from( "
		+ " select rownum rnum, no, title, startDate, endDate, updateDate from ( "
			+ " select no, title, startDate, endDate, updateDate from notice "
			+ " order by no desc "
		+ " ) "
	+ " ) where rnum between ? and ? ";
	public static final String NOTICE_GET_TOTALROW 
	= " select count(*) from notice ";
	public static final String NOTICE_VIEW  
	= " select no, title, content, "
	+ " to_char(startDate, 'yyyy.mm.dd') startDate,"
	+ " to_char(writeDate, 'yyyy.mm.dd') writeDate, "
	+ " to_char(endDate, 'yyyy.mm.dd') endDate, "
	+ " to_char(updateDate, 'yyyy.mm.dd') updateDate "
	+ " from notice where no = ? ";

	public static final String NOTICE_WRITE 
	= " insert into notice(no, title, content, startDate, endDate) "
	+ " values(notice_seq.nextval, ?, ?, ?, ?) ";

	public static final String NOTICE_UPDATE 
	= " update board set title = ?, content = ?, writeDate = ? where no = ? ";
	
	// 이미지 게시판 쿼리---------------------------------------
	//1. 리스트 - 번호, 제목, 작성자(작성자ID), 작성일, 파일명
		public static final String IMAGE_LIST
		= " select rnum, no, title, name, id, "
		+ " to_char(writeDate, 'yyyy.mm.dd') writeDate, fileName "
		+ " from( "
			+ " select rownum rnum, no, title, name, id, writeDate,fileName from ( "
				+ " select i.no, i.title, m.name, i.id, i.writeDate, i.fileName "
				+ " from image i, member m "
				+ " where i.id = m.id "
				+ " order by no desc "
			+ " ) "
		+ " ) where rnum between ? and ? ";
		// 1-1. 전체 데이터 개수 - 페이지 처리: 리스트
		public static final String IMAGE_GET_TOTALROW 
		= " select count(*) from image ";
		// 2. 이미지 보기 - 번호, 제목, 내용, 이름, 아이디, 작성일, 파일명
		public static final String IMAGE_VIEW 
		= " select i.no, i.title, i.content, m.name, i.id, "
				+ " to_char(i.writeDate, 'yyyy.mm.dd') writeDate, i.fileName "
				+ " from image i, member m "
				+ " where (no = ?) and (i.id = m.id) ";
				
	//3. 이미지 등록 - 번호, 제목, 작성자ID, 파일명 
	public static final String IMAGE_WRITE 
	= " insert into image(no, title, content, id, fileName) "
	+ " values(image_seq.nextval, ?, ?, ?, ?) ";
	//4-1. 이미지 파일 정보 수정 
	public static final String IMAGE_UPDATE_FILE 
	= " update image set fileName = ? where no = ? ";
	//5. 이미지 게시판 삭제
	public static final String IMAGE_DELETE
	= " delete from image where no = ? ";

	// QnA 쿼리-----------------------------------------------
	//1. 리스트 - 번호, 제목, 작성자(작성자ID), 작성일, 조회수, 들여쓰기
	public static final String QNA_LIST
	= " select rnum, no, title, name, id, "
	+ " to_char(writeDate, 'yyyy.mm.dd') writeDate, hit, levNo"
	+ " from( "
		+ " select rownum rnum, no, title, name, id, writeDate, hit, levNo from ( "
			+ " select q.no, q.title, m.name, q.id, q.writeDate, q.hit, q.levNo "
			+ " from qna q, member m "
			+ " where q.id = m.id "
			+ " order by q.refNo desc, q.ordNo "
		+ " ) "
	+ " ) where rnum between ? and ? ";
	
	public static final String QNA_GET_TOTALROW 
	= " select count(*) from notice ";
	//2. 질문 답변 보기 -- 답변 쓰기를 할떄도 동일한 쿼리를 사용하려고 한다. 추가 정보 - 관련글번호, 순서번호, 들여쓰기
	public static final String QNA_VIEW
	= " select q.no, q.title, q.content, m.name, q.id, q.writeDate, q.hit, "
			+ " q.refNo, q.ordNo, q.levNo "
			+ " from qna q, member m "
			+ " where (q.no = ?) and (m.id = q.id) ";
	//2-1. 조회수 1증가
	public static final String QNA_INCREASE
	= " update qna set hit = hit + 1 where no =?";
	//3-1. 질문하기
	public static final String QNA_QUESTION 
	= " insert into qna(no, title, content, id, refNo, ordNo, levNo, parentNo) "
			+ " values(qna_seq.nextval, ?, ?, ?, qna_seq.nextval, 1, 0, qna_seq.nextval) ";
	//3-2. 답변하기 --> 3-3을 먼저 한다.
	public static final String QNA_ANSWER 
	= " insert into qna(no, title, content, id, refNo, ordNo, levNo, parentNo) "
			+ " values(qna_seq.nextval, ?, ?, ?, ?, ?, ?, ?) ";
	//3-3. 답변하기의 경우 등록하기 전에 관련글 번호가 같고 순서번호가 같거나 큰 경우는 순서번호를 1증가 시킨다.
	public static final String QNA_ANSWER_INCREASE_ORDNO 
	= " update qna set ordNo = ordNo + 1 where refNo = ? and ordNo >= ? ";
	//5. 질문답변 삭제
	public static final String QNA_DELETE
	= " delete from qna where no = ? ";
	// 메시지 쿼리-----------------------------------------------------------------------
	// 메시지 리스트 - 번호, 보낸사람의 아이디, 보낸날짜, 받는 사람 아이디, 받은날짜
	public static final String MESSAGE_LIST
	= " select rnum, no, sender, "
	+ " to_char(sendDate, 'yyyy.mm.dd') sendDate, accepter, "
	+ " to_char(acceptDate, 'yyyy.mm.dd') acceptDate from ( "
		+ " select rownum rnum, no, sender, sendDate, accepter, acceptDate from ("
			+ " select no, sender, sendDate, accepter, acceptDate from message "
			+ " where sender = ? or accepter = ? "
			+ " order by no desc "
		+ " ) "
	+ " ) where rnum between ? and ? ";
	//2.
	public static final String MESSAGE_GET_TOTALROW 
	= " select count(*) from notice ";
	//3.
	public static final String MESSAGE_WRITE 
	= " insert into message(no, sender, content, accepter) "
		+ " values(message_seq.nextval, ?, ?, ?) ";
	//3. 읽기
	public static final String MESSAGE_VIEW  
	= " select no, content, sender, "
	+ " to_char(sendDate, 'yyyy.mm.dd') sendDate, accepter, "
	+ " to_char(acceptDate, 'yyyy.mm.dd') acceptDate "
	+ " from message where no = ? ";
	//3-1. 읽기 표시 처리 - 보려고 하는 글과 같고 받은 사람이 본인이어야 하고 받은 날짜가 없어야 한다.
	public static final String MESSAGE_VIEW_READ
	= " update message set acceptDate = sysdate "
			+ " where no = ? and accepter = ? and acceptDate is null ";
	//4. 삭제 = 수정을 만들지 않는다.
	public static final String MESSAGE_DELETE
	= " delete from message where no = ? ";
	
	//5. 새로운 메시지 개수 가져오기
	public static final String MESSAGE_GET_MESSAGE_CNT
	= " select count(*) from message where accepter = ? and acceptDate is null ";
	
	// 회원 관리 쿼리 ----------------------------------------------------------------------
	// 로그인 관리
	public static final String MEMBER_LOGIN	
	= " select m.id, m.name, m.gradeNo, g.gradeName from member m, grade g "
	+ " where (m.id = ? and m.pw = ?) and (m.gradeNo = g.gradeNo)";
	// 멤버 리스트
	public static final String MEMBER_LIST 
	= " select rnum, id, name, gender, "
	+ " to_char(birth, 'yyyy.mm.dd') birth, tel, status, gradeNo, gradeName from ( "
		+ " select rownum rnum, id, name, gender, birth, tel, status, gradeNo, gradeName from ( "
		+ " select m.id, m.name, m.gender, m.birth, m.tel, m.status, m.gradeNo, g.gradeName " 
		+ "from member m, grade g "
		+ " where m.gradeNo = g.gradeNo"
		+ " order by id "
		+ " ) "
		+ " ) where rnum between ? and ? ";
	// 회원 정보 보기
	public static final String MEMBER_VIEW  
	= " select m.id, m.name, m.gender, "
			+ " to_char(m.birth, 'yyyy.mm.dd')birth, m.tel, m.email, "
			+ " to_char(m.regDate, 'yyyy.mm.dd') regDate, m.status, m.gradeNo, g.gradeName "
			+ " from member m, grade g where id = ?  and ( m.gradeNo =  g.gradeNo ) ";
	// 회원 가입
	public static final String MEMBER_JOIN
	= " insert into join(id, pw, name, gender, birth, tel, email)"
			+ " values( ?, ?, ?, ?, ?, ?, ?) ";
	
		// 회원등급 수정
	public static final String MEMBER_GRADE_MODIFY
	= " update member set gradeNo = ? where id = ? ";
	
	//아이디 중복 체크
	public static final String MEMBER_CHECK_ID
	= "select id from member where id = ?";

}
