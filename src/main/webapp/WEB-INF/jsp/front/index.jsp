<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<html class="app js no-touch no-android chrome no-firefox no-iemobile no-ie no-ie10 no-ie11 no-ios no-ios7 ipad">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Google Chrome Frame也可以让IE用上Chrome的引擎: -->
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<link href="/favicon.ico" type="image/x-icon" rel=icon>
<link href="/favicon.ico" type="image/x-icon" rel="shortcut icon">
<meta name="renderer" content="webkit">
<title>登陆－现金贷</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"	href="${basePath}/resources/admin_files/min.css">
<link rel="stylesheet"	href="${basePath}/resources/admin_files/login.css">
<script src="${basePath}/resources/js/jquery/jquery-1.8.3.js"></script> 
<script src="${basePath}/resources/js/layer-v1.9.2/layer/layer.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/layer/layer.js"></script>
<link href="${basePath}/resources/js/date/bootstrap.min.css" rel="stylesheet">
<link	href="${basePath}/resources/admin_files/css.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]> 
	<script src="${basePath}/resources/js/jquery/ie/html5shiv.js"></script> 
	<script src="${basePath}/resources/js/jquery/ie/respond.min.js"></script>
<![endif]-->
</head>
<body style="background-image: url('${basePath}/resources/admin_files/2zrdI1g.jpg');margin-top:0px;background-repeat:no-repeat;background-size: 100% auto;" onkeydown="enterLogin();">
	<div id="loginbox" style="padding-top: 10%;">
		<H1>这是前端-首页</H1>
		<h3>用户名：${user.username}</h3>
		<h3>手机号：${user.mobile}</h3>
		<h3>真实姓名：${user.realname}</h3>
		
		<div style="padding:20px;">
				<a href="javascript:void(0)" class="btn btn-default" id="search">退出登陆</a> 
		</div>
	</div>
	<script style="text/javascript">
		$("#search").click(function(){
			  $.ajax({
	              type: 'post',
	              url: '${basePath}/front/user/logout',
	              dataType: 'json',
	              cache:false,
	              timeout: 60000, 
	              success: function (json) {
	            	  if(json.errno == 0 && json.data.isLogin == 0){
	            		  location.href = "${basePath}/front/user/login";
	            	  }else{
	            		  layer.msg(json.errmsg);
	            	  }
	              }
	          });
		});
	</script>
</body>
</html>
