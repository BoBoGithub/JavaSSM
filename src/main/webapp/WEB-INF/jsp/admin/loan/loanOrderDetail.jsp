<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<link href="${basePath}/resources/css/style.css" rel="stylesheet">

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
    	<div class="col-sm-12">
    		<div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h4>订单详情</h4>
                </div>
                <div class="ibox-content">
					<div class="panel panel-default">
					    <div class="panel-heading">
					        <h3 class="panel-title">借款单信息</h3>
					    </div>
					    <table class="table table-bordered table-condensed table-hover">
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>订单ID</strong></td><td style="text-align:left;width:100px;">${borrowRecord.brecordId}</td>
					        	<td style="text-align:right;width:100px;"><strong>借款状态</strong></td><td style="text-align:left;width:100px;">${borrowRecord.orderStatus}</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>借款金额</strong></td><td style="text-align:left;width:100px;">${borrowRecord.borrowMoney}元</td>
					        	<td style="text-align:right;width:100px;"><strong>借款期限</strong></td><td style="text-align:left;width:100px;">${borrowRecord.borrowLimit}天</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>利息</strong></td><td style="text-align:left;width:100px;">${borrowRecord.borrowInterest}元</td>
					        	<td style="text-align:right;width:100px;"><strong>信审费</strong></td><td style="text-align:left;width:100px;">${borrowRecord.checkMoney}元</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>服务费</strong></td><td style="text-align:left;width:100px;">${borrowRecord.serviceCharge}元</td>
					        	<td style="text-align:right;width:100px;"><strong>应还总额</strong></td><td style="text-align:left;width:100px;">${borrowRecord.totalMoney}元</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>申请时间</strong></td><td style="text-align:left;width:100px;">${borrowRecord.applyTime}</td>
					        	<td></td><td></td>
					        </tr>
					    </table>
					</div>
					
					<div class="panel panel-default">
					    <div class="panel-heading">用户信息</div>
					     <table class="table table-bordered table-hover">
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>用户ID</strong></td><td style="text-align:left;width:100px;">${userInfo.uid}</td>
					        	<td style="text-align:right;width:100px;"><strong>用户姓名</strong></td><td style="text-align:left;width:100px;">${userInfo.realName}</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>手机号</strong></td><td style="text-align:left;width:100px;">${userInfo.mobile}</td>
					        	<td style="text-align:right;width:100px;"><strong>性别</strong></td><td style="text-align:left;width:100px;">
					        		<c:if test="${userInfo.sex == 1}">男</c:if>
					        		<c:if test="${userInfo.sex == 2}">女</c:if>
					        		<c:if test="${userInfo.sex == 0}">－</c:if>
					        	</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>绑卡银行</strong></td><td style="text-align:left;width:100px;">${userInfo.bankName}</td>
					        	<td></td><td></td>
					        </tr>
					    </table>
					</div>
					
					<div class="panel panel-default">
					    <div class="panel-heading">还款计划信息</div>
					     <table class="table table-bordered table-hover">
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>还款ID</strong></td><td style="text-align:left;width:100px;">${borrowRecord.brecordId}</td>
					        	<td style="text-align:right;width:100px;"><strong>还款状态</strong></td><td style="text-align:left;width:100px;">${borrowRecord.orderStatus}</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>借款金额</strong></td><td style="text-align:left;width:100px;">${borrowRecord.borrowMoney}元</td>
					        	<td style="text-align:right;width:100px;"><strong>应还金额</strong></td><td style="text-align:left;width:100px;">${borrowRecord.totalMoney}元</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>利息</strong></td><td style="text-align:left;width:100px;">${borrowRecord.borrowInterest}元</td>
					        	<td style="text-align:right;width:100px;"><strong>实际还款</strong></td><td style="text-align:left;width:100px;">-元</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>服务费</strong></td><td style="text-align:left;width:100px;">${borrowRecord.serviceCharge}元</td>
					        	<td style="text-align:right;width:100px;"><strong>逾期费用</strong></td><td style="text-align:left;width:100px;">${borrowRecord.overdueCharge}元</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>期数</strong></td><td style="text-align:left;width:100px;">${borrowRecord.borrowLimit}天</td>
					        	<td style="text-align:right;width:100px;"><strong>逾期天数</strong></td><td style="text-align:left;width:100px;">${borrowRecord.overdueDay}天</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>应还日期</strong></td><td style="text-align:left;width:100px;">${borrowRecord.repayTime}</td>
					        	<td style="text-align:right;width:100px;"><strong>实际还款日期</strong></td><td style="text-align:left;width:100px;">${borrowRecord.realRepayTime}</td>
					        </tr>
					    </table>
					</div>
					<div class="form-group">
					    <div class="col-sm-offset-1 col-sm-13">
					      <button type="button" class="btn btn-primary marR10" id="btn">返回</button>
					    </div>
					  </div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	$("#btn").click(function(){
		//返回地址
 		 window.history.back();
	});
</script>