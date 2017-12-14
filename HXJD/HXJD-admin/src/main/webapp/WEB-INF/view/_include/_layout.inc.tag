<#macro layout active_id="" child_active_id="" >
	<!DOCTYPE html>
	<html>
  	<head>
		<meta name="renderer" content="webkit">
  		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<#include "./common/_css.inc.tag" />
	    <script type="text/javascript">
	    	var bpath="${BPATH}";
	    	var cpath="${CPATH}";
	    	var apath="${APATH}";
	    	var tempData=null;
	    </script>
	    <#include "./common/_js.inc.tag" />
 	</head>

 	<body>
		<#nested>
 	</body>
	</html>
</#macro>