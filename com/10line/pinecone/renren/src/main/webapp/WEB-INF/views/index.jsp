<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>Pannonia - premium responsive admin template by Eugene Kopyov</title>
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

</head>

<body>

	<!-- Fixed top -->
	<div id="top">
		<div class="fixed">
			<a href="index.html" title="" class="logo"><img src="img/logo.png" alt="" /></a>
			<ul class="top-menu">
				
				<li class="dropdown">
					<a class="user-menu" data-toggle="dropdown"><img src="img/userpic.png" alt="" /><span>Howdy, Eugene! <b class="caret"></b></span></a>
					<ul class="dropdown-menu">
						<li><a href="#" title=""><i class="icon-user"></i>Profile</a></li>
						<li><a href="#" title=""><i class="icon-inbox"></i>Messages<span class="badge badge-info">9</span></a></li>
						<li><a href="#" title=""><i class="icon-cog"></i>Settings</a></li>
						<li><a href="#" title=""><i class="icon-remove"></i>Logout</a></li>
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
						<div class="navbar"><div class="navbar-inner"><h6>Wazzup, Eugene!</h6></div></div>
			            <a href="#" title="" class="user"><img src="http://placehold.it/210x110" alt="" /></a>
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
		                <li class="active"><a href="media.html" title="">Media stuff</a></li>
		            </ul>
			        
		            <ul class="alt-buttons">
		                <li><a href="#" title=""><i class="icon-comments"></i><span>Messages</span></a></li>
		                <li class="dropdown"><a href="#" title="" data-toggle="dropdown"><i class="icon-tasks"></i><span>Tasks</span> <strong>(+16)</strong></a>
		                	<ul class="dropdown-menu pull-right">
		                        <li><a href="#" title=""><i class="icon-plus"></i>Add new task</a></li>
		                        <li><a href="#" title=""><i class="icon-reorder"></i>Statement</a></li>
		                        <li><a href="#" title=""><i class="icon-cog"></i>Settings</a></li>
		                	</ul>
		                </li>
		            </ul>
			    </div>
			    <!-- /breadcrumbs line -->

                <h5 class="widget-name"><i class="icon-table"></i>Table gallery items</h5>

                <!-- Media datatable -->
                <div class="widget">
                	<div class="navbar">
                    	<div class="navbar-inner">
                        	<h6>Media table</h6>
                            <div class="nav pull-right">
                                <a href="#" class="dropdown-toggle navbar-icon" data-toggle="dropdown"><i class="icon-cog"></i></a>
                                <ul class="dropdown-menu pull-right">
                                    <li><a href="#"><i class="icon-plus"></i>Add new option</a></li>
                                    <li><a href="#"><i class="icon-reorder"></i>View statement</a></li>
                                    <li><a href="#"><i class="icon-cogs"></i>Parameters</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="table-overflow">
                        <table class="table table-striped table-bordered table-checks media-table">
                            <thead>
                                <tr>
                                    <th>Image</th>
                                    <th>Description</th>
                                    <th>Date</th>
                                    <th>File info</th>
                                    <th class="actions-column">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image2 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
                                <tr>
			                        <td><a href="img/demo/big.jpg" title="" class="lightbox"><img src="http://placehold.it/37x37" alt="" /></a></td>
			                        <td><a href="#" title="">Image1 description</a></td>
			                        <td>Feb 12, 2012. 12:28</td>
			                        <td class="file-info">
			                        	<span><strong>Size:</strong> 215 Kb</span>
			                        	<span><strong>Format:</strong> .jpg</span>
			                        	<span><strong>Dimensions:</strong> 120 x 120</span>
			                        </td>
			                        <td>
		                                <ul class="navbar-icons">
		                                    <li><a href="#" class="tip" title="Add new option"><i class="icon-plus"></i></a></li>
		                                    <li><a href="#" class="tip" title="View statistics"><i class="icon-reorder"></i></a></li>
		                                    <li><a href="#" class="tip" title="Parameters"><i class="icon-cogs"></i></a></li>
		                                </ul>
			                        </td>
                                </tr>
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
		<div class="copyrights">&copy;  Brought to you by Eugene Kopyov.</div>
		<ul class="footer-links">
			<li><a href="" title=""><i class="icon-cogs"></i>Contact admin</a></li>
			<li><a href="" title=""><i class="icon-screenshot"></i>Report bug</a></li>
		</ul>
	</div>
	<!-- /footer -->

</body>
</html>
