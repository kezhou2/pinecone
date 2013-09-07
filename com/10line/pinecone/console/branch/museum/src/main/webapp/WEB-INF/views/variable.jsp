<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title><fmt:message key="application.title" /></title>
<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="img/favicon.ico" ce_href="img/favicon.ico" type="image/x-icon">
<link href="css/main.css" rel="stylesheet" type="text/css" />
<!--[if IE 8]><link href="css/ie8.css" rel="stylesheet" type="text/css" /><![endif]-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&amp;sensor=false"></script>

<script type="text/javascript" src="js/plugins/charts/excanvas.min.js"></script>
<script type="text/javascript" src="js/plugins/charts/jquery.flot.js"></script>
<script type="text/javascript" src="js/plugins/charts/jquery.flot.orderBars.js"></script>
<script type="text/javascript" src="js/plugins/charts/jquery.flot.resize.js"></script>
<script type="text/javascript" src="js/plugins/charts/jquery.flot.pie.js"></script>
<script type="text/javascript" src="js/plugins/charts/jquery.sparkline.min.js"></script>

<script type="text/javascript" src="js/plugins/ui/jquery.easytabs.min.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.collapsible.min.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.mousewheel.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.bootbox.min.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.colorpicker.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.timepicker.min.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.jgrowl.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.fancybox.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.fullcalendar.min.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.elfinder.js"></script>

<script type="text/javascript" src="js/plugins/uploader/plupload.js"></script>
<script type="text/javascript" src="js/plugins/uploader/plupload.html4.js"></script>
<script type="text/javascript" src="js/plugins/uploader/plupload.html5.js"></script>
<script type="text/javascript" src="js/plugins/uploader/jquery.plupload.queue.js"></script>

<script type="text/javascript" src="js/plugins/forms/jquery.uniform.min.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.autosize.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.inputlimiter.min.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.tagsinput.min.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.inputmask.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.select2.min.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.listbox.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.validation.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.validationEngine-cn.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.form.wizard.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.form.js"></script>

<script type="text/javascript" src="js/plugins/tables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/files/bootstrap.min.js"></script>
<script type="text/javascript" src="js/files/functions.js"></script>

<script type="text/javascript" src="js/files/utils.js"></script>
<script type="text/javascript" src="js/charts/chart1.js"></script>
<%
String querydeviceid = (String)request.getAttribute("querydeviceid");
%>
<script type="text/javascript">
var varids = [];
var devCode;
var jsonData;
var isRefreshing = false;
var isAlert = false;
var refreshid;
var isConnected = false;
var trendvalue = new Array();
var TREND_LEN = 10;
function initConfig(){
	//get all variable ids;
    $("strong").each(function(){
    	var id = $(this).attr('varid');
    	
     	if(id != null){
     		varids.push(id);
     	}
	})
	
	devCode = $('#variablelist').attr('devicecode');	
	//generate request json
 	jsonData = "[[";
 	for(var i=0;i<varids.length;i++){
 		if(i<varids.length-1){
 			jsonData += varids[i]+",";
 		}else{
 			jsonData += varids[i]+"]]";
 		}
 		
 		trendvalue[varids[i]] = new Array();
 	}
}

window.onload = function(){
	$("#variablelist").dataTable({
		"bJQueryUI": false,
		"bAutoWidth": false,
		"bFilter": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers",
		"oLanguage": {
			"sProcessing": "<img src='img/elements/loaders/5s.gif'><strong>&nbsp<fmt:message key='application.loading' /></strong>",
			"sSearch": "<span><fmt:message key='application.filter.record' /></span> _INPUT_",
			"sLengthMenu": "<span><fmt:message key='application.show.entries' /></span> _MENU_",
			"oPaginate": { "sFirst": "<fmt:message key='application.table.first' />", "sLast": "<fmt:message key='application.table.last' />", "sNext": ">", "sPrevious": "<" }
		},
		"aoColumnDefs": [
	    	{ "bSortable": false, "aTargets": [ 4, 5, 6 ] }
	    ],
		"bServerSide": true,
		"bProcessing": true,
		"fnDrawCallback": function( oSettings ) {
			var row = oSettings._iRecordsDisplay;
			for(var i=0;i<row;i++){
				$("#index"+i).select2({
					placeholder: "<fmt:message key='variable.placeholder.setting' />"
				});
				$("#index"+i).on("change", function(e) {
					var splits = e.val.split("_");
					if(splits.length >=2){
						publish(splits[0],splits[1]);
					}
				});
				
				//disable
				$("#index-"+i).select2({
					placeholder: "<fmt:message key='variable.placeholder.setting' />"
				});
				$("#index-"+i).attr("disabled","disabled");
			}
			
			//init history dialog
			$("a#historyShow").fancybox({
				maxWidth	: 520,
				maxHeight	: 250,
				fitToView	: false,
				width		: '70%',
				height		: '70%',
				autoSize	: false,
				closeClick	: false,
				openEffect	: 'none',
				closeEffect	: 'none',
				scrolling   : 'no'
			});
			
			//init background thread
			initConfig();
			setRefresh(1);
		},
		"sAjaxSource": "/museum/queryvariable.html?id=<%=querydeviceid%>"
    });
}

