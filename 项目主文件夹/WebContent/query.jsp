<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import="mybean.QueryBean"%>
<%@ page import="mybean.DeleteBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>��ѯ�ҵĲݴԼƻ�</title>
<link rel="stylesheet" type="text/css" href="css/query.css">
<!--����Ϊ���ƻ������-->
<link rel="stylesheet" type="text/css" href="css/apple.css">
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/apple.js"></script>
<%@ include file="common/apple.txt" %>
</head>
<script type="text/javascript">
function del(){
	if(window.confirm("ȷ��ɾ����Ŀ��?\n(�벻Ҫ���׷����Լ���Ŀ��)")){
		if(window.confirm("�����Ҫ�������Ŀ����?")){
			if(window.confirm("������?")){
				return true;
			}else{
			return false;	
			}
		}else{
		return false;	
		}
	}else{
		return false;
	}
}
</script>

<body>
<%@ include file="common/head.txt" %>

<div id="wrap_query">
	<div class="wrap_form">
<!--�ձ����-->
<h3>-�ҵ���Ŀ��-</h3>
<form action="QueryD" method="post">
��ѡ���ѯ���ڣ�<br/><input type="date" name="d1" required="required"/><br/>
<input type="hidden" name="fid" value="d">
<img src="images/dtree.png" class="img_d"/><input type="submit" value="��ѯ����Ŀ��" ><br/>
</form>
		<img src="images/dtree.png" class="img_d"/><a href="QueryD?fid=d&d1=all"><button>��ѯ������Ŀ��</button></a>

	</div>
	<div class="wrap_form">
<!--�±����-->
<h3>-�ҵ���Ŀ��-</h3>
<form action="QueryM" method="post">
��ѡ���ѯ�·ݣ�<br/><select name="m">
<option value="1">һ��</option><option value="2">����</option>
<option value="3">����</option><option value="4">����</option>
<option value="5">����</option><option value="6">����</option>
<option value="7">����</option><option value="8">����</option>
<option value="9">����</option><option value="10">ʮ��</option>
<option value="11">ʮһ��</option><option value="12">ʮ����</option></select><br/>
<input type="hidden" name="fid" value="m">
<img src="images/mtree.png" class="img_m"/><input type="submit" value="��ѯ����Ŀ��"><br/>
</form>
<img src="images/mtree.png" class="img_m"/><a href="QueryM?fid=m&m=all"><button>��ѯ������Ŀ��</button></a>

	</div>
	
	<div class="wrap_form">
<!--�׶α����-->
<h3>-�ҵĽ׶�Ŀ��-</h3>
<form action="QueryP" method="post">
�׶���ʼ�գ�<input type="date" name="d1" value=""><br/>
�׶ν�ֹ�գ�<input type="date" name="d2" value=""><br/>
<input type="hidden" name="fid" value="p">
<img src="images/ptree.png"/><input type="submit" value="��ѯ������׶�Ŀ��" id="m_i1"><br/>
</form>
<img src="images/ptree.png"/><a href="QueryP?fid=p&d1=all"><button>��ѯȫ�׶�Ŀ��</button></a>

	</div>

<%
if(request.getAttribute("queryBean")!=null){
	QueryBean queryBean = (QueryBean)request.getAttribute("queryBean");
%>
<div id="wrap_biao">

<div id="bkNews_q"><%=queryBean.getBkNews()%></div>
<div id="biao"><%=queryBean.getBiao()%>
</div>
<% }
%>
</div>
</div>



</body>
</html>