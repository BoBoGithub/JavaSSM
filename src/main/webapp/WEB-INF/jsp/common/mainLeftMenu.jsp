<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  + request.getServerName() + ":" + request.getServerPort()  + path;  
    pageContext.setAttribute("basePath",basePath);
%>
<section class="vbox">
	<!-- 
	<header class="header bg-primary lter text-center clearfix">
		<div class="btn-group">系统菜单</div>
	</header>
	-->
	<section class="w-f scrollable">
		<div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="5px" data-color="#333333">
			<nav class="nav-primary hidden-xs">
					<ul class="nav" id="leftMenu">	
							<li class="active">
								<a href="javascript:void(0)" class="active">    
									<i class="fa fa-turkish-lira icon"> <b class="bg-info"></b></i>
									<span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span> <span>个人信息管理</span>
								</a>
								<ul class="nav lt" style="display:block;">
										<li><a href="javascript:void(0)"  onclick="Common.loadPage('基础信息','个人信息管理','修改个人信息','${basePath}/admin/setup/user/info', true)"> <i class="fa fa-angle-right"></i> <span>修改个人信息</span></a></li>
								</ul>
							</li>
					</ul>

<!-- 
					<ul class="nav" id="leftMenu">	  
						<li>
							<a href="javascript:void(0)">    
								<i class="fa fa-turkish-lira icon"> <b class="bg-info"></b></i>
								<span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span> <span>个人信息管理</span>
							</a>
							<ul class="nav lt">
									<li><a href="javascript:void(0)"  nav-n="个人信息管理,修改个人信息,${basePath}/admin/setup/user/info"> <i class="fa fa-angle-right"></i> <span>修改个人信息</span></a></li>
							</ul>
						</li>
						<li>
							<a href="javascript:void(0)" > 
									<i class="fa fa-dashboard icon"> <b class="bg-danger"></b></i>
									<span class="pull-right"><i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span>
									<span>系统基础管理</span>
							</a>
							<ul class="nav lt">
										<li class="active"><a href="javascript:void(0)" class="active" nav-n="系统基础管理,用户管理,${basePath}/admin/setup/user/list"> <i class="fa fa-angle-right"></i> <span>用户管理</span></a></li>
										<li class="active"><a	href="javascript:void(0)" class="active" nav-n="系统基础管理,角色管理,${basePath}/admin/setup/role/list"> <i class="fa fa-angle-right"></i> <span>角色管理</span></a></li>								
										<li class="active"><a href="javascript:void(0)" class="active" nav-n="系统基础管理,菜单管理,${basePath}/admin/setup/menu/list"> <i class="fa fa-angle-right"></i> <span>菜单管理</span></a></li>								
							</ul>
						</li>
						<li>
							<a href="javascript:void(0)">  
									<i class="fa fa-pencil-square icon"> <b class="bg-warning"></b></i>
								    <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span> <span>借款用户管理</span>
							</a>
							<ul class="nav lt">
									<li class="active"><a href="javascript:void(0)" class="active" nav-n="借款用户管理,借款用户列表,${basePath}/admin/loan/user/list"> <i class="fa fa-angle-right"></i> <span>借款用户列表</span></a></li>								
							</ul>
						</li>
						<li >
							<a href="javascript:void(0)">   
								<i class="fa fa-columns icon"> <b class="bg-primary"></b></i>
								 <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span> <span>借款订单管理</span>
							</a>
							<ul class="nav lt">
									<li class="active"><a href="javascript:void(0)" class="active" nav-n="借款订单管理,借款订单列表,${basePath}/admin/loan/order/list"> <i class="fa fa-angle-right"></i> <span>借款订单列表</span></a></li>								
							</ul>
						</li>
						<li>
							<a href="javascript:void(0)">    
								<i class="fa fa-book icon"> <b class="bg-info"></b></i>
								<span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span> <span>帮助中心管理</span>
							</a>
							<ul class="nav lt">
									<li class="active"><a href="javascript:void(0)" class="active" nav-n="帮助中心管理,用户反馈,${basePath}/admin/help/feedback/list"> <i class="fa fa-angle-right"></i> <span>用户反馈</span></a></li>
							</ul>
						</li>
						
						
						<li>
							<a href="javascript:void(0)">    
								<i class="fa fa-camera icon"> <b class="bg-dark"></b></i>
								<span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span> <span>数据查询</span>
							</a>
							<ul class="nav lt">
									<li><a href="javascript:void(0)"  nav-n="数据查询,用户基础信息,${basePath}/admin/loan/user/list"> <i class="fa fa-angle-right"></i> <span>用户基础信息</span></a></li>
									<li><a href="javascript:void(0)"  nav-n="数据查询,借款订单信息,${basePath}/admin/loan/order/list"> <i class="fa fa-angle-right"></i> <span>借款订单信息</span></a></li>
							</ul>
						</li>
						<li>
							<a href="javascript:void(0)">    
								<i class="fa fa-turkish-lira icon"> <b class="bg-info"></b></i>
								<span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span> <span>借款单管理</span>
							</a>
							<ul class="nav lt">
									<li><a href="javascript:void(0)"  nav-n="借款单管理,审核借款订单,${basePath}/admin/loan/check/list"> <i class="fa fa-angle-right"></i> <span>审核借款订单</span></a></li>
									<li><a href="javascript:void(0)"  nav-n="借款单管理,机器决策订单,${basePath}/admin/loan/mechine/list"> <i class="fa fa-angle-right"></i> <span>机器决策订单</span></a></li>
							</ul>
						</li>
						<li>
							<a href="javascript:void(0)">    
								<i class="fa fa-search-plus icon"> <b class="bg-empty"></b></i>
								<span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span> <span>资金放款</span>
							</a>
							<ul class="nav lt">
									<li><a href="javascript:void(0)"  nav-n="资金放款,通道放款单列表,${basePath}/admin/loan/pay/list"> <i class="fa fa-angle-right"></i> <span>通道放款单列表</span></a></li>
							</ul>
						</li>
						<li>
							<a href="javascript:void(0)">    
								<i class="fa fa-plus-square-o icon"> <b class="bg-primary"></b></i>
								<span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i></span> <span>产品运营</span>
							</a>
							<ul class="nav lt">
									<li><a href="javascript:void(0)"  nav-n="产品运营,产品设置,${basePath}/admin/product/list"> <i class="fa fa-angle-right"></i> <span>产品设置</span></a></li>
							</ul>
						</li>
