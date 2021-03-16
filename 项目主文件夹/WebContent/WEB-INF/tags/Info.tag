<%@ tag language="java" pageEncoding="GB2312"%>
<%@ tag import="mybean.LoginBean"%>
<%@ tag import="java.sql.*"%>
<%@ tag import="java.io.*"%>
<%@ tag import="mytool.Tool"%>
<%@ variable name-given="user" variable-class="java.lang.String" scope="AT_END"%>
<%@ variable name-given="point" variable-class="java.lang.Integer" scope="AT_END"%>
<%@ variable name-given="count" variable-class="java.lang.Integer" scope="AT_END"%>
<%@ variable name-given="error" variable-class="java.lang.String" scope="AT_END" %>
<% 
//�ж��û��Ƿ��¼
String user="";
int point=0;
String error="";
int count=0;
//�û���¼��������ݿ����Ӳ�ѯ����
if(Tool.getUser(request)!=null){
	user = Tool.getUser(request);
	try{Class.forName("com.mysql.jdbc.Driver");}
	catch(Exception e){System.out.print(e.toString());}
	String uri = "jdbc:mysql://127.0.0.1/plan";
	String name = "root";
	String pass = "admin";
	Connection con;
	PreparedStatement sql;
	try{
		con = DriverManager.getConnection(uri,name,pass);
		//��ѯ�û��ܷ���
		String condition = "SELECT point FROM user WHERE u_name=?";
		sql = con.prepareStatement(condition);
		sql.setString(1,user);
		ResultSet rs = sql.executeQuery();
		rs.next();
		point = Integer.parseInt(rs.getString(1));
		//��ѯ�û���Ŀ�������
		condition = "CALL count_aim(?)";
		sql = con.prepareStatement(condition);
		sql.setString(1,user);
		rs = sql.executeQuery();
		rs.next();
		count = Integer.parseInt(rs.getString(1));
		//�û���¼�������session�е�loginBean���ݣ����洢���ݵ�Cookie�У�
		LoginBean loginBean = new LoginBean();
		loginBean.setUser(user);
		session.setAttribute("loginBean",loginBean);
		
	}
	catch(Exception e){e.printStackTrace();}
	jspContext.setAttribute("user",user);
	jspContext.setAttribute("point",point);
	jspContext.setAttribute("count",count);
	jspContext.setAttribute("error",error);
}
%>