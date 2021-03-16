<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import="mybean.LoginBean,mybean.RegisterBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>草丛计划-登录</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
<!--以下为点击苹果附件-->
<link rel="stylesheet" type="text/css" href="css/apple.css">
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/apple.js"></script>
<%@ include file="common/apple.txt" %>
</head>
<body>
<%@ include file="common/head.txt" %>

<div id="wrap_login">
	<div id="wrap_form">
<h3>-登录-</h3>
<img src="images/login.png" id="log_pic"/><br/>
<form action="Login" method="post">
用户名:<input type="text" name="user"><br/>
<label id="l2">密码:</label><input type="password" name="password"><br/>
<input type="checkbox"  name="log" value="yes" checked id="input_cookie"><span class="tips">(1小时内免登录)</span>
<div id="but1"><img src="images/button1.jpg"/><input type="submit" value="立即登录"></div>
</form><div id="but2"><img src="images/button1.jpg"/><a href="register.jsp"><button>新用户注册</button></a></div>
<% 
if(request.getAttribute("loginBean")!=null){
	LoginBean mylogBean = (LoginBean)request.getAttribute("loginBean");
%>
<div class="bkNews">登录反馈:<%=mylogBean.getBkNews()%></div>
<% }%>

<% 
if(request.getAttribute("registerBean")!=null){
	RegisterBean myregBean = (RegisterBean)request.getAttribute("registerBean");
%>
<div class="bkNews">
<%=myregBean.getBkNews()%>
<%if(myregBean.getUser()!="") {%>
您的用户名:<%=myregBean.getUser()%>&nbsp;&nbsp;
您的密码:<%=myregBean.getPassword()%><br/>
请牢记并妥善保管。
<% }%>
<% }%>
</div>
</div>
</div>



</body>
</html>