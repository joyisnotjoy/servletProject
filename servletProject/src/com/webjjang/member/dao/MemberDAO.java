package com.webjjang.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.util.db.DBInfo;
import com.webjjang.util.db.DBSQL;

public class MemberDAO {

	// 필요한 객체 선언
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 로그인 처리를 위한 메소드
	public LoginVO login(LoginVO vo) throws Exception{
		LoginVO loginVO = null;
		
		try {
			//1. 2.
			con = DBInfo.getConnection();
			// 3. 4.
			pstmt = con.prepareStatement(DBSQL.MEMBER_LOGIN);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			// 5. 실행
			rs = pstmt.executeQuery();
			// 6. 표시
			if(rs != null && rs.next()) {
				loginVO = new LoginVO();
				loginVO.setId(rs.getString("id"));		
				loginVO.setName(rs.getString("name"));		
				loginVO.setGradeNo(rs.getInt("gradeNo"));		
				loginVO.setGradeName(rs.getString("gradeName"));		
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception(" 로그인 DB 처리 중 오류");
		} finally {
			DBInfo.close(con, pstmt, rs);
		}
		
		return loginVO;
	}
	
	// 회원 리스트를 위한 메소드
	public List<MemberVO> list() throws Exception{
		List<MemberVO> list = null;
		
		try {
			con = DBInfo.getConnection();
			
			pstmt = con.prepareStatement(DBSQL.MEMBER_LIST);
			pstmt.setLong(1, 1);
			pstmt.setLong(2, 10);
			rs = pstmt.executeQuery();
			System.out.println("list확인");
			if(rs != null) {
				while(rs.next()) {
					if(list == null) list = new ArrayList<>();
					MemberVO vo = new MemberVO();
					vo.setId(rs.getString("id"));
					vo.setName(rs.getString("name"));
					vo.setGender(rs.getString("gender"));
					vo.setBirth(rs.getString("birth"));
					vo.setTel(rs.getString("tel"));
					vo.setStatus(rs.getString("status"));
					vo.setGradeNo(rs.getInt("gradeNo"));
					vo.setGradeName(rs.getString("gradeName"));
					list.add(vo);
				}
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception(" 회원 리스트 DB 처리중 오류");
		}finally {
			DBInfo.close(con, pstmt, rs);
		}
		return list;
	}
	// 회원 등급 수정 
		public int gradeModify(MemberVO vo) throws Exception{
			System.out.println("MemberDAO.gradeModify().vo:" + vo);
			int result = 0;
			
			try {
				//1. 2.
				con = DBInfo.getConnection();
				// 3. 4.
				//실행 쿼리 확인
				System.out.println("MemberDAO.gradeModify().DBSQL.MEMBER_GRADE_MODIFY: " 
				+ DBSQL.MEMBER_GRADE_MODIFY);
				pstmt = con.prepareStatement(DBSQL.MEMBER_GRADE_MODIFY);
				// sql 쿼리에 포함되어 있는 ? 의미에 맞는 데이터를 순서대로 세팅
				pstmt.setInt(1, vo.getGradeNo());
				pstmt.setString(2, vo.getId());
				// 5. 실행
				// - insert, update, delete 쿼리 실행 - int pstmt.executeQuery()
				// - select - ResultSet pstmt.executeQuery()
				result = pstmt.executeUpdate();
				// result가 1이면 수정이 일어난다. result가 0이면 수정되지 않는다.(조건에 맞는 데이터가 없다.)
				System.out.println("MemberDAO.gradeModify().result :" + result);
				// 6. 표시
				if(result == 1) {
					System.out.println("MemberDAO.gradeModify() - 회원등급 수정 완료");
				} else {
					throw new Exception("조건에 맞는 데이터(id)가 존재하지 않습니다.");
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new Exception(" 회원등급 변경 DB 처리 중 오류");
			} finally {
				DBInfo.close(con, pstmt);
			}
			
			System.out.println("MemberDAO.gradeModify().result :" + result);
			return result;
		}
		
		// 2. 회원 정보 보기
		 public MemberVO view(String id) throws Exception {
			 System.out.println("MemberDAO.view().id: " + id);
			 // 결과를 저장해서 넘겨 줘야 할 변수. 데이터 없어서 초기값 null을 세팅. 숫자면 0을 세팅
			 MemberVO vo = null;
	      try {
	         // 1.드라이버 확인 + 2.연결객체
	         con = DBInfo.getConnection();
	         // 3.sql 작성 -> DBSQL에서 작성 완료 + 4.실행 객체+데이터 셋팅
	         pstmt = con.prepareStatement(DBSQL.MEMBER_VIEW);
	         pstmt.setString(1,id);
	         // 5.실행 -> 데이터가 한 개 나오므로 반복문 필요 없음
	         rs = pstmt.executeQuery();
	         // result가 1이면 수정이 일어난다.
	         // result가 0이면 수정이 일어난다.
	         System.out.println("MemberDAO.view().rs : " + rs);
	         // 6.데이터 표시 -> 데이터 담기
	         if(rs != null && rs.next()) {
	        	 // 위에 메소드 바로 아래 리턴 타입과 같은 vo를 생성해서 데이터를 담는다.
	            vo = new MemberVO();
	            vo.setId(rs.getString("id"));
	            vo.setName(rs.getString("name"));
	            vo.setGender(rs.getString("gender"));
	            vo.setBirth(rs.getString("birth"));
	            vo.setTel(rs.getString("tel"));
	            vo.setEmail(rs.getString("email"));
	            vo.setRegDate(rs.getString("regDate"));
	            vo.setStatus(rs.getString("status"));
	            vo.setGradeNo(rs.getInt("gradeNo"));
	            vo.setGradeName(rs.getString("gradeName"));
	         }
	      }catch (Exception e) {
	         e.printStackTrace();
	         throw new Exception("회원 정보 보기 실행 중 DB 처리 오류");
	      }finally {
	         // 7.닫기
	         DBInfo.close(con, pstmt, rs);
	      }
	      System.out.println("MemberDAO.view().vo: " + vo);
	      return vo;
	   }
			public Integer join(MemberVO vo) throws Exception {
				// TODO Auto-generated method stub
				Integer result = 0;
				
				try {
					// 1. 2. 
					con = DBInfo.getConnection();
					// 3. 4.
					// 실행쿼리 확인
					System.out.println("MemberDAO.write().DBSQL.MEMBER_VIEW: "
					+ DBSQL.MEMBER_JOIN);
					pstmt = con.prepareStatement(DBSQL.MEMBER_JOIN);
					// sql 쿼리에 포함되어 있는 ? 의미에 맞는 데이터를 순서대로 셋팅
					pstmt.setString(1, vo.getId());
					pstmt.setString(2, vo.getPw());
					pstmt.setString(3, vo.getName());
					pstmt.setString(4, vo.getGender());
					pstmt.setString(5, vo.getBirth());
					pstmt.setString(6, vo.getTel());
					pstmt.setString(7, vo.getEmail());
					// 5. 실행
					//  - insert, update, delete 쿼리 실행 - int pstmt.executeUpdate()
					//  - select - ResultSet pstmt.executeQuery()
					result = pstmt.executeUpdate();
					
					// 6. 표시
					System.out.println("MemberDAO.join() - 회원가입 성공");
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					throw new Exception("MemberDAO Error - 회원 가입 DB 처리 중 오류");
				} finally {
					DBInfo.close(con, pstmt);
				}
				
				System.out.println("MemberDAO.join().result : " + result);
				return result;
			}

			//아이디 중복 체크
			public String checkId(String id) throws Exception {
				// TODO Auto-generated method stub
				String result = null;
				
				try {
					// 1. 2. 
					con = DBInfo.getConnection();
					// 3. 4.
					// 실행쿼리 확인
					System.out.println("MemberDAO.join().DBSQL.MEMBER_VIEW: "
					+ DBSQL.MEMBER_JOIN);
					pstmt = con.prepareStatement(DBSQL.MEMBER_CHECK_ID);
					// sql 쿼리에 포함되어 있는 ? 의미에 맞는 데이터를 순서대로 셋팅
					pstmt.setString(1, id);
					// 5. 실행
					//  - insert, update, delete 쿼리 실행 - int pstmt.executeUpdate()
					//  - select - ResultSet pstmt.executeQuery()
					rs = pstmt.executeQuery();
					
					// 6. 표시
					if(rs != null && rs.next())
						result = rs.getString("id");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					throw new Exception("MemberDAO Error - 아이디 중복 체크DB 처리 중 오류");
				} finally {
					DBInfo.close(con, pstmt, rs);
				}
				
				System.out.println("MemberDAO.checkId().result : " + result);
				return result;
			}


}
