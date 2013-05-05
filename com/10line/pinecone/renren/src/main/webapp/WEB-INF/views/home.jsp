<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<!--
		Charisma v1.0.0

		Copyright 2012 Muhammad Usman
		Licensed under the Apache License v2.0
		http://www.apache.org/licenses/LICENSE-2.0

		http://usman.it
		http://twitter.com/halalit_usman
	-->
<meta charset="utf-8">
<title><fmt:message key="application.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content=<fmt:message key="application.description"/>>
<meta name="author" content=<fmt:message key="application.author"/>>

<!-- The styles -->
<link id="bs-css" href="css/bootstrap-cerulean.css" rel="stylesheet">
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
</style>
<link href="css/bootstrap-responsive.css" rel="stylesheet">
<link href="css/charisma-app.css" rel="stylesheet">
<link href="css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
<link href='css/fullcalendar.css' rel='stylesheet'>
<link href='css/fullcalendar.print.css' rel='stylesheet' media='print'>
<link href='css/chosen.css' rel='stylesheet'>
<link href='css/uniform.default.css' rel='stylesheet'>
<link href='css/colorbox.css' rel='stylesheet'>
<link href='css/jquery.cleditor.css' rel='stylesheet'>
<link href='css/jquery.noty.css' rel='stylesheet'>
<link href='css/noty_theme_default.css' rel='stylesheet'>
<link href='css/elfinder.min.css' rel='stylesheet'>
<link href='css/elfinder.theme.css' rel='stylesheet'>
<link href='css/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='css/opa-icons.css' rel='stylesheet'>
<link href='css/uploadify.css' rel='stylesheet'>

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon" href="img/favicon.ico">

</head>

