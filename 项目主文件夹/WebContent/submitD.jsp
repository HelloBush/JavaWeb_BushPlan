<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import = "mybean.SubmitBean" %>
<%@ page import = "mytool.Tool" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
SubmitBean submitBean = (SubmitBean)session.getAttribute("submitBean");
String date = request.getParameter("d1");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>草丛计划-提交</title>
<link rel="stylesheet" type="text/css" href="css/submit.css">
<!--以下为点击苹果附件-->
<link rel="stylesheet" type="text/css" href="css/apple.css">
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/apple.js"></script>
<%@ include file="common/apple.txt" %>
</head>


<body>
<%@ include file="query.jsp" %>

<div class="wrap_submit">		
<h3>提交日目标</h3>
<%
if(submitBean.getBkNews()==""){
%>
<form action="SubmitD" method="post">
<span>目标日期:</span><input type="text" value="<%=submitBean.getD1()%>" disabled="disabled" name="d1"><br/>
<span>目标内容:</span><br/><textarea rows="5" cols="30" disabled="disabled" name="plan"><%=submitBean.getPlan()%></textarea><br/>
<span>目标完成情况:</span><br/><textarea rows="5" cols="30" name="sum" required="required" ></textarea><br/>
<span>日目标评分:</span><input type="number" min="0" max="10" name="point" required="required" placeholder="(0-10)"><br/>
<input type="submit" value="提交">
</form>
<!--wrap_submit-->
</div>
<%} else{%>
<div class="submit_bkNews">
<img src="images/apple.png"/>
<br/><span><%=submitBean.getBkNews()%></span></div>
<% }%>


</body>
</html>