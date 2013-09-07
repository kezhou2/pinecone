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
<script type="text/javascript">
window.onload = function(){	
	var isChangedPwd = <%=(String)request.getAttribute("changePwd")%>;
	if(isChangedPwd != null){
		if(isChangedPwd){
			$.jGrowl("<fmt:message key='profile.your.password.changed' />", { sticky: true, theme: 'growl-success', life:5});
		}else{
			$.jGrowl("<fmt:message key='profile.fail.change.password' />", { sticky: true, theme: 'growl-error', life:5});
		}
	}
	
	var isChangeProfile = <%=(String)request.getAttribute("changeProfile")%>;
	if(isChangeProfile != null){
		$("li[id='mchangepassword']").toggleClass("active");
		$("li[id='mchangeprofile']").toggleClass("active");
		$("#tab5").toggleClass("active in");
		$("#tab6").toggleClass("active in");
		
		if(isChangeProfile){
			$.jGrowl("<fmt:message key='profile.change.success' />", { sticky: true, theme: 'growl-success', life:5});
		}else{
			$.jGrowl("<fmt:message key='profile.change.fail' />", { sticky: true, theme: 'growl-error', life:5});
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

			<div class="sidebar-tabs">
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
			            <li><a href="index.html" title=""><i class="fam-application-view-tile"></i><fmt:message key="application.device" /></a></li>
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
			<div class="profile">
			<div class="row-fluid">
			<div class = "span10 offset1">
    			<div class="widget navbar-tabs">
	            	<div class="navbar">
	                	<div class="navbar-inner">
	                    	<h6><fmt:message key="profile.edit" /></h6>
	                        <ul class="nav nav-tabs pull-right">
		                        <li id="mchangepassword" class="active"><a href="#tab5" data-toggle="tab"><fmt:message key="profile.password" /></a></li>
		                        <li id="mchangeprofile" class=""><a href="#tab6" data-toggle="tab"><fmt:message key="profile.profile" /></a></li>
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
								    		<h5><fmt:message key="profile.change.password" /></h5>
								    		<span>&nbsp</span>
								    	</div>
								    	<div>
				                            <div class="control-group">
				                                <label class="control-label"><fmt:message key="profile.old.password" /></label>
				                                <div class="controls"><input id="oldpassword" type="password" name="oldpassword" class="validate[required] span12"></div>
				                            </div>
				                            <div class="control-group">
				                                <label class="control-label"><fmt:message key="profile.new.password" /></label>
				                                <div class="controls"><input id="newpassword" type="password" name="newpassword" class="validate[required] span12 ui-wizard-content"></div>
				                            </div>
				                            <div class="control-group">
				                                <label class="control-label"><fmt:message key="profile.confirm.password" /></label>
				                                <div class="controls"><input id="confirmpassword" type="password" name="confirmpassword" class="validate[required,equals[newpassword]] span12 ui-wizard-content"></div>
				                            </div>
				                            <input type="text" name="myname" value="${myname}" style="visibility: hidden;">
				                            <input type="text" name="myemail" value="${myemail}" style="visibility: hidden;">
				                        </div>
			                        </fieldset>
			                        <div class="form-actions align-right">
		                                <input type="submit" class="btn btn-info" value="<fmt:message key="profile.submit" />">
		                                <input type="reset" class="btn" value="<fmt:message key="profile.reset" />">
	                            	</div>
			                    </form>
							</div>
	                   	    <div class="tab-pane fade" id="tab6">
	                   	    	<form id="validate" class="form-horizontal" action="changeprofile.html" method="post">
			                        <fieldset>
			                            <div class="step-title">
			                            	<i>1</i>
								    		<h5><fmt:message key="profile.edit.basic.profile" /></h5>
								    		<span>&nbsp</span>
								    	</div>
								    	<div>
				                            <div class="control-group">
				                                <label class="control-label"><fmt:message key="profile.username" /></label>
				                                <div class="controls"><input type="text" name="username" class="span12 ui-wizard-content" value="${myname}"></div>
				                            </div>
				                            <div class="control-group">
				                                <label class="control-label"><fmt:message key="profile.email" /></label>
				                                <div class="controls"><input type="text" name="email" class="span12 ui-wizard-content" value="${myemail}"></div>
				                            </div>
				                        </div>
			                        </fieldset>
			                        <div class="form-actions align-right">
		                                <input type="submit" class="btn btn-info" value="<fmt:message key="profile.submit" />">
		                                <input type="reset" class="btn" value="<fmt:message key="profile.reset" />">
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
		<div class="copyrights"><fmt:message key="application.company" /></div>
		<ul class="footer-links">
			<li><a href="mailto:liugyang@gmail.com?Subject=helpme" title=""><i class="icon-cogs"></i><fmt:message key="application.contact.admin" /></a></li>
			<li><a href="http://www.pinecone.cc" title=""><i class="icon-screenshot"></i><fmt:message key="application.home.page" /></a></li>
		</ul>
	</div>
	<!-- /footer -->

</body>
</html>
