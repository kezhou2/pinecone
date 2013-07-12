<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>Pinecone - device controller</title>
<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="img/favicon.ico" ce_href="img/favicon.ico" type="image/x-icon">
<link href="css/main.css" rel="stylesheet" type="text/css" />
<!--[if IE 8]><link href="css/ie8.css" rel="stylesheet" type="text/css" /><![endif]-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&amp;sensor=false"></script>

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
<script type="text/javascript" src="js/plugins/forms/jquery.form.wizard.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.form.js"></script>

<script type="text/javascript" src="js/plugins/tables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/files/bootstrap.min.js"></script>
<script type="text/javascript" src="js/files/functions.js"></script>
<script type="text/javascript">
//devices' code user focus. 
var deviceCodes = "";
//devices' id user focus. 
var deviceIds = "";
//variables' id user focus.
var variableIds = new Array();
//json data that datatable sends
var jsonData = '${jsonData}';
//refresh thread's id;
var refreshid;

//flag for alert dialog. prevent the windwo popup too many warning dialogs.
var isAlert = false;

window.onload = function(){
			
	//initialize alerm dialog
	$("a#deviceShow").fancybox({
		'autoDimensions'	: false,
		'width'         	: 1000,
		'height'        	: 'auto',
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});

	//start refresh thread on the background
	setRefresh(5);
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
 		url:'devicestatusdata', 
 		type: 'POST',
 		data: {jsonData:jsonData}, 
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
 			
 			for(var n=0;n<obj.length;n++){
 				if(obj[n].status == 'connect'){
 					$("img[deviceId='"+obj[n].deviceId+"']").attr("src","img/demo/ok.png");
 				}else if(obj[n].status == 'disconnect'){
 					$("img[deviceId='"+obj[n].deviceId+"']").attr("src","img/demo/off.png");
 				}
 			}
 		} 
 	});
}

window.onunload = function(){
	alert("onUnload");
	stopRefresh();
	
	$.ajax({
 		url:'devicestatusdata', 
 		type: 'POST',
 		data: {isDisconnect:'true', jsonData:jsonData},
 		async:false,
		timeout: 500,
 		error: function(){}, 
 		success: function(result){
 			isConnect = false;
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
					<a class="user-menu" data-toggle="dropdown"><!-- <img src="img/userpic.png" alt="" /> --><span id="greeting_word_1">Welcome back, ${username}<b class="caret"></b></span></a>
					<ul class="dropdown-menu">
						<li><a href="profile.html" title=""><i class="fam-group-gear"></i>Profile</a></li>
						<li><a href="j_spring_security_logout" title=""><i class="fam-door-out"></i>Logout</a></li>
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

			<div class="sidebar-tabs">
		        <ul class="tabs-nav two-items">
		            <li><a href="#general" title=""><i class="icon-reorder" style="color:green"></i></a></li>
		            <li><a href="#stuff" title=""><i class="icon-cogs" style="color:green"></i></a></li>
		        </ul>

		        <div id="general">

			        <!-- Sidebar user -->
			        <div class="sidebar-user widget">
						<div class="navbar"><div class="navbar-inner"><h6 id="greeting_word_2">Welcome back, ${username}</h6></div></div>
						<div>
			            	<a href="#" title="" class="user"><img src="img/user.jpg" style="border:1px solid #d5d5d5" alt="" /></a>
			            </div>
			        </div>
			        <!-- /sidebar user -->

				    <!-- Main navigation -->
			        <ul class="navigation widget">
			            <li><a href="#" title=""><i class="fam-application-home"></i>Dashboard</a></li>
			            <li><a href="index.html" title=""><i class="fam-application-view-tile"></i>Devices</a></li>
			            <li><a href="favorites.html" title=""><i class="fam-folder-star"></i>Favorites</a></li>
			            <li class="active"><a href="environment.html" title=""><i class="fam-world"></i>Environment</a></li>
			        </ul>
			        <!-- /main navigation -->

		        </div>

		        <div id="stuff">
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
		                <li><a href="index.html">Dashboard</a></li>
		                <li class="active"><a href="#" title="">Environment</a></li>
		            </ul>
			        
		            <ul class="alt-buttons">
						<li class="dropdown"><a href="#" title="" data-toggle="dropdown"><i class="icon-cog" style="color:green"></i><span>Menu</span></a>
		                	<ul class="dropdown-menu pull-right">
		                        <li><a href="index.html" title=""><i class="fam-application-view-tile"></i>Devices</a></li>
		                        <li><a href="favorites.html" title=""><i class="fam-folder-star"></i>Favorites</a></li>
		                        <li><a href="environment.html" title=""><i class="fam-world"></i>Environment</a></li>
		                	</ul>
		                </li>
		            </ul>
			    </div>
			    <!-- /breadcrumbs line -->
				
                <!-- environment tab widget -->
                <div class="widget">
                    <div class="tabbable">
                        <ul class="nav nav-tabs"  style="margin-top: 10px;">
                        	<li class="active"><a href="#tab1" data-toggle="tab">Dashboard</a></li>
                            <li class=""><a href="#tab2" data-toggle="tab">House Plan</a></li>
                            <li class=""><a href="#tab3" data-toggle="tab">Topology</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="tab1">
                            	<table width="781px" border="1">
                            		<c:forEach var="device" items="${list}" varStatus="status">
        								<c:choose>
											<c:when test="${status.index % 10 == 0}">
												<tr>
											</c:when>
										</c:choose>
								  		<td><a href='variable.html?id=${device.id}' id='deviceShow' class='btn tip' title='${device.name}'><img src="img/demo/off.png" deviceId='${device.id}' width="78" height="71" /></a></td>
								   		<c:choose>
											<c:when test="${status.index % 10 == 9}">
												</tr>
											</c:when>
										</c:choose>
									</c:forEach>
								</table>
                            </div>
                            <div class="tab-pane" id="tab2">
                           		<img alt="" src="img/museum/network.png">
                            </div>
                            <div class="tab-pane" id="tab3">
                            	<img alt="" src="img/museum/houseplan.png">
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /media environment tab widget -->

            </div>
            <!-- /content wrapper -->

		</div>
		<!-- /content -->

	</div>
	<!-- /content container -->


	<!-- Footer -->
	<div id="footer">
		<div class="copyrights">&copy;  Pinecone Tech.</div>
		<ul class="footer-links">
			<li><a href="" title=""><i class="icon-cogs"></i>Contact admin</a></li>
			<li><a href="" title=""><i class="icon-screenshot"></i>Home page</a></li>
		</ul>
	</div>
	<!-- /footer -->

</body>
</html>