-->
				</ul>
			</nav>
		</div>
	</section>
	<footer class="footer lt hidden-xs b-t b-dark">
		<div id="chat" class="dropup">
			<section class="dropdown-menu on aside-md m-l-n">
				<section class="panel bg-white">
					<header class="panel-heading b-b b-light">Active chats</header>
					<div class="panel-body animated fadeInRight">
						<p class="text-sm">No active chats.</p>
						<p><a href="#" class="btn btn-sm btn-default">Start a chat</a></p>
					</div>
				</section>
			</section>
		</div>
		<div id="invite" class="dropup">
			<section class="dropdown-menu on aside-md m-l-n">
				<section class="panel bg-white">
					<header class="panel-heading b-b b-light">
						John <i class="fa fa-circle text-success"></i>
					</header>
					<div class="panel-body animated fadeInRight">
						<p class="text-sm">No contacts in your lists.</p>
						<p><a href="#" class="btn btn-sm btn-facebook"><i class="fa fa-fw fa-facebook"></i> Invite from Facebook</a></p>
					</div>
				</section>
			</section>
		</div>
		<a href="#nav" data-toggle="class:nav-xs" class="pull-right btn btn-sm btn-dark btn-icon"> 
			<i class="fa fa-angle-left text"></i> <i class="fa fa-angle-right text-active"></i>
		</a>
		<div class="btn-group hidden-nav-xs">
			<button type="button" title="Chats" class="btn btn-icon btn-sm btn-dark" data-toggle="dropdown" data-target="#chat">
				<i class="fa fa-comment-o"></i>
			</button>
			<button type="button" title="Contacts" class="btn btn-icon btn-sm btn-dark" data-toggle="dropdown" data-target="#invite">
				<i class="fa fa-facebook"></i>
			</button>
		</div>
	</footer>
</section>