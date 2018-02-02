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
                    <h4>产品设置管理</h4>
                    <div style="float:right;margin-top:-30px;">
                    	<button type="button" id="addProduct" class="btn btn-primary marR10">新增产品</button>
                    </div>
                </div>
                <div class="ibox-content">

					<div id="user_list_table_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
						<table class="table table-striped table-bordered table-hover  dataTable no-footer" id="user_list_table" role="grid" aria-describedby="user_list_table_info" style="width: 97%;">
						    <thead>
						        <tr role="row">
						        	<th class="text-center sorting_disabled" rowspan="1" colspan="1" aria-label="" style="width: 40px;">
						            <input id="" type="checkbox" class="ipt_check_all">
						            </th>
						            <th class="text-center sorting_asc"   style="width: 114px;">产品ID</th>
						            <th class="text-center sorting"  style="width: 110px;">产品名称</th>
						            <th class="text-center sorting"  style="width: 103px;">借款额度</th>
						            <th class="text-center sorting"  style="width: 103px;">借款期限</th>
						            <th class="text-center sorting"  style="width: 116px;">日利率</th>
						            <th class="text-center sorting"  style="width: 116px;">服务费</th>
						            <th class="text-center sorting"  style="width: 116px;">信审费</th>
						            <th class="text-center sorting"  style="width: 120px;">状态</th>
						            <th class="text-center sorting"  style="width: 135px;">操作</th>
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
	//新增产品
	$('#addProduct').click(function(){
	 	//弹出层
		layer.open({
			  title: '新增产品',
			  type: 2,
			  area: ['720px', '540px'],
			  content: ['${basePath}/admin/product/add', 'no'],
			}); 
	});
	
	//编辑产品
	function editProduct(pid){
	 	//弹出层
		layer.open({
		  title: '编辑产品',
		  type: 2,
		  area: ['720px', '540px'],
		  content: ['${basePath}/admin/product/edit?productId='+pid, 'no'],
		}); 
	}

	//加载列表数据
	getListData(1);
	function getListData(pageNum){
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
	              url: '${basePath}/admin/product/get/list',
	              dataType: 'json',
	              cache:false,
	              contentType:'application/json;charset=UTF-8',
	              data:JSON.stringify({
	            	  page: page, 
	            	  pageSize: pageSize,
	              }),  
	              timeout: 60000, 
	              success: function (json) {
	            	  $('#subLoadhtml').hide();
						if(json.errno == 0){
							appendHtml(json.data.productListData, page, Math.ceil(json.data.total/pageSize));
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
				html += "<tr role='row'>"+
										"<td class='text-center'><input type='checkbox'></td>"+
										"<td>"+data[item].productId+"</td>"+
										"<td>"+data[item].productName+"</td>"+
										"<td>￥"+data[item].borrowMoney+"</td>"+
										"<td>"+data[item].borrowLimit+"天</td>"+
										"<td>"+(Math.round(data[item].dayRate*10000)/100)+"%</td>"+
										"<td>￥"+data[item].serviceCharge+"</td>"+
										"<td style='color:gray;'>￥10</td>"+
										"<td>"+(data[item].status == 1 ? "启用" : "停用")+"</td>"+
										"<td>"+
												" <a href='javascript:void(0);' onclick='editProduct("+data[item].productId+");'>编辑</a> "+
												(data[item].status == 1 ? "<a href='javascript:void(0);' onclick='updProduct("+data[item].productId+", 1, this);'>停用</a>" : "<a href='javascript:void(0);' onclick='updProduct("+data[item].productId+", 2, this);'>启用</a>")+
												" <a href='javascript:void(0);' onclick='delProduct("+data[item].productId+", 3, this);'>删除</a>"+
										"</td>"+
								"</tr>";
			});
		}else{
			html += "<tr><td colspan='10' style='text-align:center;'>暂无数据！</td></tr>";
		}
		
		return html;
	}
	
	//删除产品
	function delProduct(pid, type, obj){
			//询问框
			layer.confirm('您确认删除数据？', {
			  btn: ['确定','取消'], //按钮
			  title: "确认信息"
			}, function(){
				updProduct(pid, type, obj);
			}, function(){});
	}
	
	//更新产品状态
	var clickBtnStatus = false;
	function updProduct(pid, type, obj){
		if(clickBtnStatus){
			return true;
		}else{
			clickBtnStatus = true;
		}
		//更新数据
		$.ajax({
            type: 'post',
            url: '${basePath}/admin/product/upd/status',
            contentType:'application/json;charset=UTF-8',
            dataType: 'json',
            timeout: 60000,
            data:JSON.stringify({
          	  productId: pid,
          	  type: type
            }),
            success: function (json) {
            	clickBtnStatus = false;
          	  if(json.errno == 0 && json.data.updStatus){
          		  //添加成功提示
          		   layer.msg((type == 3 ? "删除" : "修改")+"成功！");
          		  setTimeout(function(){
			  				//重新加载用户列表
			  				parent.getListData(page);
          		  }, 2000);
          	  }else{
          		  layer.msg(json.errmsg);
          	  }
            }
        });
	}
</script>