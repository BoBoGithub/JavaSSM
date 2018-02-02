<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<jsp:include page="common/header.jsp"  flush="true" />
<div class="middle-box text-center animated fadeInDown"  style="padding:100px;">
    <h1 style="font-size:80px;">404</h1>
	<div class="alert alert-success">
		<a href="${basePath}" class="alert-link">页面不存在或发生错误</a>
	</div>
</div>
<jsp:include page="common/footer.jsp"  flush="true" />