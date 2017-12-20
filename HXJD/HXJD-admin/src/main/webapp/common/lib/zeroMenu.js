/**
 * @Date 2017-07-05
 * zeroMS zeroMenu模块
 * Autor: zero 
 * site: www.zerocms.com
 * @copyright [www.zerocms.com]
 * @link      http://www.zerocms.com/
 * @version   [v1.9] 
 * @param     {[type]}                 exports){} [description]
 * @return    {[type]}                              [description]
 */
layui.define(['jquery', 'layer', 'common', 'element'], function(exports) {
	var $ = layui.$,
		layer = layui.layer,
		common = layui.common,
		element = layui.element;

	var zeroRightMenu = function() {
		this.config = {
			name: "",
			offsetX: 2,
			offsetY: 2,
			textLimit: 16,
			beforeShow: $.noop,
			afterShow: $.noop
		};
		this.params = '';
		this._data ='';
	};
	var D = $(document).data("func", {});
    var B = $("body");
	zeroRightMenu.prototype.ContentMenu = function(data, options,obj) {
		var that = this;
		that._data = data;
		that.params = $.extend(true, that.config, options || {});

		obj.each(function() {
			this.oncontextmenu = function(e) {
				//回调
				if ($.isFunction(that.params.beforeShow)) {
					that.params.beforeShow.call(this);
				}
				e = e || window.event;
				//阻止冒泡
				e.cancelBubble = true;
				if (e.stopPropagation) {
					e.stopPropagation();
				}
                //隐藏当前上下文菜单，确保页面上一次只有一个上下文菜单
				that.hide();
				var st = D.scrollTop();
				var jqueryMenu = that.funzeroMenu(that._data);
				if (jqueryMenu) {
                    var zero_leftX,zero_topY;
                    var browseW = $(window).width();
                    var browseH = $(window).height();
                    var menuW = $('div.zeroMenuBox').width();
                    var menuH = $('div.zeroMenuBox').height();

					if(browseW<(e.clientX + that.params.offsetX+menuW)){
						zero_leftX = e.clientX - that.params.offsetX - menuW;
						jqueryMenu.find('ul li').children('div.zeroMenuBox').css({
                            top: "-35px",
                            left: -(menuW+2)
						});
					}else{
						zero_leftX = e.clientX + that.params.offsetX;
						jqueryMenu.find('ul li').children('div.zeroMenuBox').css({
                            top: "-1px",
                            left: (menuW-5)
						});
					}
					if(browseH <(e.clientY+that.params.offsetY+menuH)){
                        zero_topY = e.clientY - that.params.offsetY - menuH;
					}else{
						zero_topY = e.clientY + st + that.params.offsetY;
					}
					jqueryMenu.css({
						display: "block",
						left: zero_leftX,
						top: zero_topY
					});
					
					D.data("target", jqueryMenu);
					D.data("trigger", this);
					//回调
					if ($.isFunction(that.params.afterShow)) {
						that.params.afterShow.call(this);	
					}
					return false;
				}
			};
		});
		if (!B.data("bind")) {
			B.bind("click", that.hide).data("bind", true);
		}
	};

	zeroRightMenu.prototype.htmlCreateMenu = function(datum) {
		var that = this;
		var dataMenu = datum || that._data,
			nameMenu = datum ? Math.random().toString() : that.params.name,
			htmlMenu = "",
			htmlCorner = "",
			clKey = "zero_menu_";
        if ($.isArray(dataMenu) && dataMenu.length){
            htmlMenu = '<div id="zeroMenu_'+ nameMenu +'" class="'+ clKey +'box zeroMenuBox">' +
								'<div class="'+ clKey +'body">' +
									'<ul class="'+ clKey +'ul">';
			$.each(dataMenu, function(i, arr) {
				if (i) {
					htmlMenu += '<li class="' + clKey + 'li_separate">&nbsp;</li>';
				}
				if ($.isArray(arr)) {
					$.each(arr, function(j, obj) {
						var text = obj.text,
							htmlMenuLi = "",
							strTitle = "",
							rand = Math.random().toString().replace(".", "");
						if (text) {
							if (text.length > that.params.textLimit) {
								text = text.slice(0, that.params.textLimit) + "…";
								strTitle = ' title="' + obj.text + '"';
							}
							if ($.isArray(obj.data) && obj.data.length) {
								htmlMenuLi = '<li class="' + clKey + 'li" data-hover="true">' + that.htmlCreateMenu(obj.data) +
									'<a href="javascript:" class="' + clKey + 'a"' + strTitle + ' data-key="' + rand + '"><i class="' + clKey + 'triangle"></i>' + text + '</a>' +
									'</li>';
							} else {
								htmlMenuLi = '<li class="' + clKey + 'li">' +
									'<a href="javascript:" class="' + clKey + 'a"' + strTitle + ' data-key="' + rand + '">' + text + '</a>' +
									'</li>';
							}

							htmlMenu += htmlMenuLi;
							var objFunc = D.data("func");
							objFunc[rand] = obj.func;
							D.data("func", objFunc);
						}
					});
				}

			});
		    htmlMenu += '</ul>' +
									'</div>' +
								'</div>';
        }
        return htmlMenu;
	};

	zeroRightMenu.prototype.funzeroMenu = function() {
		var that = this;
		var idKey = "#zeroMenu_",
			clKey = "zero_menu_",
			jqueryMenu = $(idKey + that.params.name);
		if (!jqueryMenu.size()) {
			$("body").append(that.htmlCreateMenu());
			//事件
			$(idKey + that.params.name + " a").bind("click", function() {
				var key = $(this).attr("data-key"),
					callback = D.data("func")[key];
				if ($.isFunction(callback)) {
					callback.call(D.data("trigger"));
				}
				that.hide();
				return false;
			});
			$(idKey + that.params.name + " li").each(function() {
				var isHover = $(this).attr("data-hover"),
					clHover = clKey + "li_hover";

				$(this).hover(function() {
					var jqueryHover = $(this).siblings("." + clHover);
					jqueryHover.removeClass(clHover).children("." + clKey + "box").hide();
					jqueryHover.children("." + clKey + "a").removeClass(clKey + "a_hover");

					if (isHover) {
						$(this).addClass(clHover).children("." + clKey + "box").show();
						$(this).children("." + clKey + "a").addClass(clKey + "a_hover");
					}

				});

			});
			return $(idKey + that.params.name);
		}
		return jqueryMenu;
	};
	zeroRightMenu.prototype.hide = function() {
		var target = D.data("target");
		if (target && target.css("display") === "block") {
			target.hide();
		}
	};
	zeroRightMenu.prototype.remove = function() {
		var target = D.data("target");
		if (target) {
			target.remove();
		}
	};


	// 创建zeroMenu对象
	var zeroMenu = new zeroRightMenu();

	exports('zeroMenu', function() {
		return zeroMenu;
	})
});