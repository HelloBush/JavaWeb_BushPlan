<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import="mybean.LoginBean,mybean.RegisterBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>�ݴԼƻ�-��¼</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
<!--����Ϊ���ƻ������-->
<link rel="stylesheet" type="text/css" href="css/apple.css">
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/apple.js"></script>
<%@ include file="common/apple.txt" %>
</head>
<body>
<%@ include file="common/head.txt" %>

<div id="wrap_login">
	<div id="wrap_form">
<h3>-��¼-</h3>
<img src="images/login.png" id="log_pic"/><br/>
<form action="Login" method="post">
�û���:<input type="text" name="user"><br/>
<label id="l2">����:</label><input type="password" name="password"><br/>
<input type="checkbox"  name="log" value="yes" checked id="input_cookie"><span class="tips">(1Сʱ�����¼)</span>
<div id="but1"><img src="images/button1.jpg"/><input type="submit" value="������¼"></div>
</form><div id="but2"><img src="images/button1.jpg"/><a href="register.jsp"><button>���û�ע��</button></a></div>
<% 
if(request.getAttribute("loginBean")!=null){
	LoginBean mylogBean = (LoginBean)request.getAttribute("loginBean");
%>
<div class="bkNews">��¼����:<%=mylogBean.getBkNews()%></div>
<% }%>

<% 
if(request.getAttribute("registerBean")!=null){
	RegisterBean myregBean = (RegisterBean)request.getAttribute("registerBean");
%>
<div class="bkNews">
<%=myregBean.getBkNews()%>
<%if(myregBean.getUser()!="") {%>
�����û���:<%=myregBean.getUser()%>&nbsp;&nbsp;
��������:<%=myregBean.getPassword()%><br/>
���μǲ����Ʊ��ܡ�
<% }%>
<% }%>
</div>
</div>
</div>



</body>
</html>