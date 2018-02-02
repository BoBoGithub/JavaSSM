<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<jsp:include page="../common/header.jsp"  flush="true" />
	<p class="lead">这是帮助中心主页面...</p>
<jsp:include page="../common/footer.jsp"  flush="true" />