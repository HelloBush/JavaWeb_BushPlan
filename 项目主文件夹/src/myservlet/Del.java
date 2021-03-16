package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybean.DeleteBean;
import mytool.Tool;

@WebServlet("/Del")
public class Del extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Del() {
        super();
    }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取表单区分值，进行不同处理，并创建公共变量
			//获取get中的传值，连接数据库进行删除查询
				//反馈删除Bean，根据表单区分值跳转查询页面
		String aim = request.getParameter("aim");
		String user = request.getParameter("user");
		Connection con;
		PreparedStatement sql;
		String condition;
		String uri ="jdbc:mysql://127.0.0.1/plan";
		String name="root";
		String pass="admin";
		DeleteBean deleteBean  = new DeleteBean();
		if("d".equals(aim)) {//日目标删除
			String d1 = request.getParameter("d1");
			try{Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e) {System.out.print(e.toString());}
			try {
				con = DriverManager.getConnection(uri,name,pass);
				condition = "DELETE FROM dayplan WHERE d_num=? AND u_name=?";
				sql = con.prepareStatement(condition);
				sql.setString(1, d1);
				sql.setString(2, user);
				int n = sql.executeUpdate();
				if(n!=0) {
					deleteBean.setBkNews("删除"+Tool.handleDate(d1)+"的目标成功！");
					
				}else {
					deleteBean.setBkNews("删除"+Tool.handleDate(d1)+"的目标失败！");
				}
				request.setAttribute("deleteBean", deleteBean);
				request.getRequestDispatcher("QueryD?fid=d&d1=all").forward(request,response);
			}
			catch(Exception e) {System.out.print(e.toString());}
		}else if("m".equals(aim)) {//月目标删除
			String m = request.getParameter("m");
			String m_num = request.getParameter("m_num");
			try{Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e) {System.out.print(e.toString());}
			try {
				con = DriverManager.getConnection(uri,name,pass);
				condition = "DELETE FROM monthplan WHERE m=? AND m_num=? AND u_name=?";
				sql = con.prepareStatement(condition);
				sql.setString(1, m);
				sql.setString(2, m_num);
				sql.setString(3, user);
				int n = sql.executeUpdate();
				if(n!=0) {
					deleteBean.setBkNews("删除"+m+"月的目标成功！");
					
				}else {
					deleteBean.setBkNews("删除"+m+"月的目标失败！");
				}
				request.setAttribute("deleteBean", deleteBean);
				request.getRequestDispatcher("QueryM?fid=m&m=all").forward(request,response);
			}
			catch(Exception e) {System.out.print(e.toString());}
		}else if("p".equals(aim)) {
			String p_num = request.getParameter("p_num");
			try{Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e) {System.out.print(e.toString());}
			try {
				con = DriverManager.getConnection(uri,name,pass);
				condition = "DELETE FROM phaseplan WHERE p_num=? AND u_name=?";
				sql = con.prepareStatement(condition);
				sql.setString(1, p_num);
				sql.setString(2, user);
				int n = sql.executeUpdate();
				if(n!=0) {
					deleteBean.setBkNews("删除序号为"+p_num+"的阶段目标成功！");
					
				}else {
					deleteBean.setBkNews("删除序号为"+p_num+"的阶段目标失败！");
				}
				request.setAttribute("deleteBean", deleteBean);
				request.getRequestDispatcher("QueryP?fid=p&d1=all").forward(request,response);
			}
			catch(Exception e) {System.out.print(e.toString());}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
