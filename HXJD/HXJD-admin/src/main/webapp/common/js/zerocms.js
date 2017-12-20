var zeroTab;
layui.use(['jquery','zeroElem','layer','common','form','zeroMenu','zeroTab'],function(){
	var $ = layui.$,
		zeroElem = layui.zeroElem,
		layer = layui.layer,
		common = layui.common,
		form = layui.form,
		// 页面上下文菜单
		zeroMenu = layui.zeroMenu();
		//核心操作
		zeroTab = layui.zeroTab({
			top_menu: '#zero_top_menu',
			left_menu: '#zero_left_menu',
			zero_elem: '#zero_tab',
			spreadOne: true
		});
    // 页面禁止双击选中
    $('body').bind("selectstart", function() {return false;});
    zeroTab.basepath(bpath);
    // 菜单初始化
    // 方法1：
	zeroTab.menuSet({
		  tyep:'GET',
		  url: bpath+'/admin/getMenuss?t='+Math.random(),
		  topFilter: 'TopMenu',
		  lefFilter: 'zeroSide'
	});
    zeroTab.menu();
    // 方法2：
    /*$.ajaxSettings.async = false;
	$.getJSON('/backstage/datas/data.json?t=' + Math.random(), function(menuData) {
		zeroTab.menuSet({
			data: menuData,
			spreadOne: false,
			topFilter: 'TopMenu',
			lefFilter: 'zeroSide'
		});
		zeroTab.menu();
	});
    $.ajaxSettings.async = true;*/
    // 1监听导航菜单点击事件 请注释2
    $('#zero_top_menu li').on('click',function(){
    	 var group = $(this).children('a').data('group');
    	 zeroTab.on('click(TopMenu)',group);
    	 //监听左侧菜单点击事件
    	 zeroTab.on('click(zeroSide)',group,function(data){
              zeroTab.tabAdd(data.field);
    	 });
    })
    $('#zero_top_menu li').eq(0).click();
    // 2若不存在顶级菜单 注释以上顶级菜单监听js
	// zeroTab.on('click(zeroSide)','0', function(data) {
	// 	zeroTab.tabAdd(data.field);
	// });
	$(document).ready(function() {
		var fScreen = localStorage.getItem("fullscreen_info");
		var themeName = localStorage.getItem('themeName');
		if (themeName) {
			$("body").attr("class", "");
			$("body").addClass("zeroTheme-" + themeName);
		}
		if (fScreen && fScreen != 'false') {
			var fScreenIndex = layer.alert('按ESC退出全屏', {
				title: '进入全屏提示信息',
				skin: 'layui-layer-lan',
				closeBtn: 0,
				anim: 4,
				offset: '100px'
			}, function() {
				entryFullScreen();
				$('#FullScreen').html('<i class="zero-icon zero-quanping"></i>退出全屏');
				layer.close(fScreenIndex);
			});
		}
	});
	// 刷新iframe
	$("#refresh_iframe").click(function() {
		$('#zero_tab_content').children('.layui-show').children('iframe')[0].contentWindow.location.reload(true);
	});
	// 常用操作
	$('#buttonRCtrl').find('dd').each(function() {
		$(this).on('click', function() {
			var eName = $(this).children('a').attr('data-eName');
			zeroTab.tabCtrl(eName);
		});
	});

    //清除缓存
    $('#clearCached').on('click', function () {
        zeroTab.cleanCached();
        layer.alert('缓存清除完成!本地存储数据也清理成功！', { icon: 1, title: '系统提示' }, function () {
            location.reload();//刷新
        });
    });
    
    $('#zeroTheme').on('click', function() {
		var fScreen = localStorage.getItem('fullscreen_info');
		var themeName = localStorage.getItem('themeName');
		layer.open({
			type: 1,
			title: false,
			closeBtn: true,
			shadeClose: false,
			shade: 0.35,
			area: ['490px', '365px'],
			isOutAnim: true,
			resize: false,
			anim: Math.ceil(Math.random() * 6),
			content: $('#zeroThemeSet').html(),
			success: function(layero, index) {
				if (fScreen && fScreen != 'false') {
					$("input[lay-filter='fullscreen']").attr("checked", "checked");
				}
				if (themeName) {
					$("#themeName option[value='" + themeName + "']").attr("selected", "selected");
				}
				form.render();
			}
		});
		// 全屏开启
		form.on('switch(fullscreen)', function(data) {
			var fValue = data.elem.checked;
			localStorage.setItem('fullscreen_info', fValue); //fullscreen_info:fValue

		});
//        // tabSession开启
//		form.on('switch(tabSession)', function(data) {
//			var tabS = data.elem.checked;
//			localStorage.setItem('tabSessions', tabS); //tabSessions:tabS
//		});
		// tab选项卡切换是否自动刷新
		form.on('switch(autoRefresh)',function(data){
            var auto = data.elem.checked;
			localStorage.setItem('autoRefresh', auto); 
		});
		// 主题设置
		form.on('select(zeroTheme)', function(data) {
			var themeValue = data.value;
			localStorage.setItem('themeName', themeValue); //themeName:themeValue
			if (themeName) {
				$("body").attr("class", "");
				$("body").addClass("zeroTheme-" + themeName);
			}
			form.render('select');
		});

		return false;
        // 是否存入数据库
		// form.on('submit(submitlocal)',function(data){
		// })
	});
    
    // 全屏切换
	$('#FullScreen').bind('click', function() {
		var fullscreenElement =
			document.fullscreenElement ||
			document.mozFullScreenElement ||
			document.webkitFullscreenElement;
		if (fullscreenElement == null) {
			entryFullScreen();
			$(this).html('<i class="zero-icon">&#xe604;</i>退出全屏');
		} else {
			exitFullScreen();
			$(this).html('<i class="zero-icon">&#xe604;</i>全屏');
		}
	});

	// 进入全屏：
	function entryFullScreen() {
		var docE = document.documentElement;
		if (docE.requestFullScreen) {
			docE.requestFullScreen();
		} else if (docE.mozRequestFullScreen) {
			docE.mozRequestFullScreen();
		} else if (docE.webkitRequestFullScreen) {
			docE.webkitRequestFullScreen();
		}
	}

	// 退出全屏
	function exitFullScreen() {
		var docE = document;
		if (docE.exitFullscreen) {
			docE.exitFullscreen();
		} else if (docE.mozCancelFullScreen) {
			docE.mozCancelFullScreen();
		} else if (docE.webkitCancelFullScreen) {
			docE.webkitCancelFullScreen();
		}
	}
	// 登出系统
	$('#logout').on('click',function(){
		var url ='login.html';
		common.logOut('退出登陆提示！','你真的确定要退出系统吗？',url);
	});

    var zeroMenuData = [
		[{
			text: "刷新当前页",
		    func: function() {
		    	$(".layui-tab-content .layui-tab-item").each(function() {
		    		if ($(this).hasClass('layui-show')) {
		    			$(this).children('iframe')[0].contentWindow.location.reload(true);
		    		}
		    	});
		    }
		},{
			text: "重载主框架",
			func: function() {
				document.location.reload();
			}
		},{
			text: "设置系统主题",
		    func: function() {
			    $("#zeroTheme").trigger("click");
		    }
		}, {
			text: "选项卡常用操作",
			data: [
				[{
					text: "定位当前选项卡",
					func: function() {
						$("#tabCtrD").trigger("click");
					}
				},{
					text: "关闭当前选项卡",
					func: function() {
						$("#tabCtrA").trigger("click");
					}
				}, {
					text: "关闭其他选项卡",
					func: function() {
						$("#tabCtrB").trigger("click");
					}
				}, {
					text: "关闭全部选项卡",
					func: function() {
						$("#tabCtrC").trigger("click");
					}
				}]
			]
		}]
	];
	zeroMenu.ContentMenu(zeroMenuData,{
         name: "body" 
	},$('body'),bpath);
	$('#zero_body').mouseover(function(){
        zeroMenu.remove();
	});


	$('.pressKey').on('click', function() {
		var titW = parseInt($('#zero_tab').width() - 270);
		var $tabUl = $('#zero_tab').find('li'),
			all_li_w = 0;
		$tabUl.each(function(i, n) {
			all_li_w += $(n).outerWidth(true);
		});
		if (titW >= all_li_w) {
			layer.tips('当前没有可移动的选项卡！', $(this), {
				tips: [3, '#FF5722']
			});
		}
	});

     
    function key(e) {
        var keynum;
        if (window.event) {
            keynum = e.keyCode;
        } else if (e.which) {
            keynum = e.which;
        }
        if(e.altKey && keynum == 76){
         	 lockSystem();
         }
    }
	// 锁屏控制提示
    $('#lock').mouseover(function(){
   	   layer.tips('请按Alt+L快速锁屏！', '#lock', {
             tips: [1, '#FF5722'],
             time: 1500
       });
    });
    var locked = 0;
    // 锁定屏幕
   function lockSystem(){
   		
   	   var url = cpath+'/datas/lock.json';
   	   locked = 1;
   	   $.post(
   	   	   url,
   	   	   function(data){
   	   	   if(data.lock){
   	   	   	  checkLockStatus(locked);
   	   	   }else{
              layer.alert('锁屏失败，请稍后再试！');
   	   	   }
   	   });
   	   startTimer();
   	   
   }
   
   // 点击锁屏
   $('#lock').click(function(){
   	    lockSystem();
   });
   // 解锁进入系统
   $('#unlock').click(function(){
   	    unlockSys();
   });
   function unlockSys(){
   	   if($('#unlock_pass').val() == 'zerocms'){
           locked = 0;
   	   	   checkLockStatus(locked);
   	   	   return;
   	   }else{
   	   	   layer.tips('模拟锁屏，输入密码：zerocms 解锁', $('#unlock'), {
				tips: [3, '#FF5722'],
				time:1000
			});
   	   	   return;
   	   }  
   }
   // 监控lock_password 键盘事件
   $('#unlock_pass').keypress(function(e){
        if (window.event && window.event.keyCode == 13) {
            unlockSys();
            return false;
        }
    });

    function startTimer(){
   	    var today=new Date();
        var h=today.getHours();
        var m=today.getMinutes();
        var s=today.getSeconds();
        m = m < 10 ? '0' + m : m;
        s = s < 10 ? '0' + s : s;
        $('#time').html(h+":"+m+":"+s);
        t=setTimeout(function(){startTimer()},500);
   }
   // 锁屏状态检测
   function checkLockStatus(locked){
        // 锁屏
        if(locked == 1){
        	$('.lock-screen').show();
            $('#locker').show();
            $('#zero_admin_out').hide();
            $('#lock_password').val('');
        }else{
        	$('.lock-screen').hide();
            $('#locker').hide();
            $('#zero_admin_out').show();
        }
    } 
    
    // 兼容蛋疼的移动端
	$('#zeroMobile').on('click', function() {
		if ($('.zero-mobile #zero_left').css("display") == "none") {
			$('.zero-mobile #zero_left').show();
		} else {
			$('.zero-mobile #zero_left').hide();
		}
        
        if ($('.zero-mobile .zerocms-top-menu').css("display") == "none") {
			$('.zero-mobile .zerocms-top-menu').show();
		} else {
			$('.zero-mobile .zerocms-top-menu').hide();
		}

	});
    var device = layui.device();
	// 兼容IE8 chrome 60以下版本 calc
	if(device.ie && device.ie <9){
          $('.zerocms-top').width($('#zero_admin_out').width()-200);
	}else if(navigator.userAgent.indexOf("Chrome") <= 60 && navigator.userAgent.indexOf("Chrome") > -1){
          $('.zerocms-top').width($('#zero_admin_out').width()-200);
	}

});