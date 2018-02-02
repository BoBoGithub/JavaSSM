<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<jsp:include page="../../common/header.jsp"  flush="true" />
<link href="${basePath}/resources/css/jquery.treeTable.css" rel="stylesheet">
<script type="text/javascript" src="${basePath}/resources/js/jquery/jquery.treetable.js"></script>
<script type="text/javascript">
	//处理table
  $(document).ready(function() {
    $("#dnd-example").treeTable({
    	indent: 20
    	});
  });
  function checknode(obj){
	 //更新自身的是否选中状态
	 $(obj).attr("checked", ($(obj).attr("checked") == 'checked' ? 'checked' : false));
      var chk = $("input[type='checkbox']");
      var count = chk.length;
      var num = chk.index(obj);
      var level_top = level_bottom =  chk.eq(num).attr('level')
      for (var i=num; i>=0; i--){
              var le = chk.eq(i).attr('level');
              if(eval(le) < eval(level_top)) {
                  chk.eq(i).attr("checked",'checked');
                  var level_top = level_top-1;
              }
      }
      for (var j=num+1; j<count; j++){
              var le = chk.eq(j).attr('level');
              if(chk.eq(num).attr("checked")=='checked') {
                  if(eval(le) > eval(level_bottom)) chk.eq(j).attr("checked",'checked');
                  else if(eval(le) == eval(level_bottom)) break;
              }else {
                  if(eval(le) > eval(level_bottom)) chk.eq(j).attr("checked",false);
                  else if(eval(le) == eval(level_bottom)) break;
              }
      }
  }
</script>

<section class="panel panel-default">
			<div class="panel-body">
				<table width="100%" cellspacing="0" id="dnd-example" class="table table-striped table-bordered table-hover  dataTable no-footer">
					<tbody>
						<c:forEach var="menu" items="${menuListData}">
							<tr id='node-${menu.id}'  ${menu.pnode}>
								<td style='padding-left:30px;'><input type='checkbox' name='menuId' value='${menu.id}' level='${menu.level}' ${menu.checked} onclick='javascript:checknode(this);'> ${menu.name}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<footer class="panel-footer text-right bg-light lter">
				<button type="button" class="btn btn-success btn-s-xs" id="submit">提交</button>
				<button type="button" class="btn btn-danger btn-s-xs" id="closeWin">关闭</button>
			</footer> 
		</section>

<script type="text/javascript">
	//提交操作
	$("#submit").click(function(){
		//设置按钮状态
		Common.changeBtnDisable("#submit");
		
		//提取选中的checkbox
		var menuIds = [];
		$("[name='menuId'][checked]").each(function(){
			menuIds.push($(this).val());
		});

		//提交设置角色权限
		$.ajax({
            type: 'post',
            url: '${basePath}/admin/setup/set/role/permit',
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            data:JSON.stringify({
          	  roleId: ${roleId}, 
          	menuIds: menuIds
            }),  
            timeout: 60000, 
            success: function (json) {
					if(json.errno == 0 && json.data.ret){
						//获取窗口索引
		  				var index = parent.layer.getFrameIndex(window.name); 
						
		  				//关闭当前窗口
		  			    parent.layer.close(index);
					}else{
						layer.msg(json.errmsg);
						
						//设置按钮状态
	  					Common.changeBtnAble("#submit");
					}
            }
        });
	});

	//关闭操作
	$('#closeWin').click(function(){
		//获取窗口索引
		var index = parent.layer.getFrameIndex(window.name); 
		
		//关闭当前窗口
		    parent.layer.close(index);
	});
</script>
<jsp:include page="../../common/footer.jsp"  flush="true" />