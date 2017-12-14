<div class="col-md-3 left_col">
 <div class="left_col scroll-view">
   <div class="navbar nav_title" style="border: 0;">
     <a href="${BPATH}" class="site_title"><i class="fa fa-leaf"></i> <span>${admin_top_title}</span></a>
   </div>

   <div class="clearfix"></div>

   <!-- menu profile quick info -->
   <div class="profile clearfix">
     <div class="profile_pic">
       <img src="${BPATH}<#if _user.avatar??>${_user.avatar!}<#else>/attachment/avatar/astronaut.png</#if>" alt="..." class="img-circle profile_img">
     </div>
     <div class="profile_info">
       <span>欢迎您,</span>
       <h2><#if _user??>${_user.nickname!}</#if></h2>
     </div>
   </div>
   <!-- /menu profile quick info -->

   <br />

   <!-- sidebar menu -->
   <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
      <div class="menu_section">
         <#include "_menu.inc.tag" />
      </div>

   </div>
   <!-- /sidebar menu -->

   <!-- /menu footer buttons -->
<!--    <div class="sidebar-footer hidden-small"> -->
<!--      <a data-toggle="tooltip" data-placement="top" title="Settings"> -->
<!--        <span class="glyphicon glyphicon-cog" aria-hidden="true"></span> -->
<!--      </a> -->
<!--      <a data-toggle="tooltip" data-placement="top" title="FullScreen" id="FullScreen"> -->
<!--        <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span> -->
<!--      </a> -->
<!--      <a data-toggle="tooltip" data-placement="top" title="Lock"> -->
<!--        <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span> -->
<!--      </a> -->
<!--      <a data-toggle="tooltip" data-placement="top" title="Logout" href="login.html"> -->
<!--        <span class="glyphicon glyphicon-off" aria-hidden="true"></span> -->
<!--      </a> -->
<!--    </div> -->
   <!-- /menu footer buttons -->
  </div>
</div>
<!-- top navigation -->
<div class="top_nav">
  	<div class="nav_menu">
    	<nav>
      		<div class="nav toggle">
        		<a id="menu_toggle"><i class="fa fa-bars"></i></a>
      		</div>

      		<ul class="nav navbar-nav navbar-right">
        		<li class="">
          			<a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
            			<img src="${BPATH}<#if _user.avatar??>${_user.avatar!}<#else>/attachment/avatar/astronaut.png</#if>" alt=""> <#if _user??>${_user.nickname!}</#if> <span class=" fa fa-angle-down"></span>
          			</a>
	          		<ul class="dropdown-menu dropdown-usermenu pull-right">
	         			<li><a onclick = "showModal('${BPATH}/admin/aboutme')"><i class="fa fa-user-circle pull-right fa-fw"></i> 个人资料</a></li>
	           			<li><a onclick="showModal('${BPATH}/admin/aboutme/changePassword')"><i class="fa fa-lock pull-right fa-fw"></i> 修改密码</a></li>
	            		<li class="divider-dashed"></li>
	            		<li><a onclick="showModal('${BPATH}/admin/b')"><i class="fa fa-question-circle-o pull-right fa-fw"></i> 帮助</a></li>
	            		<li class="divider-dashed"></li>
	            		<li><a href="${BPATH}/admin/logout"><i class="fa fa-sign-out pull-right fa-fw"></i> 退出</a></li>
	          		</ul>
        		</li>
      		</ul>
    	</nav>
	</div>
</div>
<#include "./tag/defModal.tag" />
<#include "./tag/delModal.tag" />
<!-- /top navigation -->