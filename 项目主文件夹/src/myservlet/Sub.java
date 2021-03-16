package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.SubmitBean;
import mytool.Tool;

@WebServlet("/Sub")
public class Sub extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public Sub() {
	        super();
	    }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
   
//此servlet处理查询页面的提交按钮转发
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//
    	String aim = request.getParameter("aim");
    	if("d".equals(aim)) {
    			SubmitBean submitBean = new SubmitBean();
    			String d1 = request.getParameter("d1");
    			String user = request.getParameter("user");
    			HttpSession session =request.getSession();
    			String plan = "";
    			//连接数据库拿目标内容
    			Connection con;
    			PreparedStatement sql;
    			String condition;
    			String uri ="jdbc:mysql://127.0.0.1/plan";
    			String name="root";
    			String pass="admin";
    			try {Class.forName("com.mysql.jdbc.Driver");}
    			catch(Exception e){System.out.print(e.toString());}
    			try {
    				con = DriverManager.getConnection(uri,name,pass);
    				condition = "SELECT d_plan FROM dayplan WHERE u_name=? AND d_num=?";
    				sql = con.prepareStatement(condition);
    				sql.setString(1, user);
    				sql.setString(2, d1);
    				ResultSet rs = sql.executeQuery();
    				rs.next();
    				plan = rs.getString(1);
    			}
    			catch(Exception e) {System.out.print(e.toString());}
    			submitBean.setD1(d1);
    			submitBean.setPlan(plan);
    			session.setAttribute("submitBean", submitBean);
    			request.getRequestDispatcher("submitD.jsp").forward(request, response);
    	}else if("m".equals(aim)) {//月目标的提交转发=====================
    		SubmitBean submitBean = new SubmitBean();
			String m = request.getParameter("m");
			String m_num = request.getParameter("m_num");
			String user = request.getParameter("user");
			HttpSession session =request.getSession();
			String plan = "";
			//连接数据库拿目标内容
			Connection con;
			PreparedStatement sql;
			String condition;
			String uri ="jdbc:mysql://127.0.0.1/plan";
			String name="root";
			String pass="admin";
			try {Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e){System.out.print(e.toString());}
			try {
				con = DriverManager.getConnection(uri,name,pass);
				condition = "SELECT m_plan FROM monthplan WHERE u_name=? AND m_num=? AND m=?";
				sql = con.prepareStatement(condition);
				sql.setString(1, user);
				sql.setString(2, m_num);
				sql.setString(3, m);
				ResultSet rs = sql.executeQuery();
				rs.next();
				plan = rs.getString(1);
			}
			catch(Exception e) {System.out.print(e.toString());}
			submitBean.setM(m);
			submitBean.setM_num(m_num);
			submitBean.setPlan(plan);
			session.setAttribute("submitBean", submitBean);
			request.getRequestDispatcher("submitM.jsp").forward(request, response);
    	}else if("p".equals(aim)) {//阶段目标的提交转发===============================
    		SubmitBean submitBean = new SubmitBean();
			String p_num = request.getParameter("p_num");
			String user = request.getParameter("user");
			HttpSession session =request.getSession();
			String plan = "";
			//连接数据库拿目标内容
			Connection con;
			PreparedStatement sql;
			String condition;
			String uri ="jdbc:mysql://127.0.0.1/plan";
			String name="root";
			String pass="admin";
			try {Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e){System.out.print(e.toString());}
			try {
				con = DriverManager.getConnection(uri,name,pass);
				condition = "SELECT p_plan FROM phaseplan WHERE u_name=? AND p_num=?";
				sql = con.prepareStatement(condition);
				sql.setString(1, user);
				sql.setString(2, p_num);
				ResultSet rs = sql.executeQuery();
				rs.next();
				plan = rs.getString(1);
			}
			catch(Exception e) {System.out.print(e.toString());}
			submitBean.setP_num(p_num);
			submitBean.setPlan(plan);
			session.setAttribute("submitBean", submitBean);
			request.getRequestDispatcher("submitP.jsp").forward(request, response);
    	}
    	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
}
    }
