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
var varids = [];
var devid;
var jsonData;
var isRefreshing = false;
var isAlert = false;
var refreshid;
var isConnected = false;
window.onload = function(){
	//get all variable ids;
    $('table td').each(function(){
    	var id = $(this).attr('varid');
    	
     	if(id != null){
     		varids.push(id);
     	}
	})
	
	devid = $('#variablelist').attr('deviceid');
    //alert("device id:"+devid);
	
	//generate request json
 	jsonData = "[";
 	for(var i=0;i<varids.length;i++){
 		if(i<varids.length-1){
 			jsonData += varids[i]+",";
 		}else{
 			jsonData += varids[i]+"]";
 		}
 	}
 	//alert(jsonData);
 	
 	refreshid = setInterval('refresh()',2000);
}

function refresh(){
	isRefreshing = true;
	$.ajax({
 		url:'subscribedata', 
 		type: 'POST',
 		data: {ids:jsonData,deviceid:devid}, 
		timeout: 1000,
 		error: function(){
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
 			var splits = result.split(",");
 			if(splits.length > 1){
 				for(var n=1;n<splits.length;n++){
 					var tmp = splits[n].split(":");
 					var id = tmp[0];
 					var value = tmp[1];
 					$("td[varid='"+id+"']>strong").text(value);
 				}
 			}
 		} 
 	});
}

window.onunload = function(){
	alert("onUnload");
	if(isRefreshing){
		clearInterval(refreshid);
	}
	
	
	$.ajax({
 		url:'subscribedata', 
 		type: 'POST',
 		data: {isDisconnect:'true'},
 		async:false,
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
 		data: {deviceid:devid,variableid:varid,vvalue:value}, 
		timeout: 1000,
 		error: function(){
 			$.jGrowl('There is something wrong with channel!', { sticky: true, theme: 'growl-error', life:1000});
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
</script>
</head>

<body>

	<!-- Fixed top -->
	<div id="top">
		<div class="fixed">
			<a href="index.html" title="" class="logo"><img src="img/logo.png" alt="" /></a>
			<ul class="top-menu">
				
				<li class="dropdown">
					<a class="user-menu" data-toggle="dropdown"><!-- <img src="img/userpic.png" alt="" /> --><span id="greeting_word_1">Welcome back, ${username} <b class="caret"></b></span></a>
					<ul class="dropdown-menu">
						<li><a href="#" title=""><i class="icon-user"></i>Profile</a></li>
						<li><a href="#" title=""><i class="icon-inbox"></i>Messages<span class="badge badge-info">9</span></a></li>
						<li><a href="j_spring_security_logout" title=""><i class="icon-signout"></i>Logout</a></li>
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
		            <li><a href="#general" title=""><i class="icon-reorder"></i></a></li>
		            <li><a href="#stuff" title=""><i class="icon-cogs"></i></a></li>
		        </ul>

		        <div id="general">

			        <!-- Sidebar user -->
			        <div class="sidebar-user widget">
						<div class="navbar"><div class="navbar-inner"><h6 id="greeting_word_2">Welcome back, ${username}</h6></div></div>
						<div>
			            	<a href="#" title="" class="user"><img src="http://placehold.it/210x110" alt="" /></a>
			            </div>
			        </div>
			        <!-- /sidebar user -->

				    <!-- Main navigation -->
			        <ul class="navigation widget">
			            <li><a href="#" title=""><i class="icon-home"></i>Dashboard</a></li>
			            <li class="active"><a href="index.html" title=""><i class="icon-tasks"></i>Devices</a></li>
			            <li><a href="friends.html" title=""><i class="icon-group"></i>Friend</a></li>
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
		                <li><a href="index.html">Devices</a></li>
		                <li class="active"><a href="#" title="">${device.name }</a></li>
		            </ul>
			        
		            <ul class="alt-buttons">
						<li><a href="#" title=""><i class="icon-plus"></i><span>Active Device</span></a></li>
						<li class ="dropdown"><a href="#" title="" data-toggle="dropdown"><i class="icon-cog"></i><span>Menu</span></a>
		                	<ul class="dropdown-menu pull-right">
		                        <li><a href="#" title=""><i class="icon-tasks"></i>Devices</a></li>
		                        <li><a href="#" title=""><i class="icon-group"></i>Friends</a></li>
		                	</ul>
		                </li>
		            </ul>
			    </div>
			    <!-- /breadcrumbs line -->
				
                <!-- Media datatable -->
                <div class="widget">
                	<div class="navbar">
                    	<div class="navbar-inner">
                        	<h6>Variable table</h6>
                        </div>
                    </div>
                    <div class="table-overflow">
                        <table id='variablelist' class="table table-striped table-bordered table-checks media-table" deviceid="${device.code }">
                            <thead>
                                <tr>
                                	<th>ID</th>
                                    <th>Type</th>
                                    <th>Name</th>
                                    <th>Value</th>
                                    <th class="actions-column">Actions</th>
                                </tr>
                            </thead>
                            <tbody> 
								<c:forEach var="variable" items="${list}">
									<tr>
										<td>${variable.id}</td>
										<td>${variable.type}</td>
										<td>${variable.name}</td>
										<c:choose>
											<c:when test="${variable.type == 'read'}">
												<td varid="${variable.id }" class="vvalue"><strong>loading...</strong></td>
											</c:when>
											<c:when test="${variable.type == 'write'}">
												<td varid="${variable.id }" class="vvalue"><strong>--</strong></td>
											</c:when>
										</c:choose>
										
										<td>
											<ul class="table-controls">
												<li>
													<div class="btn-group">
														<c:choose>
														    <c:when test="${variable.type == 'read'}">
														      	<button class="disabled btn dropdown-toggle" data-toggle="dropdown">Setting <span class="caret dd-caret"></span></button>
														    </c:when>
														    <c:when test="${variable.type == 'write'}">
														      	<button class="btn dropdown-toggle" data-toggle="dropdown">Setting <span class="caret dd-caret"></span></button>
																<ul class="dropdown-menu">
																	<c:forEach var="item" items="${variable.items}">
																	<li><a href="#" onclick="publish('${variable.id }','${item.value}')" >${item.value}</a></li>
																	</c:forEach>
																</ul>
														    </c:when>
														</c:choose>
													</div>
												</li>
											</ul>
										</td>
									</tr>
								</c:forEach>
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
		<div class="copyrights">&copy;  Pinecone Tech.</div>
		<ul class="footer-links">
			<li><a href="" title=""><i class="icon-cogs"></i>Contact admin</a></li>
			<li><a href="" title=""><i class="icon-screenshot"></i>Home page</a></li>
		</ul>
	</div>
	<!-- /footer -->

</body>
</html>
