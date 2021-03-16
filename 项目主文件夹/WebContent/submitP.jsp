<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import = "mybean.SubmitBean" %>
<%@ page import = "mytool.Tool" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
SubmitBean submitBean = (SubmitBean)session.getAttribute("submitBean");
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
<h3>提交阶段目标</h3>
<%
if(submitBean.getBkNews()==""){
%>
<form action="SubmitP" method="post">
<span>阶段目标序号:</span><input type="text" value="<%=submitBean.getP_num()%>" disabled="disabled" name="p_num"><br/>
<span>阶段目标内容:</span><br/><textarea rows="5" cols="30" disabled="disabled" name="plan"><%=submitBean.getPlan()%></textarea><br/>
<span>阶段目标完成情况:</span><br/><textarea rows="5" cols="30" name="sum" required="required" ></textarea><br/>
<span>目标评分:</span><input type="number" min="0" max="100" name="point" required="required" placeholder="(0-100)"><br/>
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