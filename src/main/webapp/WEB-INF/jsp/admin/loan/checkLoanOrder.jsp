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
                    <h4>审核借款单</h4>
                </div>
                <div class="ibox-content">
					<div class="panel panel-default">
					    <div class="panel-heading">
					        <h3 class="panel-title">实名信息</h3>
					    </div>
					    <table class="table table-bordered table-condensed table-hover">
					        <tr>
					        	<td style="text-align:right;width:20%;"><strong>姓名</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.realname}</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>性别</strong></td><td style="text-align:left;width:100px;"><c:if test="${userBaseInfo.sex == 1}">男</c:if><c:if test="${userBaseInfo.sex == 2}">女</c:if></td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>民族</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.userNation}</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>身份证号</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.cardnum}</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>住址</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.cardAddress}</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>出生年月</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.birthday}</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>签发证机关</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.cardOffice}</td>
					        </tr>
					         <tr>
					        	<td style="text-align:right;width:100px;"><strong>有效期限</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.cardExpire}</td>
					        </tr>
					    </table>
					</div>
					
					<div class="panel panel-default">
					    <div class="panel-heading">基础信息</div>
					     <table class="table table-bordered table-hover">
					        <tr>
					        	<td style="text-align:right;width:20%;;"><strong>学历</strong></td><td style="text-align:left;width:100px;">
					        		<c:if test="${userBaseInfo.degree == 0}">初中及以下</c:if>
					        		<c:if test="${userBaseInfo.degree == 1}">高中或中专</c:if>
					        		<c:if test="${userBaseInfo.degree == 2}">专科</c:if>
					        		<c:if test="${userBaseInfo.degree == 3}">本科</c:if>
					        		<c:if test="${userBaseInfo.degree == 4}">研究生及以上</c:if>
					        	</td>
					        </tr>
							<tr>
					        	<td style="text-align:right;width:100px;"><strong>婚姻</strong></td><td style="text-align:left;width:100px;">
					        		<c:if test="${userBaseInfo.marriage == 0}">未婚</c:if>
					        		<c:if test="${userBaseInfo.marriage == 1}">已婚</c:if>
					        	</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>子女</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.child}个</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>居住地址</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.address}</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>居住时长</strong></td><td style="text-align:left;width:100px;">
					        		<c:if test="${userBaseInfo.liveTime == 0}">1年以下</c:if>
					        		<c:if test="${userBaseInfo.liveTime == 1}">1~3年</c:if>
					        		<c:if test="${userBaseInfo.liveTime == 2}">3~5年</c:if>
					        		<c:if test="${userBaseInfo.liveTime == 3}">5年以上</c:if>
					        	</td>
					        </tr>
							<tr>
					        	<td style="text-align:right;width:100px;"><strong>微信</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.wechat}</td>
					        </tr>
					   		<tr>
					        	<td style="text-align:right;width:100px;"><strong>邮箱</strong></td><td style="text-align:left;width:100px;">${userBaseInfo.email}</td>
					        </tr>
					    </table>
					</div>
					
					<div class="panel panel-default">
					    <div class="panel-heading">职业信息</div>
					     <table class="table table-bordered table-hover">
					        <tr>
					        	<td style="text-align:right;width:20%;;"><strong>职业</strong></td><td style="text-align:left;width:100px;">${userJobInfo.jobName}</td>
					        </tr>
							<tr>
					        	<td style="text-align:right;width:100px;"><strong>月收入</strong></td><td style="text-align:left;width:100px;">
					        		<c:if test="${userJobInfo.income == 0}">2000元以下</c:if>
					        		<c:if test="${userJobInfo.income == 1}">2000~5000元</c:if>
					        		<c:if test="${userJobInfo.income == 2}">5001~8000</c:if>
					        		<c:if test="${userJobInfo.income == 3}">8001~12000元</c:if>
					        		<c:if test="${userJobInfo.income == 4}">12001~18000元</c:if>
					        		<c:if test="${userJobInfo.income == 5}">18000元以上</c:if>
					        	</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>单位名称</strong></td><td style="text-align:left;width:100px;">${userJobInfo.company}</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>单位地址</strong></td><td style="text-align:left;width:100px;">${userJobInfo.address}</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>单位电话</strong></td><td style="text-align:left;width:100px;">${userJobInfo.phone}</td>
					        </tr>
					    </table>
					</div>
					
					<div class="panel panel-default">
					    <div class="panel-heading">银行卡信息</div>
					     <table class="table table-bordered table-hover">
							<tr>
					        	<td style="text-align:right;width:20%;;"><strong>开户行</strong></td><td style="text-align:left;width:100px;">${userBankInfo.bankName}</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>开户省市</strong></td><td style="text-align:left;width:100px;">${userBankInfo.address}</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>预留手机号</strong></td><td style="text-align:left;width:100px;">${userBankInfo.mobile}</td>
					        </tr>
					        <tr>
					        	<td style="text-align:right;width:100px;"><strong>银行卡号</strong></td><td style="text-align:left;width:100px;">${userBankInfo.bankNum}</td>
					        </tr>
					    </table>
					</div>
					
					<div class="panel panel-default">
					    <div class="panel-heading">紧急联系人</div>
					     <table class="table table-bordered table-hover data-striped">
					     <thead>
							<tr>
					        	<td style="text-align:left;width:100px;border-bottom:1px solid #DAD4D4;"><strong>联系人关系</strong></td>
					        	<td style="text-align:left;width:100px;border-bottom:1px solid #DAD4D4;"><strong>姓名</strong></td>
					        	<td style="text-align:left;width:100px;border-bottom:1px solid #DAD4D4;"><strong>电话</strong></td>
					        </tr>
					        </thead>
					        <c:if test="${userLinkManInfo != null}">
					        	<c:forEach var="linkMan" items="${userLinkManInfo}">
					        			 <tr>
								        	<td style="text-align:left;width:100px;">
								        		<c:if test="${linkMan.relation == 0}">朋友</c:if>
								        		<c:if test="${linkMan.relation == 1}">父母</c:if>
								        		<c:if test="${linkMan.relation == 2}">兄弟</c:if>
								        		<c:if test="${linkMan.relation == 3}">姐妹</c:if>
								        		<c:if test="${linkMan.relation == 4}">同事</c:if>
								        	</td>
								        	<td style="text-align:left;width:100px;">${linkMan.name}</td>
								        	<td style="text-align:left;width:100px;">${linkMan.phone}</td>
								        </tr>
					        	</c:forEach>
					        </c:if>
					        <c:if test="${userLinkManInfo == null}">
					        		<tr>
					        			<td colspan="3" style="text-align:center;">暂无联系人数据</td>
					        		</tr>
					        </c:if>        
					     </table>
					</div>
					<div class="form-group" style="margin-left:-30px;">
						<label class="col-sm-2 control-label">拒绝原因：</label>
						<div class="col-sm-9" style="margin-left:-85px;">
							<textarea class="form-control" rows="3" placeholder="拒绝时，可输入拒绝原因" name="Desc" id="reasonDesc"></textarea>
						</div>
					</div>
					<div class="form-group" style="margin-top:120px;">
					    <div class="col-sm-offset-2 col-sm-13">
					      <button type="button" class="btn btn-success btn-s-xs" id="btn">通过</button>　　
					      <button type="button" class="btn btn-danger btn-s-xs" id="btn2">拒绝</button>
					    </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	//审核通过操作
	$("#btn").click(function(){
		//审核通过
		updateOrderStatus(1);
	});
	
	//拒绝通过
	$("#btn2").click(function(){
		//审核被拒
		updateOrderStatus(2);
	});
	
	//更新订单状态操作
	function updateOrderStatus(type){
		//设置按钮状态
		Common.changeBtnDisable(type == 1 ? "#btn" : "#btn2");
		$.ajax({
			type: 'post',
            url: '${basePath}/admin/loan/check/order',
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            timeout: 60000, 
            data:JSON.stringify({
          	  orderId: ${orderId},
          	  type: type,
          	  reason: $.trim($("#reasonDesc").val())
            }),
            success: function (json) {
          	  if(json.errno == 0 && json.data.status){
	          	  //提示信息
          		  layer.msg(type == 1 ? "审核通过!" : "拒绝通过!");
	          	  
	          	  //跳转　返回员页面
	          	  setTimeout(function(){Common.loadPage('订单管理', '借款单管理', '审核借款单', '${basePath}/admin/loan/check/list')}, 2000);
          	  }else{
          		  //提示信息
          		  layer.msg(json.errmsg);
          		  
          		//设置按钮状态
          		Common.changeBtnAble(type == 1 ? "#btn" : "#btn2");
          	  }
            }
        });
	}
</script>