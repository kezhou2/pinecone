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
function disconnect(id){
	bootbox.confirm("Are you sure?", function(result) {
		if(result == true){
			$.get("disconnectdevice.html?id="+id,function(result){
				if(result == 'true'){
					$.jGrowl('Disconnected the device!', { sticky: true, theme: 'growl-success', life:5000});
					window.location.reload();
				}else{
					$.jGrowl('Failure in disconnecting the device!', { sticky: true, theme: 'growl-error', life:5000});
				}
			});	
	  }
	});
}

function addDevice(devid){
	bootbox.confirm("Do you want to add the device into your favorites?", function(result) {
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
		 				$.jGrowl('Favorite added!', { sticky: true, theme: 'growl-success', life:1000});
		 				$("#device"+devid).attr("onclick","removeDevice('"+devid+"')");
		 				$("#device"+devid).attr("title","Remove from Favorites");
		 				$("#device"+devid).html("<i class='icon-star'></i>");
		 			}else{
		 				$.jGrowl('Setting failed!', { sticky: true, theme: 'growl-error', life:1000});
		 			}
		 		} 
		 	});
		}
	});
}

function removeDevice(devid){
	bootbox.confirm("Do you want to remove the device from your favorites?", function(result) {
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
		 				$.jGrowl('Favorite removed!', { sticky: true, theme: 'growl-success', life:1000});
		 				$("#device"+devid).attr("onclick","addDevice('"+devid+"')");
		 				$("#device"+devid).attr("title","Add to Favorites");
		 				$("#device"+devid).html("<i class='icon-star-empty'></i>");
		 			}else{
		 				$.jGrowl('Setting failed!', { sticky: true, theme: 'growl-error', life:1000});
		 			}
		 		} 
		 	});
		}
	});
}

function editDeviceInfo(devid){
	bootbox.prompt("Please input device mac id:", function(mac) {
		if(mac != null){
			bootbox.prompt("Please input device address",function(addr){
				if(addr != null){
					var address = encodeURI(addr);
					$.get("editdeviceinfo.html?id="+devid+"&mac="+mac+"&addr="+address,function(result){
						if(result == 'true'){
							$.jGrowl('Appended device information!', { sticky: true, theme: 'growl-success', life:5000});
							window.location.reload();
						}else{
							$.jGrowl('Failure in appending device information!', { sticky: true, theme: 'growl-error', life:5000});
						}
					});
				}
			});
		}
	});
}
</script>
<%-- <%
String username = (String)request.getSession().getAttribute("username");
%> --%>
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
		            <li><a href="#general" title=""><i class="icon-reorder"></i></a></li>
		            <li><a href="#stuff" title=""><i class="icon-cogs"></i></a></li>
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
			            <li class="active"><a href="index.html" title=""><i class="fam-application-view-tile"></i>Devices</a></li>
			            <li><a href="favorites.html" title=""><i class="fam-folder-star"></i>Favorites</a></li>
			            <li><a href="environment.html" title=""><i class="fam-world"></i>Environment</a></li>
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
		                <li class="active"><a href="index.html" title="">Devices</a></li>
		            </ul>
			        
		            <ul class="alt-buttons">
						<li><a href="#" id="active-device-dialog" class="active-device-dialog" title="Active Device"><i class="icon-plus"></i><span>Active Device</span></a></li>
						<li class="dropdown"><a href="#" title="" data-toggle="dropdown"><i class="icon-cog"></i><span>Menu</span></a>
		                	<ul class="dropdown-menu pull-right">
		                        <li><a href="index.html" title=""><i class="fam-application-view-tile"></i>Devices</a></li>
		                        <li><a href="favorites.html" title=""><i class="fam-folder-star"></i>Favorites</a></li>
		                        <li><a href="environment.html" title=""><i class="fam-world"></i>Environment</a></li>
		                	</ul>
		                </li>
		            </ul>
			    </div>
			    <!-- /breadcrumbs line -->
				
                <!-- Media datatable -->
                <div class="widget">
                	<div class="navbar">
                    	<div class="navbar-inner" style="margin-top: 10px; margin-bottom: 10px;">
                        	<h6>Devices table</h6>
                        </div>
                    </div>
                    <div class="table-overflow">
                        <table id='devicelist' class="table table-striped table-bordered table-checks media-table">
                            <thead>
                                <tr>
                                    <th>Icon</th>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Code</th>
                                    <th>Mac</th>
                                    <th>Address</th>
                                    <th class="actions-column">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                            <!-- 
								<c:forEach var="device" items="${list}">
									<tr>
										<td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
										<td>${device.name}</td>
										<td>Feb 12, 2012. 12:28</td>
										<td>${device.code}</td>
										<td>
											<ul class="navbar-icons">
												<li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
												<li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
												<li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
											</ul>
										</td>
									</tr>
								</c:forEach>
								-->
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
