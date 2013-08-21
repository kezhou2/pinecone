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
		<form id="login" class="form-horizontal" method="post" action="/j_spring_security_check">
		  <legend>登录</legend>
		  <div id="username" class="control-group">
			<div class="controls">
			  <div class="input-prepend">
				<span class="add-on"><i class="icon-user"></i></span> 
				<input type="text" class="input-xlarge" id="j_username" name="j_username" placeholder="用户名">
			  </div>
			</div>
		  </div>
		  <div class="control-group">
			<div class="controls">
			  <div class="input-prepend">
				<span class="add-on"><i class="icon-lock"></i></span> 
				<input type="password" class="input-xlarge" id="j_password" name="j_password" placeholder="密码">
			  </div>
			</div>
		  </div>
		  <div class="control-group">
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
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/additional-methods.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/localization/messages_zh.js"></script>
	<script src="js/localization/messages_zh.js"></script>
	<script src="js/jquery.html5storage.min.js"></script>
	<script type="text/javascript">
		
		$(document).ready(function() {
			
			if(<%=request.getParameter("error")%>) {
				$("#username").before(
					"<div class='control-group'>"+
						"<div class='controls'>"+
							"<strong>登录失败，请重新输入!</strong>"+
						"</div>"+
					"</div>"
				);
			}
			
			$("#login").validate({
				rules : {
					j_username : {
						required : true,
						alphanumeric : true
					},
					j_password : {
						required : true
					}
				},
				submitHandler : function(form) {  
					$.sessionStorage.setItem("username", $("#j_username").val());
					$.sessionStorage.setItem("password", $("#j_password").val());
		            form.submit();
		        }
			});
		
		});
	
	</script>
  </body>
</html>
