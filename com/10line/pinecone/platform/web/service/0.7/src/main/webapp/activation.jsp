<!DOCTYPE html>
<html lang="zh">
  <head>
    <title>松果网</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
	<link href="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="css/main.css" rel="stylesheet">
  </head>
  <body>
	<div class="container">
	  <div class="well">
		<form id="activation" class="form-horizontal" method="post" action="#">
		  <legend>激活设备</legend>
		  <div class="control-group">
			<div class="controls">
			  <div class="input-prepend">
				<span class="add-on"><i class="icon-qrcode"></i></span> 
				<input type="text" id="devicecode" class="input-xlarge" name="devicecode" placeholder="设备编码">
			  </div>
			</div>
		  </div>
		  <div class="control-group">
			<div class="controls">
			  <div class="input-prepend">
				<span class="add-on"><i class="icon-pencil"></i></span> 
				<input type="text" id="devicename" class="input-xlarge" name="devicename" placeholder="设备名称">
			  </div>
			</div>
		  </div>	
		  <div class="control-group">
			<div class="controls">
			  <button type="submit" class="btn btn-success">确定</button>
			  <a type="button" href="console.html" class="btn btn-success">取消</a>
			</div>
		  </div>
		</form>
	  </div>
	</div>
	<script src="http://cdnjs.bootcss.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>  
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/localization/messages_zh.js"></script>
	<script src="js/jquery.html5storage.min.js"></script>
	<script type="text/javascript">
		
		$(document).ready(function() {
		
			$("#activation").validate({
				rules : {
					devicecode : {
						required : true,
						remote : {
							url : "/rest/m/device/search/activation/codes",
							username : $.sessionStorage.getItem("username"),
				    		password : $.sessionStorage.getItem("password")
						}
					},
					devicename : {
						required : true
					}
				},
				submitHandler : function() {
					getDeviceId($("#devicecode").val());
		        }
			});
		
		});
		
		function getDeviceId(code) {
    		$.ajax({
		    	type : "GET",
		    	url : "/rest/device/search/codes?code=" + code,
		    	dataType : "json",
		    	username : $.sessionStorage.getItem("username"),
		    	password : $.sessionStorage.getItem("password"),
		    	success : function(data) {
		    		var href = data.results[0]._links[2].href;
		    		activateDevice(href.substring(href.lastIndexOf("/") + 1));
		    	}
			});
    	}
		
		function activateDevice(id) {
			$.ajax({
		    	type : "POST",
		    	username : $.sessionStorage.getItem("username"),
	    		password : $.sessionStorage.getItem("password"),
		    	url : "/rest/device/" + id + "/user",
		    	contentType : "text/uri-list; charset=utf-8",
		    	data : "http://" + <%=request.getServerName()%> + "/rest/user/" + $.sessionStorage.getItem("userId"),
		    	success : function() {
		    		window.location = "console.html";
		    	}
			});
		}
	
	</script>
  </body>
</html>
