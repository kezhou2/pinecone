<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
  <head>
    <title>松果网</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
	<link href="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap-responsive.min.css" rel="stylesheet">
	<script src="http://cdnjs.bootcss.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>  
	<script src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>
	<link href="css/main.css" rel="stylesheet">
  </head>
  <body>
	<div class="container">
	  <div class="well">
		<form id="signup" class="form-horizontal" method="post" action="registeruser.html">
		  <legend>注册</legend>
		  <div id='name_input' class="control-group">
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
				<span class="add-on"><i class="icon-envelope"></i></span> 
				<input type="text" class="input-xlarge" id="email" name="email" placeholder="电子邮件">
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
			<div class="controls">
			  <div class="input-prepend">
				<span class="add-on"><i class="icon-lock"></i></span> 
				<input type="Password" id="conpassword" class="input-xlarge" name="conpassword" placeholder="确认密码">
			  </div>
			</div>
		  </div>
		  <div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
			  <button type="submit" class="btn btn-success">提交</button>
			  <a type="button" href="index.html" class="btn btn-success">返回</a>
			</div>
		  </div>
		</form>
	  </div>
	</div>
	
	<script type="text/javascript">
		
		$(document).ready(function() {
			
			$.validator.addMethod("alphanumeric", function(value, element) {
		        return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
			}); 
		
			$("#signup").validate({

				rules : {
					username : {
						required : true,
						alphanumeric : true,
						remote: {
	    		        	url: "validatename.html",      //url地址
	    		        	type: "post",           //发送方式
	    		        	dataType: "json",       //数据格式     
	    		        	data: {                 //要传递的数据
	    		        		username: function() {
	    		        			return $("#username").val();
	    		        		}
							}
	    		        }
					},
					email : {
						required : true,
						email : true,
						remote: {
	    		        	url: "validateemail.html",      //url地址
	    		        	type: "post",           //发送方式
	    		        	dataType: "json",       //数据格式     
	    		        	data: {                 //要传递的数据
	    		        		username: function() {
	    		        			alert("");
	    		        			return $("#email").val();
	    		        		}
							}
	    		        }
					},
					password : {
						required : true,
						minlength : 8
					},
					conpassword : {
						required : true,
						equalTo : "#password"
					}
				},
				messages : {
		            username : {
		            	required : "不能为空",
		            	alphanumeric: "只能为字母或者数字",
		            	remote:"用户名已经被注册"
		            },
					email : {
						required : "不能为空",
						email : "不是合法电子邮件格式",
						remote:"邮箱已经被占用"
					},
					password : {
						required : "不能为空",
						minlength : "不能少于8个字符"
					},
					conpassword : {
						required : "不能为空",
						equalTo : "请输入相同的密码"
					}
		        },
				errorClass : "help-inline"
			});
		
		});
	
	</script>
  </body>
</html>
