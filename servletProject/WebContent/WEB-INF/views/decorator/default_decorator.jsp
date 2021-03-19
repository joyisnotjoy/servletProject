<!-- sitemesh 사용을 위한 설정 파일 -->
<!-- 작성자 : 이영환 -->
<!-- 작성일 : 2020-06-30 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
System.out.println("default_decorator.do [path] : " + request.getContextPath());
request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>웹짱:<decorator:title /></title>
<!--  BootStrap 라이브러리 등록 전체적으로 진행을 했다. filter가 적용이 되면 개별적으로 한것은 다 지워야 한다. -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
header, footer {
	background: AntiqueWhite;
}

pre {
	background: white;
	border: 0px;
}

/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}

/* Add a gray background color and some padding to the footer */
footer {
	background-color: black;
	padding: 25px;
	color: #ddd;
}

.carousel-inner img {
	width: 100%; /* Set width to 100% */
	margin: auto;
	min-height: 200px;
}

/* Hide the carousel text when the screen is less than 600 pixels wide */
@media ( max-width : 600px) {
	.carousel-caption {
		display: none;
	}
/*  	#Log_image{ */ 
/* 		display: none; */
/* 	} */
}

article {
	min-height: 400px;
	margin-top: 80px;
	margin-bottom: 80px;
}

#welcome {
	color: grey;
	margin: 0 auto;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		<c:if test="${!empty login}">
		getMessageCnt();
		var myVar = setInterval(getMessageCnt, 3000);
		function getMessageCnt() {
			// 서버에 가서 사용자가 받은 새로운 메시지의 개수를 가져오는 처리
			$("#messageCnt").load("/ajax/getMessageCnt.do");
			function(result, status){
				console.log(status);
				if(status =="error"){
					clearTimeout(myVar);
					// 경고를 띄운다.
					alert("세션이 끊어졌습니다. 다시 로그인 하세요.");
					// 경고의 확인을 누르면 로그인으로 이동시킨다.
					location = "/member/loginForm.do";
				}
				}
			);
		}
		</c:if>
	});
</script>
<decorator:head/>
</head>
<body>
	<header>
<!-- 		<div id="log_image"><img src="/upload/image/dog01.jpg"/></div> -->
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#myNavbar">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/">Logo</a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<li><a href="${path }/notice/list.do">공지사항</a></li>
						<li><a href="${path }/image/list.do">이미지</a></li>
						<li><a href="${path }/board/list.do">게시판</a></li>
						<!-- &amp; - &, &lt; -> < , &gt; -> >, &nbsp; blank -->
						<li><a href="${path }/qna/list.do">Q&amp;A</a></li>
						<c:if test="${!empty login }">
						<!-- 로그인이 되어 있는 경우의 메뉴 -->
						<li><a href="${path }/message/list.do">메시지</a></li>
						</c:if>
					</ul>
				    <!--  메인 메뉴 부분의 로그인 사용자 정보 -->
				    <ul class="nav navbar-nav navbar-right">
				    <c:if test="${empty login }">
			    	 	<li><a href="${path }/member/joinForm.do">
			    	 	<span class="glyphicon glyphicon-user"></span> 회원가입</a>
			     		<li><a href="${path }/member/loginForm.do"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
			    	 	</c:if>
				    <c:if test="${!empty login }">
			    	 	<!-- 로그인이 되어있는 경우의 메뉴 -->
			    	 	<li>
			    	 		<a href="#">
			    	 		<span class="glyphicon glyphicon-user"></span> ${login.name }
							<span class="badge" id="messageCnt">0</span>
						</a>			    	
			    	 	</li>
			     		<li>
			     			<a href="${path }/member/logout.do"><span class="glyphicon glyphicon-log-out"></span> Logout</a>
			     		</li>
			    	 	</c:if>
		   			</ul>
				</div>
			</div>
		</nav>
	</header>
	<article>
		<decorator:body />
	</article>
	<footer class="container-fluid text-center navbar navbar-inverse navbar-fixed-bottom">
		<p>이 홈페이지의 저작권은 이영환에게 있습니다.</p>
	</footer>
</body>
</html>