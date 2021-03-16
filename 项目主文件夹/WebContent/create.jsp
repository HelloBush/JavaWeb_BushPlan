<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import="mybean.CreateBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>草丛计划-创建</title>
<link rel="stylesheet" type="text/css" href="css/create.css">

<!--以下为点击苹果附件-->
<link rel="stylesheet" type="text/css" href="css/apple.css">
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/apple.js"></script>
<%@ include file="common/apple.txt" %>
</head>

<body>
<%@ include file="common/head.txt" %>

<div id="wrap_create">
	<div class="choose">
		<div class="chose">
		<img src="images/green.png" class="hand"/>
		<a href="create.jsp?aim=d"><img src="images/cd.png" class="but_c"/></a>
		</div>		
		<div class="chose">
		<img src="images/yellow.png" class="hand"/>
		<a href="create.jsp?aim=m"><img src="images/cm.png" class="but_c"/></a>
		</div>		
		<div class="chose">
		<img src="images/brown.png" class="hand"/>
		<a href="create.jsp?aim=p"><img src="images/cp.png" class="but_c"/></a>
		</div>
	</div>
	<div id="wrap_form">
	<%
	if(request.getParameter("aim")!=null){
		String aim = request.getParameter("aim");
	%>
	<%if("d".equals(aim)){ %>
	<div id="wrap_form_d">
	<h3>-创建日目标-</h3>
<form action="Create" method="post">
目标日期:<input type="date" name="d1" id="d_i1"/><br/>
请描述你此日的目标:<br/><textarea id="d_i2" rows="10" cols="30" name="plan" placeholder="例如:
#6:30起床
1/记忆30个单词
2/完成数学练习
3/下午跑步
4/晚上听半小时听力
#23:00前睡觉"></textarea><br/>
<span class="tips">目标不要轻易更改，请科学合理建立。<br/>目标内容框右下角可调整大小。</span>
<input type="hidden" value="d" name="fid" required="required"><br/>
<input type="submit" value="我决定了" required="required" id="d_i3"><br/>
</form>
	</div>
	<% }else if("m".equals(aim)){%>
	<div id="wrap_form_m">
<h3>-创建月目标-</h3>
<form action="Create" method="post">
请选择月份:<select name="m" id="m_i1">
<option value="1">一月</option><option value="2">二月</option>
<option value="3">三月</option><option value="4">四月</option>
<option value="5">五月</option><option value="6">六月</option>
<option value="7">七月</option><option value="8">八月</option>
<option value="9">九月</option><option value="10">十月</option>
<option value="11">十一月</option><option value="12">十二月</option></select><br/>
请描述你的月目标:<br/><textarea rows="10" cols="30" name="plan" placeholder="月目标建议长远一点，可适当说明完成的标准，定性或者定量。" id="m_i2"></textarea><br/>
<span class="tips">目标不要轻易更改，请科学合理建立。<br/>目标内容框右下角可调整大小。</span>
<input type="hidden" value="m" name="fid" required="required"><br/>
<input type="submit" value="我决定了" required="required" id="m_i3"><br/>
</form>
	</div>
	<% }else if("p".equals(aim)){%>
	<div id="wrap_form_p">
<h3>-创建阶段目标-</h3>
<form action="Create" method="post">
请选择起始日:<input type="date" name="d1" id="p_i1"/><br/>
请选择截止日:<input type="date" name="d2" id="p_i2"/><br/>
请描述你的目标:<br/><textarea id="p_i4" rows="10" cols="30" name="plan" placeholder="阶段目标可长可短，自定义期限。"></textarea><br/>
<span class="tips">目标不要轻易更改，请科学合理建立。<br/>目标内容框右下角可调整大小。</span>
<input type="hidden" value="p" name="fid" required="required"><br/>
<input type="submit" value="我决定了" required="required" id="p_i3"><br/>
</form>
	</div>
	<%}%>
	<% }%>
		</div>
<% if(request.getAttribute("createBean")!=null){
	CreateBean createBean = (CreateBean)request.getAttribute("createBean");
%>
	<div id="wrap_bkNews">
		<h3>-创建反馈-</h3>
		<p class="bkNews"><%=createBean.getBkNews()%></p>
		<div><textarea rows="10" cols="30" disabled="false" class="plan"><%=createBean.getPlan()%></textarea></div>
	</div>
	<%} %>
</div>

</body>
</html>