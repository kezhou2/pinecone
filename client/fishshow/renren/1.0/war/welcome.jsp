<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--
第一步，放置跨域文件，http://wiki.dev.renren.com/wiki/%E6%94%BE%E7%BD%AE%E8%B7%A8%E5%9F%9F%E6%96%87%E4%BB%B6

第二部步，在页面开头添加DOCTYPE声明和namespace属性设置（可选）
如果你在页面中使用了EXNML，则为了使浏览器正确识别EXNML，需要页面开头以下面的代码开头
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:xn="http://www.renren.com/2009/xnml">



<head>
</head>

<body>
<%--
第三步，调用父框架关闭弹层函数
--%>
<script type="text/javascript">
this.parent.XN.Connect.closeOAuthDialog(true);

</script>

</body>
</html>

