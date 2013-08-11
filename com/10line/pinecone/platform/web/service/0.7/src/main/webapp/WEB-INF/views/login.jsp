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
		<form id="login" class="form-horizontal" method="post" action="#">
		  <legend>登录</legend>
		  <div class="control-group">
			<div class="controls">
			  <div class="input-prepend">
				<span class="add-on"><i class="icon-user"></i></span> 
				<input type="text" class="input-xlarge" id="username" name="username" placeholder="用户名">
			  </div>
			</div>
		  </div>
		  <div class="control-group">
			<div class="controls">
			  <div class="input-prepend">
				<span class="add-on"><i class="icon-lock"></i></span> 
				<input type="Password" id="password" class="input-xlarge" name="password" placeholder="密码">
			  </div>
			</div>
		  </div>
		  <div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
			  <button type="submit" class="btn btn-success">登录</button>
			  <a type="button" href="signup.html" class="btn btn-success">注册</a>
			  <a type="button" href="index.html" class="btn btn-success">返回</a>
			</div>
		  </div>
		</form>
	  </div>
	</div>
	<script src="http://cdnjs.bootcss.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
	<script src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>
	<script type="text/javascript">
		
		$(document).ready(function() {
		
			$.validator.addMethod("alphanumeric", function(value, element) {
		        return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
			}); 
			
			$("#login").validate({
				rules : {
					username : {
						required : true,
						alphanumeric : true
					},
					password : {
						required : true,
						minlength : 8
					}
				},
				messages : {
		            username : {
		            	required : "不能为空",
		            	alphanumeric: "只能为字母或者数字"
		            },
					password : {
						required : "不能为空",
						minlength : "不能少于8个字符"
					}
		        },
				errorClass : "help-inline"
			});
		
		});
	
	</script>
  </body>
</html>
