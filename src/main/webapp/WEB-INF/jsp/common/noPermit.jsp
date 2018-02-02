<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<jsp:include page="header.jsp"  flush="true" />
<div class="middle-box text-center animated fadeInDown"  style="padding:100px;">
    <h1 style="font-size:80px;">无权限访问</h1>
	<div class="alert alert-success">
		<a  class="alert-link">您没有权限访问当前页面</a>
	</div>
</div>
<jsp:include page="footer.jsp"  flush="true" />