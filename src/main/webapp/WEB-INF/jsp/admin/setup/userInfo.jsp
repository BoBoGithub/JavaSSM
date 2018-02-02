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
                    <h4>个人信息</h4>
                </div>
                <div class="ibox-content">
					<div class="form-horizontal" role="form">
										<div class="form-group">
											<label class="col-sm-2 control-label" style="margin-left:-70px;">所属角色：</label>
											<div class="col-sm-9">
												<input type="text" class="form-control"   value="${roleName}" disabled>
											</div>
										</div>
										<div class="line line-dashed line-lg pull-in"></div>
										<div class="form-group">
											<label class="col-sm-2 control-label" style="margin-left:-70px;">用户名：</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" value="${userName}" disabled>
											</div>
										</div>
										<div class="line line-dashed line-lg pull-in"></div>
										<div class="form-group">
											<label class="col-sm-2 control-label" style="margin-left:-70px;" for="realname">真实姓名：</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" placeholder="请输入真实新姓名" name="realname" id="realname" value="${realName}">
											</div>
										</div>
										<div class="line line-dashed line-lg pull-in"></div>
										<div class="form-group">
											<label class="col-sm-2 control-label" style="margin-left:-70px;" for="email">用户邮箱：</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" placeholder="请输入邮箱地址" name="email" id="email" value="${email}">
											</div>
										</div>
									</div>
									<footer class="panel-footer text-left bg-light lter">
										<button type="button" class="btn btn-success btn-s-xs" id="submit">提交</button>
									</footer> 
			</div>
		</div>
	</div>
</div>
</div>
	
		<script>
			$('#submit').click(function(){
				//设置按钮状态
				Common.changeBtnDisable("#submit");
				
				//获取提交参数
				var realName	= $.trim($("#realname").val());
				var email			= $.trim($("#email").val());
				
				//检查真实姓名
				if(realname == ''){
					//提示
					layer.msg("请填写真实姓名！");
					$("#realname").select();
					
					//设置按钮状态
					Common.changeBtnAble("#submit");
					
					return false;	
				}
				
				//检查用户邮箱
				if(email == ""){
					//提示
					layer.msg("请填写邮箱地址！");
					$("#email").select();
					
					//设置按钮状态
					Common.changeBtnAble("#submit");
					
					return false;	
				}
				
				//提交用户修改
				$.ajax({
	              type: 'post',
	              url: '${basePath}/admin/setup/user/edit/info',
	              contentType:'application/json;charset=UTF-8',
	              dataType: 'json',
	              cache:false,
	              timeout: 60000, 
	              data:JSON.stringify({
	            	  realName: realName, 
	            	  email: email
	              }),
	              success: function (json) {
	            	  if(json.errno == 0 && json.data.updRet){
	            		  layer.msg("修改成功！");
	            	  }else{
	            		  layer.msg(json.errmsg);
	            	  }
	            	  
  					//设置按钮状态
  					Common.changeBtnAble("#submit");
	              }
	          });
			});
		</script>
<jsp:include page="../../common/footer.jsp"  flush="true" />