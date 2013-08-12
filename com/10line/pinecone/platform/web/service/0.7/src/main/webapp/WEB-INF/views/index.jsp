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
              <li><a href="http://shop104362768.taobao.com" target="_blank">商店</a></li>
              <li><a href="http://e.weibo.com/pinecone201204" target="_blank">微博</a></li>
              <li><a href="signup.html">注册</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
	<div class="jumbotron masthead">
  	  <div class="container">
    	<h1>松果网</h1>
    	<p>便捷、稳定、智能的物联网应用平台，让生活更美好、现代。</p>
    	<p><a href="login.html" class="btn btn-success btn-large">立即登录</a></p>
  	  </div>
	</div>
	<div class="container">
  	  <div class="marketing">
    	<h1>设备接入</h1>
    	<p class="marketing-byline">下载相应设备的SDK进行开发，马上接入你的设备吧!</p>
    	<div class="row-fluid">
      	  <div class="span4">
            <a href="http://www.arduino.cc/" target="_blank"><img class="marketing-img" src="images/arduino.jpg"></a>
            <h2>Arduino</h2>
            <p>Arduino是一款便捷灵活、方便上手的开源电子原型平台，它适用于所有对“互动”有兴趣的朋友们。</p>
            <p><a class="btn btn-default" href="download/pinecone.zip">Download SDK V0.1 &raquo;</a></p>
          </div>
      	  <div class="span4">
            <a href="http://www.raspberrypi.org/" target="_blank"><img class="marketing-img" src="images/raspberry_pi.jpg"></a>
            <h2>Raspberry Pi</h2>
            <p>Raspberry Pi（中文名为“树莓派”）是为学生计算机编程教育而设计，只有信用卡大小的卡片式电脑。</p>
            <p><a class="btn btn-default" href="#">Download SDK V0.1 &raquo;</a></p>
          </div>
          <div class="span4">
            <a href="http://www.zigbee.org/" target="_blank"><img class="marketing-img" src="images/zigbee.jpg"></a>
            <h2>ZigBee</h2>
            <p>ZigBee是基于IEEE802.15.4标准的低功耗个域网协议，用于开发短距离、低功耗的无线通信设备。</p>
            <p><a class="btn btn-default" href="#">Download SDK V0.1 &raquo;</a></p>
          </div>
        </div>
        <hr class="soften">
        <h1>特色介绍</h1>
        <p class="marketing-byline">为喜爱科技的你量身定制，一起来发现它的价值吧！</p>
        <div class="row-fluid">
      	  <div class="span4">
            <img class="marketing-img" src="images/feature.png">
            <h2>使用便捷</h2>
            <p>下载SDK，接入设备，轻松进行远程监控。</p>
          </div>
      	  <div class="span4">
            <img class="marketing-img" src="images/feature.png">
            <h2>稳定高效</h2>
            <p>借助云平台的力量，帮你贴心照顾好设备。</p>
          </div>
          <div class="span4">
            <img class="marketing-img" src="images/feature.png">
            <h2>智能分析</h2>
            <p>通过全面的分析，让你的生活更智能。</p>
          </div>
        </div>
      </div>
      <footer class="footer">
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
      </footer>
    </div>
	<script src="http://cdnjs.bootcss.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://cdnjs.bootcss.com/ajax/libs/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
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
    
    </script>
  </body>
</html>