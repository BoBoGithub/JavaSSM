<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>

<link href="${basePath}/resources/css/style.css" rel="stylesheet">
<script src="${basePath}/resources/js/date/bootstrap-datetimepicker.min.js"></script>
<script src="${basePath}/resources/js/date/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${basePath}/resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/>

<div class="margin-top-2 alert alert-info" data-ng-show="vm.tableDisplay &amp;&amp; !vm.nopermission"><b>提醒：</b><span>Tip提示信息测试使用</span></div>

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h4>机器决策订单管理</h4>
                </div>
                <div class="ibox-content">
           		 <div class="uc m-l-5 m-r-5" role="form" style="padding:10px 0px;">
	           		 <div class="form-horizontal" role="form">
	           		 		 <div class="form-group">
								    <label for="orderId" class="col-sm-2 control-label">订单ID：</label>
								    <div class="col-sm-1">
								      <input type="text" class="form-control" style="width: 150px;;" id="orderId" placeholder="请输入订单ID">
								    </div>
								    
								    <label for="borrowMoney" class="col-sm-2 control-label">借款额度：</label>
								    <div class="col-sm-1">
								      <input type="text" class="form-control" style="width: 150px;" id="borrowMoney" placeholder="请输入借款额度">
								    </div>

								    <label for="borrowLimit" class="col-sm-2 control-label">借款期限：</label>
								   <div class="col-sm-2 btn-group bootstrap-select show-tick">
											<input type="hidden" id="borrowLimit" value="" />
											<button type="button"  class="btn dropdown-toggle btn-default" data-toggle="dropdown" data-id="first-disabled2"  aria-expanded="false">
												<span class="filter-option pull-left" id="borrowLimitText">全部</span>&nbsp;<span class="caret"></span>
											</button>
											<ul class="borrow-limit-class dropdown-menu open inner" role="menu" style="max-height: 130px; overflow-y: auto;cursor:pointer;">
												<li data-original-index="" class="selected active"><a>全部</a></li>
												<li data-original-index="1"><a>7天</a></li>
												<li data-original-index="2"><a>15天</a></li>
												<li data-original-index="3"><a>30天</a></li>	
											</ul>
										</div>  
							  </div>
			           		   <div class="form-group">
								     <label for="uid" class="col-sm-2 control-label">用户UID：</label>
								    <div class="col-sm-1">
								      <input type="text" class="form-control"  style="width: 150px;" id="uid" placeholder="请输入用户UID">
								    </div>

								    <label for="orderStatus" class="col-sm-2 control-label">订单状态：</label>
									<div class="col-sm-2 btn-group bootstrap-select show-tick">
											<input type="hidden" id="orderStatus" value="" />
											<button type="button"  class="btn dropdown-toggle btn-default" data-toggle="dropdown" data-id="first-disabled2"  aria-expanded="false">
												<span class="filter-option pull-left" id="orderStatusText">全部</span>&nbsp;<span class="caret"></span>
											</button>
											<ul class="order-status-class dropdown-menu open inner" role="menu" style="max-height: 130px; overflow-y: auto;cursor:pointer;">
												<li data-original-index="" class="selected active"><a>全部</a></li>
												<li data-original-index="2"><a>审核通过(机器)</a></li>
												<li data-original-index="-3"><a>审核失败(机器)</a></li>
											</ul>
									</div> 
							  </div>
							  
							  <div class="form-group">
							  		<label for="applyStartTime" class="col-sm-2 control-label">申请时间：</label>
							  		<div class="col-lg-2">
								      <input type="text" class="form_datetime form-control"  placeholder="开始申请时间" id="applyStartTime">
								    </div>
								    <div class="col-lg-2">
								      <input type="text" class="form_datetime form-control" placeholder="结束申请时间" id="applyEndTime">
								    </div>
							  </div>
							  
							<div class="form-group">
							  		<label for="scoreStart" class="col-sm-2 control-label">数据评分：</label>
							  		<div class="col-lg-2">
								      <input type="text" class="form-control"  placeholder="最小数据评分" id="scoreStart">
								    </div>
								    <div class="col-lg-2">
								      <input type="text" class="form-control" placeholder="最大数据评分" id="scoreEnd">
								    </div>
							  </div>

							<div class="form-group">
							    <div class="col-sm-offset-2 col-sm-10">
							      <button type="button" class="btn btn-default" id="btn">查询</button>
							    </div>
							  </div>
					</div>
	           </div>

					<div id="user_list_table_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
						<table class="table table-striped table-bordered table-hover  dataTable no-footer" id="user_list_table" role="grid" aria-describedby="user_list_table_info" style="width: 97%;">
						    <thead>
						        <tr role="row">
						        	<th class="text-center sorting_disabled" rowspan="1" colspan="1" aria-label="" style="width: 40px;">
						            <input id="" type="checkbox" class="ipt_check_all">
						            </th>
						            <th class="text-center sorting_asc"   style="width: 114px;">借款单ID</th>
						            <th class="text-center sorting"  style="width: 110px;">借款金额</th>
						            <th class="text-center sorting"  style="width: 103px;">借款期限</th>
						            <th class="text-center sorting"  style="width: 118px;">风控评分</th>
						            <th class="text-center sorting"  style="width: 120px;">借款人UID</th>
						            <th class="text-center sorting"  style="width: 135px;">借款人姓名</th>
						            <th class="text-center sorting"  style="width: 116px;">借款状态</th>
						            <th class="text-center sorting"  style="width: 156px;">申请时间</th>
						            <th class="text-center sorting"  style="width: 136px;">操作</th>
						        </tr>
						    </thead>
						    <tbody id="tableList"></tbody>
						</table>
						<section class="scrollable" style="margin-top: 25px;"><div id="subLoadhtml"></div></section>
						<div id="pagerList"></div>
					</div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    //时间控件弹出框
    $(".form_datetime").datetimepicker({
    	 format: "yyyy-mm-dd",
    	 autoclose: true,
    	 todayBtn: true,
    	 todayHighlight: true,
    	 showMeridian: true,
    	 pickerPosition: "bottom-left",
    	 language: 'zh-CN',//中文，需要引用zh-CN.js包
    	 startView: 2,//月视图
    	 minView: 2//日期时间选择器所能够提供的最精确的时间选择视图
     });
    
    //借款期限　Select选择框点击
    $('.borrow-limit-class li').click(function(){
    	//动态赋值
    	$("#borrowLimitText").html($(this).find('a').html());
    	$("#borrowLimit").val($(this).attr('data-original-index'));
    	
    	//设置选装状态
    	$(this).addClass("selected active").siblings().removeClass("selected active");
    });
    
    //订单状态　Select选择框点击
    $('.order-status-class li').click(function(){
    	//动态赋值
    	$("#orderStatusText").html($(this).find('a').html());
    	$("#orderStatus").val($(this).attr('data-original-index'));
    	
    	//设置选装状态
    	$(this).addClass("selected active").siblings().removeClass("selected active");
    });
    
    //提交查询
    $("#btn").click(function(){
    	getListData(1);
    });
    
	//加载列表数据
	getListData(1);
	function getListData(pageNum){
		//提取订单id
		var orderId = $.trim($("#orderId").val());
		
		//提取用户uid
		var uid = $.trim($('#uid').val());
		
		//提取手机号
		var mobile = $.trim($("#mobile").val());
		
		//提取申请额度
		var borrowMoney = $.trim($("#borrowMoney").val());
		
		//提取借款期限
		var borrowLimit = $.trim($("#borrowLimit").val());
		
		//提取订单状态
		var orderStatus = $.trim($("#orderStatus").val());
		
		//提取申请时间
		var applyStartTime = $.trim($('#applyStartTime').val());
		var applyEndTime = $.trim($('#applyEndTime').val());
		
		//提取风控分数
		var scoreStart = $.trim($("#scoreStart").val());
		var scoreEnd	=  $.trim($("#scoreEnd").val());
		
		//检查风控最低分数
		if(scoreStart != ''){
			if(!isNaN(scoreStart) && (scoreStart < 0 || scoreStart > 10)){
				//选中最小分数
				$("#scoreStart").select();
				layer.msg("最小数据评分不能小于0 不能大于10");
				return false;
			}
		}else{
			scoreStart = 0.001;
		}
		
		//检查风控最高分数
		if(scoreEnd != ''){
			if(!isNaN(scoreEnd) && (scoreEnd >= 0 || scoreEnd < 10)){
				//选中最小分数
				$("#scoreEnd").select();
				layer.msg("最大数据评分不能小于0 不能大于10");
				return false;
			}
		}else{
			scoreEnd = 10;
		}

		//加载图标
		$('#tableList').html("");
		$("#pagerList").html("");
		$("#subLoadhtml").show().html("<div style='text-align:center'><img src='${basePath}/resources/images/loading.gif'/></div>");

		//设置查询页码
		var page = pageNum;
		var pageSize = 9;

		//加载数据
		  $.ajax({
	              type: 'post',
	              url: '${basePath}/admin/get/loan/order/list',
	              dataType: 'json',
	              cache:false,
	              contentType:'application/json;charset=UTF-8',
	              data:JSON.stringify({
	            	  orderId:orderId,
	            	  uid:uid,
	            	  borrowMoney: borrowMoney,
	            	  borrowLimit: borrowLimit,
	            	  orderStatus: orderStatus,
	            	  applyStartTime: applyStartTime,
	            	  applyEndTime: applyEndTime,
	            	  scoreStart: scoreStart,
	            	  scoreEnd: scoreEnd, 
	            	  page: page, 
	            	  pageSize: pageSize,
	              }),  
	              timeout: 60000, 
	              success: function (json) {
	            	  $('#subLoadhtml').hide();
						if(json.errno == 0){
							appendHtml(json.data.list, page, Math.ceil(json.data.total/pageSize));
						}else{
							layer.msg(json.errmsg);
						}
	              }
	          });
	}
	
	//页面追加数据
	function appendHtml(data, page, total){
		//提取列表数据
		var  html = dealUserList(data);
		
		//提取分页数据
		var pager = Common.createPagerHtml(page, total);
		
		//追加用户数据
		$('#tableList').html(html);
		
		//追加分页数据
		$('#pagerList').html(pager);
	}
	
	//处理列表数据
	function dealUserList(data){
		var  html					= "";
		var borrowLimit	= "";
		var borrowStatus	= "";
		if(data.length > 0){
			$.each(data, function(item){
				//设置借款期限
				if(data[item].borrowLimit == 1){
					borrowLimit = "7天";
				}else if(data[item].borrowLimit == 2){
					borrowLimit = "15天";
				}else if(data[item].borrowLimit == 3){
					borrowLimit = "30天";
				}else{
					borrowLimit = "X天";
				}
				
				html += "<tr role='row'>"+
										"<td class='text-center'><input type='checkbox'></td>"+
										"<td>"+data[item].brecordId+"</td>"+
										"<td>￥"+data[item].borrowMoney+"</td>"+
										"<td>"+borrowLimit+"</td>"+
										"<td>"+data[item].score+"</td>"+
										"<td>"+data[item].uid+"</td>"+
										"<td>"+data[item].realName+"</td>"+
										"<td>"+data[item].status+"</td>"+
										"<td>"+data[item].applyTime+"</td>"+
										"<td> <a href='javascript:void(0);' onclick=\"Common.loadPage('订单管理', '借款订单管理', '订单明细', '${basePath}/admin/loan/order/detail?orderId="+data[item].brecordId+"', true)\">订单详情</a>  风控报告</td>"+						
								"</tr>";
			});
		}else{
			html += "<tr><td colspan='10' style='text-align:center;'>暂无数据！</td></tr>";
		}
		
		return html;
	}    
</script>