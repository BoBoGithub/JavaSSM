<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;
    pageContext.setAttribute("basePath",basePath);
%>
<link href="${basePath}/resources/css/style.css" rel="stylesheet">
<div class="margin-top-2 alert alert-info" data-ng-show="vm.tableDisplay &amp;&amp; !vm.nopermission"><b bo-text="'msg.cm.lb.tips'|translate">提醒：</b><span bo-text="'msg.sub.contact.tip'|translate">Tip提示信息测试使用</span></div>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h4>用户管理列表</h4>
                    <div style="float:right;margin-top:-30px;">
                    	<button type="button" id="addAccount" class="btn btn-primary marR10">新增</button>
                    </div>
                </div>
                <div class="ibox-content">
                	<div class="form-horizontal" role="form">
           		 	 <div class="form-group">
						    <label for="accountName" class="col-sm-2 control-label">用户名：</label>
						    <div class="col-sm-1">
						      <input type="text" class="form-control" style="width: 150px;" id="accountName" placeholder="请输入用户名">
						    </div>
						    
						    <label for="mobile" class="col-sm-1 control-label"></label>
						    <div class="col-sm-1">
						      <a href="javascript:void(0)" class="btn btn-default" id="search">查询</a> 
						    </div>
					  </div>
					  </div>
                
					<div id="user_list_table_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
						<table class="table table-striped table-bordered table-hover  dataTable no-footer" id="role_list_table" style="width: 980px;">
						    <thead>
						        <tr role="row">
						        	<th class="text-center sorting_disabled"  style="width: 20px;"><input id="" type="checkbox" class="ipt_check_all"></th>
						            <th class="text-center sorting_asc"  style="width: 64px;">用户UID</th>
						            <th class="text-center sorting"  style="width: 110px;">用户名</th>
						            <th class="text-center sorting"  style="width: 133px;">真实姓名</th>
						            <th class="text-center sorting"  style="width: 50px;">创建时间</th>
						            <th class="text-center sorting"  style="width: 106px;">用户状态</th>
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
	//新增用户
	$('#addAccount').click(function(){
	 	//弹出层
		layer.open({
			  title: '新增用户',
			  type: 2,
			  area: ['720px', '500px'],
			  content: ['${basePath}/admin/setup/user/add', 'no'],
			}); 
	});
	
	//修改用户
	function editUser(uid){
	 	//弹出层
		layer.open({
			  title: '修改用户',
			  type: 2,
			  area: ['720px', '500px'],
			  content: ['${basePath}/admin/setup/user/edit?adminUid='+uid, 'no'],
		});
	}
	
	//禁用用户
	function forbidUser(uid){
		updAdminUserStatus(uid, 1, '禁用成功！');
	}
	
	//恢复用户
	function renewUser(uid){
		updAdminUserStatus(uid, 0, '恢复成功！');
	}
	
	//删除用户
	function delUser(uid){
		updAdminUserStatus(uid, 2, '删除成功！');
	}
	
	//更新用户状态
	function updAdminUserStatus(uid, status, msg){
		  $.ajax({
              type: 'post',
              url: '${basePath}/admin/setup/del/user',
              dataType: 'json',
              contentType:'application/json;charset=UTF-8',
              timeout: 60000,
              data:JSON.stringify({
            	  adminUid: uid,
            	  status: status
              }),  
              success: function (json) {
            	  $('#subLoadhtml').hide();
            	  if(json.errno == 0){
            		  layer.msg(msg);
            		  
            		  //重新加载页面
            		  getListData(page);
					}else{
						layer.msg(json.errmsg);
					}
              }
          });
	}

	//查询数据
	$("#search").click(function(){
		//检查查询条件
		var searchStr = $.trim($("#accountName").val());
		if(searchStr.length == 0){
			//提示信息并返回
			$("#accountName").select();
			layer.msg('请输入正确的用户名！', function(){});
			return false;
		}
		
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
		var  html = "";
		var userStatus = "";
		if(data.length > 0){
			$.each(data, function(item){
				userStatus = data[item].status ? "禁用":"正常";
				html += "<tr class='text-center'><td><input type='checkbox'></td><td>"+data[item].uid+"</td>"+"<td>"+data[item].username+"</td>"+"<td>"+data[item].realname+"</td>"+"<td>"+data[item].ctime+"</td>"+"<td>"+userStatus+"</td>";
				if(data[item].uid == 1){
					html += "<td><a style='color:gray;text-decoration:none;''>修改</a> | <a style='color:gray;text-decoration:none;'>禁用</a> | <a style='color:gray;text-decoration:none;'>删除</a></td>";
				}else{
					if(data[item].status){
						html += "<td><a onclick='editUser("+data[item].uid+")'' style='cursor:pointer'>修改</a> | <a onclick='renewUser("+data[item].uid+")' style='cursor:pointer'>还原</a> | <a onclick='delUser("+data[item].uid+")' style='cursor:pointer'>删除</a></td>";
					}else{
						html += "<td><a onclick='editUser("+data[item].uid+")'' style='cursor:pointer'>修改</a> | <a onclick='forbidUser("+data[item].uid+")' style='cursor:pointer'>禁用</a> | <a onclick='delUser("+data[item].uid+")' style='cursor:pointer'>删除</a></td>";
					}
				}
				html += "</tr>";
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
		
		//设置查询页码
		 page = pageNum;
		var pageSize = 9;
		
		//加载数据
		  $.ajax({
	              type: 'post',
	              url: '${basePath}/admin/setup/get/user/list',
	              dataType: 'json',
	              contentType:'application/json;charset=UTF-8',
	              timeout: 60000,
	              data:JSON.stringify({
	            	  page: page, 
	            	  pageSize: pageSize,
	            	  userName:$.trim($("#accountName").val())
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
