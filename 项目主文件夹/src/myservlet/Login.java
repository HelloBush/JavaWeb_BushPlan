package myservlet;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mytool.Tool;
import mybean.LoginBean;
import java.sql.*;
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void init(ServletConfig config) throws ServletException {
	super.init(config);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginBean loginBean = new LoginBean();
		//验证
		String user = request.getParameter("user").trim();
		String password = request.getParameter("password").trim();
		HttpSession session = request.getSession();
		boolean vv = true;
		if(Tool.isnull(user)||Tool.isnull(password)||user.length()<3||password.length()<6){
			vv = false;
			loginBean.setBkNews("请将信息填写完整，并检查长度");
			request.setAttribute("loginBean", loginBean);
			request.getRequestDispatcher("login.jsp").forward(request,response);
			return;
		}//连接数据库
		if(vv){
			try {Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e) {loginBean.setBkNews(e.toString());}
		String uri = "jdbc:mysql://127.0.0.1/plan";
		String name = "root";
		String pass = "admin";
		Connection con;
		PreparedStatement sql;
		try {
			con = DriverManager.getConnection(uri,name,pass);
			String condition = "SELECT u_pwd FROM user WHERE u_name=?";
			sql = con.prepareStatement(condition);
			sql.setString(1, Tool.handleString(user));
			ResultSet rs = sql.executeQuery();
			if(rs.next()) {//有密码处理
				String tPassword = rs.getString(1);
				if(password.equals(tPassword)) {//密码正确处理
					loginBean.setUser(user);
					loginBean.setBkNews("欢迎登录："+user);
					con.close();
					request.setAttribute("loginBean", loginBean);
					session.setAttribute("loginBean",loginBean);
					//存入Cookie
					Cookie cookie = new Cookie("user",user);
					if(request.getParameterValues("log")!=null) {
					cookie.setMaxAge(3600);//1小时过期
					}
					response.addCookie(cookie);
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
				}else {//密码错误处理
					loginBean.setBkNews("用户名或密码错误，请检查。");
					request.setAttribute("loginBean", loginBean);
					con.close();
					request.getRequestDispatcher("login.jsp").forward(request,response);
					return;
				}
			}
				else {//无密码处理
					loginBean.setBkNews("用户名不存在，请检查后登录。");
					request.setAttribute("loginBean", loginBean);
					con.close();
					request.getRequestDispatcher("login.jsp").forward(request, response);
					return;
			}
		}
		catch(Exception e) {e.printStackTrace();}
//		
	}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	

}
