<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>Pannonia - premium responsive admin template by Eugene Kopyov</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<!--[if IE 8]><link href="css/ie8.css" rel="stylesheet" type="text/css" /><![endif]-->
<!--[if IE 9]><link href="css/ie9.css" rel="stylesheet" type="text/css" /><![endif]-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>

<script type="text/javascript" src="js/plugins/forms/jquery.uniform.min.js"></script>

<script type="text/javascript" src="js/files/bootstrap.min.js"></script>

<script type="text/javascript" src="js/files/login.js"></script>

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
	<div class="register">
    <form id="validate" class="form-horizontal" action="#">
	                <fieldset>

	                    <!-- Form validation -->
	                    <div class="widget">
	                        <div class="navbar"><div class="navbar-inner"><h6>Form validation</h6></div></div>
	                    	<div class="well row-fluid">

	                            <div class="control-group">
	                                <label class="control-label">Input field validation: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="text" class="validate[required] span12" name="req" id="req">
	                                </div>
	                            </div>
	                        
	                            <div class="control-group">
	                                <label class="control-label">Password: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="password" class="validate[required] span12" name="password1" id="password1">
	                                </div>
	                            </div>
	                        
	                            <div class="control-group">
	                                <label class="control-label">Repeat password: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="password" class="validate[required,equals[password]] span12" name="password2" id="password2">
	                                </div>
	                            </div>
	                        
	                            <div class="control-group">
	                                <label class="control-label">Minimum size: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="text" class="validate[required,minSize[6]] span12" name="minValid" id="minValid">
	                                </div>
	                            </div>
	                        
	                            <div class="control-group">
	                                <label class="control-label">Maximum size: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="text" class="validate[required,maxSize[6]] span12" value="0123456789" name="maxValid" id="maxValid">
	                                </div>
	                            </div>
	                            <div class="control-group">
	                                <label class="control-label">Only letters: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="text" value="this is an invalid char '.'" class="validate[required,custom[onlyLetterSp]] span12" name="lettersValid" id="lettersValid">
	                                </div>
	                            </div>
	                        
	                            <div class="control-group">
	                                <label class="control-label">Only numbers and space: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="text" value="10.1" class="validate[required,custom[onlyNumberSp]] span12" name="numsValid" id="numsValid">
	                                </div>
	                            </div>
	                        
	                            <div class="control-group">
	                                <label class="control-label">Regular expressions: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="text" value="too many spaces obviously" class="validate[required,custom[onlyLetterNumber]] span12" name="regexValid" id="regexValid">
	                                </div>
	                            </div>
	                        
	                            <div class="control-group">
	                                <label class="control-label">Email address: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="text" value="" class="validate[required,custom[email]] span12" name="emailValid" id="emailValid">
	                                </div>
	                            </div>

	                            <div class="control-group">
	                                <label class="control-label">Past: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="text" value="2011/06/27" class="validate[custom[date],past[2010/01/01]] span12" name="past" id="past">
	                                    <span class="help-block">Checks that the value is a date in the past. Please enter a date ealier than 2010/01/01</span>
	                                </div>
	                            </div>

	                            <div class="control-group">
	                                <label class="control-label">Future: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="text" value="2011-01-" class="validate[custom[date],future[NOW]] span12" name="future" id="future">
	                                    <span class="help-block">Checks that the value is a date in the future. Please enter a date older than today's date</span>
	                                </div>
	                            </div>

	                            <div class="control-group">
	                                <label class="control-label">Date format: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <input type="text" value="2011-01-" class="validate[custom[date],future[NOW]] span12" name="dateformatValid" id="dateformatValid">
	                                </div>
	                            </div>

	                            <div class="control-group">
	                                <label class="control-label">Select: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <div class="selector" id="uniform-undefined"><span>Usual select box</span><select name="select2" class="validate[required] styled" data-prompt-position="topLeft:-1,-5" style="opacity: 0;">
	                                        <option value="">Usual select box</option>
	                                        <option value="opt2">Option 2</option>
	                                        <option value="opt3">Option 3</option>
	                                        <option value="opt4">Option 4</option>
	                                        <option value="opt5">Option 5</option>
	                                        <option value="opt6">Option 6</option>
	                                        <option value="opt7">Option 7</option>
	                                        <option value="opt8">Option 8</option>
	                                    </select></div>
	                                </div>
	                            </div>

	                            <div class="control-group">
	                                <label class="control-label">Minimum: <span class="text-error">*</span></label>
	                                <div class="controls">

										<label class="checkbox inline">
											<div class="checker" id="uniform-maxcheck1"><span><input class="validate[minCheckbox[2]] styled" type="checkbox" name="group[group]" id="maxcheck1" value="5" data-prompt-position="topLeft:-1,-5" style="opacity: 0;"></span></div>
											Check me
										</label>
										<label class="checkbox inline">
											<div class="checker" id="uniform-maxcheck2"><span><input class="validate[minCheckbox[2]] styled" type="checkbox" name="group[group]" id="maxcheck2" value="3" data-prompt-position="topLeft:-1,-5" style="opacity: 0;"></span></div>
											Unchecked
										</label>
										<label class="checkbox inline">
											<div class="checker" id="uniform-maxcheck3"><span><input class="validate[minCheckbox[2]] styled" type="checkbox" name="group[group]" id="maxcheck3" value="9" data-prompt-position="topLeft:-1,-5" style="opacity: 0;"></span></div>
											Unchecked
										</label>

	                                </div>
	                            </div>

	                            <div class="control-group">
	                                <label class="control-label">Maximum: <span class="text-error">*</span></label>
	                                <div class="controls">

										<label class="checkbox inline">
											<div class="checker" id="uniform-maxcheck4"><span><input class="validate[maxCheckbox[2]] styled" type="checkbox" name="group2" id="maxcheck4" value="5" data-prompt-position="topLeft:-1,-5" style="opacity: 0;"></span></div>
											Check me
										</label>
										<label class="checkbox inline">
											<div class="checker" id="uniform-maxcheck5"><span><input class="validate[maxCheckbox[2]] styled" type="checkbox" name="group2" id="maxcheck5" value="3" data-prompt-position="topLeft:-1,-5" style="opacity: 0;"></span></div>
											Unchecked
										</label>
										<label class="checkbox inline">
											<div class="checker" id="uniform-maxcheck6"><span><input class="validate[maxCheckbox[2]] styled" type="checkbox" name="group2" id="maxcheck6" value="9" data-prompt-position="topLeft:-1,-5" style="opacity: 0;"></span></div>
											Unchecked
										</label>

	                                </div>
	                            </div>

	                            <div class="control-group">
	                                <label class="control-label">Single checkbox: <span class="text-error">*</span></label>
	                                <div class="controls">

										<label class="checkbox inline">
											<div class="checker" id="uniform-accept"><span><input class="validate[required] styled" type="checkbox" name="accept" id="accept" value="5" data-prompt-position="topRight:0,-5" style="opacity: 0;"></span></div>
											Accept terms?
										</label>

	                                </div>
	                            </div>

	                            <div class="control-group">
	                                <label class="control-label">Textarea: <span class="text-error">*</span></label>
	                                <div class="controls">
	                                    <textarea rows="5" cols="5" name="textarea" class="validate[required] span12"></textarea>
	                                </div>
	                            </div>

	                            <div class="form-actions align-right">
	                                <button type="submit" class="btn btn-info">Submit</button>
	                                <button type="reset" class="btn">Reset</button>
	                            </div>

	                        </div>

	                    </div>
	                    <!-- /form validation -->

	                </fieldset>
				</form>
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
