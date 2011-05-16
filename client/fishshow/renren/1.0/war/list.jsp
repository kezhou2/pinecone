<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:xn="http://www.renren.com/2009/xnml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="http://static.connect.renren.com/js/v1.0/FeatureLoader.jsp"></script>
<title>iframe页面1</title>
<link   href="/demoapp/css/all.css" media="all" type="text/css" rel="stylesheet" />	
</head>

<body>
<%=request.getParameter("xn_sig_session_key") %>
<div>
    <div class="xnml-header" id="xnmlHeader">
	<div class="xnml-logo"><font color="white" style="font-size:30px;margin-left:8px;margin-top:5px;font-weight:bold;">demo app</font></div>
	   <div class="xnml-intro">
	      <p>这是一个demo app，在这个app中主要实现了授权，邀请，iframe自定义高度和发送新鲜事等app常用的功能模块。</p>
	   </div>
	</div>
    </div>
    <div id="xnmlShellBody">
	<div class="title1">
	    <p style="font-size:23px"><b>示例列表</b></p>
	</div>
	<div style="border: 1px solid rgb(255, 221, 136); padding: 5px; background-color: rgb(255, 238, 187); font-family: Monaco,monospace; line-height: 1.3em;">
		<a href="http://pinecone.renren.10line.cc/LocalDemo/invite/index" >邀请好友</a>：app邀请实现的一个新特性。将好友控件显示在一个弹层中，点击发送请求后浏览器不再刷新页面而是直接关闭弹层。<br/>
	</div><br/>
	<div style="border: 1px solid rgb(255, 221, 136); padding: 5px; background-color: rgb(255, 238, 187); font-family: Monaco,monospace; line-height: 1.3em;">
		<a href="http://pinecone.renren.10line.cc/LocalDemo/iframe/index" >iframe自定义高度</a>：iframe高度调整接口。通过此接口可以调整iframe_canvas的高度。<br/>
	</div><br/>
	<div style="border: 1px solid rgb(255, 221, 136); padding: 5px; background-color: rgb(255, 238, 187); font-family: Monaco,monospace; line-height: 1.3em;">
		<a href="http://pinecone.renren.10line.cc/LocalDemo/feed/index" >自定义新鲜事</a>：新鲜事接口。调用此接口可以像用户的好友发送新鲜事。
	</div>
    </div>
</div>
</body>
</html>