function refresh(){
	isRefreshing = true;
	$.ajax({
 		url:'subscribedata', 
 		type: 'POST',
 		data: {ids:jsonData,devicecodes:devCode}, 
		timeout: 1000,
		cache:false,
 		error: function(XMLHttpRequest, textStatus, errorThrown){
 			if(!isAlert){
 				isAlert = true;
 			}
 		}, 
 		success: function(result){
 			isConnected = true;
 			var deviceData = result.split("/");
 			for(var m=0;m<deviceData.length;m++){
	 			var splits = deviceData[m].split(",");
	 			if(splits.length > 1){
	 				for(var n=1;n<splits.length;n++){
	 					var tmp = splits[n].split(":");
	 					var id = tmp[0];
	 					var value = tmp[1];
	 					$("strong[varid='"+id+"']").text(value);
	 					setTrend(value,id);
	 				}
	 			}
 			}
 		} 
 	});
}

function setTrend(newvalue, varid){
	if(trendvalue[varid] == null)
		return;
	
	var len = trendvalue[varid].length;
	if(len == null)
		return;
	
	if(len == TREND_LEN)
		trendvalue[varid].shift();
		
	trendvalue[varid].push(newvalue);
	$("span[varid='"+varid+"']").sparkline(trendvalue[varid]);
}

function setRefresh(time){
	isRefreshing = true;
	clearInterval(refreshid);
	refreshid = setInterval("refresh()",time*1000);
}

function stopRefresh(){
	if(isRefreshing){
		clearInterval(refreshid);
		isRefreshing = false;
	}
}

window.onunload = function(){
	stopRefresh();
	
	$.ajax({
 		url:'subscribedata', 
 		type: 'POST',
 		data: {isDisconnect:'true', devicecodes:devCode},
 		async:false,
 		cache:false,
		timeout: 500,
 		error: function(){}, 
 		success: function(result){
 			isConnect = false;
 		} 
 	});
}

function publish(varid, value){
	$.ajax({
 		url:'publishdata', 
 		type: 'POST',
 		data: {deviceid:devCode,variableid:varid,vvalue:value}, 
		timeout: 1000,
 		error: function(XMLHttpRequest, textStatus, errorThrown){
 			$.jGrowl(textStatus, { sticky: true, theme: 'growl-error', life:1000});
 		}, 
 		success: function(result){
 			if(result == "true"){
 				$.jGrowl("<fmt:message key='variable.setting.success' />", { sticky: true, theme: 'growl-success', life:1000});
 			}else if(result == "false"){
 				$.jGrowl("<fmt:message key='variable.setting.fail' />", { sticky: true, theme: 'growl-error', life:1000});
 			}
 		} 
 	});
}

function addDevice(devid){
	bootbox.confirm("<fmt:message key='variable.adddevicetofavorites' />", function(result) {
		if(result){
			$.ajax({
		 		url:'adddevicetofocus.html', 
		 		type: 'post',
		 		data: {deviceid:devid}, 
				timeout: 5000,
		 		error: function(XMLHttpRequest, textStatus, errorThrown){
		 			$.jGrowl(textStatus, { sticky: true, theme: 'growl-error', life:1000});
		 		}, 
		 		success: function(result){
		 			if(result == 'true'){
		 				$.jGrowl("<fmt:message key='variable.adddevicesuccess' />", { sticky: true, theme: 'growl-success', life:1000});
		 				$("#addFavorite").attr("onclick","removeDevice('${device.id}')");
		 				$("#addFavorite").attr("title","<fmt:message key='variable.tooltip.remove.favorites' />");
		 				$("#addFavorite").html("<i class='fam-bell-delete'></i><span>Remove</span></a>");
		 			}else{
		 				$.jGrowl("<fmt:message key='variable.adddevicefail' />", { sticky: true, theme: 'growl-error', life:1000});
		 			}
		 		} 
		 	});
		}
	});
}

