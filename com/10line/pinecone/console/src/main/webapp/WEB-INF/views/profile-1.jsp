<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>Pinecone - device controller</title>
<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="img/favicon.ico" ce_href="img/favicon.ico" type="image/x-icon">
<link href="css/main.css" rel="stylesheet" type="text/css" />
<!--[if IE 8]><link href="css/ie8.css" rel="stylesheet" type="text/css" /><![endif]-->
<!--[if IE 9]><link href="css/ie9.css" rel="stylesheet" type="text/css" /><![endif]-->
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

<script type="text/javascript" src="js/globalize/globalize.js"></script>
<script type="text/javascript" src="js/globalize/globalize.culture.de-DE.js"></script>
<script type="text/javascript" src="js/globalize/globalize.culture.ja-JP.js"></script>

<script type="text/javascript" src="js/plugins/tables/jquery.dataTables.min.js"></script>

<script type="text/javascript" src="js/files/bootstrap.min.js"></script>

<script type="text/javascript" src="js/files/functions.js"></script>

</head>

<body class="no-background">

	<!-- Fixed top -->
	<div id="top">
		<div class="fixed">
			<a href="index.html" title="" class="logo"><img src="img/logo.png" alt="" /></a>
		</div>
	</div>
	<!-- /fixed top -->


    <!-- Register block -->
    <div class="profile">
		<div class="row-fluid">
			<div class = "span8 offset2">
    			<div class="widget navbar-tabs">
	            	<div class="navbar">
	                	<div class="navbar-inner">
	                    	<h6>User Profile Editor</h6>
	                        <ul class="nav nav-tabs pull-right">
		                        <li class="active"><a href="#tab5" data-toggle="tab">Profile</a></li>
		                        <li class=""><a href="#tab6" data-toggle="tab">Password</a></li>
	                        </ul>
	                	</div>
	              	</div>
	                <div class="tabbable">
	                    <div class="tab-content">
	                   	    <div class="tab-pane fade active in" id="tab5">
	                   	    	<form id="wizard1" method="post" action="#" class="form-horizontal row-fluid well ui-formwizard">
			                        <fieldset class="step ui-formwizard-content" id="step1" style="display: block;">
			                            <div class="step-title">
			                            	<i>1</i>
								    		<h5>Enter your basic information</h5>
								    		<span>&nbsp</span>
								    	</div>
								    	<div>
				                            <div class="control-group">
				                                <label class="control-label">Username:</label>
				                                <div class="controls"><input type="text" name="username" class="span12 ui-wizard-content"></div>
				                            </div>
				                            <div class="control-group">
				                                <label class="control-label">Email:</label>
				                                <div class="controls"><input type="text" class="span12 ui-wizard-content"></div>
				                            </div>
				                        </div>
			                        </fieldset>
			                        <div class="form-actions align-right">
			                            <input class="btn ui-wizard-content ui-formwizard-button" id="back-1" value="Back" type="reset" disabled="disabled">
			                            <input type="submit" class="btn btn-danger ui-wizard-content ui-formwizard-button" id="next-1" value="Next">
			                        </div>
			                    </form>
	                   	    </div>
	                       	<div class="tab-pane fade" id="tab6">
	                       		<form id="wizard1" method="post" action="#" class="form-horizontal row-fluid well ui-formwizard">
			                        <fieldset class="step ui-formwizard-content" id="step1" style="display: block;">
			                            <div class="step-title">
			                            	<i>1</i>
								    		<h5>Password Change</h5>
								    		<span>&nbsp</span>
								    	</div>
								    	<div>
				                            <div class="control-group">
				                                <label class="control-label">old password:</label>
				                                <div class="controls"><input type="password" name="oldpassword" class="span12 ui-wizard-content"></div>
				                            </div>
				                            <div class="control-group">
				                                <label class="control-label">new password:</label>
				                                <div class="controls"><input type="password" name="newpassword" class="span12 ui-wizard-content"></div>
				                            </div>
				                            <div class="control-group">
				                                <label class="control-label">confirm new password:</label>
				                                <div class="controls"><input type="password" name="confirmpassword" class="span12 ui-wizard-content"></div>
				                            </div>
				                        </div>
			                        </fieldset>
			                        <!-- <fieldset id="step2" class="step ui-formwizard-content" style="display: none;">
			                            <div class="step-title">
			                            	<i>2</i>
								    		<h5>Please entro</h5>
								    		<span>Aenean sem dui, semper sit amet luctus sit amet, molestie vitae nunc</span>
								    	</div>
			                            <div class="control-group">
			                                <label class="control-label">Your city:</label>
			                                <div class="controls"><input type="text" class="span12 ui-wizard-content" disabled="disabled"></div>
			                            </div>
			                            <div class="control-group">
			                                <label class="control-label">Other details:</label>
			                                <div class="controls"><input type="text" class="span12 ui-wizard-content" disabled="disabled"></div>
			                            </div>
			                        </fieldset> -->
			                        <div class="form-actions align-right">
			                            <input class="btn ui-wizard-content ui-formwizard-button" id="back-1" value="Back" type="reset" disabled="disabled">
			                            <input type="submit" class="btn btn-danger ui-wizard-content ui-formwizard-button" id="next-1" value="Next">
			                        </div>
			                    </form>
							</div>
	                    </div>
	                </div>
	        	</div>
        	</div>
    	</div>
	</div>
    <!-- /register block -->


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
