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
<script type="text/javascript">
window.onload = function(){
$("#submitBtn").click(function(e){
	e.preventDefault();
	
	
	
	
	$.ajax({
 		url:'setalerm.html', 
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
	
	
	parent.$.jGrowl('Disconnected the device!', { sticky: true, theme: 'growl-success', life:5000});
	alert("Submitted");
	parent.$.fancybox.close();
});
}
</script>
</head>

<body  class="no-background">
	<div>
		<form id="variable_form" action="setalerm.html" method="POST">
			<input type="text" value="${deviceId}" name="deviceId" style="display:none">
			<input type="text" value="${variableId}" name="variableId" style="display:none">
	    	<div class="widget">
	            <div class="navbar"><div class="navbar-inner"><h6>Variable alerm setting</h6></div></div>
	
	            <div class="well">
	                
	                <div class="control-group">
	                    <label class="control-label">Condition type:</label>
	                    <div class="controls">
	                        <select id="conditionType" name="conditionType" class="styled" style="opacity: 0;">
	                            <option value="numeric">Numeric</option>
	                            <option value="string">String</option>
	                        </select>
	                    </div>
	                    <label class="control-label">Condition:</label>
	                    <div class="controls">
	                        <select id="condition" name="condition" class="styled" style="opacity: 0;">
	                            <option value=">">></option>
	                            <option value=">=">>=</option>
	                            <option value="=">=</option>
	                            <option value="<"><</option>
	                            <option value="<="><=</option>
	                            <option value="!=">!=</option>
	                        </select>
	                    </div>
	                    <label class="control-label">Value:</label>
	                    <div class="controls"><input type="text" name="variablevalue" class="span12" placeholder="Variable value"></div>
	                </div>
	                
	                <div class="control-group">
	                    <label class="control-label">Alerm type:</label>
	                    <div class="controls">
	                    	<label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox1"><span><input type="checkbox" id="inlineCheckbox1" value="log" name="clog" class="styled" style="opacity: 0;"></span></div>Log</label>
	                    	<label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox1"><span><input type="checkbox" id="inlineCheckbox1" value="page" name="cpage" class="styled" style="opacity: 0;"></span></div>Page</label>
	                        <label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox2"><span><input type="checkbox" id="inlineCheckbox2" value="sound" name="csound" class="styled" style="opacity: 0;"></span></div>Sound</label>
	                        <label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox3"><span><input type="checkbox" id="inlineCheckbox3" value="sms" name="csms" class="styled" style="opacity: 0;"></span></div>SMS</label>
	                        <label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox4"><span><input type="checkbox" id="inlineCheckbox4" value="email" name="cemail" class="styled" style="opacity: 0;"></span></div>Email</label>
	                    </div>
	                    <label class="control-label">Cell phone:</label>
	                    <div class="controls"><input type="text" name="cellphone" class="span12" placeholder="Regular field"></div>
	                    <label class="control-label">Email address:</label>
	                    <div class="controls"><input type="text" name="email" class="span12" placeholder="Regular field"></div>
	                </div>
	                
	                <div class="form-actions align-right">
	                    <button id="submitBtn" type="submit" class="btn btn-primary">Submit</button>
	                    <button type="reset" class="btn">Reset</button>
	                </div>
	
	            </div>
	            
	        </div>
	    </form>
	</div>
</body>
</html>
