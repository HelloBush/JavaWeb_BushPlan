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

@WebServlet("/SubmitD")
public class SubmitD extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public SubmitD() {
	        super();
	    }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
   
//此servlet根据请求方式的不同，判断是跳转提交页面还是提交完成目标，进行不同处理；
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//表单提交post：获取目标完成信息，存入数据库，反馈提交页面
    	HttpSession session = request.getSession();
		SubmitBean submitBean = (SubmitBean)session.getAttribute("submitBean");
		String d1 = submitBean.getD1();
		String plan = request.getParameter("plan");
    	String sum = request.getParameter("sum");
    	String point = request.getParameter("point");
    	String user = Tool.getUser(request);
    	if(Tool.isnull(sum)||Tool.isnull(point)) {
    		submitBean.setBkNews("请将目标完成信息填写完整再提交哦！");
    		submitBean.setD1(d1);
    		submitBean.setPlan(plan);
    		session.setAttribute("submitBean", submitBean);
    		request.getRequestDispatcher("submitD.jsp").forward(request, response);
    	}
    	try {Class.forName("com.mysql.jdbc.Driver");}
		catch(Exception e){System.out.print(e.toString());}
    	try {
    	Connection con;
		PreparedStatement sql;
		String uri ="jdbc:mysql://127.0.0.1/plan";
		String name="root";
		String pass="admin";
		String condition = "CALL sub_dp(?,?,?,?)";
		con = DriverManager.getConnection(uri,name,pass);
		sql = con.prepareStatement(condition);
		sql.setString(1,Tool.handleString(sum));
	    sql.setString(2, point);
	    sql.setString(3, user);
	    sql.setString(4, d1);
	    int n = sql.executeUpdate();
    	if(n!=0) {//受影响行数不为零;
    		submitBean.setBkNews("完成"+Tool.handleDate(d1)+"目标成功，今日你离自己的目标又进了一步。");
    		submitBean.setD1(d1);
    		submitBean.setPlan(plan);
    		submitBean.setSum(sum);
    		session.setAttribute("submitBean", submitBean);
    		request.getRequestDispatcher("submitD.jsp").forward(request, response);
    	}else {//受影响行数为零;
    		submitBean.setBkNews("提交失败，请检查是否已经完成该目标！");
    		submitBean.setD1(d1);
    		submitBean.setPlan(plan);
    		submitBean.setSum(sum);
    		session.setAttribute("submitBean", submitBean);
    		request.getRequestDispatcher("submitD.jsp").forward(request, response);
    	}
    	}
    	catch(Exception e) {System.out.print(e.toString());}
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
    }
}
