package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;

import mybean.RegisterBean;
import mytool.Tool;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
	super.init(config);
	}
	//���ı��봦����
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//����ע��Bean�洢����
		RegisterBean registerBean = new RegisterBean();
		//��ȡ�û��������ݲ����кϷ����ж�
		String user = request.getParameter("user").trim();
		String password = request.getParameter("password").trim();
		String password2 = request.getParameter("password2").trim();
		String email = request.getParameter("email").trim();

			//�ǿ���֤
		boolean vv=true;
		if(Tool.isnull(user)||Tool.isnull(password)||Tool.isnull(password2)||user.length()<3||password.length()<6||user.length()>12||password.length()>18||!password.equals(password2)||!Tool.verify(user)||!Tool.verify(password)) {
			vv=false;
			if(Tool.isnull(user)||Tool.isnull(password)||Tool.isnull(password2)) registerBean.setBkNews("��Ǹ��ע��ʧ�ܣ��뽫��Ҫ��Ϣ��д������");
			else if(user.length()<3||password.length()<6||user.length()>12||password.length()>18) registerBean.setBkNews("��Ǹ��ע��ʧ�ܣ��û��������볤�Ȳ���ȷ��");
			else if(!password.equals(password2)) registerBean.setBkNews("��Ǹ��ע��ʧ�ܣ������������벻һ�¡�");
			else if(!Tool.verify(user)||!Tool.verify(password)) registerBean.setBkNews("��Ǹ��ע��ʧ�ܣ��벻Ҫʹ�������ַ���");
			request.setAttribute("registerBean", registerBean);
			request.getRequestDispatcher("register.jsp").forward(request,response);
		}
		if(vv) {
		//��ʼ�������ݿ�����û��洢
		try{Class.forName("com.mysql.jdbc.Driver");}
		catch(Exception e){registerBean.setBkNews("�������ݿ�����ʧ�ܣ��������ݿ������ļ�!");}
		String uri = "jdbc:mysql://127.0.0.1/plan";
		String name = "root";
		String pass = "admin";
		Connection con;
		PreparedStatement sql;
		try{
			con = DriverManager.getConnection(uri,name,pass);
			String condition = "CALL add_user(?,?,?)";
			sql = con.prepareStatement(condition);
			sql.setString(1,Tool.handleString(user));
			sql.setString(2,Tool.handleString(password));
			sql.setString(3,Tool.handleString(email));
			int m = sql.executeUpdate();
			if(m!=0){
			registerBean.setUser(user);
			registerBean.setPassword(password);
			registerBean.setEmail(email);
			registerBean.setBkNews("��ϲ����ע��ݴԼƻ��ɹ���");
			con.close();
			request.setAttribute("registerBean", registerBean);
			request.getRequestDispatcher("login.jsp").forward(request,response);
			}
		}
			catch(Exception e) {
				registerBean.setBkNews("������˼�����û����ѱ�ʹ�ã������Ŷ��");
				request.setAttribute("registerBean", registerBean);
				request.getRequestDispatcher("register.jsp").forward(request,response);}
	}
}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
