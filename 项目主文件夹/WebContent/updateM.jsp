<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import = "mybean.UpdateBean" %>
<%@ page import = "mytool.Tool" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
UpdateBean updateBean = (UpdateBean)session.getAttribute("updateBean");
String m = updateBean.getM();
String ms = "<ԭΪ"+m+"��Ŀ��>";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>�ݴԼƻ�-����</title>
<link rel="stylesheet" type="text/css" href="css/update.css">
<!--����Ϊ���ƻ������-->
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
<form action="UpdateM" method="post">
�·ݵ���Ϊ:<select name="m" >
<option value="1">һ��<option value="2">����
<option value="3">����<option value="4">����
<option value="5">����<option value="6">����
<option value="7">����<option value="8">����
<option value="9">����<option value="10">ʮ��
<option value="11">ʮһ��<option value="12">ʮ����</select><span><%=ms%></span><br/>
<span>��Ŀ�������޸�Ϊ:</span><br/><textarea rows="5" cols="30"  name="plan"><%=updateBean.getPlan()%></textarea><br/>
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