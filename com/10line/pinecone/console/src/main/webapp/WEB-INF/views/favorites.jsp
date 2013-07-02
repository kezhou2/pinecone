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
<%
String conf = (String)request.getAttribute("focusConf");
%>
<script type="text/javascript">
//devices' id user focus. 
var deviceIds = [];
//variables' id user focus.
var variableIds = new Array();
//json data that datatable sends
var jsonData;
//refresh thread's id;
var refreshid;
//
var isConnected = false;
//used to store variables history data
var trendvalue = new Map();
//define the size of history data
var TREND_LEN = 10;
//focus configuration in json style
var focusConf = <%=conf%>

function initConfig(){
	for(var i=0;i<focusConf.length;i++){
		var c = focusConf[i];
		var splits = focusConf[i].variableIds.split("_");
		for(var j=0;j<splits.length;j++){
			if(splits[j] != ""){
				trendvalue.put(splits[j],new Array());
				variableIds.push(splits[j]);
			}
		}
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
		     		$(this).parent().attr("colspan",6);
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
					if(splits.length >=2){
						publish(splits[0],splits[1]);
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
				'autoDimensions'	: false,
				'width'         	: 1000,
				'height'        	: 'auto',
				'transitionIn'		: 'none',
				'transitionOut'		: 'none'
			});
			//initialize history dialog
			
			$("a#historyShow").fancybox({
				'autoDimensions'	: false,
				'width'         	: 500,
				'height'        	: 500,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'autoScale'		: false,
				
			});
			
			//setRefresh(2);
		},	
		"sAjaxSource": "/console/queryfocusvariable.html"
	});

}

function alermVariable(deviceId, variableId){
	
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
	
	<!-- fancy box for variable's alerm setting -->
	<div style="display:none">
		<form id="variable_form" action="#">
	    	<div class="widget">
	            <div class="navbar"><div class="navbar-inner"><h6>Variable alerm setting</h6></div></div>
	
	            <div class="well">
	                
	                <div class="control-group">
	                    <label class="control-label">Condition type:</label>
	                    <div class="controls">
	                        <select id="conditionType" name="select2" class="styled" style="opacity: 0;">
	                            <option value="opt1">Numeric</option>
	                            <option value="opt2">String</option>
	                        </select>
	                    </div>
	                    <label class="control-label">Condition:</label>
	                    <div class="controls">
	                        <select id="condition" name="select2" class="styled" style="opacity: 0;">
	                            <option value="opt1">></option>
	                            <option value="opt2">>=</option>
	                            <option value="opt3">=</option>
	                            <option value="opt4"><</option>
	                            <option value="opt5"><=</option>
	                            <option value="opt6">!=</option>
	                        </select>
	                    </div>
	                    <label class="control-label">Value:</label>
	                    <div class="controls"><input type="text" name="regular" class="span12" placeholder="Regular field"></div>
	                </div>
	                
	                <div class="control-group">
	                    <label class="control-label">Alerm type:</label>
	                    <div class="controls">
	                    	<label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox1"><span><input type="checkbox" id="inlineCheckbox1" value="option1" class="styled" style="opacity: 0;"></span></div>Log</label>
	                    	<label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox1"><span><input type="checkbox" id="inlineCheckbox1" value="option1" class="styled" style="opacity: 0;"></span></div>Page</label>
	                        <label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox2"><span><input type="checkbox" id="inlineCheckbox2" value="option2" class="styled" style="opacity: 0;"></span></div>Sound</label>
	                        <label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox3"><span><input type="checkbox" id="inlineCheckbox3" value="option3" class="styled" style="opacity: 0;"></span></div>SMS</label>
	                        <label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox4"><span><input type="checkbox" id="inlineCheckbox4" value="option4" class="styled" style="opacity: 0;"></span></div>Email</label>
	                    </div>
	                    <label class="control-label">Cell phone:</label>
	                    <div class="controls"><input type="text" name="regular" class="span12" placeholder="Regular field"></div>
	                    <label class="control-label">Email address:</label>
	                    <div class="controls"><input type="text" name="regular" class="span12" placeholder="Regular field"></div>
	                </div>
	                
	                <div class="form-actions align-right">
	                    <button type="submit" class="btn btn-primary">Submit</button>
	                    <button type="button" class="btn btn-danger">Cancel</button>
	                    <button type="reset" class="btn">Reset</button>
	                </div>
	
	            </div>
	            
	        </div>
	    </form>
	</div>
	<!-- /end fancy box for variable's alerm setting -->
	
	<!-- fancy box for history show -->
	<div class="span4" style="display:none">
    	<div class="widget" id="history_panel">
			<div class="navbar"><div class="navbar-inner"><h6>Variable history value</h6></div></div>
            <div class="well body">
            	<ul class="stats-details">
            		<li>
            			<strong>Current balance</strong>
            			<span>latest update on 12:39 am</span>
            		</li>
            		<li>
            			<div class="number">
	            			<a href="#" title="" data-toggle="dropdown"></a>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" title=""><i class="icon-refresh"></i>Reload data</a></li>
								<li><a href="#" title=""><i class="icon-calendar"></i>Change time period</a></li>
								<li><a href="#" title=""><i class="icon-download-alt"></i>Download statement</a></li>
							</ul>
							<span>6,458</span>
						</div>
            		</li>
            	</ul>
            	<div class="graph" id="chart1"></div>
            </div>
        </div>
  	</div>
	<!-- /end fancy box for history show -->
</body>
</html>
