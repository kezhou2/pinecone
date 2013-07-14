<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page  import="java.util.*" %>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title><fmt:message key="application.title" /></title>
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
	
	//get data from form
	var deviceId = ${deviceId};
	var variableId = ${variableId};
	var conditionType = $("select[name='conditionType']").val();
	var condition = $("select[name='condition']").val();
	var variablevalue = $("input[name='variablevalue']").val();
	var clog = $("#clog").val();
	var cpage = $("cpage").val();
	var csound = $("csound").val();
	var csms = $("csms").val();
	var cemail = $("cemail").val();
	var cellphone = $("input[name='cellphone']").val();
	var email = $("input[name='email']").val();
	
	//submit to servlet
	$.ajax({
 		url:'setalerm.html', 
 		type: 'post',
 		data: {
			deviceId:deviceId,
			variableId:variableId,
			conditionType:conditionType,
			condition:condition,
			variablevalue:variablevalue,
			clog:clog,
			cpage:cpage,
			csound:csound,
			csms:csms,
			cemail:cemail,
			cellphone:cellphone,
			email:email
 		}, 
		timeout: 5000,
 		error: function(XMLHttpRequest, textStatus, errorThrown){
 			$.jGrowl(textStatus, { sticky: true, theme: 'growl-error', life:1000});
 		}, 
 		success: function(result){
 			if(result == 'true'){
 				parent.$.jGrowl('<fmt:message key="alermsetting.success.add" />', { sticky: true, theme: 'growl-success', life:1000});
 				window.parent.setAlermStr(variableId,(condition+variablevalue));
 				parent.$.fancybox.close();
 			}else{
 				$.jGrowl('<fmt:message key="alermsetting.failure.add" />', { sticky: true, theme: 'growl-error', life:1000});
 				parent.$.fancybox.close();
 			}
 		} 
 	});
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
	            <div class="navbar"><div class="navbar-inner"><h6><fmt:message key="alermsetting.variable.setting" /></h6></div></div>
	
	            <div class="well">
	                
	                <div class="control-group">
	                    <label class="control-label"><fmt:message key="alermsetting.condition.type" /></label>
	                    <div class="controls">
	                        <select id="conditionType" name="conditionType" class="styled" style="opacity: 0;">
	                            <option value="numeric"><fmt:message key="alermsetting.condition.type.numeric" /></option>
	                            <option value="string"><fmt:message key="alermsetting.condition.type.string" /></option>
	                        </select>
	                    </div>
	                    <label class="control-label"><fmt:message key="alermsetting.condition" /></label>
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
	                    <label class="control-label"><fmt:message key="alermsetting.variable.value" /></label>
	                    <div class="controls"><input type="text" name="variablevalue" class="span12" placeholder="Variable value"></div>
	                </div>
	                
	                <div class="control-group">
	                    <label class="control-label"><fmt:message key="alermsetting.alerm.type" /></label>
	                    <div class="controls">
	                    	<label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox1"><span><input type="checkbox" id="clog" value="log" name="clog" class="styled" style="opacity: 0;"></span></div><fmt:message key="alermsetting.log" /></label>
	                    	<label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox1"><span><input type="checkbox" id="cpage" value="page" name="cpage" class="styled" style="opacity: 0;"></span></div><fmt:message key="alermsetting.page" /></label>
	                        <label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox2"><span><input type="checkbox" id="csound" value="sound" name="csound" class="styled" style="opacity: 0;"></span></div><fmt:message key="alermsetting.sound" /></label>
	                        <label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox3"><span><input type="checkbox" id="csms" value="sms" name="csms" class="styled" style="opacity: 0;"></span></div><fmt:message key="alermsetting.sms" /></label>
	                        <label class="checkbox inline"><div class="checker" id="uniform-inlineCheckbox4"><span><input type="checkbox" id="cemail" value="email" name="cemail" class="styled" style="opacity: 0;"></span></div><fmt:message key="alermsetting.email" /></label>
	                    </div>
	                    <label class="control-label"><fmt:message key="alermsetting.cellphone" /></label>
	                    <div class="controls"><input type="text" name="cellphone" class="span12" placeholder="Regular field"></div>
	                    <label class="control-label"><fmt:message key="alermsetting.email.address" /></label>
	                    <div class="controls"><input type="text" name="email" class="span12" placeholder="Regular field"></div>
	                </div>
	                
	                <div class="form-actions align-right">
	                    <button id="submitBtn" type="submit" class="btn btn-primary"><fmt:message key="alermsetting.submit" /></button>
	                    <button type="reset" class="btn"><fmt:message key="alermsetting.reset" /></button>
	                </div>
	
	            </div>
	            
	        </div>
	    </form>
	</div>
</body>
</html>
