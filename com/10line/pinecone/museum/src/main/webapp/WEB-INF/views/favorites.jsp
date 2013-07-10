<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page  import="java.util.*" %>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>Pinecone - device controller</title>
<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico"
	type="image/x-icon">
<link rel="shortcut icon" href="img/favicon.ico"
	ce_href="img/favicon.ico" type="image/x-icon">
<link href="css/main.css" rel="stylesheet" type="text/css" />
<!--[if IE 8]><link href="css/ie8.css" rel="stylesheet" type="text/css" /><![endif]-->
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700'
	rel='stylesheet' type='text/css'>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&amp;sensor=false"></script>

<script type="text/javascript" src="js/plugins/charts/excanvas.min.js"></script>
<script type="text/javascript" src="js/plugins/charts/jquery.flot.js"></script>
<script type="text/javascript" src="js/plugins/charts/jquery.flot.orderBars.js"></script>
<script type="text/javascript" src="js/plugins/charts/jquery.flot.resize.js"></script>
<script type="text/javascript" src="js/plugins/charts/jquery.flot.pie.js"></script>
<script type="text/javascript" src="js/plugins/charts/jquery.sparkline.min.js"></script>

<script type="text/javascript"
	src="js/plugins/ui/jquery.easytabs.min.js"></script>
<script type="text/javascript"
	src="js/plugins/ui/jquery.collapsible.min.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.mousewheel.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.bootbox.min.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.colorpicker.js"></script>
<script type="text/javascript"
	src="js/plugins/ui/jquery.timepicker.min.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.jgrowl.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.fancybox.js"></script>
<script type="text/javascript"
	src="js/plugins/ui/jquery.fullcalendar.min.js"></script>
<script type="text/javascript" src="js/plugins/ui/jquery.elfinder.js"></script>

<script type="text/javascript" src="js/plugins/uploader/plupload.js"></script>
<script type="text/javascript"
	src="js/plugins/uploader/plupload.html4.js"></script>
<script type="text/javascript"
	src="js/plugins/uploader/plupload.html5.js"></script>
<script type="text/javascript"
	src="js/plugins/uploader/jquery.plupload.queue.js"></script>

<script type="text/javascript"
	src="js/plugins/forms/jquery.uniform.min.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.autosize.js"></script>
<script type="text/javascript"
	src="js/plugins/forms/jquery.inputlimiter.min.js"></script>
<script type="text/javascript"
	src="js/plugins/forms/jquery.tagsinput.min.js"></script>
<script type="text/javascript"
	src="js/plugins/forms/jquery.inputmask.js"></script>
<script type="text/javascript"
	src="js/plugins/forms/jquery.select2.min.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.listbox.js"></script>
<script type="text/javascript"
	src="js/plugins/forms/jquery.validation.js"></script>
<script type="text/javascript"
	src="js/plugins/forms/jquery.validationEngine-en.js"></script>
<script type="text/javascript"
	src="js/plugins/forms/jquery.form.wizard.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.form.js"></script>

<script type="text/javascript"
	src="js/plugins/tables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/files/bootstrap.min.js"></script>
<script type="text/javascript" src="js/files/functions.js"></script>
<script type="text/javascript" src="js/files/utils.js"></script>
<script type="text/javascript" src="js/charts/chart1.js"></script>
<script type="text/javascript">
//devices' code user focus. 
var deviceCodes = "";
//devices' id user focus. 
var deviceIds = "";
//variables' id user focus.
var variableIds = new Array();
//json data that datatable sends
var jsonData = "${jsonData}";
//refresh thread's id;
var refreshid;
//
var isConnected = false;
//used to store variables history data
var trendvalue = new Map();
//define the size of history data
var TREND_LEN = 10;
//focus configuration in json style
var deviceStr = "${deviceIds}";
var variableStr = "${variableIds}";
//flag for alert dialog. prevent the windwo popup too many warning dialogs.
var isAlert = false;

var jsonObject;

function initConfig(){
	//get all variable ids;
    $("strong").each(function(){
    	var code = $(this).attr('deviceCode');
    	var id = $(this).attr('deviceId');
    	
     	if(code != null){
     		deviceCodes = deviceCodes+code+"_";
     		deviceIds = deviceIds+id+"_";
     	}
	})
	
    var _variableIds = variableStr.split("_");
    for(var i=0;i<_variableIds.length;i++){
    	trendvalue.put(_variableIds[i],new Array());
    	variableIds.push(_variableIds[i]);
    }
}

