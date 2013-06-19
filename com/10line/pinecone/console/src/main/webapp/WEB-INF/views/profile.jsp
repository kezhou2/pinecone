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
window.onload = function(){	
	var isChangedPwd = <%=(String)request.getAttribute("changePwd")%>;
	if(isChangedPwd != null){
		if(isChangedPwd){
			$.jGrowl('Your password is changed!', { sticky: true, theme: 'growl-success', life:5});
		}else{
			$.jGrowl('Failure in changing password!', { sticky: true, theme: 'growl-error', life:5});
		}
	}
	
	var isChangeProfile = <%=(String)request.getAttribute("changeProfile")%>;
	if(isChangeProfile != null){
		$("li[id='mchangepassword']").toggleClass("active");
		$("li[id='mchangeprofile']").toggleClass("active");
		$("#tab5").toggleClass("active in");
		$("#tab6").toggleClass("active in");
		
		if(isChangeProfile){
			$.jGrowl('Your profile is changed!', { sticky: true, theme: 'growl-success', life:5});
		}else{
			$.jGrowl('Failure in changing your profile!', { sticky: true, theme: 'growl-error', life:5});
		}
	}
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
						<li><a href="profile.html" title=""><i class="icon-user"></i>Profile</a></li>
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
			<div class="profile">
			<div class="row-fluid">
			<div class = "span10 offset1">
    			<div class="widget navbar-tabs">
	            	<div class="navbar">
	                	<div class="navbar-inner">
	                    	<h6>User Profile Editor</h6>
	                        <ul class="nav nav-tabs pull-right">
		                        <li id="mchangepassword" class="active"><a href="#tab5" data-toggle="tab">Password</a></li>
		                        <li id="mchangeprofile" class=""><a href="#tab6" data-toggle="tab">Profile</a></li>
	                        </ul>
	                	</div>
	              	</div>
	                <div class="tabbable">
	                    <div class="tab-content">
	                    	<div class="tab-pane fade active in" id="tab5">
	                       		<form id="validate" class="form-horizontal" action="changepassword.html" method="post">
			                        <fieldset>
			                            <div class="step-title">
			                            	<i>1</i>
								    		<h5>Password Change</h5>
								    		<span>&nbsp</span>
								    	</div>
								    	<div>
				                            <div class="control-group">
				                                <label class="control-label">old password:</label>
				                                <div class="controls"><input id="oldpassword" type="password" name="oldpassword" class="validate[required] span12"></div>
				                            </div>
				                            <div class="control-group">
				                                <label class="control-label">new password:</label>
				                                <div class="controls"><input id="newpassword" type="password" name="newpassword" class="validate[required] span12 ui-wizard-content"></div>
				                            </div>
				                            <div class="control-group">
				                                <label class="control-label">confirm new password:</label>
				                                <div class="controls"><input id="confirmpassword" type="password" name="confirmpassword" class="validate[required,equals[newpassword]] span12 ui-wizard-content"></div>
				                            </div>
				                            <input type="text" name="myname" value="${myname}" style="visibility: hidden;">
				                            <input type="text" name="myemail" value="${myemail}" style="visibility: hidden;">
				                        </div>
			                        </fieldset>
			                        <div class="form-actions align-right">
		                                <input type="submit" class="btn btn-info" value="submit">
		                                <input type="reset" class="btn" value="reset">
	                            	</div>
			                    </form>
							</div>
	                   	    <div class="tab-pane fade" id="tab6">
	                   	    	<form id="validate" class="form-horizontal" action="changeprofile.html" method="post">
			                        <fieldset>
			                            <div class="step-title">
			                            	<i>1</i>
								    		<h5>Enter your basic information</h5>
								    		<span>&nbsp</span>
								    	</div>
								    	<div>
				                            <div class="control-group">
				                                <label class="control-label">Username:</label>
				                                <div class="controls"><input type="text" name="username" class="span12 ui-wizard-content" value="${myname}"></div>
				                            </div>
				                            <div class="control-group">
				                                <label class="control-label">Email:</label>
				                                <div class="controls"><input type="text" name="email" class="span12 ui-wizard-content" value="${myemail}"></div>
				                            </div>
				                        </div>
			                        </fieldset>
			                        <div class="form-actions align-right">
		                                <input type="submit" class="btn btn-info" value="submit">
		                                <input type="reset" class="btn" value="reset">
	                            	</div>
			                    </form>
	                   	    </div>
	                       	
	                    </div>
	                </div>
	        	</div>
        	</div>
        	</div>
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
