<ul class="nav <#if l??> child_menu <#else> side-menu </#if>">
	<#if l??><#else><li ><a href="${BPATH}/admin/index"><i class="fa fa-home pull-left fa-fw"></i>首页</a></li></#if>
	<#if _menus??>
		<#list _menus as menu>
			<li id="${menu.m.tag!}">
			<a <#if menu.n=0> onclick="${menu.m.url!}');" </#if> > 
				<#if menu.m.parent=0> 
					<i class="fa ${menu.m.ico!''} pull-left fa-fw"></i> 
				</#if> 
				${menu.m.name}  
				<#if menu.n!=0> 
					<span class="fa fa-chevron-down fa-fw"> </span>
				</#if>
			</a>
				<#if menu.n != 0 >
					<ul class="nav child_menu">
						<#list menu.children as menu_>
							<#if menu_.n != 0>
									<li id="${menu_.m.tag!}"><a>${menu_.m.name!}<span class="fa fa-chevron-down fa-fw"></span></a>
										<#assign _menus=menu_.children>
										<#assign l=1>
										<#include "_menu.inc.tag" />
									</li>
								<#else>
									<li id="${menu_.m.tag!}"><a onclick="showAtRight('${BPATH}/${menu_.m.url!}');">${menu_.m.name!}</a></li>
							</#if>
						</#list>
					</ul>
				</#if>
			</li>
		</#list>
	</#if>
</ul>