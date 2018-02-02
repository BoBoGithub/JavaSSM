<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
    
    //设置菜单图标样式
    String[] themIcon = {"fa-th-large",  "fa-file-o",  "fa-search-minus", "fa-dribbble", "fa-windows", "fa-apple",  "fa-arrow-circle-o-up",  "fa-list-alt", "fa-gavel",  "fa-maxcdn",  "fa-flag-o",  "fa-chevron-circle-up",  "fa-xing",  "fa-tumblr"};
    pageContext.setAttribute("themIcon", themIcon);
%>
<header class="bg-dark dk header navbar navbar-fixed-top-xs">
	<div class="navbar-header aside-md" >
		<a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html"><i class="fa fa-bars"></i></a>
		<a  class="navbar-brand" data-toggle="fullscreen"><img src="${basePath}/resources/notebook/notebook_files/logo.png" class="m-r-sm">Mi-Gang</a>
		<a class="btn btn-link visible-xs" data-toggle="dropdown"data-target=".nav-user"> <i class="fa fa-cog"></i></a>
	</div>
	<ul class="nav navbar-nav hidden-xs">
		<c:if test="${userBigMenuList != null}">
			<c:forEach var="menu" items="${userBigMenuList}">
			<li>
				<a class="dker" href="javascript:void(0);"  onclick="getSubMenu('${menu.name}', ${menu.id});"> <i class="fa ${themIcon[menu.id%14]}"></i> <span class="font-bold">${menu.name}</span></a>
			</li>
			</c:forEach>
		</c:if>
	</ul>
	<ul class="nav navbar-nav navbar-right m-n hidden-xs nav-user" style="cursor:pointer;">
		<li class="hidden-xs">
			<a class="dropdown-toggle dk" data-toggle="dropdown"> <i class="fa fa-bell"></i> <span class="badge badge-sm up bg-danger m-l-n-sm count" style="display: inline-block;">3</span></a>
			<section class="dropdown-menu aside-xl">
				<section class="panel bg-white">
					<header class="panel-heading b-light bg-light">
						<strong>You have <span class="count" style="display: inline;">3</span> notifications</strong>
					</header>
					<div class="list-group list-group-alt animated fadeInRight">
						<a   class="media list-group-item" style="display: block;">
							<span class="pull-left thumb-sm text-center"><i class="fa fa-envelope-o fa-2x text-success"></i></span>
							<span class="media-body block m-b-none">Sophi sent you a email<br>
							<small class="text-muted">1 minutes ago</small></span>
						</a>
						<a class="media list-group-item">
							<span class="pull-left thumb-sm"><img src="${basePath}/resources/notebook/notebook_files/avatar.jpg" alt="John said" class="img-circle"></span> 
							<span class="media-body block m-b-none"> Use awesome animate.css<br> <small class="text-muted">10minutes ago</small></span>
						</a> 
						<a  class="media list-group-item">
							<span class="media-body block m-b-none"> 1.0 initial released<br><small class="text-muted">1 hour ago</small></span>
						</a>
					</div>
					<footer class="panel-footer text-sm">
						<a class="pull-right"><iclass="fa fa-cog"></i></a> <a href="index.html#notes" data-toggle="class:show animated fadeInRight">See all the notifications</a>
					</footer>
				</section>
			</section>
		</li>
		<li class="dropdown hidden-xs">
			<a class="dropdown-toggle dker" data-toggle="dropdown"><i class="fa fa-fw fa-search"></i></a>
			<section class="dropdown-menu aside-xl animated fadeInUp">
				<section class="panel bg-white">
					<form role="search">
						<div class="form-group wrapper m-b-none">
							<div class="input-group">
								<input type="text" class="form-control" placeholder="Search">
								<span class="input-group-btn">
									<button type="submit" class="btn btn-info btn-icon">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</div>
					</form>
				</section>
			</section>
		</li>
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown"> 
				<span class="thumb-sm avatar pull-left"> <img src="${basePath}/resources/notebook/notebook_files/avatar.jpg"></span> ${username} <b class="caret"></b>
			</a>
			<ul class="dropdown-menu animated fadeInRight">
					<span class="arrow top"></span>
					<li><a  >设置</a></li>
					<li><a  onclick="javascript:updPwd();">密码修改</a></li>
					<li><a > <span class="badge bg-danger pull-right">3</span> 消息</a></li>
					<li><a>帮助</a></li>
					<li class="divider"></li>
					<li><a href="${basePath}/user/logout">退出</a></li>
				</ul>
		</li>
	</ul>
	<script>
		//修改密码
		function updPwd(){
		 	//弹出层
			layer.open({
				  title: '密码修改',
				  type: 2,
				  area: ['720px', '420px'],
				  content: ['${basePath}/admin/setup/user/edit/pwd', 'no'],
				});
		}
		
		//动态加载子菜单
		function getSubMenu(menuName, pid){
			//提交用户修改
			$.ajax({
              type: 'post',
              url: '${basePath}/admin/setup/get/sub/menu',
              contentType:'application/json;charset=UTF-8',
              dataType: 'json',
              timeout: 60000, 
              data:JSON.stringify({
					pid: pid
              }),
              success: function (menuData) {
   					if(menuData.errno == 0 && menuData.data.subMenuList.length > 0){
   						//拼接菜单追加到左侧栏
   						var html = "";
   						var themIcon = ['fa-dashboard', 'fa-pencil-square', 'fa-columns', 'fa-book', 'fa-camera', 'fa-turkish-lira', 'fa-search-plus', 'fa-plus-square-o'];
   						var themColor= ["bg-info", "bg-danger", "bg-warning", "bg-primary", "bg-dark", "bg-empty"];
   						var subMenuList = menuData.data.subMenuList;
   						for(var i = 0;i<subMenuList.length;i++){
   							console.log(i%8);
   							console.log(themColor[i%8]);
   							html += '<li>';
   							html +=		 '<a href="javascript:void(0)">';
   							html += 		'<i class="fa '+themIcon[i%8]+' icon"> <b class="'+themColor[i%6]+'"></b></i>';
   							html += 		'<span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span> <span>'+subMenuList[i].name+'</span>';
   							html += 	'</a>';
   							html += 	'<ul class="nav lt menuLi">';
   							
   							//设置三级菜单
   							for(var j = 0; j<subMenuList[i].child.length; j++){
   								html += 		'<li><a href="javascript:void(0)"  onclick="Common.loadPage(\''+menuName+'\', \''+subMenuList[i].name+'\',\''+subMenuList[i].child[j].name+'\',\'${basePath}'+subMenuList[i].child[j].url+'\', true)"> <i class="fa fa-angle-right"></i> <span>'+subMenuList[i].child[j].name+'</span></a></li>';
   							}
   							html += 	'</ul>';
   							html += '</li>';
   						}
						
						//追加到页面
						$('#leftMenu').html(html);
   					}else if(menuData.errno != 0){
   						layer.msg(menuData.errmsg);
   					}
              }
          });
		}
	</script>
</header>