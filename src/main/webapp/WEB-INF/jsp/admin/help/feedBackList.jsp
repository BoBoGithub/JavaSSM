<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;
    pageContext.setAttribute("basePath",basePath);
%>
<link href="${basePath}/resources/css/style.css" rel="stylesheet">
<script src="${basePath}/resources/js/date/bootstrap-datetimepicker.min.js"></script>
<script src="${basePath}/resources/js/date/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${basePath}/resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/>

<div class="margin-top-2 alert alert-info" data-ng-show="vm.tableDisplay &amp;&amp; !vm.nopermission"><b bo-text="'msg.cm.lb.tips'|translate">提醒：</b><span bo-text="'msg.sub.contact.tip'|translate">Tip提示信息测试使用</span></div>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h4>问题反馈管理列表</h4>
                </div>
                <div class="ibox-content">
                     
                     
                     <div class="uc m-l-5 m-r-5" role="form" style="padding:10px 0px;">
		           		 <div class="form-horizontal" role="form">
			           		 	 <div class="form-group">
									    <label for="uid" class="col-sm-2 control-label">用户UID：</label>
									    <div class="col-sm-1">
									      <input type="text" class="form-control" style="width: 150px;;" id="uid" placeholder="请输入用户UID">
									    </div>
	
									    <label for="udid" class="col-sm-2 control-label">设备标识：</label>
									    <div class="col-sm-1">
									      <input type="text" class="form-control"  style="width: 300px;" id="udid" placeholder="请输入设备标识">
									    </div>
								  </div>
								  
								  <div class="form-group">
								  		<label for="startTime" class="col-sm-2 control-label">反馈时间：</label>
								  		<div class="col-lg-2">
									      <input type="text" class="form_datetime form-control"  placeholder="开始反馈时间" id="startTime" >
									    </div>
									    <div class="col-lg-2">
									      <input type="text" class="form_datetime form-control" placeholder="结束反馈时间" id="endTime">
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
						<table class="table table-striped table-bordered table-hover  dataTable no-footer" id="role_list_table" style="width: 980px;">
						    <thead>
						        <tr role="row">
						        	<th class="text-center sorting_disabled"  style="width: 20px;"><input id="" type="checkbox" class="ipt_check_all"></th>
						            <th class="text-center sorting_asc"  style="width: 64px;">反馈ID</th>
						            <th class="text-center sorting"  style="width: 60px;">用户UID</th>
						            <th class="text-center sorting"  style="width: 103px;">设备标识</th>
						            <th class="text-center sorting"  style="width: 150px;">反馈内容</th>
						            <th class="text-center sorting"  style="width: 106px;">反馈时间</th>
						            <th class="text-center sorting"  style="width: 106px;">管理操作</th>
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

<script style="text/javascript">
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
 
	//显示返回具体内容
	function showDetail(id, obj){
		//战士内容
		$("#detail_"+id).toggle();
		//切换文字
		if($("#detail_"+id).attr("style") == "display: none;"){
			$(obj).html("明细");
		}else{
			$(obj).html("收起");
		}
	}

	//查询数据
	$("#btn").click(function(){		
		//获取数据
		getListData(1);
	});
	
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
		if(data.length > 0){
			var html = "";
			$.each(data, function(item){
				//主题内容
				html += "<tr class='text-center'><td><input type='checkbox'></td><td>"+data[item].feedBackId+"</td>"+"<td>"+data[item].uid+"</td>"+"<td>"+data[item].udid+"</td>"+"<td>"+data[item].shorCon+"</td>"+"<td>"+data[item].addTime+"</td>";
				html += "<td><a style='cursor:pointer' onclick='showDetail("+data[item].feedBackId+", this)'>详情</a></td>";
				html += "</tr>";
				
				//反馈内容明细
				html += "<tr style='display:none;' id='detail_"+data[item].feedBackId+"'><td colspan='7' style='padding:20px;'><strong>反馈具体内容：</strong><br>　　"+data[item].content+"</td></tr>";
			});
		}else{
			html += "<tr><td colspan='7' style='text-align:center;'>暂无数据！</td></tr>";
		}
		
		return html;
	}
	
	//加载列表数据
	var page = 1;
	getListData(1);
	function getListData(pageNum){
		//加载图标
		$("#subLoadhtml").html("<div style='text-align:center'><img src='${basePath}/resources/images/loading.gif'/></div>");
		
		//设置查询条件
		var uid				= $.trim($("#uid").val());
		var udid			= $.trim($("#udid").val());
		var startTime	= $.trim($("#startTime").val());
		var endTime	= $.trim($("#endTime").val());
		
		//设置查询页码
		 page = pageNum;
		var pageSize = 9;
		
		//加载数据
		  $.ajax({
	              type: 'post',
	              url: '${basePath}/admin/help/get/feedback/list',
	              dataType: 'json',
	              contentType:'application/json;charset=UTF-8',
	              timeout: 60000,
	              data:JSON.stringify({
	            	  page: page, 
	            	  pageSize: pageSize,
	            	  uid: uid,
	            	  udid: udid,
	            	  startTime: startTime,
	            	  endTime: endTime
	              }),  
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
</script>
