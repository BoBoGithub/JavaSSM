/**
 * 工具组件 对原有的工具进行封装，自定义某方法统一处理
 */
;
(function(){
	Common = {
		//设置设置按钮状态为不可点击
		changeBtnDisable: function(obj){
			$(obj).addClass('disabled');
			$(obj).prop('disabled', true);
		},
		//设置按钮状态为可点击
		changeBtnAble: function(obj){
			$(obj).removeClass('disabled');
			$(obj).prop('disabled', false);
		},
		//加载右侧
		loadingImg : function() {
			return  '<div class="alert alert-warning"><button type="button" class="close" data-dismiss="alert"><i class="ace-icon fa fa-times"></i></button><div style="text-align:center"><img src="' + resPath + '/images/loading.gif"/><div></div>';
		},
		//加载页面
		loadPage: function(bigTitle, menuTitle, pageTitle, pageUrl, addHistory){
			var html = '<li><i class="fa fa-home"></i>';
			html+='<a href="'+rootPath+'/main/index">Home</a></li>';
			html+='<li><a href="javascript:void(0)">'+bigTitle+'</a></li>';
			html+='<li><a href="javascript:void(0)">'+menuTitle+'</a></li>';
			html+='<li><a href="javascript:void(0)">'+pageTitle+'</a></li>';
			$("#topli").html(html);
			var tb = $("#loadhtml");
			tb.html(Common.loadingImg());
			tb.load(pageUrl);
			
			//设置历史请求记录
			if(addHistory){
				var state = {
						title: menuTitle,
						bigTitle: bigTitle,
						pageTitle: pageTitle,
						url: pageUrl.replace(rootPath+"/", rootPath+"/#")
				};
				//新增历史记录
				window.history.pushState(state, document.title, pageUrl.replace(rootPath+"/", rootPath+"/#"));
			}
		},
		//定时检测登录状态
		checkUserStatus:function(url){
			$.ajax({
					type: 'post',url: rootPath+"/user/checks",dataType: 'json',cache:false,timeout: 60000,success: function (data) {
						if(data.errno == 0 && data.data.status == 0){
							location.href=location.href;
						}
					}
			});
		},
		//创建分页
		createPagerHtml: function(page, total){
			var pager = "";
			if(total <= 1){
				return pager;
			}
			
			//处理分页数据
			pager += '<ul class="pagination">';
			
			//上一页
			pager += '<li '+(page == 1 ? 'class="disabled"' : '' )+'><a '+(page == 1 ? 'href="javaScript:void(0)"' : '')+(page != 1 ? 'href="javaScript:getListData('+(page -1)+')"' : '')+'>上一页</a></li>';

			//页码  当总页数小于等于7时，显示页码1...7页 
			if(total <= 7){
				for(var i = 1; i <= total;i++){
					pager += '<li '+(page == i ? 'class="active"' : '')+'><a href="javaScript:getListData('+i+')">'+i+'</a></li>';
				}
			}
			
			//当总页数大于7时
			if(total > 7){
				//当前页数小于等于4时，显示1到5...最后一页
				if(page <= 4){
					for(var i = 1; i<= 5; i++){
						pager += '<li '+(page == i ? 'class="active"' : '')+'><a href="javaScript:getListData('+i+')">'+i+'</a></li>';
					}
					pager += '<li><a href="#">...</a></li>';
					pager +='<li'+(page == total ? 'class="active"' : '')+'><a href="javaScript:getListData('+total+')">'+total+'</a></li>';
				}
				
				//当前页数大于4时，如果当前页小于总页码书-3，则显示1...n-1,n,n+1...最后一页
				if(page > 4){
					if(page < total -3){
						pager += '<li><a href="javaScript:getListData(1)">1</a></li><li><a href="#">...</a></li>';
						for(var i = parseInt(page)-1;i <= parseInt(page)+1;i++){
							pager += '<li '+(page == i ? 'class="active"' : '')+'><a href="javaScript:getListData('+i+')">'+i+'</a></li>';
						}
						
						pager += '<li><a href="#">...</a></li>';
						
						pager += '<li '+(page == total ? 'class="active"' : '')+'><a href="javaScript:getListData('+total+')">'+total+'</a></li>';
					}
				}
				
				//当前页数大于4时，如果当前页大于总页码书-4，则显示1...最后一页-3，最后一页-2，最后一页-1，最后一页
				if(page > total-4){
					pager += '<li><a href="javaScript:getListData(1)">1</a></li><li><a href="#">...</a></li>';
					for(var i=total-3;i<=total; i++){
						pager += '<li '+(page == i ? 'class="active"' : '')+'><a href="javaScript:getListData('+i+')">'+i+'</a></li>';
					}
				}
			}

			//下一页 当当前页码为最后一页或者最后一页为0时，隐藏下一页按钮 当当前页码不等于总页码时，跳转下一页
			pager += '<li '+(page == total || total == 0 ?  'class="disabled"' : '')+'><a '+(page == total || total == 0 ? 'href="javaScript:void(0)"' : '')+' '+(page != total ? 'href="javaScript:getListData('+(page+1)+')"' : '')+'>下一页</a></li>';
					
			pager += '<li>';
					pager += '<div class="form-group">';
						    pager += '<div class="col-sm-1">';
						      pager += '<input type="text" class="form-control" style="width: 85px;" id="pageNum" placeholder="跳转页码">';
						    pager += '</div>';
						    pager += '<div class="col-sm-1" style="margin-left:60px;">';
						    	pager += '<a href="javascript:void(0)" class="btn btn-default" id="setPage" onclick="Common.setPage()">提交</a>'; 
						    pager += '</div>';
					  pager += '</div>';
					  
			pager += '</li>';
			pager += "</ul>";
			
			return pager;
		},
		setPage: function(){
			//获取设置的页码
			var pageNum = $.trim($('#pageNum').val());
			if(pageNum.length == 0 || isNaN(pageNum)){
				$('#pageNum').select();
				return false;
			}
			getListData(pageNum);
		}
	}
})();

//响应浏览器的前进、后退操作
window.addEventListener('popstate', function(e){if (history.state){Common.loadPage(e.state.bigTitle, e.state.title, e.state.pageTitle, e.state.url.replace(rootPath+"/#", rootPath+"/"), false);}}, false);

// 表单json格式化方法……不使用&拼接
/**
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, // month
			"d+" : this.getDate(), // day
			"h+" : this.getHours(), // hour
			"m+" : this.getMinutes(), // minute
			"s+" : this.getSeconds(), // second
			"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
			"S" : this.getMilliseconds()// millisecond
		}
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}
})(jQuery);
**/