function removeDevice(devid){
	bootbox.confirm("<fmt:message key='variable.removedevicefromfavorites' />", function(result) {
		if(result){
			$.ajax({
		 		url:'removedevicetofocus.html', 
		 		type: 'post',
		 		data: {deviceid:devid}, 
				timeout: 5000,
		 		error: function(XMLHttpRequest, textStatus, errorThrown){
		 			$.jGrowl(textStatus, { sticky: true, theme: 'growl-error', life:1000});
		 		}, 
		 		success: function(result){
		 			if(result == 'true'){
		 				$.jGrowl("<fmt:message key='variable.removedevicesuccess' />", { sticky: true, theme: 'growl-success', life:1000});
		 				$("#addFavorite").attr("onclick","addDevice('${device.id}')");
		 				$("#addFavorite").attr("title","<fmt:message key='variable.tooltip.add.favorites' />");
		 				$("#addFavorite").html("<i class='fam-bell-add'></i><span>Add</span></a>");
		 			}else{
		 				$.jGrowl("<fmt:message key='variable.removedevicefail' />", { sticky: true, theme: 'growl-error', life:1000});
		 			}
		 		} 
		 	});
		}
	});
	
}

function addVariable(devid,varid){
	bootbox.confirm("<fmt:message key='variable.addvariabletofavorites' />", function(result) {
		if(result){
			$.ajax({
		 		url:'addvariabletofocus.html', 
		 		type: 'post',
		 		data: {deviceid:devid,variableid:varid}, 
				timeout: 5000,
		 		error: function(XMLHttpRequest, textStatus, errorThrown){
		 			$.jGrowl(textStatus, { sticky: true, theme: 'growl-error', life:1000});
		 		}, 
		 		success: function(result){
		 			if(result == 'true'){
		 				$.jGrowl("<fmt:message key='variable.addvariablesuccess' />", { sticky: true, theme: 'growl-success', life:1000});
		 				$("#var"+varid).attr("onclick","removeVariable("+devid+","+varid+")");
		 				$("#var"+varid).attr("title","<fmt:message key='variable.tooltip.remove.favorites' />");
		 				$("#var"+varid).html("<i class='fam-bell-delete'></i>");
		 			}else{
		 				$.jGrowl("<fmt:message key='variable.addvariablefail' />", { sticky: true, theme: 'growl-error', life:1000});
		 			}
		 		} 
		 	});
		}
	});
}

function removeVariable(devid,varid){
	bootbox.confirm("<fmt:message key='variable.removevariablefromfavorites' />", function(result) {
		if(result){
			$.ajax({
		 		url:'removevariabletofocus.html', 
		 		type: 'post',
		 		data: {deviceid:devid,variableid:varid}, 
				timeout: 5000,
		 		error: function(XMLHttpRequest, textStatus, errorThrown){
		 			$.jGrowl(textStatus, { sticky: true, theme: 'growl-error', life:1000});
		 		}, 
		 		success: function(result){
		 			if(result == 'true'){
		 				$.jGrowl("<fmt:message key='variable.removevariablesuccess' />", { sticky: true, theme: 'growl-success', life:1000});
		 				$("#var"+varid).attr("onclick","addVariable("+devid+","+varid+")");
		 				$("#var"+varid).attr("title","<fmt:message key='variable.tooltip.add.favorites' />");
		 				$("#var"+varid).html("<i class='fam-bell-add'></i>");
		 			}else{
		 				$.jGrowl("<fmt:message key='variable.removevariablefail' />", { sticky: true, theme: 'growl-error', life:1000});
		 			}
		 		} 
		 	});
		}
	});
}
</script>
</head>

