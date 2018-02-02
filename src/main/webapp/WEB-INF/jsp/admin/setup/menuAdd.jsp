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
				<div class="form-group">
					<label class="col-sm-3 control-label">上级菜单</label>
					<div class="col-sm-9">
						<div class="dropdown">  
								<button class="btn btn-default" data-toggle="dropdown" id="dropdownBtn">  
									<span id="menText">${adminMenu.name}</span>  
									<span class="caret"></span>  
								</button>  
								<input type="hidden" value="${adminMenu.id}" id="parentId" />
								<ul class="dropdown-menu" style="max-height: 285px; overflow-y: auto;cursor:pointer;" id="menuSelect">  
									<li data-original-index="0" <c:if test="${menuId == 0}">class="selected active"</c:if>><a>作为一级菜单</a></li>
									<c:forEach var="menu" items="${menuListData}">
										<li data-original-index="${menu.id}"><a>${menu.name}</a></li>  
									</c:forEach>
								</ul>  
						</div> 
					</div>
				</div>
				<div class="line line-dashed line-lg pull-in"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label">菜单名称</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" placeholder="请输入菜单名称" name="menuName" id="menuName">
					</div>
				</div>
				<div class="line line-dashed line-lg pull-in"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label">请求地址</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" placeholder="请输入请求地址" name="requestUrl" id="requestUrl">
					</div>
				</div>
				<div class="line line-dashed line-lg pull-in"></div>
 				<div class="form-group">
				    <label for="menuStatus" class="col-sm-3 control-label">是否显示菜单：</label>
					<label class="radio-inline">
						<input type="radio" name="menuStatus" value="0" checked> 是
					</label>
					<label class="radio-inline">
						<input type="radio" name="menuStatus"  value="1" > 否
					</label>
				</div>
			</div>
			<footer class="panel-footer text-right bg-light lter">
				<button type="button" class="btn btn-success btn-s-xs" id="submit">提交</button>
				<button type="button" class="btn btn-danger btn-s-xs" id="closeWin">关闭</button>
			</footer> 
		</section>
		<script>
			//点击上级菜单操作
			$("#dropdownBtn").click(function(){
				$("#menuSelect").show();
			});
			
			 //父级菜单　Select选择框点击
		    $('.dropdown-menu li').click(function(){
		    	//动态赋值
		    	$("#menText").html($(this).find('a').html());
		    	$("#parentId").val($(this).attr('data-original-index'));
		    	
		    	//设置选装状态
		    	$(this).addClass("selected active").siblings().removeClass("selected active");
		    	
		    	//隐藏弹出窗
		    	$("#menuSelect").hide();
		    });
		
			//关闭操作
			$('#closeWin').click(function(){
            	//获取窗口索引
  				var index = parent.layer.getFrameIndex(window.name); 
            	
				//关闭当前窗口
  			    parent.layer.close(index);
			});
		
			//提交操作
			$('#submit').click(function(){
				//设置按钮状态
				Common.changeBtnDisable("#submit");
				
				//获取提交参数
				var parentId		= $.trim($("#parentId").val());
				var menuName = $.trim($("#menuName").val());
				var requestUrl	= $.trim($("#requestUrl").val());
				var menuStatus = $("input[name='menuStatus']:checked").val();
				
				//检查菜单名称
				if(menuName == ''){
					//提示菜单名称不能为空
					layer.msg("菜单名称不能为空！");
					$("#menuName").select();
					
					//设置按钮状态
					Common.changeBtnAble("#submit");
					
					return false;
				}
				
				//检查菜单请求地址
				if(requestUrl == ''){
					//提示菜单请求地址不能为空
					layer.msg("请求地址不能为空！");
					$("#requestUrl").select();
					
					//设置按钮状态
					Common.changeBtnAble("#submit");
					
					return false;
				}
				
				//提交新增数据
				$.ajax({
					  type: 'post',
		              url: '${basePath}/admin/setup/add/menu',
		              dataType: 'json',
		              contentType:'application/json;charset=UTF-8',
		              timeout: 60000, 
		              data:JSON.stringify({
		            	  parentId: parentId,
		            	  menuName: menuName,
		            	  requestUrl: requestUrl,
		            	  menuStatus: menuStatus
		              }),
		              success: function (json) {
		            	  if(json.errno == 0 && json.data.ret){
				            	//获取窗口索引
				  				var index = parent.layer.getFrameIndex(window.name); 
				  				
				            	//刷新菜单列表
				            	parent.flushPage();
				            	
				  				//关闭当前窗口
				  			    parent.layer.close(index);
		            	  }else{
		            		  //提示信息
		            		  layer.msg(json.errmsg);
		            		  
		  					//设置按钮状态
		  					Common.changeBtnAble("#submit");
		            	  }
		              }
		          });
				});
		</script>
<jsp:include page="../../common/footer.jsp"  flush="true" />