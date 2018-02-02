<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
                    <h4>角色管理列表</h4>
                    <div style="float:right;margin-top:-30px;">
                    	<button type="button" id="addAccount" class="btn btn-primary marR10">新增</button>
                    </div>
                </div>
                <div class="ibox-content">
					<div id="user_list_table_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
						<table class="table table-striped table-bordered table-hover  dataTable no-footer" id="role_list_table" style="width: 980px;">
						    <thead>
						        <tr role="row">
						        	<th class="text-center sorting_disabled"  style="width: 40px;"><input id="" type="checkbox" class="ipt_check_all"></th>
						            <th class="text-center sorting_asc"  style="width: 44px;">角色ID</th>
						            <th class="text-center sorting"  style="width: 110px;">角色名称</th>
						            <th class="text-center sorting"  style="width: 133px;">角色描述</th>
						            <th class="text-center sorting"  style="width: 50px;">状态</th>
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
	//新增用户 //http://blog.csdn.net/yuanzhugen/article/details/50298225
	$('#addAccount').click(function(){
	 	//弹出层
		layer.open({
			  title: '新增角色',
			  type: 2,
			  area: ['720px', '357px'],
			  content: ['${basePath}/admin/setup/role/add', 'no'],
			}); 
	});
	
	//角色权限设置
	function setRolePpriv(roleName, roleId){
		//弹出层
		layer.open({
			  title: roleName+'－权限设置',
			  type: 2,
			  area: ['720px', '450px'],
			  content: ['${basePath}/admin/setup/role/permit?roleId='+roleId],
		}); 
	}
	
	//角色下的成员
	function roleUser(roleName, roleId){
		//弹出层
		layer.open({
			  title: roleName+'－成员列表',
			  type: 2,
			  area: ['720px', '485px'],
			  content: ['${basePath}/admin/setup/role/user?roleId='+roleId],
		}); 
	}
	
	//删除操作
	function delRole(roleId){
		$.ajax({
            type: 'post',
            url: '${basePath}/admin/setup/del/role',
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            data:JSON.stringify({
            	roleId: roleId
            }),  
            timeout: 60000, 
            success: function (json) {
            	 if(json.errno == 0 && json.data.ret){
	            		 layer.msg("删除成功！");
						//重新加载页面
						getListData(page);
				}else{
					layer.msg(json.errmsg);
				}
            }
        });
	}
	
	//修改角色信息
	function editRole(roleId){
		//弹出层
		layer.open({
			  title: '角色修改',
			  type: 2,
			  area: ['720px', '357px'],
			  content: ['${basePath}/admin/setup/role/edit?roleId='+roleId, 'no'],
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
		var status = "";
		if(data.length > 0){
			$.each(data, function(item){
				//设置角色状态
				status = data[item].status == 0 ? "正常" : "禁用";
				html += "<tr><td class='text-center'><input type='checkbox'></td><td>"+data[item].roleId+"</td>"+"<td>"+data[item].roleName+"</td>"+"<td>"+data[item].roleDesc+"</td>"+"<td>"+status+"</td>"+
				"<td>";
				if(data[item].roleId==1){
					html += "<a style='color:gray;text-decoration:none;'>权限设置</a> | "+
					"<a onclick='roleUser(\""+data[item].roleName+"\","+data[item].roleId+");'' style='cursor:pointer;'>成员管理</a> | "+
					"<a  style='color:gray;text-decoration:none;'>修改</a> | "+
					"<a  style='color:gray;text-decoration:none;'>删除</a>";
				}else{
					html += "<a onclick='setRolePpriv(\""+data[item].roleName+"\","+data[item].roleId+");'' style='cursor:pointer;'>权限设置</a> | "+
					"<a onclick='roleUser(\""+data[item].roleName+"\","+data[item].roleId+");'' style='cursor:pointer;'>成员管理</a> | "+
					"<a onclick='editRole("+data[item].roleId+");'' style='cursor:pointer;'>修改</a> | "+
					"<a onclick='delRole("+data[item].roleId+");'' style='cursor:pointer;'>删除</a>";
				}
				html += "</td></tr>";
			});
		}else{
			html += "<tr><td colspan='6' style='text-align:center;'>暂无数据！</td></tr>";
		}
		
		return html;
	}
	
	//加载列表数据
	var page = 1;
	getListData(1);
	function getListData(pageNum){
		//加载图标
		$('#tableList').html("");
		$("#pagerList").html("");
		$("#subLoadhtml").html("<div style='text-align:center'><img src='${basePath}/resources/images/loading.gif'/></div>");
		
		//设置查询页码
		page = pageNum;
		var pageSize = 9;
		
		 //加载数据
		  $.ajax({
	              type: 'post',
	              url: '${basePath}/admin/setup/get/role/list',
	              dataType: 'json',
	              contentType:'application/json;charset=UTF-8',
	              data:JSON.stringify({
	            	  page: page, 
	            	  pageSize: pageSize
	              }),  
	              timeout: 60000, 
	              success: function (json) {
	            	  $('#subLoadhtml').hide();
						if(json.errno != 0){
							layer.msg(json.errmsg);
						}else{
							appendHtml(json.data.list, page, Math.ceil(json.data.total/pageSize));
						}
	              }
	          });
	}
</script>
