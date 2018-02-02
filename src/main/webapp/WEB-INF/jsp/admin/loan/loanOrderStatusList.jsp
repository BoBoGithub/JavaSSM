<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<jsp:include page="../../common/header.jsp"  flush="true" />

<section class="panel panel-default">
			<div class="panel-body">
		<table class="table table-hover">
		  <caption>借款单状态明细</caption>
		  <thead>
		    <tr>
		      <th>状态项</th>
		      <th>操作人</th>
		      <th>时间</th>
		    </tr>
		  </thead>
		  <tbody>
		 	 <c:forEach var="orderList" items="${orderStatusList}">
			    <tr>
			      <td>${orderList.title}</td>
			      <td>${orderList.name}</td>
			      <td>${orderList.time}</td>
			    </tr>
 		  </c:forEach>
		  </tbody>
		</table>
</div>

<footer class="panel-footer text-right bg-light lter">
	<button type="button" class="btn btn-danger btn-s-xs" id="closeWin">关闭</button>
</footer> 
			
</section>

<script>
	//关闭操作
	$('#closeWin').click(function(){
          	//获取窗口索引
				var index = parent.layer.getFrameIndex(window.name); 
          	
		//关闭当前窗口
			    parent.layer.close(index);
	});
</script>
<jsp:include page="../../common/footer.jsp"  flush="true" />