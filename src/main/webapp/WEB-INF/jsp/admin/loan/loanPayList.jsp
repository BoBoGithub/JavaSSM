<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>

<link href="${basePath}/resources/css/style.css" rel="stylesheet">
<div class="margin-top-2 alert alert-info" data-ng-show="vm.tableDisplay &amp;&amp; !vm.nopermission"><b>提醒：</b><span>Tip提示信息测试使用</span></div>

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h4>通道放款单管理</h4>
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

								    <label for="orderStatus" class="col-sm-2 control-label">放款状态：</label>
									<div class="col-sm-1 btn-group bootstrap-select show-tick">
											<input type="hidden" id="orderStatus" value="6" />
											<button type="button"  class="btn dropdown-toggle btn-default" data-toggle="dropdown" data-id="first-disabled2"  aria-expanded="false">
												<span class="filter-option pull-left" id="orderStatusText">放款成功</span>&nbsp;<span class="caret" style="margin-right:47px;"></span>
											</button>
											<ul class="order-status-class dropdown-menu open inner" role="menu" style="max-height: 130px; overflow-y: auto;cursor:pointer;">
												<li data-original-index="4"><a>待放款</a></li>
												<li data-original-index="6" class="selected active"><a>放款成功</a></li>
												<li data-original-index="5"><a>放款失败</a></li>
											</ul>
									</div> 
									
									<label for="orderStatus" class="col-sm-2 control-label">资金方：</label>
									<div class="col-sm-2 btn-group bootstrap-select show-tick">
											<input type="hidden" id="fundSize" value="" />
											<button disabled type="button"  class="btn dropdown-toggle btn-default" data-toggle="dropdown" data-id="first-disabled2"  aria-expanded="false">
												<span class="filter-option pull-left" id="orderStatusText">选择资金方</span>&nbsp;<span class="caret"></span>
											</button>
											<ul class="order-status-class dropdown-menu open inner" role="menu" style="max-height: 130px; overflow-y: auto;cursor:pointer;">
												<li data-original-index="" class="selected active"><a>全部资金方</a></li>
												<li data-original-index="2"><a>米缸金融</a></li>
												<li data-original-index="-3"><a>民生银行</a></li>
											</ul>
									</div> 
							  </div>
							  
							  <div class="form-group">
								    <label for="orderStatus" class="col-sm-2 control-label">收款行：</label>
									<div class="col-sm-1 btn-group bootstrap-select show-tick">
											<input type="hidden" id="fundSize" value="" />
											<button disabled type="button"  class="btn dropdown-toggle btn-default" data-toggle="dropdown" data-id="first-disabled2"  aria-expanded="false">
												<span class="filter-option pull-left" id="orderStatusText">选择收款行</span>&nbsp;<span class="caret" style="margin-right:47px;"></span>
											</button>
											<ul class="order-status-class dropdown-menu open inner" role="menu" style="max-height: 130px; overflow-y: auto;cursor:pointer;">
												<li data-original-index="" class="selected active"><a>全部收款行</a></li>
												<li data-original-index="2"><a>米缸金融</a></li>
												<li data-original-index="-3"><a>民生银行</a></li>
											</ul>
									</div> 
								    
								    <label for="orderStatus" class="col-sm-2 control-label">放款渠道：</label>
									<div class="col-sm-2 btn-group bootstrap-select show-tick">
											<input type="hidden" id="fundSize" value="" />
											<button disabled type="button"  class="btn dropdown-toggle btn-default" data-toggle="dropdown" data-id="first-disabled2"  aria-expanded="false">
												<span class="filter-option pull-left" id="orderStatusText">选择放款渠道</span>&nbsp;<span class="caret"></span>
											</button>
											<ul class="order-status-class dropdown-menu open inner" role="menu" style="max-height: 130px; overflow-y: auto;cursor:pointer;">
												<li data-original-index="" class="selected active"><a>全部放款渠道</a></li>
												<li data-original-index="2"><a>米缸金融</a></li>
												<li data-original-index="-3"><a>民生银行</a></li>
											</ul>
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
						            <th class="text-center sorting_asc"   style="width: 114px;">订单ID</th>
						            <th class="text-center sorting"  style="width: 110px;">借款金额</th>
						            <th class="text-center sorting"  style="width: 103px;">期限</th>
						            <th class="text-center sorting"  style="width: 103px;">应还金额</th>
						            <th class="text-center sorting"  style="width: 116px;">放款状态</th>
						            <th class="text-center sorting"  style="width: 116px;">放款渠道</th>
						            <th class="text-center sorting"  style="width: 116px;">资金方</th>
						            <th class="text-center sorting"  style="width: 120px;">借UID</th>
						            <th class="text-center sorting"  style="width: 135px;">借款人姓名</th>
						            <th class="text-center sorting"  style="width: 135px;">收款行</th>
						            <th class="text-center sorting"  style="width: 135px;">银行卡号</th>						            
						            <th class="text-center sorting"  style="width: 156px;">放款时间</th>
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
		
		//提取申请额度
		var borrowMoney = $.trim($("#borrowMoney").val());
		
		//提取借款期限
		var borrowLimit = $.trim($("#borrowLimit").val());
		
		//提取订单状态
		var orderStatus = $.trim($("#orderStatus").val());


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
	            	  page: page, 
	            	  pageSize: pageSize,
	              }),  
	              timeout: 60000, 
	              success: function (json) {
	            	  $('#subLoadhtml').hide();
						if(json.errno == 0){
							appendHtml(json.data.list, page, Math.ceil(json.data.total/pageSize));
						}else{
							layer.msg(json.errmsg, function(){});
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
										"<td>￥888</td>"+
										"<td>"+data[item].status+"</td>"+
										"<td style='color:gray;'>云南信托</td>"+
										"<td style='color:gray;'>资金方</td>"+
										"<td>"+data[item].uid+"</td>"+
										"<td>"+data[item].realName+"</td>"+
										"<td>"+data[item].bankName+"</td>"+
										"<td>"+data[item].bankNum+"</td>"+
										"<td>"+data[item].loanTime+"</td>"+
										"<td> <a href='javascript:void(0);' onclick='showRepayDetail("+data[item].brecordId+");'>还款明细</a> 发起还款</td>"+						
								"</tr>";
			});
		}else{
			html += "<tr><td colspan='14' style='text-align:center;'>暂无数据！</td></tr>";
		}
		
		return html;
	}
	
	//显示还款明细
	var repayDetailBtn = false;
	function showRepayDetail(orderId){
		if(repayDetailBtn){
			return true;
		}else{
			repayDetailBtn = true;
		}
		
		//调取数据
		$.ajax({
              type: 'post',
              url: '${basePath}/admin/get/loan/repay/detail/byid',
              dataType: 'json',
              cache:false,
              contentType:'application/json;charset=UTF-8',
              data:JSON.stringify({
            	  orderId:orderId
              }),  
              timeout: 60000, 
              success: function (json) {
            	   repayDetailBtn = false;
					if(json.errno == 0){
						showDetail(json.data.repayDetail);
					}else{
						layer.msg(json.errmsg, function(){});
					}
              }
          });
	}
	
	//显示弹窗
	function showDetail(borrowRecord){
		//设置显示数据
		var html = '<div>';
		html += 	'<table class="table table-bordered table-hover">';
		html += 		'<caption><h4>还款明细</h4></caption>';
		html += 			'<tbody>';
		html += 				'<tr>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>订单ID</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">'+borrowRecord.orderId+'</td>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>还款状态</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">'+borrowRecord.status+'</td>';
		html += 				'</tr>';
		html += 				'<tr>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>借款金额</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">￥'+borrowRecord.borrowMoney+'</td>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>应还金额</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">￥'+borrowRecord.totalMoney+'</td>';
		html += 				'</tr>';
		html += 				'<tr>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>利息</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">￥'+borrowRecord.borrowInterest+'</td>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>实际还款</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">￥'+borrowRecord.realTotalMoney+'</td>';
		html += 				'</tr>';
		html += 				'<tr>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>服务费</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">￥'+borrowRecord.serviceCharge+'</td>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>逾期费用</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">￥'+borrowRecord.overdueCharge+'</td>';
		html += 				'</tr>';
		html += 				'<tr>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>期数</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">'+borrowRecord.borrowLimit+'</td>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>逾期天数</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">'+borrowRecord.overdueDay+'天</td>';
		html += 				'</tr>';
		html += 				'<tr>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>还款日期</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">'+borrowRecord.repayTime+'</td>';
		html += 					'<td style="text-align:right;width:20%;;"><strong>实际还款日期</strong></td>';
		html += 					'<td style="text-align:left;width:100px;">'+borrowRecord.realRepayTime+'</td>';
		html += 				'</tr>';
		html += 			'</tbody>';
		html += 		'</table>';
		html += 	'</div>';
		
		layer.open({
		  title: "还款明细",
		  type: 1,
		  skin: 'layui-layer-rim', //加上边框
		  area: ['560px', '340px'], //宽高
		  content: html
		});
	}
</script>