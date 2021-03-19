<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
<link rel="icon" href="/favicon.ico" type="image/x-icon">
<meta charset="UTF-8">
<title>질문하기</title>

  
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
		// 제목 4자 이상
		if(!checkLength($("#title"), "제목" , 4)) return false;
		// 내용 4자 이상
		if(!checkLength($("#content"), "내용" , 4)) return false;
		// submit 이벤트 취소
	});
	
});


</script>

</head>
<body>
<div class="container">
	
	<h1>질문하기</h1>
	<form action="question.jsp" id="writeForm" method="post">
		<div class="form-group">
			<!-- 제목 -->
			<label for="title">제목</label>
			<input name="title" class="form-control" id="title" required="required"
			placeholder="제목을 4자 이상 입력 하셔야 합니다.">
		</div>
		
		<div class="form-group">
			<!-- 내용 -->
			<label for="content">내용</label>
			<textarea rows="7" name="content" class="form-control" id="content" required="required"
			 placeholder="내용을 4자 이상 입력 하셔야 합니다."></textarea>
		</div>		
		
<button >등록</button>
<button type="reset" >새로입력</button>
<button type="button" id="cancelBtn">취소</button>
</form>
</div>
</body>
</html>