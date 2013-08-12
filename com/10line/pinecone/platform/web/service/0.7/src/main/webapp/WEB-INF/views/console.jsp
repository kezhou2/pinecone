<%@ page contentType="text/html;charset=UTF-8"%>
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
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">松果网</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li><a href="#">激活设备</a></li>
              <li><a href="#">退出</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
	<header class="jumbotron subhead">
  	  <div class="container">
    	<h1>管理控制台</h1>
    	<p class="lead">欢迎你XXX，开始管理你的设备吧！</p>
      </div>
	</header>
    <div class="container">
  	  <div class="marketing">
    	<h1>设备管理</h1>
    	<p class="marketing-byline">探索未知的智能世界，感知数据的力量!</p>
    	<div class="row-fluid">
      	  <div id="map_canvas"/>
        </div>
      </div>
    </div>
    <footer class="footer">
      <div class="container">
        <p>&copy; 2013 北京松果科技有限公司</p>
        <ul class="footer-links">
          <li><a href="#">关于我们</a></li>
          <li class="muted">·</li>
          <li><a href="#">在线帮助</a></li>
          <li class="muted">·</li>
          <li><a href="#">隐私条款</a></li>
          <li class="muted">·</li>
          <li><a href="#">用户协议</a></li>
        </ul>
        <p><a href="http://www.miitbeian.gov.cn/" target="_blank">京ICP备13022507号</a></p>
      </div>
    </footer>
	<script src="http://cdnjs.bootcss.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
    <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBKkEdoXQxDyl3OKTnMjkvRX-HzOxmsxi8&sensor=false"></script>
    <script src="js/jquery.scrollUp.min.js"></script>
    <script type="text/javascript">
      
    	$(document).ready(function() {
    	
			$.scrollUp({
				scrollName: 'scrollUp', 
	    		topDistance: '300', 
	    		topSpeed: 300, 
	    		animation: 'fade', 
	    		animationInSpeed: 200, 
	    		animationOutSpeed: 200, 
	    		scrollText: '', 
	    		activeOverlay: false 
			});
	
		});
    
      	function initialize() {
        	var map = new google.maps.Map(document.getElementById("map_canvas"), {
          		center: new google.maps.LatLng(39.905841, 116.391596),
          		zoom: 8, mapTypeId: google.maps.MapTypeId.ROADMAP
        	});
        
        	var content = '<div>'+
        		'<p>Bill的客厅</p>'+
        		'<ul class="nav nav-pills">'+
        		'<li><a href="#"><i class="icon-edit"></i> 修改</a></li>'+
        		'<li><a href="#"><i class="icon-remove"></i> 删除</a></li>'+ 
        		'</ul>'+
        		'<table class="table table-hover">'+
        		'<tbody>'+
        		'<tr><td>壁灯</td><td>Off</td><td>'+  
        		'<a href="#"><i class="icon-search"></i> 查询 </a>'+
        		'<a href="#"><i class="icon-wrench"></i> 设置</a>'+
        		'</td></tr>'+
        		'<tr><td>温度</td><td>20°C</td><td>'+ 
        		'<a href="#"><i class="icon-search"></i> 查询 </a>'+
        		'<a href="#"><i class="icon-wrench"></i> 设置</a>'+ 
        		'</td></tr>'+
        		'</tbody>'+
        		'</table>'+
        		'</div>';
      
        	addDevice(map, 39.202820, 116.191596, "Bill的客厅", content);
      	}
      
      	function addDevice(map, lat, lng, title, content) {
   	    	var marker = new google.maps.Marker({
          		map: map, title: title,
          		animation: google.maps.Animation.DROP,
          		position: new google.maps.LatLng(lat, lng)
        	});
        	google.maps.event.addListener(marker, 'click', function() {
          		onClickDevice(map, marker, content);
        	});
      	}
      
      	function onClickDevice(map, marker, content) {
        	var infoWindow = new google.maps.InfoWindow({
    	  		content: content
        	});
        	marker.setClickable(false);
    		infoWindow.open(map, marker);
    		google.maps.event.addListener(infoWindow, "closeclick", function() {
    	  		marker.setClickable(true);
    		});
      	}
      
      	google.maps.event.addDomListener(window, 'load', initialize);
      	google.maps.event.addDomListener(window, 'resize', initialize);
      
    </script>
  </body>
</html>