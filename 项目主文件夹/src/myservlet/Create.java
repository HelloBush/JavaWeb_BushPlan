package myservlet;

import java.util.Date;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.text.SimpleDateFormat;

import mybean.LoginBean;
import mytool.Tool;
import mybean.CreateBean;
@WebServlet("/Create")
public class Create extends HttpServlet {
	private static final long serialVersionUID = 1L;  
  public Create() {
        super();
    }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取表单区分值,根据不同表单进行不同处理
		String fid = request.getParameter("fid");
		HttpSession session = request.getSession();
		String user="";
		LoginBean loginBean= (LoginBean)session.getAttribute("loginBean");
		if(loginBean!=null) {//从session中获取用户信息
		user = loginBean.getUser();
		}else {//session没有则从Cookie中获取
			Cookie [] cs = request.getCookies();
			for(Cookie cookie : cs) {
				if("user".equals(cookie.getName())) {
					user = cookie.getValue();
				}
			}
		}
			//创建CreateBean对象
		CreateBean createBean = new CreateBean();
		if("d".equals(fid)) {//添加日目标数据：用户名、日期、日目标内容
			String d1 = request.getParameter("d1").trim();
			String plan = request.getParameter("plan").trim();
			if(Tool.isnull(d1)||Tool.isnull(plan)) {
				createBean.setBkNews("创建目标失败，请将目标信息填写完整！");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);
			}else {
			//===========数据库连接===========================
			try {Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e) {createBean.setBkNews(e.toString());}
			String uri = "jdbc:mysql://127.0.0.1/plan";
			String name = "root";
			String pass = "admin";
			Connection con;
			PreparedStatement sql;
			try {
				con = DriverManager.getConnection(uri,name,pass);
				String condition = "CALL add_dp(?,?,?)";
				sql  = con.prepareStatement(condition);
				sql.setString(1,user);
				sql.setString(2, Tool.handleString(d1));
				sql.setString(3, Tool.handleString(plan));
				int m = sql.executeUpdate();
				if(m!=0) {
					createBean.setBkNews(d1+"的目标创建成功，但这只是开始。");
					createBean.setPlan(Tool.handleString(plan));
					createBean.setD1(d1);
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}else {
					createBean.setBkNews("创建目标失败，请检查该日是否已有日目标。");
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}
			}
			catch(Exception e) {
				createBean.setBkNews("创建目标失败，请检查该日是否已有日目标。");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);}
			}
		}else if("m".equals(fid)) {//月目标数据：用户名、月份、月目标内容；
			String m = request.getParameter("m");
			String plan = request.getParameter("plan").trim();
			if(Tool.isnull(m)||Tool.isnull(plan)) {
				createBean.setBkNews("创建目标失败，请将目标信息填写完整！");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);
			}else {
			//===========数据库连接===========================
			try {Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e) {createBean.setBkNews(e.toString());}
			String uri = "jdbc:mysql://127.0.0.1/plan";
			String name = "root";
			String pass = "admin";
			Connection con;
			PreparedStatement sql;
			try {
				con = DriverManager.getConnection(uri,name,pass);
				String condition = "CALL add_mp(?,?,?)";
				sql  = con.prepareStatement(condition);
				sql.setString(1,user);
				sql.setString(2, m);
				sql.setString(3, Tool.handleString(plan));
				int n = sql.executeUpdate();
				//查询月目标序号
				sql = con.prepareStatement("SELECT Max(m_num) FROM monthplan WHERE m=? AND u_name=?");
				sql.setString(1, m);
				sql.setString(2, user);
				ResultSet rs =  sql.executeQuery();
				rs.next();
				String m_num = rs.getString(1);
				if(n!=0) {
					createBean.setBkNews(m+"月第"+m_num+"个新目标创建成功，但这只是开始。");
					createBean.setPlan(Tool.handleString(plan));
					createBean.setM(m.toString());
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}else {
					createBean.setBkNews("创建目标失败，请检查填写信息。");
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}
			}
			catch(Exception e) {
				createBean.setBkNews("创建目标失败，请检查填写信息。");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);}
			}
		}else if("p".equals(fid)) {//阶段目标数据：用户名、起始日、截止日、目标内容
			String d1 = request.getParameter("d1");
			String d2 = request.getParameter("d2");
			String plan = request.getParameter("plan").trim();
			if(Tool.isnull(d1)||Tool.isnull(d2)||Tool.isnull(plan)) {
				createBean.setBkNews("创建目标失败，请将目标信息填写完整！");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);
			}else {
			//判断日期是否正确
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			try {
			Date a = sdf.parse(d1);
			Date b = sdf.parse(d2);
			if(a.after(b)) {
				createBean.setBkNews("创建目标失败，起始日晚于截止日。");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);
			}
		}
			catch(Exception e) {
				createBean.setBkNews("创建目标失败，日期格式错误");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);
			}
			//===========数据库连接===========================
			try {Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e) {createBean.setBkNews(e.toString());}
			String uri = "jdbc:mysql://127.0.0.1/plan";
			String name = "root";
			String pass = "admin";
			Connection con;
			PreparedStatement sql;
			try {
				con = DriverManager.getConnection(uri,name,pass);
				String condition = "CALL add_pp(?,?,?,?)";
				sql  = con.prepareStatement(condition);
				sql.setString(1,user);
				sql.setString(2, d1);
				sql.setString(3, d2);
				sql.setString(4, Tool.handleString(plan));
				int n = sql.executeUpdate();
				if(n!=0) {
					//以下是日期格式转换
					Date dd1 =  new SimpleDateFormat("yyyy-MM-dd").parse(d1); 
					String ddd1 = new SimpleDateFormat("yyyy年MM月dd日").format(dd1);
					
					Date dd2 =  new SimpleDateFormat("yyyy-MM-dd").parse(d2); 
					String ddd2 = new SimpleDateFormat("yyyy年MM月dd日").format(dd2);
					
					createBean.setBkNews("新的阶段性目标创建成功，你要在"+ddd1+"到"+ddd2+"之内完成下面的目标.");
					createBean.setPlan(Tool.handleString(plan));
					createBean.setD1(d1);
					createBean.setD2(d2);
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}else {
					createBean.setBkNews("创建目标失败，请检查填写信息。");
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}
			}
			catch(Exception e) {
				System.out.print(e.toString());
				createBean.setBkNews("创建目标失败，请检查填写信息。");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);}
		}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		doPost(request, response);
	}

}