<body>
	<!-- topbar starts -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</a> <a class="brand" style="width: 250px" href="home.html"> <img
					style="width: 250px; height: 50px" alt="Pinecone Logo"
					src="img/logo_title.png" /></a>

				<!-- theme selector starts -->
				<!--
				<div class="btn-group pull-right theme-container" >
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-tint"></i><span class="hidden-phone"> Change Theme / Skin</span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu" id="themes">
						<li><a data-value="classic" href="#"><i class="icon-blank"></i> Classic</a></li>
						<li><a data-value="cerulean" href="#"><i class="icon-blank"></i> Cerulean</a></li>
						<li><a data-value="cyborg" href="#"><i class="icon-blank"></i> Cyborg</a></li>
						<li><a data-value="redy" href="#"><i class="icon-blank"></i> Redy</a></li>
						<li><a data-value="journal" href="#"><i class="icon-blank"></i> Journal</a></li>
						<li><a data-value="simplex" href="#"><i class="icon-blank"></i> Simplex</a></li>
						<li><a data-value="slate" href="#"><i class="icon-blank"></i> Slate</a></li>
						<li><a data-value="spacelab" href="#"><i class="icon-blank"></i> Spacelab</a></li>
						<li><a data-value="united" href="#"><i class="icon-blank"></i> United</a></li>
					</ul>
				</div>
				-->
				<!-- theme selector ends -->

				<!-- user dropdown starts -->
				<!--<div class="btn-group pull-right" >
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i><span class="hidden-phone"> admin</span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">Profile</a></li>
						<li class="divider"></li>
						<li><a href="login.html">Logout</a></li>
					</ul>
				</div> -->
				<!-- user dropdown ends -->

				<div class="top-nav nav-collapse pull-right">
					<!--
					<ul class="nav">
						<li><a href="#" style="padding-top:10px;">浏览网站</a></li>
						<li>
							<form class="navbar-search pull-left">
								<input placeholder="检索" class="search-query span2" name="query" type="text">
							</form>
						</li>
					</ul>
					-->
					<a href="login.html" class="btn btn-large"
						style="margin-top: 20px;"><fmt:message key="application.login" /></a>
					<a href="registry.html" class="btn btn-large"
						style="margin-top: 20px;"><fmt:message
							key="application.registry" /></a>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<!-- topbar ends -->
	<div class="container-fluid">
		<div class="row-fluid">

			<!-- left menu starts -->
			<div class="span2 main-menu-span">
				<div class="well nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li class="nav-header hidden-tablet"><fmt:message
								key="application.menu" /></span></li>
						<li><a class="ajax-link" href="home.html"><i
								class="icon-home"></i><span class="hidden-tablet"><fmt:message
										key="application.homepage" /></span></a></li>
						<li><a class="ajax-link" href="products.html"><i
								class="icon-shopping-cart"></i><span class="hidden-tablet">
									<fmt:message key="application.product" /></span></a></li>
						<li><a class="ajax-link" href="developer.html"><i
								class="icon-wrench"></i><span class="hidden-tablet"><fmt:message
										key="application.developer" /></span></a></li>
						<li><a class="ajax-link" href="sensation.html"><i
								class="icon-globe"></i><span class="hidden-tablet"><fmt:message
										key="application.sensation" /></span></a></li>
						<li><a href="login.html"><i class="icon-lock"></i><span
								class="hidden-tablet"><fmt:message
										key="application.login" /></span></a></li>
					</ul>
				</div>
				<!--/.well -->
			</div>
			<!--/span-->
			<!-- left menu ends -->

			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript"
							target="_blank">JavaScript</a> enabled to use this site.
					</p>
				</div>
			</noscript>

			<div id="content" class="span10">
				<!-- content starts -->

				<div></div>

				<div class="row-fluid sortable">
					<div style="width: 100%; margin-top: 0px" class="box span9">
						<div class="box-content">
							<div class="row-fluid">
								<div class="tooltip-demo well">
									<div class="row-fluid">
										<div class="span6">
											<blockquote>
												<div class="page-header">
													<h1>
														<fmt:message key="application.title" />
														<small style="padding-left: 50px; line-height: 40px;"><fmt:message key="application.titlesentence" /></small>
													</h1>
												</div>
												<br>
												<p style="line-height: 26px;"><fmt:message key="application.welcomesentence" /></p>
												<small class="pull-right">Let's <cite title="">go</cite></small>
												<br>
												<br>
												<br>
											</blockquote>
										</div>
										<div class="span6">
											<img src="img/index_header.jpg" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span4 ">
									<div class="well">
										<div class="boxtitle"><fmt:message key="application.platform" /></div>
										<div class="box_icon">
											<img src="img/icon1.gif" alt="" title="" border="0">
										</div>
										<p class="text_content">
											<fmt:message key="application.platform.description" /><br>
											<br> <a href="#" class="read_more"><img
												src="img/read_more.gif" alt="" title="" border="0"></a>
										</p>
									</div>
								</div>
								<div class="span4 ">
									<div class="well">
										<div class="boxtitle"><fmt:message key="application.api" /></div>
										<div class="box_icon">
											<img src="img/icon2.gif" alt="" title="" border="0">
										</div>
										<p class="text_content">
											<fmt:message key="application.api.description" /><br>
											<br> <a href="#" class="read_more"><img
												src="img/read_more.gif" alt="" title="" border="0"></a>
										</p>
									</div>
								</div>
								<div class="span4 ">
									<div class="well">
										<div class="boxtitle"><fmt:message key="application.intelligence.sensation" /></div>
										<div class="box_icon">
											<img src="img/icon3.gif" alt="" title="" border="0">
										</div>
										<p class="text_content">
											<fmt:message key="application.intelligence.sensation.description" /><br>
											<br> <a href="#" class="read_more"><img
												src="img/read_more.gif" alt="" title="" border="0"></a>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!--/span-->
					<!--/span-->
					<!--/span-->
					<!--/span-->



				</div>
				<!--/row-->

				<!-- content ends -->
			</div>
			<!--/#content.span10-->
		</div>
		<!--/fluid-row-->

		<hr>

		<div class="modal hide fade" id="myModal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>Settings</h3>
			</div>
			<div class="modal-body">
				<p>Here settings can be configured...</p>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">Close</a> <a href="#"
					class="btn btn-primary">Save changes</a>
			</div>
		</div>

		<footer>
			<p class="pull-left">
				<a href="#" target="_blank"><fmt:message key="application.problem" />常见问题</a> <a href="#" target="_blank"><fmt:message key="application.privacy.policy" /></a>
				<a href="#" target="_blank"><fmt:message key="application.contact" /></a>
			</p>
			<p class="pull-right" style="color: #2071A1;"><fmt:message key="application.copyright" /></p>
		</footer>

	</div>
	<!--/.fluid-container-->

	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<!-- jQuery -->
	<script src="js/jquery-1.7.2.min.js"></script>
	<!-- jQuery UI -->
	<script src="js/jquery-ui-1.8.21.custom.min.js"></script>
	<!-- transition / effect library -->
	<script src="js/bootstrap-transition.js"></script>
	<!-- alert enhancer library -->
	<script src="js/bootstrap-alert.js"></script>
	<!-- modal / dialog library -->
	<script src="js/bootstrap-modal.js"></script>
	<!-- custom dropdown library -->
	<script src="js/bootstrap-dropdown.js"></script>
	<!-- scrolspy library -->
	<script src="js/bootstrap-scrollspy.js"></script>
	<!-- library for creating tabs -->
	<script src="js/bootstrap-tab.js"></script>
	<!-- library for advanced tooltip -->
	<script src="js/bootstrap-tooltip.js"></script>
	<!-- popover effect library -->
	<script src="js/bootstrap-popover.js"></script>
	<!-- button enhancer library -->
	<script src="js/bootstrap-button.js"></script>
	<!-- accordion library (optional, not used in demo) -->
	<script src="js/bootstrap-collapse.js"></script>
	<!-- carousel slideshow library (optional, not used in demo) -->
	<script src="js/bootstrap-carousel.js"></script>
	<!-- autocomplete library -->
	<script src="js/bootstrap-typeahead.js"></script>
	<!-- tour library -->
	<script src="js/bootstrap-tour.js"></script>
	<!-- library for cookie management -->
	<script src="js/jquery.cookie.js"></script>
	<!-- calander plugin -->
	<script src='js/fullcalendar.min.js'></script>
	<!-- data table plugin -->
	<script src='js/jquery.dataTables.min.js'></script>

	<!-- chart libraries start -->
	<script src="js/excanvas.js"></script>
	<script src="js/jquery.flot.min.js"></script>
	<script src="js/jquery.flot.pie.min.js"></script>
	<script src="js/jquery.flot.stack.js"></script>
	<script src="js/jquery.flot.resize.min.js"></script>
	<!-- chart libraries end -->

	<!-- select or dropdown enhancer -->
	<script src="js/jquery.chosen.min.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script src="js/jquery.uniform.min.js"></script>
	<!-- plugin for gallery image view -->
	<script src="js/jquery.colorbox.min.js"></script>
	<!-- rich text editor library -->
	<script src="js/jquery.cleditor.min.js"></script>
	<!-- notification plugin -->
	<script src="js/jquery.noty.js"></script>
	<!-- file manager library -->
	<script src="js/jquery.elfinder.min.js"></script>
	<!-- star rating plugin -->
	<script src="js/jquery.raty.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="js/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script src="js/jquery.autogrow-textarea.js"></script>
	<!-- multiple file upload plugin -->
	<script src="js/jquery.uploadify-3.1.min.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script src="js/jquery.history.js"></script>
	<!-- application script for Charisma demo -->
	<script src="js/charisma.js"></script>


</body>
</html>
