layui.use(['jquery','common','layer','form','zeroMenu'],function(){
    var $ = layui.$,
        layer = layui.layer,
        form = layui.form,
        common = layui.common;
    // 页面上下文菜单
    var zeroMenu = layui.zeroMenu();

    
    var mar_top = ($(document).height()-$('#zero_login').height())/2.5;
    $('#zero_login').css({'margin-top':mar_top});
    common.zeroCmsSuccess('用户名：zero 密码：zero 无须输入验证码，输入正确后直接登录后台!','zeroMS后台帐号登录提示',20);
    var placeholder = '';
    $("#zero_form input[type='text'],#zero_form input[type='password']").on('focus',function(){
          placeholder = $(this).attr('placeholder');
          $(this).attr('placeholder','');
    });
    $("#zero_form input[type='text'],#zero_form input[type='password']").on('blur',function(){
          $(this).attr('placeholder',placeholder);
    });
    
    common.zeroCmsLoadJq('../common/js/jquery.supersized.min.js', function() {
        $.supersized({
            // 功能
            slide_interval: 3000,
            transition: 1,
            transition_speed: 1000,
            performance: 1,
            // 大小和位置
            min_width: 0,
            min_height: 0,
            vertical_center: 1,
            horizontal_center: 1,
            fit_always: 0,
            fit_portrait: 1,
            fit_landscape: 0,
            // 组件
            slide_links: 'blank',
            slides: [{
                image: '../backstage/images/login/1.jpg'
            }, {
                image: '../backstage/images/login/2.jpg'
            }, {
                image: '../backstage/images/login/3.jpg'
            }]
        });
    });

    form.on('submit(submit)',function(data){
        if(data.field.user_name == 'zero' && data.field.password == 'zero'){
            layer.msg('登录成功',{icon:1,time:1000});
            setTimeout(function(){
                window.location.href = '/backstage/index.html';
            },1000);
           
        }else{
            layer.tips('用户名:zero 密码：zero 无需输入验证码', $('#password'), {
               tips: [3, '#FF5722']
            });
        }
        return false;
    });

    // 右键菜单控制
    var zerocmsMenuData = [
        [{
            text: "刷新页面",
            func: function() {
                top.document.location.reload();
            }
        }, {
            text: "检查元素(F12)",
            func: function() {
                common.zeroCmsError('抱歉！暂不支持此功能，可加入zeroCMS交流群下载源码',common.zeroCore.tit);
            }
        }],
        [{
            text: "访问zeroCMS官网",
            func: function() {
                window.open('http://www.zerocms.com');
            }
        },{
            text: "给zeroMS点个赞",
            func: function() {
                window.open('http://fly.layui.com/case/u/109200');
            }
        }]
    ];
    zeroMenu.ContentMenu(zerocmsMenuData,{
         name: "html" 
    },$('html'));
});
    