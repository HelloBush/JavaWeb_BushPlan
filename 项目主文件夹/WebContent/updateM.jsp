<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import = "mybean.UpdateBean" %>
<%@ page import = "mytool.Tool" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
UpdateBean updateBean = (UpdateBean)session.getAttribute("updateBean");
String m = updateBean.getM();
String ms = "<原为"+m+"月目标>";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>草丛计划-调整</title>
<link rel="stylesheet" type="text/css" href="css/update.css">
<!--以下为点击苹果附件-->
<link rel="stylesheet" type="text/css" href="css/apple.css">
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/apple.js"></script>
<%@ include file="common/apple.txt" %>
</head>
<body>


<%@ include file="query.jsp" %>

<div class="wrap_update">		
<h3>调整月目标</h3>
<%
if(updateBean.getBkNews()==""){
%>
<form action="UpdateM" method="post">
月份调整为:<select name="m" >
<option value="1">一月<option value="2">二月
<option value="3">三月<option value="4">四月
<option value="5">五月<option value="6">六月
<option value="7">七月<option value="8">八月
<option value="9">九月<option value="10">十月
<option value="11">十一月<option value="12">十二月</select><span><%=ms%></span><br/>
<span>月目标内容修改为:</span><br/><textarea rows="5" cols="30"  name="plan"><%=updateBean.getPlan()%></textarea><br/>
<input type="submit" value="提交">
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