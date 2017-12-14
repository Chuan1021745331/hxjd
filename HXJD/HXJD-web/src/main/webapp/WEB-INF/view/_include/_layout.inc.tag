<#macro layout active_id="" child_active_id="" >
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${sys_title} </title>
	<#include "./common/_css.inc.tag" />
	<!-- require -->
	
    <!-- jQuery -->
    <script src="${CPATH}/vendors/jquery/dist/jquery.min.js"></script>
    <script src="${CPATH}/vendors/jquery/dist/jquery.form.min.js"></script>
    
<!--     <link href="${CPATH}/nth-tabs/jquery.scrollbar.css" rel="stylesheet"> -->
<!--     <script src="${CPATH}/nth-tabs/jquery.scrollbar.min.js"></script> -->
<!-- 	<link href="${CPATH}/nth-tabs/css/nth.tabs.css" rel="stylesheet"> -->
<!-- 	<script src="${CPATH}/nth-tabs/js/nth.tabs.js"></script> -->

    <script type="text/javascript">
    	var bpath="${BPATH}";
    	var cpath="${CPATH}";
    	var apath="${APATH}";
    </script>
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <#include "_head.inc.tag" />

        <!-- page content -->
		<div class="right_col nth-tabs" id="content" role="main">
			<#nested>
		</div>
        <!-- /page content -->
		
        <!-- footer content -->
        <footer>
          <div class="pull-right">
            	${copyright!}
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>
    <#include "./common/_js.inc.tag" />
  </body>
</html>
</#macro>