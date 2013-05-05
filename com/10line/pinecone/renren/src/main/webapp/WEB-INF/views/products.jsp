<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
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
	<title>松果网——产品介绍</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="物联网应用平台，智能你的生活！">
	<meta name="author" content="北京松果科技有限责任公司">

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
	<link href='css/fullcalendar.print.css' rel='stylesheet'  media='print'>
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
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" style="width:250px" href="home.html"> <img style="width:250px;height:50px" alt="Pinecone Logo" src="img/logo_title.png" /></a>
				
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
				
				<!-- <div class="top-nav nav-collapse">
					<ul class="nav">
						<li><a href="#" style="padding-top:10px;">浏览网站</a></li>
						<li>
							<form class="navbar-search pull-left">
								<input placeholder="检索" class="search-query span2" name="query" type="text">
							</form>
						</li>
					</ul>
				</div> --><!--/.nav-collapse -->
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
						<li class="nav-header hidden-tablet">菜单</li>
						<li><a class="ajax-link" href="home.html"><i class="icon-home"></i><span class="hidden-tablet"> 主页</span></a></li>
						<li><a class="ajax-link" href="products.html"><i class="icon-shopping-cart"></i><span class="hidden-tablet"> 产品</span></a></li>
						<li><a class="ajax-link" href="developer.html"><i class="icon-wrench"></i><span class="hidden-tablet"> 开发者</span></a></li>
						<li><a class="ajax-link" href="sensation.html"><i class="icon-globe"></i><span class="hidden-tablet"> 感知</span></a></li>
						<li><a href="login.html"><i class="icon-lock"></i><span class="hidden-tablet"> 登陆</span></a></li>
					</ul>
				</div><!--/.well -->
			</div><!--/span-->
			<!-- left menu ends -->
			
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			
			<div id="content" class="span10">
			<!-- content starts -->
			
			<div class="row-fluid sortable">
				<div style="margin-top:0px" class="box span12">
				  <div class="box-content">
				    <section id="icons1">
						  <div class="page-header">
							<h1>平台介绍</h1>
						  </div>
						  <div class="row-fluid bs-icons"></div>

						  <br>

						  <div class="row-fluid">
							<div class="span8">
							  <h3>特点</h3>
							  <p>松果网提供的产品主要包括以下几个部分：物联网应用开放数据API，物联网应用和物联网智能设备。</p>
							  <li>物联网智能设备为用户提供自主品牌产品，使用户享受到更加物美价廉的智能产品。</li>
						  <li>物联网应用开放数据API为开发者提供基于云存储平台二次开放接口，使开发者可在松果网这样一个中间件平台上，进行物联网各种应用的开发和集成。</li>
						  <li>物联网应用通过松果网平台为用户提供各种丰富多彩的基于智能设备的应用，使用户能够在这里工作和生活。</li>
							</div>
							<div class="span3 well"><img src="img/product_features.png"/></div>
							</div>
							<div  class="row-fluid">
							<div class="span3">
							  <h3>技术架构</h3>
							  <p>松果网的技术架构有一下一些特性：</p>
	<li>支持海量数据的高效存储</li>
      <li>支持高并发大规模用户的访问</li>
      <li> 支持可扩展的开放通用数据接口</li>
      <li> 支持不同智能设备的无缝集成</li>
      <li> 支持第三方应用的开发与部署</li>
      <li> 支持用户个人隐私与安全保护</li>	
							</div>	  
						<div class="span8"><img src="img/product_struct.jpg" /></div>
							</div>
							<div  class="row-fluid">
							<h3>发明专利</h3>
      <p>松果网已经申请了一项发明专利《一种用于智能设备监控的通用接口方法》。</p>
<p>近年来，互联网技术不断发展，物联网的概念已经逐渐被人们所认知。如何在复杂网络环境下，通过降低设备通信、控制的复杂性，通过标准的访问控制接口，完成对不同设备统一地监控管理成为一个重要的研究方向。</p>
<p>本专利的目标就是提出一种用于设备监控的通用接口方法，以求在互联网条件下，达到降低设备监控的复杂性和屏蔽设备监控的差异性的目的。</p>
<p>本专利针对不同的物理硬件设备，均通过WEB形式的通用接口进行监控，采用json, xml作为主要的数据交换格式，数据传输时，采用数据压缩技术进一步提高数据的传输效率。本发明支持各种不同形式的客户端，如手机，浏览器，桌面应用等，这些客户端通过调用本发明提供的通用监控接口，无需再考虑底层硬件设备的通信差异。</p>
      </div>
							</div>
							
						  </div>

						  <h3>&nbsp;</h3>
				    </section>
				  </div>
				</div><!--/span-->
			
			</div><!--/row-->
    
					<!-- content ends -->
			</div><!--/#content.span10-->
				</div><!--/fluid-row-->
				
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
				<a href="#" class="btn" data-dismiss="modal">Close</a>
				<a href="#" class="btn btn-primary">Save changes</a>
			</div>
		</div>

		<footer>
			<p class="pull-left"><a href="#" target="_blank">常见问题</a> <a href="#" target="_blank">隐私条款</a> <a href="#" target="_blank">联系我们</a> </p>
			<p class="pull-right" style="color: #2071A1;">©2011-2012 北京松果科技有限责任公司, 版权所有</p>
		</footer>
		
	</div><!--/.fluid-container-->

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
