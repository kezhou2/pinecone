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
<script type="text/javascript" src="js/plugins/forms/jquery.form.wizard.js"></script>
<script type="text/javascript" src="js/plugins/forms/jquery.form.js"></script>

<script type="text/javascript" src="js/plugins/tables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/files/bootstrap.min.js"></script>
<script type="text/javascript" src="js/files/functions.js"></script>
<script type="text/javascript">
</script>

</head>

<body>
	<!-- Fixed top -->
	<div id="top">
		<div class="fixed">
			<a href="index.html" title="" class="logo"><img src="img/logo.png" alt="" /></a>
			<ul class="top-menu">
				
				<li class="dropdown">
					<a class="user-menu" data-toggle="dropdown"><!-- <img src="img/userpic.png" alt="" /> --><span id="greeting_word_1"><fmt:message key="application.welcome"><fmt:param value="${username}" /></fmt:message><b class="caret"></b></span></a>
					<ul class="dropdown-menu">
						<li><a href="profile.html" title=""><i class="fam-group-gear"></i><fmt:message key="application.profile" /></a></li>
						<li><a href="j_spring_security_logout" title=""><i class="fam-door-out"></i><fmt:message key="application.logout" /></a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<!-- /fixed top -->


	<!-- Breadcrumbs line -->
    <div class="crumbs">
           <ul id="breadcrumbs" class="breadcrumb"> 
               <li><a href="index.html"><fmt:message key="application.dashboard" /></a></li>
               <li class="active"><a href="#" title=""><fmt:message key="application.environment" /></a></li>
           </ul>
        
           <ul class="alt-buttons">
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
    
    <!-- Error wrapper -->
	<div class="error-page">
	    <span class="reason">503</span>
		<div class="error-content">
	        <span class="reason-title"><fmt:message key="application.error.503" /></span>

	    	<!-- Search widget -->
	    	<form class="search" action="#">
	    		<div class="autocomplete-append">
		    		<input type="text" placeholder="search website..." id="autocomplete" />
		    		<input type="submit" class="btn btn-info" value="Search" />
		    	</div>
	    	</form>
	    	<!-- /search widget -->

	        <div class="row-fluid error-buttons">
	            <a href="index.html" title="" class="btn btn-info span6"><fmt:message key="application.error.backtodashboad" /></a>
	            <a href="login.html" title="" class="btn btn-success span6"><fmt:message key="application.error.loginagain" /></a>
	        </div>
	    </div>
	</div>  
	<!-- /error wrapper -->

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
