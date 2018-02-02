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
					<label for="productName" class="col-sm-3 control-label">产品名称</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" placeholder="请输入产品名称" name="productName" id="productName">
					</div>
				</div>
				
				<div class="line line-dashed line-lg pull-in"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="borrowMoney">借款额度</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" placeholder="请输入借款额度" name="borrowMoney" id="borrowMoney">
					</div>
				</div>
				
				<div class="line line-dashed line-lg pull-in"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="borrowLimit">借款期限</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" placeholder="请输入借款期限" name="borrowLimit" id="borrowLimit">
					</div>
				</div>
				
				<div class="line line-dashed line-lg pull-in"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="dayRate">日利率</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" placeholder="请输入日利率" name="dayRate" id="dayRate">
					</div>
				</div>
				
				<div class="line line-dashed line-lg pull-in"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="serviceCharge">服务费</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" placeholder="请输入服务费" name="serviceCharge" id="serviceCharge">
					</div>
				</div>
				
				<div class="line line-dashed line-lg pull-in"></div>
				<div class="form-group">
				    <label for="productStatus" class="col-sm-3 control-label">产品状态：</label>
					<label class="radio-inline">
						<input type="radio" name="productStatus" value="1" checked> 启用
					</label>
					<label class="radio-inline">
						<input type="radio" name="productStatus" value="0"> 停用
					</label>
				</div>
								
			</div>
			<footer class="panel-footer text-right bg-light lter">
				<button type="button" class="btn btn-success btn-s-xs" id="submit">提交</button>
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
		
			$('#submit').click(function(){
				//设置按钮状态
				Common.changeBtnDisable("#submit");
				
				//获取提交参数
				var productName		= $.trim($("#productName").val());
				var borrowMoney		= $.trim($("#borrowMoney").val());
				var borrowLimit			= $.trim($("#borrowLimit").val());
				var dayRate 					= $.trim($("#dayRate").val());
				var serviceCharge			= $.trim($("#serviceCharge").val());
				var productStatus		= $("input[name='productStatus']:checked").val();
				
				//检查产品名称
				if(productName == ''){
					//提示产品名称有问题
					layer.msg("请输入产品名称！", function(){});
					$('#productName').select();
					
					//设置按钮状态
					Common.changeBtnAble("#submit");
					return false;
				}
				
				//检查借款金额
				if(borrowMoney == '' || isNaN(borrowMoney)){
					//提示借款金额有问题
					layer.msg("请填写借款金额！", function(){});
					$("#borrowMoney").select();
					
					//设置按钮状态
					Common.changeBtnAble("#submit");
					return false;	
				}
				
				//检查借款期限
				if(borrowLimit == '' || isNaN(borrowLimit)){
					//提示借款期限有问题
					layer.msg("请填写借款期限！", function(){});
					$("#borrowLimit").select();
					
					//设置按钮状态
					Common.changeBtnAble("#submit");
					return false;	
				}
				
				//检查日利率
				if(dayRate == '' || isNaN(dayRate)){
					//提示日利率有问题
					layer.msg("请填写日利率！", function(){});
					$("#dayRate").select();
					
					//设置按钮状态
					Common.changeBtnAble("#submit");
					return false;
				}
				
				//检查服务费
				if(serviceCharge == '' || isNaN(serviceCharge)){
					//提示服务费有问题
					layer.msg("请填服务费！", function(){});
					$("#serviceCharge").select();
					
					//设置按钮状态
					Common.changeBtnAble("#submit");
					return false;
				}

				//提交新增数据
				$.ajax({
	              type: 'post',
	              url: '${basePath}/admin/product/do/add',
	              contentType:'application/json;charset=UTF-8',
	              dataType: 'json',
	              cache:false,
	              timeout: 60000, 
	              data:JSON.stringify({
	            	  productName:	productName,
	            	  borrowMoney: borrowMoney, 
	            	  borrowLimit: borrowLimit,
	            	  dayRate: dayRate,
	            	  serviceCharge: serviceCharge,
	            	  productStatus: productStatus,
	              }),
	              success: function (json) {
	            	  if(json.errno == 0 && json.data.addStatus){
	            		  //添加成功提示
	            		   layer.msg("提交成功！");
	            		  setTimeout(function(){
	            				//获取窗口索引
				  				var index = parent.layer.getFrameIndex(window.name); 
			
				  				//重新加载用户列表
				  				parent.getListData(1);
				  				//关闭当前窗口
				  			    parent.layer.close(index);
	            		  }, 2000);
	            	  }else{
	            		  layer.msg(json.errmsg);
	            		  
	  					//设置按钮状态
	  					Common.changeBtnAble("#submit");
	            	  }
	              }
	          });
			});
		</script>
<jsp:include page="../../common/footer.jsp"  flush="true" />