<body>

	<!-- Fixed top -->
	<div id="top">
		<div class="fixed">
			<a href="index.html" title="" class="logo"><img src="img/logo.png" alt="" /></a>
			<ul class="top-menu">
				<li class="dropdown">
					<a class="user-menu" data-toggle="dropdown"><img src="img/userpic.png" alt="" /><span id="greeting_word_1"><fmt:message key="application.welcome"><fmt:param value="${username}" /></fmt:message><b class="caret"></b></span></a>
					<ul class="dropdown-menu">
						<li><a href="profile.html" title=""><i class="fam-group-gear"></i><fmt:message key="application.profile" /></a></li>
						<li><a href="j_spring_security_logout" title=""><i class="fam-door-out"></i><fmt:message key="application.logout" /></a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<!-- /fixed top -->


	<!-- Content container -->
	<div id="container">

		<!-- Sidebar -->
		<div id="sidebar">

			<div class="sidebar-tabs"  data-easytabs="true">
		        <ul class="tabs-nav two-items">
		        </ul>

		        <div id="general">

			        <!-- Sidebar user -->
			        <div class="sidebar-user widget">
						<div class="navbar"><div class="navbar-inner"><h6 id="greeting_word_2"><fmt:message key="application.welcome"><fmt:param value="${username}" /></fmt:message></h6></div></div>
						<div>
			            	<a href="#" title="" class="user"><img src="img/user.jpg" style="border:1px solid #d5d5d5" alt="" /></a>
			            </div>
			        </div>
			        <!-- /sidebar user -->

				    <!-- Main navigation -->
			        <ul class="navigation widget">
			            <li class="active"><a href="index.html" title=""><i class="fam-application-view-tile"></i><fmt:message key="application.device" /></a></li>
			            <li><a href="favorites.html" title=""><i class="fam-folder-star"></i><fmt:message key="application.favorites" /></a></li>
			            <li><a href="environment.html" title=""><i class="fam-world"></i><fmt:message key="application.environment" /></a></li>
			        </ul>
			        <!-- /main navigation -->

		        </div>

		    </div>
		</div>
		<!-- /sidebar -->

		<!-- Content -->
		<div id="content">

		    <!-- Content wrapper -->
		    <div class="wrapper">

			    <!-- Breadcrumbs line -->
			    <div class="crumbs">
		            <ul id="breadcrumbs" class="breadcrumb"> 
		                <li><a href="index.html"><fmt:message key="application.dashboard" /></a></li>
		                <li><a href="index.html"><fmt:message key="application.device" /></a></li>
		                <li class="active"><a href="#" title="">${device.name }</a></li>
		            </ul>
			        
		            <ul class="alt-buttons">
		            	<li><a href="#" id="active-device-dialog" class="active-device-dialog" title="Active Device"><i class="icon-plus" style="color:green"></i><span><fmt:message key="application.active" /></span></a></li>
						<li class="dropdown"><a href="#" title="" data-toggle="dropdown"><i class="icon-cog" style="color:green"></i><span><fmt:message key="application.menu" /></span></a>
		                	<ul class="dropdown-menu pull-right">
		                        <li><a href="index.html" title=""><i class="fam-application-view-tile"></i><fmt:message key="application.device" /></a></li>
		                        <li><a href="favorites.html" title=""><i class="fam-folder-star"></i><fmt:message key="application.favorites" /></a></li>
		                        <li><a href="environment.html" title=""><i class="fam-world"></i><fmt:message key="application.environment" /></a></li>
		                	</ul>
		                </li>
		            </ul>
			    </div>
			    <!-- /breadcrumbs line -->
				
                <!-- Media datatable -->
                <div class="widget">
                	<div class="navbar">
                    	<div class="navbar-inner" style="margin-top: 10px; margin-bottom: 10px;">
                        	<h6><fmt:message key="variable.title" /></h6>
                        	<div class="nav pull-right">
                        		<c:choose>
									<c:when test="${addedFavorate == false}">
										<li><a id="addFavorite" href="#"  onclick="addDevice('${device.id}')" title="<fmt:message key="variable.tooltip.add" />"><i class="fam-bell-add"></i><span><fmt:message key="variable.add" /></span></a></li>
									</c:when>
									<c:when test="${addedFavorate == true}">
										<li><a id="addFavorite" href="#"  onclick="removeDevice('${device.id}')" title="<fmt:message key="variable.tooltip.remove" />"><i class="fam-bell-delet"></i><span><fmt:message key="variable.remove" /></span></a></li>
									</c:when>
								</c:choose>
                                <a href="#" class="dropdown-toggle navbar-icon" data-toggle="dropdown" title="<fmt:message key="variable.refreshtime" />"><i class="icon-refresh" style="color:green"></i></a>
                                <ul class="dropdown-menu pull-right">
                                	<li><a href="#" onclick="setRefresh(1000)"><fmt:message key="variable.stop" /></a></li>
                                	<li><a href="#" onclick="setRefresh(2)">2s</a></li>
                                    <li><a href="#" onclick="setRefresh(10)">10s</a></li>
	                                <li><a href="#" onclick="setRefresh(30)">30s</a></li>
	                                <li><a href="#" onclick="setRefresh(60)">60s</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="table-overflow">
                        <table id='variablelist' class="table table-striped table-bordered table-checks media-table" devicecode="${device.code}">
                            <thead>
                                <tr>
                                	<th><fmt:message key="variable.id" /></th>
                                    <th><fmt:message key="variable.type" /></th>
                                    <th><fmt:message key="variable.name" /></th>
                                    <th><fmt:message key="variable.value" /></th>
                                    <th><fmt:message key="variable.trend" /></th>
                                    <th class="actions-column"><fmt:message key="variable.action" /></th>
                                    <th class="actions-column"><fmt:message key="variable.attention" /></th>
                                </tr>
                            </thead>
                            <tbody> 
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- /media datatable -->

            </div>
            <!-- /content wrapper -->

		</div>
		<!-- /content -->

	</div>
	<!-- /content container -->


	<!-- Footer -->
	<div id="footer">
		<div class="copyrights"><fmt:message key="application.company" /></div>
		<ul class="footer-links">
			<li><a href="mailto:liugyang@gmail.com?Subject=helpme" title=""><i class="icon-cogs"></i><fmt:message key="application.contact.admin" /></a></li>
			<li><a href="http://www.pinecone.cc" title=""><i class="icon-screenshot"></i><fmt:message key="application.home.page" /></a></li>
		</ul>
	</div>
	<!-- /footer -->
</body>
</html>
