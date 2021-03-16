<%@ page language="java" contentType="text/html;charset=GB2312"
    pageEncoding="GB2312"%>
<%@ page import="mybean.RegisterBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>草丛计划-注册</title>
<link rel="stylesheet" type="text/css" href="css/register.css">
<!--以下为点击苹果附件-->
<link rel="stylesheet" type="text/css" href="css/apple.css">
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/apple.js"></script>
<%@ include file="common/apple.txt" %>
</head>
<body>
<%@ include file="common/head.txt" %>

<div id="wrap_register">

<div id="wrap_form">
<h3>-注册-</h3>
<img src="images/register.png" id="reg_pic"/><br/>
<span class="tips">(带*号必填)</span>
<form action="Register" method="post">
<label id="l1">用户名:</label><input type="text" name="user"  required="required"><span class="must">*</span><br/><span class="tips">(3-12字符以内，包含数字、字母，不区分大小写)</span><br/>
<label id="l2">密码:</label><input type="password" name="password"  required="required"><span class="must">*</span><br/><span class="tips">(6-18字符以内，包含数字、字母，不区分大小写)</span><br/>
<label id="l3">再次输入密码:</label><input type="password" name="password2"  required="required"><span class="must">*</span><br/>
<label id="l4">邮箱:</label><input type="email" name="email"><br/>
<div id="but1"><img src="images/button1.jpg"/><input type="submit" value="立即注册"></div>
</form><div id="but2"><img src="images/button1.jpg"/><a href="login.jsp"><button>已有账号登录</button></a></div>
<% 
if(request.getAttribute("registerBean")!=null){
	RegisterBean mybean = (RegisterBean)request.getAttribute("registerBean");
%>
<p id="bkNews">注册反馈:<%=mybean.getBkNews()%>
<%}
%>
</div>
	</div>


</body>
</html>