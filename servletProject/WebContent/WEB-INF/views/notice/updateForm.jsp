<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
<link rel="icon" href="/favicon.ico" type="image/x-icon">
<meta charset="UTF-8">
<title>공지 글수정 폼</title>

  <!-- Bootstrap 라이브러리 등록 - CDN방식 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  
  <!--  formUtil.js 등록 -->
  <script type="text/javascript" src="../js/formUtil.js"></script>
  
<script type="text/javascript">

$(function(){
	
	$("#cancelBtn").click(function(){
		//alert("취소");
		//이전 페이지로 이동
		history.back();
	});
	
	// submit() 이벤트에 데이터 검사
	$("#writeForm").submit(function(){
		//alert("데이터 전달 이벤트");
		
		// 필수 입력
		// 제목
		if(!require($("#title"), "제목")) return false;
		// 내용
		if(!require($("#content"), "내용")) return false;
		// 작성자
		if(!require($("#writer"), "작성자")) return false;
		// 길이
		// 제목 4자 이상
		if(!checkLength($("#title"), "제목" , 4)) return false;
		// 내용 4자 이상
		if(!checkLength($("#content"), "내용" , 4)) return false;
		// 작성자 2자 이상
		if(!checkLength($("#writer"), "작성자" , 2)) return false;
		// submit 이벤트 취소
	});
	
});


</script>

</head>
<body>
<div class="container">
	
	<h1>공지 글수정</h1>
	<form action="update.do" id="writeForm" method="post">
	<input name="page" type="hidden" value="${pageObject.page }">
	<input name="perPageNum" type="hidden" value="${pageObject.perPageNum }">
		<div class="form-group">
			<!-- 제목 -->
			<label for="no">번호</label>
			<input name="no" class="form-control" id="no" readonly="readonly" 
			value="${vo.no }">
		</div>
		<div class="form-group">
			<!-- 제목 -->
			<label for="title">제목</label>
			<input name="title" class="form-control" id="title" required="required"
			placeholder="제목을 4자 이상 입력 하셔야 합니다." value="${vo.title }">
		</div>
		
		<div class="form-group">
			<!-- 내용 -->
			<label for="content">내용</label>
			<textarea rows="7" name="content" class="form-control" id="content" required="required"
			 placeholder="내용을 4자 이상 입력 하셔야 합니다." >${vo.content }</textarea>
		</div>		
		
		<div class="form-group">
			<label for="startDate">공지시작일</label>
			<input name="startDate" id="startDate" class="form-control" type="date"
			value="${vo.startDate }">
		</div>
		<div class="form-group">
			<label for="endDate">공지종료일</label>
			<input name="endDate" id="endDate" class="form-control" type="date"
			value="${vo.endDate }">
		</div>
		
		
<button>수정</button>
<button type="reset" >새로입력</button>
<button type="button" id="cancelBtn">취소</button>
</form>
</div>
</body>
</html>