<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import="mybean.CreateBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>�ݴԼƻ�-����</title>
<link rel="stylesheet" type="text/css" href="css/create.css">

<!--����Ϊ���ƻ������-->
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
	<h3>-������Ŀ��-</h3>
<form action="Create" method="post">
Ŀ������:<input type="date" name="d1" id="d_i1"/><br/>
����������յ�Ŀ��:<br/><textarea id="d_i2" rows="10" cols="30" name="plan" placeholder="����:
#6:30��
1/����30������
2/�����ѧ��ϰ
3/�����ܲ�
4/��������Сʱ����
#23:00ǰ˯��"></textarea><br/>
<span class="tips">Ŀ�겻Ҫ���׸��ģ����ѧ��������<br/>Ŀ�����ݿ����½ǿɵ�����С��</span>
<input type="hidden" value="d" name="fid" required="required"><br/>
<input type="submit" value="�Ҿ�����" required="required" id="d_i3"><br/>
</form>
	</div>
	<% }else if("m".equals(aim)){%>
	<div id="wrap_form_m">
<h3>-������Ŀ��-</h3>
<form action="Create" method="post">
��ѡ���·�:<select name="m" id="m_i1">
<option value="1">һ��</option><option value="2">����</option>
<option value="3">����</option><option value="4">����</option>
<option value="5">����</option><option value="6">����</option>
<option value="7">����</option><option value="8">����</option>
<option value="9">����</option><option value="10">ʮ��</option>
<option value="11">ʮһ��</option><option value="12">ʮ����</option></select><br/>
�����������Ŀ��:<br/><textarea rows="10" cols="30" name="plan" placeholder="��Ŀ�꽨�鳤Զһ�㣬���ʵ�˵����ɵı�׼�����Ի��߶�����" id="m_i2"></textarea><br/>
<span class="tips">Ŀ�겻Ҫ���׸��ģ����ѧ��������<br/>Ŀ�����ݿ����½ǿɵ�����С��</span>
<input type="hidden" value="m" name="fid" required="required"><br/>
<input type="submit" value="�Ҿ�����" required="required" id="m_i3"><br/>
</form>
	</div>
	<% }else if("p".equals(aim)){%>
	<div id="wrap_form_p">
<h3>-�����׶�Ŀ��-</h3>
<form action="Create" method="post">
��ѡ����ʼ��:<input type="date" name="d1" id="p_i1"/><br/>
��ѡ���ֹ��:<input type="date" name="d2" id="p_i2"/><br/>
���������Ŀ��:<br/><textarea id="p_i4" rows="10" cols="30" name="plan" placeholder="�׶�Ŀ��ɳ��ɶ̣��Զ������ޡ�"></textarea><br/>
<span class="tips">Ŀ�겻Ҫ���׸��ģ����ѧ��������<br/>Ŀ�����ݿ����½ǿɵ�����С��</span>
<input type="hidden" value="p" name="fid" required="required"><br/>
<input type="submit" value="�Ҿ�����" required="required" id="p_i3"><br/>
</form>
	</div>
	<%}%>
	<% }%>
		</div>
<% if(request.getAttribute("createBean")!=null){
	CreateBean createBean = (CreateBean)request.getAttribute("createBean");
%>
	<div id="wrap_bkNews">
		<h3>-��������-</h3>
		<p class="bkNews"><%=createBean.getBkNews()%></p>
		<div><textarea rows="10" cols="30" disabled="false" class="plan"><%=createBean.getPlan()%></textarea></div>
	</div>
	<%} %>
</div>

</body>
</html>