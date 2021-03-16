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

import mybean.UpdateBean;
import mytool.Tool;

@WebServlet("/UpdateD")
public class UpdateD extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public UpdateD() {
	        super();
	    }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
   
//此servlet处理日目标的修改提交数据库更新；
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//表单提交post：获取目标信息，存入数据库，反馈提交页面
    	HttpSession session = request.getSession();
		UpdateBean updateBean = (UpdateBean)session.getAttribute("updateBean");
		String d1_new = request.getParameter("d1");
		String plan_new = request.getParameter("plan");
		String d1 = updateBean.getD1();
    	String user = Tool.getUser(request);
    	if(Tool.isnull(d1_new)||Tool.isnull(plan_new)) {
    		updateBean.setBkNews("请将目标信息填写完整再确认修改哦！");
    		updateBean.setD1(d1_new);
    		updateBean.setPlan(plan_new);
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateD.jsp").forward(request, response);
    	}
    	try {Class.forName("com.mysql.jdbc.Driver");}
		catch(Exception e){System.out.print(e.toString());}
    	try {
    	Connection con;
		PreparedStatement sql;
		String uri ="jdbc:mysql://127.0.0.1/plan";
		String name="root";
		String pass="admin";
		String condition = "UPDATE dayplan SET d_num=?,d_plan=? WHERE u_name=? AND d_num=?";
		con = DriverManager.getConnection(uri,name,pass);
		sql = con.prepareStatement(condition);
	    sql.setString(1, d1_new);
	    sql.setString(2, Tool.handleString(plan_new));
	    sql.setString(3,user);
	    sql.setString(4, d1);
	    int n = sql.executeUpdate();
    	if(n!=0) {//受影响行数不为零;
    		updateBean.setBkNews("修改"+Tool.handleDate(d1)+"目标成功，有时候适当调整也是不错的！");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateD.jsp").forward(request, response);
    	}else {//受影响行数为零;
    		updateBean.setBkNews("修改失败，该日已有目标！");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateD.jsp").forward(request, response);
    	}
    	}
    	catch(Exception e) {
    		updateBean.setBkNews("修改失败，该日已有目标！");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateD.jsp").forward(request, response);
    	}
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
    }
}
