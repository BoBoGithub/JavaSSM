<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<link href="${basePath}/resources/css/style.css" rel="stylesheet">
<script src="${basePath}/resources/js/date/bootstrap-datetimepicker.min.js"></script>
<script src="${basePath}/resources/js/date/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${basePath}/resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/>

<div class="alert alert-danger" style="margin: 2px auto 0px">
	Tip提示信息－测试使用
</div>

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h4>用户列表</h4>
                </div>
                <div class="ibox-content">
           		 <div class="uc m-l-5 m-r-5" role="form" style="padding:10px 0px;">
           		  
           		  <div class="form-horizontal" role="form">
           		 	 <div class="form-group">
						    <label for="uid" class="col-sm-2 control-label">用户UID：</label>
						    <div class="col-sm-1">
						      <input type="text" class="form-control" style="width: 150px;" id="uid" placeholder="请输入用户UID">
						    </div>
						    
						    <label for="mobile" class="col-sm-2 control-label">手机号：</label>
						    <div class="col-sm-1">
						      <input type="text" class="form-control" style="width: 150px;" id="mobile" placeholder="请输入手机号">
						    </div>
					  </div>
				
					 <div class="form-group">
						    <label for="realName" class="col-sm-2 control-label">是否实名：</label>
						      <div class="radio">
									<label>
										<input type="radio" name="realName" value="0" checked>全部
									</label>
									<label>
										<input type="radio" name="realName" value="1" > 已实名
									</label>
									<label>
										<input type="radio" name="realName"  value="2" > 未实名
									</label>
								</div>
					  </div>
					 
					   <div class="form-group">
						    <label for="infoAuth" class="col-sm-2 control-label">是否完善信息：</label>
						      <div class="radio">
									<label>
										<input type="radio" name=infoAuth  value="0" checked>全部
									</label>
									<label>
										<input type="radio" name="infoAuth" value="1" > 已完善
									</label>
									<label>
										<input type="radio" name="infoAuth"  value="2" > 未完善
									</label>
								</div>
					  </div>
					   
					   <div class="form-group">
						    <label for="phoneAuth" class="col-sm-2 control-label">手机号授信：</label>
						      <div class="radio">
									<label>
										<input type="radio" name=phoneAuth  value="0" checked>全部
									</label>
									<label>
										<input type="radio" name="phoneAuth" value="1" > 已授信
									</label>
									<label>
										<input type="radio" name="phoneAuth"  value="2" > 未授信
									</label>
								</div>
					  </div>
					  
					  <div class="form-group">
					  		<label for="reg_time" class="col-sm-2 control-label">注册时间：</label>
					  		<div class="col-lg-2">
						      <input type="text" class="form_datetime form-control" placeholder="开始时间" id="regStartTime">
						    </div>
						    <div class="col-lg-2">
						      <input type="text" class="form_datetime form-control" placeholder="结束时间" id="regEndTime">
						    </div>
					  </div>
					  
					  <div class="form-group">
					  			<label for="dropdownBtnp" class="col-sm-2 control-label">所在地：</label>
					  			<div class="col-sm-1">
									<div class="dropdown">  
											<button class="btn btn-default" data-toggle="dropdown" id="dropdownBtnp">  
												<span id="provinceText">选择省份</span>  
												<span class="caret"></span>  
											</button>  
											<input type="hidden" value="0" id="province" />
											<ul class="dropdown-menu" style="max-height: 285px; overflow-y: auto;cursor:pointer;" id="pSelect">
												<li data-original-index="0" class="selected active"><a>选择省份</a></li>
												<c:forEach var="region" items="${regionList}">
														<li data-original-index="${region.regionId}"><a>${region.name}</a></li>
												</c:forEach>
											</ul>  
									</div> 
					   			</div>
					   			
					   			<div class="col-sm-1" style="margin-left:10px;">
									<div class="dropdown">  
											<button class="btn btn-default" data-toggle="dropdown" id="dropdownBtnc">  
												<span id="cityText">选择城市</span>  
												<span class="caret"></span>  
											</button>  
											<input type="hidden" value="0" id="city" />
											<ul class="dropdown-menu" style="max-height: 285px; overflow-y: auto;cursor:pointer;" id="cSelect">  
												<li data-original-index="0" class="selected active"><a>选择城市</a></li> 
											</ul>  
									</div> 
								</div>
								
					   			<div class="col-sm-1" style="margin-left:10px;">
									<div class="dropdown">  
											<button class="btn btn-default" data-toggle="dropdown" id="dropdownBtnd">  
												<span id="districtText">选择区县</span>  
												<span class="caret"></span>  
											</button>  
											<input type="hidden" value="0" id="district" />
											<ul class="dropdown-menu" style="max-height: 285px; overflow-y: auto;cursor:pointer;" id="dSelect">  
												<li data-original-index="0" class="selected active"><a>选择区县</a></li>
											</ul>  
									</div> 
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
	<table class="table table-striped table-bordered table-hover  dataTable no-footer" id="user_list_table" role="grid" aria-describedby="user_list_table_info" style="width: 928px;">
	    <thead>
	        <tr role="row">
	        	<th class="text-center sorting_disabled" rowspan="1" colspan="1" aria-label="" style="width: 40px;">
	            <input id="" type="checkbox" class="ipt_check_all">
	            </th>
	            <th class="text-center sorting_asc"   style="width: 104px;">用户ID</th>
	            <th class="text-center sorting" style="width: 110px;">手机号</th>
	            <th class="text-center sorting" style="width: 93px;">IP地址</th>
	            <th class="text-center sorting"  style="width: 110px;">是否实名</th>
	            <th class="text-center sorting"  style="width: 115px;">是否完善信息</th>
	            <th class="text-center sorting"  style="width: 86px;">手机号授信</th>
	            <th class="text-center sorting"  style="width: 86px;">借款笔数</th>
	            <th class="text-center sorting"  style="width: 118px;">注册时间</th>
	        </tr>
	    </thead>
	    <tbody id="tableList">
	 
	   </tbody>
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
	 //父级菜单　省份 Select选择框点击
    $('#pSelect li').click(function(){
    	//动态赋值
    	var pid = $(this).attr('data-original-index');
    	$("#provinceText").html($(this).find('a').html());
    	$("#province").val(pid);
    	
    	//设置选装状态
    	$(this).addClass("selected active").siblings().removeClass("selected active");
    	
    	//设置自己菜单初始化
    	$("#cityText").html("选择城市");
    	$("#city").val(0);
    	$('#dSelect').html("<li data-original-index='0' class='selected active'><a>选择区县</a></li>");
    	$("#districtText").html("选择区县");
    	$("#district").val(0);
    	
    	//联动菜单
    	getRegionData(pid, "cSelect", "选择城市");
    });
	 
	 //父级菜单　城市 Select选择框点击
	 function getCitySelect(obj){
		   	//动态赋值
	    	var pid = $(obj).attr('data-original-index');
	    	$("#cityText").html($(obj).find('a').html());
	    	$("#city").val(pid);
	    	
	    	//设置选装状态
	    	$(obj).addClass("selected active").siblings().removeClass("selected active");
	    	
	    	//初始化子地区
	    	$("#districtText").html("选择区县");
	    	$("#district").val(0);
	    	
	    	//联动菜单
	    	getRegionData(pid, "dSelect", "选择区县");
	 }
	 
	 //父级菜单　区县 Select选择框点击
	 function getDistrictSelect(obj){
		   	//动态赋值
	    	var pid = $(obj).attr('data-original-index');
	    	$("#districtText").html($(obj).find('a').html());
	    	$("#district").val(pid);
	    	
	    	//设置选装状态
	    	$(obj).addClass("selected active").siblings().removeClass("selected active");
	 }
	 
	 //调取地区数据
	 function getRegionData(pid, id, firstEle){
		//动态调取省份下的城市数据
	   	 $.ajax({
	            type: 'post',
	            url: '${basePath}/admin/help/get/region/list',
	            dataType: 'json',
	            contentType:'application/json;charset=UTF-8',
	            data:JSON.stringify({pid: pid}),  
	            timeout: 60000, 
	            success: function (json) {
						if(json.errno == 0){
							appendSelectHtml(json.data.regionList, id, firstEle);
						}else{
							layer.msg(json.errmsg);
						}
	            }
	        }); 
	 }
	 
	 //追加下拉框数据
	 function appendSelectHtml(regionData, id, firstEle){
		 //设置追加内容
		 var html = "<li data-original-index='0' class='selected active'><a>"+firstEle+"</a></li>";
		 if(regionData.length > 0){
			 $.each(regionData, function(item){
				 html += "<li data-original-index='"+regionData[item].regionId+"' onclick='"+(id == "dSelect" ? "getDistrictSelect(this)" : "getCitySelect(this)")+"'><a>"+regionData[item].name+"</a></li>";
			});
		 }
		 
		 //追加元素
		 $("#"+id).html(html);
	 }

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
    
    //提交查询
    $("#btn").click(function(){
    	getListData(1);
    });
    
	//加载列表数据
	getListData(1);
	function getListData(pageNum){
		//提取用户uid
		var uid = $.trim($('#uid').val());
		
		//提取手机号
		var mobile = $.trim($("#mobile").val());
		
		//提取实名信息
		var realName = $("input[name='realName']:checked").val();
		
		//提取完善个人信息
		var infoAuth = $("input[name='infoAuth']:checked").val();
		
		//提取手机号授信
		var phoneAuth = $("input[name='phoneAuth']:checked").val();
		
		//提取注册时间
		var regStartTime = $.trim($('#regStartTime').val());
		var regEndTime = $.trim($('#regEndTime').val());
		
		//提取所在地
		var province	= $.trim($("#province").val());
		var city 			= $.trim($("#city").val());
		var district		= $.trim($("#district").val());
		
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
	              url: '${basePath}/admin/get/loan/user/list',
	              dataType: 'json',
	              cache:false,
	              contentType:'application/json;charset=UTF-8',
	              data:JSON.stringify({
	            	  uid:uid,
	            	  mobile: mobile,
	            	  realName: realName,
	            	  infoAuth: infoAuth,
	            	  phoneAuth: phoneAuth,
	            	  regStartTime: regStartTime,
	            	  regEndTime: regEndTime,
	            	  province: province,
	            	  city: city,
	            	  district: district,
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
		var  html = "";
		if(data.length > 0){
			$.each(data, function(item){
				html += "<tr role='row'>"+
										"<td class='text-center'><input type='checkbox'></td>"+
										"<td>"+data[item].uid+"</td>"+
										"<td>"+data[item].mobile+"</td>"+
										"<td>"+data[item].ip+"</td>"+
										"<td>"+(data[item].realName == '1' ? "是" : "否")+"</td>"+
										"<td>"+(data[item].infoAuth == '1' ? "是" : "否")+"</td>"+
										"<td>"+(data[item].mobileAuth == '1' ? "是" : "否")+"</td>"+
										"<td>"+data[item].loanNum+"</td>"+
										"<td>"+data[item].regTime+"</td>"+
								"</tr>";
			});
		}else{
			html += "<tr><td colspan='9' style='text-align:center;'>暂无数据！</td></tr>";
		}
		
		return html;
	}
    
</script>