<%@ page language="java" contentType="text/html;charset=GB2312"
    pageEncoding="GB2312"%>
<%@ page import="mybean.RegisterBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>�ݴԼƻ�-ע��</title>
<link rel="stylesheet" type="text/css" href="css/register.css">
<!--����Ϊ���ƻ������-->
<link rel="stylesheet" type="text/css" href="css/apple.css">
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/apple.js"></script>
<%@ include file="common/apple.txt" %>
</head>
<body>
<%@ include file="common/head.txt" %>

<div id="wrap_register">

<div id="wrap_form">
<h3>-ע��-</h3>
<img src="images/register.png" id="reg_pic"/><br/>
<span class="tips">(��*�ű���)</span>
<form action="Register" method="post">
<label id="l1">�û���:</label><input type="text" name="user"  required="required"><span class="must">*</span><br/><span class="tips">(3-12�ַ����ڣ��������֡���ĸ�������ִ�Сд)</span><br/>
<label id="l2">����:</label><input type="password" name="password"  required="required"><span class="must">*</span><br/><span class="tips">(6-18�ַ����ڣ��������֡���ĸ�������ִ�Сд)</span><br/>
<label id="l3">�ٴ���������:</label><input type="password" name="password2"  required="required"><span class="must">*</span><br/>
<label id="l4">����:</label><input type="email" name="email"><br/>
<div id="but1"><img src="images/button1.jpg"/><input type="submit" value="����ע��"></div>
</form><div id="but2"><img src="images/button1.jpg"/><a href="login.jsp"><button>�����˺ŵ�¼</button></a></div>
<% 
if(request.getAttribute("registerBean")!=null){
	RegisterBean mybean = (RegisterBean)request.getAttribute("registerBean");
%>
<p id="bkNews">ע�ᷴ��:<%=mybean.getBkNews()%>
<%}
%>
</div>
	</div>


</body>
</html>