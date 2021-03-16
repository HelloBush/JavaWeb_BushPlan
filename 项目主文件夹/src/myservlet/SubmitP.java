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

@WebServlet("/SubmitP")
public class SubmitP extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public SubmitP() {
	        super();
	    }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
   
//此servlet处理阶段目标完成内容的提交；
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//表单提交post：获取目标完成信息，存入数据库，反馈提交页面
    	HttpSession session = request.getSession();
		SubmitBean submitBean = (SubmitBean)session.getAttribute("submitBean");
		String p_num = submitBean.getP_num();
		String plan = request.getParameter("plan");
    	String sum = request.getParameter("sum");
    	String point = request.getParameter("point");
    	String user = Tool.getUser(request);
    	if(Tool.isnull(sum)||Tool.isnull(point)) {
    		submitBean.setBkNews("请将阶段目标完成信息填写完整再提交哦！");
    		submitBean.setP_num(p_num);
    		submitBean.setPlan(plan);
    		session.setAttribute("submitBean", submitBean);
    		request.getRequestDispatcher("submitP.jsp").forward(request, response);
    	}
    	try {Class.forName("com.mysql.jdbc.Driver");}
		catch(Exception e){System.out.print(e.toString());}
    	try {
    	Connection con;
		PreparedStatement sql;
		String uri ="jdbc:mysql://127.0.0.1/plan";
		String name="root";
		String pass="admin";
		String condition = "CALL sub_pp(?,?,?,?)";
		con = DriverManager.getConnection(uri,name,pass);
		sql = con.prepareStatement(condition);
		sql.setString(1,user);
	    sql.setString(2, p_num);
	    sql.setString(3, Tool.handleString(sum));
	    sql.setString(4, point);
	    int n = sql.executeUpdate();
    	if(n!=0) {//受影响行数不为零;
    		submitBean.setBkNews("阶段目标序号为"+p_num+"的目标完成成功，你离自己的目标又进了一步。");
    		submitBean.setP_num(p_num);
    		submitBean.setPlan(plan);
    		submitBean.setSum(sum);
    		session.setAttribute("submitBean", submitBean);
    		request.getRequestDispatcher("submitP.jsp").forward(request, response);
    	}else {//受影响行数为零;
    		submitBean.setBkNews("提交失败，请检查是否已经完成该目标！");
    		submitBean.setP_num(p_num);
    		submitBean.setPlan(plan);
    		submitBean.setSum(sum);
    		session.setAttribute("submitBean", submitBean);
    		request.getRequestDispatcher("submitP.jsp").forward(request, response);
    	}
    	}
    	catch(Exception e) {System.out.print(e.toString());}
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
    }
}
