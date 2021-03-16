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
	//中文编码处理方法
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//创建注册Bean存储数据
		RegisterBean registerBean = new RegisterBean();
		//获取用户输入数据并进行合法性判断
		String user = request.getParameter("user").trim();
		String password = request.getParameter("password").trim();
		String password2 = request.getParameter("password2").trim();
		String email = request.getParameter("email").trim();

			//非空验证
		boolean vv=true;
		if(Tool.isnull(user)||Tool.isnull(password)||Tool.isnull(password2)||user.length()<3||password.length()<6||user.length()>12||password.length()>18||!password.equals(password2)||!Tool.verify(user)||!Tool.verify(password)) {
			vv=false;
			if(Tool.isnull(user)||Tool.isnull(password)||Tool.isnull(password2)) registerBean.setBkNews("抱歉，注册失败，请将必要信息填写完整。");
			else if(user.length()<3||password.length()<6||user.length()>12||password.length()>18) registerBean.setBkNews("抱歉，注册失败，用户名或密码长度不正确。");
			else if(!password.equals(password2)) registerBean.setBkNews("抱歉，注册失败，两次输入密码不一致。");
			else if(!Tool.verify(user)||!Tool.verify(password)) registerBean.setBkNews("抱歉，注册失败，请不要使用中文字符。");
			request.setAttribute("registerBean", registerBean);
			request.getRequestDispatcher("register.jsp").forward(request,response);
		}
		if(vv) {
		//开始连接数据库进行用户存储
		try{Class.forName("com.mysql.jdbc.Driver");}
		catch(Exception e){registerBean.setBkNews("加载数据库驱动失败，请检查数据库驱动文件!");}
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
			registerBean.setBkNews("恭喜您，注册草丛计划成功！");
			con.close();
			request.setAttribute("registerBean", registerBean);
			request.getRequestDispatcher("login.jsp").forward(request,response);
			}
		}
			catch(Exception e) {
				registerBean.setBkNews("不好意思，该用户名已被使用，请更换哦。");
				request.setAttribute("registerBean", registerBean);
				request.getRequestDispatcher("register.jsp").forward(request,response);}
	}
}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
