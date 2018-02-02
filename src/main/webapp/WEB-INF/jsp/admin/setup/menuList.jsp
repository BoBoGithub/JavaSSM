<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <h4>菜单管理列表</h4>
                    <div style="float:right;margin-top:-30px;">
                    	<button type="button" id="addNewMenu" class="btn btn-primary marR10">新增菜单</button>
                    </div>
                </div>
                <div class="ibox-content">
					<div id="user_list_table_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
						<table class="table table-striped table-bordered table-hover  dataTable no-footer" id="menu_list_table" role="grid" aria-describedby="menu_list_table_info" style="width: 980px;">
						    <thead>
						        <tr role="row">
						        	<th class="text-center sorting_disabled"  style="width: 40px;"><input id="" type="checkbox" class="ipt_check_all"></th>
						            <th class="text-center sorting_asc" style="width: 74px;">菜单ID</th>
						            <th class="text-center sorting" style="width: 650px;">菜单名称</th>
						            <th class="text-center sorting" style="width: 156px;">管理操作</th>
						        </tr>
						    </thead>
						    <tbody>
						    		<c:forEach var="menu" items="${menuListData}">
							    		<tr>
								    		<td class='text-center'><input type='checkbox'></td>
								    		<td>${menu.id}</td>
								    		<td>${menu.name}</td>
								    		<td><a onclick="addChildMenu(${menu.id})" style="cursor:pointer;">添加子菜单</a> | <a onclick="editChildMenu(${menu.id})" style="cursor:pointer;">修改</a> | <a onclick="delChildMenu(${menu.id})" style="cursor:pointer;">删除</a></td>
								    	</tr>
									</c:forEach>
						    </tbody>
						</table>
					</div>
                </div>
            </div>
        </div>
    </div>
</div>

<script style="text/javascript">
	//新增菜单
	$('#addNewMenu').click(function(){
		addChildMenu(0);
	});
	
	//刷新当前页面
	function flushPage(){
		var tb = $("#loadhtml");
    	tb.html(Common.loadingImg());
		tb.load("${basePath}/admin/setup/menu/list");
	}

	//新增子菜单
	function addChildMenu(id){
	 	//弹出层
		layer.open({
			  title: '新增菜单',
			  type: 2,
			  area: ['720px', '407px'],
			  content: ['${basePath}/admin/setup/menu/add?menuId='+id, 'no'],
			});
	}
	
	//修改菜单
	function editChildMenu(id){
	 	//弹出层
		layer.open({
			  title: '修改菜单',
			  type: 2,
			  area: ['720px', '407px'],
			  content: ['${basePath}/admin/setup/menu/edit?menuId='+id, 'no'],
		});
	}
	
	//删除菜单
	function delChildMenu(menuId){
		$.ajax({
            type: 'post',
            url: '${basePath}/admin/setup/del/menu',
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            data:JSON.stringify({
            	menuId: menuId
            }),  
            timeout: 60000, 
            success: function (json) {
            	 if(json.errno == 0 && json.data.ret){
	            		 layer.msg("删除成功！");
						//重新加载页面
	            		 flushPage();
				}else{
					layer.msg(json.errmsg);
				}
            }
        });
	}
</script>