window.onload = function(){
	$("#focusList").dataTable({
		"bJQueryUI": false,
		"bAutoWidth": false,
		"bFilter": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers",
		"oLanguage": {
			"sProcessing": "Loading...",
			"sSearch": "<span>Filter records:</span> _INPUT_",
			"sLengthMenu": "<span>Show entries:</span> _MENU_",
			"oPaginate": { "sFirst": "First", "sLast": "Last", "sNext": ">", "sPrevious": "<" }
		},
		"aoColumnDefs": [
	    	{ "bSortable": false, "aTargets": [ 4, 5, 6 ] }
	    ],
		"bServerSide": true,
		"bProcessing": true,
		"fnDrawCallback": function( oSettings ) {
			//init background thread
			initConfig();
			
			//get device row
			$("td>strong").each(function(){
		    	var id = $(this).attr('deviceId');
		     	if(id != null){
		     		$(this).parent().attr("colspan",7);
		     		$(this).parent().next().remove();
		     		$(this).parent().next().remove();
		     		$(this).parent().next().remove();
		     		$(this).parent().next().remove();
		     		$(this).parent().next().remove();
		     		$(this).parent().next().remove();
		     	}
			})
			
			for(var i=0;i<variableIds.length;i++){
				$("#index"+variableIds[i]).select2({
					placeholder: 'Setting'
				});
				$("#index"+variableIds[i]).on("change", function(e) {
					var splits = e.val.split("_");
					if(splits.length >=3){
						publish(splits[0],splits[1],splits[2]);
					}
				});
				
				//disable
				$("#index-"+variableIds[i]).select2({
					placeholder: "Setting"
				});
				$("#index-"+variableIds[i]).attr("disabled","disabled");
			}
			
			//initialize alerm dialog
			$("a#alermVariable").fancybox({
				maxWidth	: 520,
				maxHeight	: 850,
				fitToView	: false,
				width		: '70%',
				height		: '95%',
				autoSize	: false,
				closeClick	: false,
				openEffect	: 'none',
				closeEffect	: 'none',
				scrolling   : 'no',
				type        : 'iframe',
			});
			//initialize history dialog
			
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
			
			//start refresh thread on the background
			setRefresh(5);
		},	
		"sAjaxSource": "/console/queryfocusvariable.html"
	});

}

function setTrend(newvalue, varid){
	if(trendvalue.get(varid) == null)
		return;
	
	var len = trendvalue.get(varid).length;
	if(len == null)
		return;
	
	if(len == TREND_LEN)
		trendvalue.get(varid).shift();
		
	trendvalue.get(varid).push(newvalue);
	$("span[varid='"+varid+"']").sparkline(trendvalue.get(varid));
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

function refresh(){
	isRefreshing = true;
	$.ajax({
 		url:'subscribefavoritesdata', 
 		type: 'POST',
 		data: {ids:jsonData,devicecodes:deviceCodes,deviceids:deviceIds}, 
		timeout: 1000,
 		error: function(XMLHttpRequest, textStatus, errorThrown){
 			if(!isAlert){
 				isAlert = true;
	 			bootbox.confirm("Lost connection. Connect device again?", function(result) {
	 				if(result =='false'){
	 					clearInterval(refreshid);
	 					isRefreshing = false;
	 					isAlert = false;
	 				}
	 			});
 			}
 		}, 
 		success: function(result){
 			isConnected = true;
 			var obj = eval('(' + result + ')'); 
 			jsonObject = obj;
 			
 			for(var n=0;n<obj.length;n++){
 				for(var m=0;m<obj[n].value.length;m++){
 					var id = obj[n].value[m].id;
 					var value = obj[n].value[m].value;
 					var isAlerm = obj[n].value[m].isAlerm;
 					$("strong[varid='"+obj[n].value[m].id+"']").text(obj[n].value[m].value);
 					setTrend(value,obj[n].value[m].id);
 					if(obj[n].value[m].isAlerm){
 						$("strong[varid='"+obj[n].value[m].id+"']").parent().parent().toggleClass("error")
 					}else{
 						$("strong[varid='"+obj[n].value[m].id+"']").parent().parent().removeClass("error")
 					}
 				}
 			}
 		} 
 	});
}

window.onunload = function(){
	alert("onUnload");
	stopRefresh();
	
	$.ajax({
 		url:'subscribefavoritesdata', 
 		type: 'POST',
 		data: {isDisconnect:'true', devicecodes:deviceCodes},
 		async:false,
		timeout: 500,
 		error: function(){}, 
 		success: function(result){
 			isConnect = false;
 		} 
 	});
}

function publish(devCode, varid, value){
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
 				$.jGrowl('Setting finished!', { sticky: true, theme: 'growl-success', life:1000});
 			}else if(result == "false"){
 				$.jGrowl('Setting failed!', { sticky: true, theme: 'growl-error', life:1000});
 			}
 		} 
 	});
}

