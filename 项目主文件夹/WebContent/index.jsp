<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import="mybean.LoginBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>草丛计划-首页</title>
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/AppleAndBug.js"></script>
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/AppleAndBug.css">

</head>
<body>
<%@ include file="common/head.txt" %>

<div id="wrap_welcome">
	<div id="wrap_screen">
	<div id="wrap_appleandbug">
<div id="app"><img  src="images/app.png"/><span id="app_text"></span></div>
<div id="bug"><img  id="bug_img" src="images/bug.png"/><span id="bug_text"></span></div>		
	</div>
	<div id="wrap_poem">
<!--	marquee的属性设置需要在标签内设置-->
		<br/><br/><marquee id="d1" direction="right" onmouseover="this.stop()" onmouseout="this.start()" scrolldelay="400" >千磨万击还坚劲，任尔东西南北风。</marquee>
		<br/><br/><marquee id="d2" direction="right" onmouseover="this.stop()" onmouseout="this.start()" scrolldelay="200" >宝剑锋从磨砺出，梅花香自苦寒来。</marquee>
		<br/><br/><marquee id="d3" direction="right" onmouseover="this.stop()" onmouseout="this.start()" scrolldelay="300" >穷且益坚，不坠青云之志。</marquee>
		<br/><br/><marquee id="d4" direction="right" onmouseover="this.stop()" onmouseout="this.start()" scrolldelay="150" >沉舟侧畔千帆过，病树前头万木春。</marquee>
		<br/><br/><marquee id="d5" direction="right" onmouseover="this.stop()" onmouseout="this.start()" scrolldelay="330" >路漫漫其修远兮，吾将上下而求索。</marquee>
		<br/><br/><marquee id="d6" direction="right" onmouseover="this.stop()" onmouseout="this.start()" scrolldelay="250" >博观而约取，厚积而薄发。</marquee>
	</div>
	<%if(request.getAttribute("loginBean")!=null){
		LoginBean loginBean = (LoginBean)request.getAttribute("loginBean");
		%>
		<div id="log_bkNews"><%=loginBean.getBkNews()%></div>
		<%} %>
	</div>

</div>
</body>
</html>