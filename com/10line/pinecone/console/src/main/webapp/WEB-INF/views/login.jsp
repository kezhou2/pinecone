<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title><fmt:message key="application.title" /></title>
<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="img/favicon.ico" ce_href="img/favicon.ico" type="image/x-icon">
<link href="css/main.css" rel="stylesheet" type="text/css" />
<!--[if IE 8]><link href="css/ie8.css" rel="stylesheet" type="text/css" /><![endif]-->
<!--[if IE 9]><link href="css/ie9.css" rel="stylesheet" type="text/css" /><![endif]-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>

<script type="text/javascript" src="js/plugins/forms/jquery.uniform.min.js"></script>

<script type="text/javascript" src="js/files/bootstrap.min.js"></script>

<script type="text/javascript" src="js/files/login.js"></script>

<script type="text/javascript">
window.onload = function(){
	var error = <%=request.getParameter("error")%>;
	if(error){
		//$("#name_input").before(
		//	"<div class='control-group info'>"+
		//		"<span class='help-block'>Welcome!</span>"+
		//	"</div>"
		//);
		$("#name_input").before(
			"<div class='control-group warning'>"+
				"<span class='help-block'>Authentication failed, please enter again.</span>"+
			"</div>"
		);
	}
	
	var isregister = <%=(String)request.getAttribute("isregister")%>;
	if(isregister){
		$("#name_input").before(
			"<div class='control-group warning'>"+
				"<span class='help-block'>Registry successed, congratulation!</span>"+
			"</div>"
		);
	}
}
</script>

</head>

<body class="no-background">

	<!-- Fixed top -->
	<div id="top">
		<div class="fixed">
			<a href="login.html" title="" class="logo"><img src="img/logo.png" alt="" /></a>
		</div>
	</div>
	<!-- /fixed top -->


    <!-- Login block -->
    <div class="login">
        <div class="navbar">
            <div class="navbar-inner">
                <h6><i class="icon-user"></i>Login page</h6>
                <div class="nav pull-right">
                    <a href="#" class="dropdown-toggle navbar-icon" data-toggle="dropdown"><i class="icon-cog"></i></a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="register.html"><i class="icon-plus"></i>Register</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="well">
            <form action="${pageContext.request.contextPath}/j_spring_security_check" class="row-fluid" method="post">
                <div id="name_input" class="control-group">
                    <label class="control-label">Username</label>
                    <div class="controls"><input class="span12" type="text" name="j_username" placeholder="username" /></div>
                </div>
                
                <div id="password_input" class="control-group">
                    <label class="control-label">Password:</label>
                    <div class="controls"><input class="span12" type="password" name="j_password" placeholder="password" /></div>
                </div>

                <div id="remember_input" class="control-group">
                    <div class="controls"><label class="checkbox inline"><input type="checkbox" name="_spring_security_remember_me" class="styled" value="" checked="checked">Remember me</label></div>
                </div>

                <div class="login-btn">
                	<input name="submit" type="submit" value="Log me in" class="btn btn-danger btn-block" />
                	<div class="controls"><a href="register.html">Register to be user→</a></div>
                </div>
            </form>
        </div>
    </div>
    <!-- /login block -->


	<!-- Footer -->
	<div id="footer">
		<div class="copyrights">&copy;  Pinecone Tech.</div>
		<ul class="footer-links">
			<li><a href="" title=""><i class="icon-cogs"></i>Contact admin</a></li>
			<li><a href="" title=""><i class="icon-screenshot"></i>Home page</a></li>
		</ul>
	</div>
	<!-- /footer -->

</body>
</html>