function setAlermStr(variableId, alermStr){
	$("strong[alermVarid='"+variableId+"']").text(alermStr);
}

</script>
</head>

<body>

	<!-- Fixed top -->
	<div id="top">
		<div class="fixed">
			<a href="index.html" title="" class="logo"><img
				src="img/logo.png" alt="" /></a>
			<ul class="top-menu">

				<li class="dropdown"><a class="user-menu"
					data-toggle="dropdown"> <!-- <img src="img/userpic.png" alt="" /> -->
						<span id="greeting_word_1">Welcome back, ${username} <b
							class="caret"></b></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="profile.html" title=""><i class="icon-user"></i>Profile</a></li>
						<li><a href="j_spring_security_logout" title=""><i	class="icon-signout"></i>Logout</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
	<!-- /fixed top -->


	<!-- Content container -->
	<div id="container">

		<!-- Sidebar -->
		<div id="sidebar">

			<div class="sidebar-tabs">
				<ul class="tabs-nav two-items">
					<li><a href="#general" title=""><i class="icon-reorder"></i></a></li>
					<li><a href="#stuff" title=""><i class="icon-cogs"></i></a></li>
				</ul>

				<div id="general">

					<!-- Sidebar user -->
					<div class="sidebar-user widget">
						<div class="navbar">
							<div class="navbar-inner">
								<h6 id="greeting_word_2">Welcome back, ${username}</h6>
							</div>
						</div>
						<div>
							<a href="#" title="" class="user"><img
								src="http://placehold.it/210x110" alt="" /></a>
						</div>
					</div>
					<!-- /sidebar user -->

					<!-- Main navigation -->
					<ul class="navigation widget">
						<li><a href="#" title=""><i class="icon-home"></i>Dashboard</a></li>
						<li><a href="index.html" title=""><i class="icon-tasks"></i>Devices</a></li>
						<li class="active"><a href="favorites.html" title=""><i	class="icon-bookmark"></i>Favorites</a></li>
						<li><a href="environment.html" title=""><i class="icon-sitemap"></i>Environment</a></li>
					</ul>
					<!-- /main navigation -->

				</div>

				<div id="stuff"></div>

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
						<li><a href="index.html">Dashboard</a></li>
						<li class="active"><a href="#" title="">My favorites</a></li>
					</ul>

					<ul class="alt-buttons">
						<li><a href="#" id="active-device-dialog"
							class="active-device-dialog" title="Active Device"><i
								class="icon-plus"></i><span>Active Device</span></a></li>
						<li class="dropdown"><a href="#" title=""
							data-toggle="dropdown"><i class="icon-cog"></i><span>Menu</span></a>
							<ul class="dropdown-menu pull-right">
								<li><a href="index.html" title=""><i class="icon-tasks"></i>Devices</a></li>
								<li><a href="favorites.html" title=""><i class="icon-bookmark"></i>Favorites</a></li>
								<li><a href="environment.html" title=""><i class="icon-sitemap"></i>Environment</a></li>
							</ul></li>
					</ul>
				</div>
				<!-- /breadcrumbs line -->

				<!-- Media datatable -->
				<div class="widget">
					<div class="navbar">
						<div class="navbar-inner">
							<h6>Variable table</h6>
							<div class="nav pull-right">
								<a href="#" class="dropdown-toggle navbar-icon"
									data-toggle="dropdown" title="Refresh time"><i
									class="icon-refresh"></i></a>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" onclick="setRefresh(1000)">Stop</a></li>
									<li><a href="#" onclick="setRefresh(2)">2s</a></li>
									<li><a href="#" onclick="setRefresh(10)">10s</a></li>
									<li><a href="#" onclick="setRefresh(30)">30s</a></li>
									<li><a href="#" onclick="setRefresh(60)">60s</a></li>
								</ul>
							</div>
						</div>
					</div>
					<h5 class="widget-name" style="margin-top: 15px">
						<i class="icon-film"></i>Focus devices and variabls
					</h5>
					<div class="table-overflow">
						<table id='focusList' class="table table-striped table-bordered table-checks media-table">
							<thead>
								<tr>
									<th>ID</th>
									<th>Type</th>
									<th>Name</th>
									<th>Value</th>
									<th>Trend</th>
									<th>Alerm</th>
									<th class="actions-column">Actions</th>
									<th class="actions-column">Attention</th>
								</tr>
							</thead>
							<tbody></tbody>
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
		<div class="copyrights">&copy; Pinecone Tech.</div>
		<ul class="footer-links">
			<li><a href="" title=""><i class="icon-cogs"></i>Contact
					admin</a></li>
			<li><a href="" title=""><i class="icon-screenshot"></i>Home
					page</a></li>
		</ul>
	</div>
	<!-- /footer -->
</body>
</html>
