<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import = "mybean.UpdateBean" %>
<%@ page import = "mytool.Tool" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
UpdateBean updateBean = (UpdateBean)session.getAttribute("updateBean");
String date =request.getParameter("d1");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>�ݴԼƻ�-����</title>
<link rel="stylesheet" type="text/css" href="css/update.css">

<link rel="stylesheet" type="text/css" href="css/apple.css">
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/apple.js"></script>
<%@ include file="common/apple.txt" %>
</head>

<body>

<%@ include file="query.jsp" %>

<div class="wrap_update">		
<h3>������Ŀ��</h3>
<%
if(updateBean.getBkNews()==""){
%>
<form action="UpdateD" method="post">
<span>Ŀ�����ڵ���Ϊ:</span><input type="date" value="<%=date%>" name="d1"><br/>
<span>Ŀ�������޸�Ϊ:</span><br/><textarea rows="5" cols="30"  name="plan"><%=updateBean.getPlan()%></textarea><br/>
<input type="submit" value="�ύ">
</form>
<!--wrap_update-->
</div>
<%} else{%>
<div class="update_bkNews">
<img src="images/apple_green.png"/>
<br/><span><%=updateBean.getBkNews()%></span></div>
<% }%>

</body>
</html>