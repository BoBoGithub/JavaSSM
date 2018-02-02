<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<!DOCTYPE html>
<html class="app">
<head>
<title>管理后台－现金贷</title>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="/favicon.ico" type="image/x-icon" rel="shortcut icon">
<link rel="stylesheet" href="${basePath}/resources/notebook/notebook_files/font.css" type="text/css">
<link rel="stylesheet" href="${basePath}/resources/notebook/notebook_files/app.v1.css" type="text/css">
<link rel="stylesheet" href="${basePath}/resources/css/numberONe.css" type="text/css">
<!-- base start 重要部分不可删改-->
<script type="text/javascript" src="${basePath}/resources/notebook/notebook_files/app.v1.js"></script>
<script type="text/javascript" src="${basePath}/resources/notebook/notebook_files/app.plugin.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/jquery/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/jquery/jquery-validation/messages_cn.js"></script>
<!--  <script type="text/javascript" src="${basePath}/resources/js/layer-v1.9.2/layer/layer.js"></script>-->
<script type="text/javascript" src="${basePath}/resources/js/layer/layer.js"></script>
<script type="text/javascript" src="${basePath}/resources/common/common.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/jquery/underscore.js"></script>
<script type="text/javascript" src="${basePath}/resources/common/lyGrid.js"></script>
<!--[if lt IE 9]> <script src="${basePath}/resources/js/jquery/ie/html5shiv.js"></script> <script src="${basePath}/resources/js/jquery/ie/respond.min.js"></script><![endif]-->
<link href="${basePath}/resources/js/date/bootstrap.min.css" rel="stylesheet">
<link href="${basePath}/resources/js/date/font-awesome.min.css" rel="stylesheet">
<!-- <script type="text/javascript" src="${basePath}/resources/js/system/user/updatePassword.js"></script> -->
<style type="text/css">
.l_err{
    background: none repeat scroll 0 0 #FFFCC7;
    border: 1px solid #FFC340;
    font-size: 12px;
    padding: 4px 8px;
    width: 200px;
    display: none;
}
.error{
  border: 3px solid #FFCCCC;
}
.form-group{
  padding-left: 15px
}
.left{
	text-align: left;
	padding-left: 10px;
}
.right{
	text-align: right;
}
.hidden-xs{
	display: inherit;
}
.gray-bg {
  background-color: #f3f3f4;
}
</style>
<!-- base end -->
<script type="text/javascript">
//设置Host
var rootPath = "${basePath}";
var resPath		= "${basePath}/resources";

function onloadurl(){
	return true;
	$("[data-url]").each(function () {
		var tb = $(this);
		tb.html(Common.loadingImg());
		tb.load(rootPath+tb.attr("data-url"));
    });
}
layer.config({
    extend: ['default/layer.css'], //加载新皮肤
    fix : false,// 用于设定层是否不随滚动条而滚动，固定在可视区域。
    skin: 'layer-ext-myskin' //一旦设定，所有弹层风格都采用此主题。
});
</script>
<!-- 返回顶部 -->
<script type="text/javascript" src="${basePath}/resources/js/scrolltopcontrol.js"></script>
<script type="text/javascript">
	$(function() {
		//修改在手机上点击菜单后菜单不关闭问题
		var winwidth = $("body").width();
		if(winwidth<770){
		  $("#nav ul.lt li").click(function(){
			$("#nav").removeClass("nav-off-screen");
		 });
		}

		var tb = $("#loadhtml");
		tb.html(Common.loadingImg());
		tb.load(rootPath+"/main/welcome");
	});
</script>
</head>
<body>
	<section class="vbox">
		<jsp:include page="common/mainTopHeader.jsp"  flush="true" />
		<section>
			<section class="hbox stretch">
				<aside class="bg-dark lter aside-md hidden-print" id="nav">
					<jsp:include page="common/mainLeftMenu.jsp"  flush="true" />
				</aside>
				<section id="content">
					<section id="id_vbox" class="vbox gray-bg">
						<ul class="breadcrumb no-border no-radius b-b b-light" id="topli"></ul>
						<section class="scrollable " style="margin-top: 35px;"><div id="loadhtml" ></div></section>
					</section>
				</section>
				<aside class="bg-light lter b-l aside-md hide" id="notes">
					<div class="wrapper">Notification</div>
				</aside>
			</section>
		</section>
	</section>
	<!-- Bootstrap -->
	<div id="flotTip" style="display:none ; position: absolute;">－－底部－－</div>
	<script style="text/javascript">Common.checkUserStatus();</script>
</body>
</html>