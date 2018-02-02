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
<title>登陆－现金贷后台管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"	href="${basePath}/resources/admin_files/min.css">
<link rel="stylesheet"	href="${basePath}/resources/admin_files/login.css">
<script src="${basePath}/resources/js/jquery/jquery-1.8.3.js"></script> 
<script src="${basePath}/resources/js/layer-v1.9.2/layer/layer.js"></script>
<link	href="${basePath}/resources/admin_files/css.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]> 
	<script src="${basePath}/resources/js/jquery/ie/html5shiv.js"></script> 
	<script src="${basePath}/resources/js/jquery/ie/respond.min.js"></script>
<![endif]-->
</head>
<body onload="javascript:topLoad()" style="background-image: url('${basePath}/resources/admin_files/2zrdI1g.jpg');margin-top:0px;background-repeat:no-repeat;background-size: 100% auto;" onkeydown="enterLogin();">
	<div id="loginbox" style="padding-top: 10%;">
		<form id="loginform" name="loginform" class="form-vertical" style="background-color: rgba(0, 0, 0, 0.5) !important; background: #000; filter: alpha(opacity = 50); *background: #000; *filter: alpha(opacity = 50); /*黑色透明背景结束*/ color: #FFF; bottom: 0px; right: 0px; border: 1px solid #000;" action="${basePath}/resources/login.shtml" method="post">
			<div class="control-group normal_text">
				<table style="width: 100%">
					<tr>
						<td align="left">
							<img src="${basePath}/resources/admin_files/logo_left.png" alt="Logo">
						</td>
						<td align="center" style="font-weight: bold;color: gray;">现金贷管理后台登陆</td>
						<td align="right">
							<img src="${basePath}/resources/admin_files/logo_left.png" alt="Logo">
						</td>
					</tr>
				</table>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly" style="background: #28b779">
							<img src="${basePath}/resources/admin_files/account_1.png" alt="请输入账号..">
						</span><input type="text" placeholder="登陆用户" name="username" id="username" value="" style="height: 32px; margin-bottom: 0px;"/>
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly">
							<img src="${basePath}/resources/admin_files/lock_1.png" alt="请输入密码..">
						</span><input type="password" placeholder="登陆密码" name="password" id="password" value="" style="height: 32px; margin-bottom: 0px;" />
					</div>
				</div>
			</div>
			<div class="form-actions">
				<span class="pull-left" style="width: 33%"><a href="#" class="flip-link btn btn-info" id="to-recover">忘记密码？</a></span>
				<span class="pull-left" style="width: 33%"><a style="display:none;" class="flip-link btn btn-danger" id="to-recover">一键初始化</a></span>
				<span class="pull-right"><a type="button" href="javascript:checkUserForm()" class="btn btn-success">登&nbsp;&nbsp;录</a></span>
			</div>
		</form>
	</div>
	
	<script type="text/javascript">
		//处理登陆请求
		function checkUserForm() {
			//获取用户名
			$(this).html("登录中...");

	          // 登陆操作
	          $.ajax({
	              type: 'post',
	              url: '${basePath}/user/dologin',
	              dataType: 'json',
	              cache:false,
	              data:{  
	            	  username:$.trim($("#username").val()),  
	            	  password: $.trim($("#password").val()),
	            	  //captcha: $.trim($('#captcha').val())
	              },  
	              timeout: 60000, 
	              success: function (json) {
						if(json.errno == 0 && json.data.isLogin){
							window.location.href="${basePath}/main/index";
						}else{
							layer.msg(json.errmsg);
						}
	              }
	          });
		}
		
		//回车时，默认是登陆
		 function enterLogin(){
			 if(window.event.keyCode == 13){
				 checkUserForm();
			 }
		 }
		
		//刷新
		function topLoad(){
			if(window != top){
		        top.location.href=location.href;
		    }
		}
		
		//默认选中
		$('#username').select();
	</script>
</body>
</html>
