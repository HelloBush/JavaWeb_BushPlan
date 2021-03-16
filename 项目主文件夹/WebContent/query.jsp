<%@ page language="java" contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<%@ page import="mybean.QueryBean"%>
<%@ page import="mybean.DeleteBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GB2312">
<title>查询我的草丛计划</title>
<link rel="stylesheet" type="text/css" href="css/query.css">
<!--以下为点击苹果附件-->
<link rel="stylesheet" type="text/css" href="css/apple.css">
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="js/apple.js"></script>
<%@ include file="common/apple.txt" %>
</head>
<script type="text/javascript">
function del(){
	if(window.confirm("确定删除此目标?\n(请不要轻易放弃自己的目标)")){
		if(window.confirm("你真的要放弃这个目标吗?")){
			if(window.confirm("再想想?")){
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
<!--日表单外框-->
<h3>-我的日目标-</h3>
<form action="QueryD" method="post">
请选择查询日期：<br/><input type="date" name="d1" required="required"/><br/>
<input type="hidden" name="fid" value="d">
<img src="images/dtree.png" class="img_d"/><input type="submit" value="查询此日目标" ><br/>
</form>
		<img src="images/dtree.png" class="img_d"/><a href="QueryD?fid=d&d1=all"><button>查询所有日目标</button></a>

	</div>
	<div class="wrap_form">
<!--月表单外框-->
<h3>-我的月目标-</h3>
<form action="QueryM" method="post">
请选择查询月份：<br/><select name="m">
<option value="1">一月</option><option value="2">二月</option>
<option value="3">三月</option><option value="4">四月</option>
<option value="5">五月</option><option value="6">六月</option>
<option value="7">七月</option><option value="8">八月</option>
<option value="9">九月</option><option value="10">十月</option>
<option value="11">十一月</option><option value="12">十二月</option></select><br/>
<input type="hidden" name="fid" value="m">
<img src="images/mtree.png" class="img_m"/><input type="submit" value="查询此月目标"><br/>
</form>
<img src="images/mtree.png" class="img_m"/><a href="QueryM?fid=m&m=all"><button>查询所有月目标</button></a>

	</div>
	
	<div class="wrap_form">
<!--阶段表单外框-->
<h3>-我的阶段目标-</h3>
<form action="QueryP" method="post">
阶段起始日：<input type="date" name="d1" value=""><br/>
阶段截止日：<input type="date" name="d2" value=""><br/>
<input type="hidden" name="fid" value="p">
<img src="images/ptree.png"/><input type="submit" value="查询此区间阶段目标" id="m_i1"><br/>
</form>
<img src="images/ptree.png"/><a href="QueryP?fid=p&d1=all"><button>查询全阶段目标</button></a>

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