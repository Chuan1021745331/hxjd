/**
 * LarryCMS Common模块
 * Autor: Larry 
 * site: www.larrycms.com
 * Date :2017-01-24
 */
var t,
	page=!parent.page?1:parent.page,
	where=!parent.where?null:parent.where,
	active,
	zTree,
	treeNode_=null,
	treeType=0 ;
layui.define(['form','element','layer','jquery','table'],function(exports){
   var $ = layui.$,
   	   form = layui.form,
       device = layui.device(),
       layer = layui.layer,
       element = layui.element,
       table = layui.table;
   var LarryCommon = {
        larryCore: {
            tit: '提示您：',
            version: 'LarryCMSV1.9',
            errorTit:'错误提示！',
            errorDataTit:'数据源配置出错',
            paramsTit:'参数错误提示',
            closeTit:'关闭失败提示'
        },
        closeIndexs: {},
       /**
        * 关闭弹出层
        */
        larryCmsClose: function() {
            if (!this.closeIndexs['_' + this.index]) {
                this.closeIndexs['_' + this.index] = true;
                return layer.close(this.index);
            }
        },
        /**
         * 弹出警告消息框
         * @param msg 消息内容
         * @param callback 回调函数
         * @return {*|undefined}
         */
        larryCmsalert: function(msg, callback) {
            this.larryCmsClose();
            return this.index = layer.alert(msg, {
                end: callback,
                scrollbar: false
            });
        },
       /**
        * @description 抛出异常错误信息
        */
       larryCmsError: function(msg,title){
           parent.layer.alert(msg,{
              title: title,
              skin:'larry-debug',
			  icon: 2,
			  time: 0,
			  resize: false,
			  zIndex: layer.zIndex,
			  anim: Math.ceil(Math.random() * 6)
           });
           return;
       },
       /**
        * @description table的弹出页面,带刷新
        */
       tableOpenLayer: function(title,icon,url){
    	   var index = layer.open({
   	        	type: 2 ,
   	        	title: '<i class="icon iconfont '+icon+'"></i>'+title,
   	        	content: url,
   	        	resize: false,
   	        	anim: Math.ceil(Math.random() * 6),
    	   		success : function(layero, index){
   					setTimeout(function(){
   						layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
   							tips: 3
   						});
   					},500)
   				}
    	   });
    	   $(window).resize(function(){
   				layer.full(index);
   		   })
    	   layer.full(index);
       },
       simpleLayer:function(title,url,w,h){
    	   var index = top.layer.open({
  	        	type: 2 ,
  	        	title: title,
  	        	content: url,
  	        	resize: false,
  	        	area: [w, h],
  	        	anim: Math.ceil(Math.random() * 6),
	   	   		success : function(layero, index){
  					setTimeout(function(){
  						layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
  							tips: 3
  						});
  					},500)
  				}
	   	   });
       },
       formSubmit: function(id,url){
    	   form.on('submit('+id+')', function(data) {
    		   $.ajax({
   					url:url,
   					type:"POST",
   					data:data.field,
   					dataType:"json",
   					success:function(json){
   						//console.log("old");
   						if(json.errorCode==0){
   							top.parent.MSG(4,  json.message);
   							if(typeof(parent.t) != "undefined"){
   								parent.t.reload({
   	   			   	     			page: {
   	   			   	     		        curr: page //重新从第 1 页开始
   	   			   	     		    },
   	   			   	     			where: { //设定异步数据接口的额外参数，任意设
   	   			   	     				menuId: where
   	   			   	     			}
   	   		   	        		})
   							}
   							if(parent.treeNode_ != null){
   								if(parent.treeType==1){
   									parent.zTree.addNodes(parent.treeNode_, {id:json.data.id, pId:json.data.parent, name:json.data.name});
   								}else if(parent.treeType==2){
   									var node = parent.zTree.getNodeByParam("id", json.data.id, null);
   									node.name=json.data.name;
   									parent.zTree.updateNode(node);
   								}
   								
   								parent.treeNode_ =null;
   								parent.treeType = 0;
   							}
   							
   	   						var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
   	   		   				parent.layer.close(index); //再执行关闭
   						}else{
   							top.parent.MSG(3,  json.message);
   						}
   						
   					},
   					error:function(res){
   						top.parent.MSG(3, "数据提交异常");
   					}
   				});
   				
   				return false;
    	   });
       }, 
       /**
        * @description 普通ajax表单提交，id:lay-filter的Id， formId:表单Id， url:提交链接
        */
       formAjaxSubmit: function(id,formId,url){ 
    	   form.on('submit('+id+')', function(data) { 
    		   //$('"#'+formId+'"')
    	   $.post(url, $("#"+formId).serialize(),function(json,status){
				//console.log("new");
	    		if("success"==status){
					if(json.errorCode==0){
						top.parent.MSG(4,  json.message);
						if(typeof(parent.t) != "undefined"){
							parent.t.reload({
				   	     			page: {
				   	     		        curr: page //重新从第 1 页开始
				   	     		    },
				   	     			where: { //设定异步数据接口的额外参数，任意设
				   	     				menuId: where
				   	     			}
			   	        		})
						}
						if(parent.treeNode_ != null){
							if(parent.treeType==1){
								parent.zTree.addNodes(parent.treeNode_, {id:json.data.id, pId:json.data.parent, name:json.data.name});
							}else if(parent.treeType==2){
								var node = parent.zTree.getNodeByParam("id", json.data.id, null);
								node.name=json.data.name;
								parent.zTree.updateNode(node);
							}
							
							parent.treeNode_ =null;
							parent.treeType = 0;
						}
						
							var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			   				parent.layer.close(index); //再执行关闭
					}else{
						top.parent.MSG(3,  json.message);
					}
	    		} else {
	    			top.parent.MSG(3, "数据提交异常");
	    		}
			});
			
			return false;
    	   });
       },
       
       
       /**
        * @description 成功提示信息
        */
        larryCmsSuccess: function(msg, title,time) {
            this.larryCmsClose();
            return this.index = parent.layer.alert(msg, {
                title: title,
                skin:'larry-green',
                icon: 1,
                time: (time || 0) * 1000,
                resize: false,
                zIndex: layer.zIndex,
                anim: Math.ceil(Math.random() * 6)
            });
        },
       /**
        * @description 确认对话框
        */
        larryCmsConfirm: function(msg, url) {
            var self = this;
            return this.index = parent.layer.confirm(msg, {
                icon: 3,
                skin:'larry-green',
                title: self.larryCore.tit,
                offset: '200px',
                closeBtn: 0,
                skin: 'layui-layer-molv',
                anim: Math.ceil(Math.random() * 6),
                btn: ['确定', '取消']
            }, function(index) {
            	$.ajax({
   					url:url,
   					type:"POST",
   					dataType:"json",
   					success:function(json){
   						if(json.errorCode==0){
   							top.parent.MSG(4, json.message);
   	   		   				parent.layer.close(index); //再执行关闭
   						}else{
   							top.parent.MSG(3, json.message);
   						}
   						
   					},
   					error:function(res){
   						top.parent.MSG(3, "数据提交异常");
   					}
   				});
            	t.reload({
   	     			page: {
   	     		        curr: page //重新从第 1 页开始
   	     		    },
   	     			where: { //设定异步数据接口的额外参数，任意设
   	     				menuId: where
   	     			}
	        	})
	        	tempData=null;
                layer.close(index);
            }, function(index) {
            	parent.layer.close(index);
            });
        },
       // 系统消息提示处理
       /**
        * @description 
        */
        larryCmsMessage: function(msg,mark,time){
            var  that = this;
             msg = msg || 'default';
             mark = mark || 'other';
             Time = time || 2000;
             var htmlcon = htmldoc(function() {/*
                 <div class="larrycms-message" id="messageBox">
                     <div class="message-con clearfix" id="msgstatus">
                         <i id="larryicon" class="larry-icon"></i>
                         <div id="resultmsg" class='resultmsg'>larry</div>
                     </div>
                 </div>               
             */});
             var screenHeight = $(window).height();
             if(msg != 'default'){
             	if(mark == 'success' || mark == 'error'){
                     var index = layer.open({
                         type: 1,
                         closeBtn: 0, 
                         anim: Math.ceil(Math.random() * 6),
                         shadeClose: false,
                         shade: 0,
                         title: false,
                         area: ['520px', 'auto'],
                         content: htmlcon,
                         time: Time,
                         resize: false,
                         offset: [(screenHeight > 760) ? ((screenHeight - 320) / 2 + "px") : "60px"],
                         success: function(layero, index) {
                             $("#resultmsg").text(msg);
                             if(mark == 'success'){
                                 $('#larryicon').addClass('larry-chenggongtishi1');
                             }else{
                                 $('#messageBox').addClass('larry-message-error');
                                 $('#larryicon').addClass('larry-Error');
                             }
                             var conh = $('#messageBox').height();
                             if (conh > 97) {
                                 $('#messageBox').addClass('addWidth');
                                 $('#layui-layer' + index + ' .layui-layer-content').width(620);
                                 $('#layui-layer' + index + ' .layui-layer-content').height($('#messageBox').height());
                                 if ($('#messageBox').height() > 97) {
                                     var mtop = ($('#messageBox').height() - 90) / 2;
                                     $('#larryicon').css({"margin-top": mtop});
                                 }
                             }
                         }
                     });
             	}else{
             		var index = layer.open({
                         type: 1,
                         closeBtn: 0,
                         anim: Math.ceil(Math.random() * 6),
                         shadeClose: false,
                         shade: 0,
                         title: false,
                         area: ['720px', 'auto'],
                         content: htmlcon,
                         time: 2000,
                         resize: false,
                         offset: [(screenHeight > 760) ? (screenHeight - 360) / 2 + "px" : "40px"],
                         success: function(layero, index) {
                             $("#resultmsg").text(that.larryCore.tit+msg);
                             $('#messageBox').addClass('larry-message-tips');
                             $('#larryicon').addClass('larry-xiaoxitishi');
                             var conh = $('#messageBox').height();
                             if (conh > 100) {
                                 $('#messageBox').addClass('addWidth');
                                 $('#layui-layer' + index + ' .layui-layer-content').width(720);
                                 $('#layui-layer' + index + ' .layui-layer-content').height($('#messageBox').height());
                                 if ($('#messageBox').height() > 100) {
                                     var mtop = ($('#messageBox').height() - 90) / 2;
                                     $('#larryicon').css({"margin-top": mtop});
                                 }
                             }
                         }
                     });
             	}
             }else{

             }
             return;
        },
       /**
        * @description 加载jq第三方插件（可以使用layui Jq 也可以自定义传入任何版本的jq，并让依赖jq的第三方插件正常运行随调随用）
        */
       larryCmsLoadJq: function(jsUrl,callback,jqUrl){
            
            var urlArray = jsUrl.split("?")[0].split("/");
            var js_src = urlArray[urlArray.length -1];
           
            var scripts = document.getElementsByTagName("script");
            // 构建plugin
            var script = document.createElement("script");
            script.setAttribute("type", "text/javascript");
            script.setAttribute("src", jsUrl);
            script.setAttribute("async", true);
	        script.setAttribute("defer", true);

            var jqUrl = arguments[2] ? arguments[2] : cpath+'/js/jquery-1.12.4.min.js';
            var urlArrayJq = jqUrl.split("?")[0].split("/");
            var jq_src = urlArrayJq[urlArrayJq.length - 1];
            //环境中无$对象存在的时执行（基本用不上）
			if(!$) {
				// 构建jq
				var jq = document.createElement("script");
				jq.setAttribute("type", "text/javascript");
				jq.setAttribute("src", jqUrl);
				jq.setAttribute("async", false);
				jq.setAttribute("defer", false);
				var head = document.getElementsByTagName("head")[0];
			}

            var body = document.getElementsByTagName("body")[0];

            
            // 判断jq及插件是否存在
			if(!!scripts && 0 != scripts.length) {
                var jsName = new Array();
                for (var i = 0; i < scripts.length; i++) {
					var jsArray = scripts[i].src.split("?")[0].split("/");
					jsName[i] = jsArray[jsArray.length - 1];
				}
				var num = $.inArray('layui.js', jsName);
				// 判断插件是否存在
				if(($.inArray(jq_src, jsName) == -1) && ($.inArray(js_src, jsName) == -1)) {
                    if (!window.jQuery && $) {
						window.jQuery = $;
						if(!device.ie) {
							scripts[num].after(script);
						}else {
							body.appendChild(script);
						}
					}else if(!window.jQuery && !$) {
						head.appendChild(jq);
						body.appendChild(script);
					}else{
                        if(!device.ie) {
                            scripts[num].after(script);
                        }else {
                            body.appendChild(script);
                        }
                    }
					runHack();
					return true;
				}else if(($.inArray(jq_src, jsName) != -1) && ($.inArray(js_src, jsName) == -1)) {
					var num2 = $.inArray(jq_src, jsName);
					if(!device.ie) {
						scripts[num2].after(script);
					}else {
						body.appendChild(script);
					}
					runHack();
					return true;
				}else if(($.inArray(jq_src, jsName) != -1) && ($.inArray(js_src, jsName) != -1)){
					//jq存在 插件也存在，当然直接使用咯！
					runHack();
					return true;
				}else{
					layer.alert('上下文环境中未检测到jquery文件引入，任何依赖jquery的第三方插件将不能正常运行！！！');
				}
			}
			function runHack() {
				// IE
				if (document.all) {
					script.onreadystatechange = function() {
						var state = this.readyState;
						if (state === 'loaded' || state === 'complete') {                         
							callback();
						}
					};
				} else {
					//firefox, chrome
					script.onload = function() {
						callback();
					};
				}
			}
       },
       // 退出系统注销登录
        logOut: function (title, text, url,type, dataType, data, callback) {
            parent.layer.confirm(text, {
                title: title,
                resize: false,
                btn: ['确定退出系统', '不，我点错了！'],
                btnAlign: 'c',
                icon: 3

            }, function () {
            	$.ajax({
   					url:bpath+'/admin/logout',
   					type:"POST",
   					dataType:"json",
   					success:function(json){
   						if(json.errorCode==0){
   							top.parent.MSG(4, json.message);
   	   		   				parent.layer.close(index); //再执行关闭
   						}else{
   							top.parent.MSG(3, json.message);
   						}
   						
   					},
   					error:function(res){
   						top.parent.MSG(3, "数据提交异常");
   					}
   				});
			location.href = bpath+'/admin/login';
            }, function () {
                
            });
        }
   };
	
   /**
    * @description html处理函数
    */
   function htmldoc(fn){
   	    return fn.toString().split('\n').slice(1,-1).join('\n')+'\n';
   }
   exports('common',LarryCommon);
});