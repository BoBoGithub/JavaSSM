<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<html>
<head>
	<title>后台管理</title>
	<script type="text/javascript" src="${basePath}/resources/js/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/layer/layer.js"></script>
	<script type="text/javascript" src="${basePath}/resources/common/common.js"></script>
	<link href="${basePath}/resources/js/date/bootstrap.min.css" rel="stylesheet">
</head>
<body>

