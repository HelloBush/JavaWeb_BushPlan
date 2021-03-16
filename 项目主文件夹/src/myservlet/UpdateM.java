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

@WebServlet("/UpdateM")
public class UpdateM extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public UpdateM() {
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
		String m_new= request.getParameter("m");
		String plan_new = request.getParameter("plan");
		String m = updateBean.getM();
		String m_num = updateBean.getM_num();
    	String user = Tool.getUser(request);
    	if(Tool.isnull(m_new)||Tool.isnull(plan_new)) {
    		updateBean.setBkNews("请将目标信息填写完整再确认修改哦！");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateM.jsp").forward(request, response);
    	}
    	try {Class.forName("com.mysql.jdbc.Driver");}
		catch(Exception e){System.out.print(e.toString());}
    	try {
    	Connection con;
		PreparedStatement sql;
		String uri ="jdbc:mysql://127.0.0.1/plan";
		String name="root";
		String pass="admin";
		String condition = "CALL upd_mp(?,?,?,?,?)";
		con = DriverManager.getConnection(uri,name,pass);
		sql = con.prepareStatement(condition);
	    sql.setString(1, user);
	    sql.setString(2, m);
	    sql.setString(3,m_num);
	    sql.setString(4, m_new);
	    sql.setString(5, Tool.handleString(plan_new));
	    int n = sql.executeUpdate();
    	if(n!=0) {//受影响行数不为零;
    		updateBean.setBkNews("修改"+m+"月目标成功，有时候适当调整也是不错的！");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateM.jsp").forward(request, response);
    	}else {//受影响行数为零;
    		updateBean.setBkNews("修改失败，请检查！");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateM.jsp").forward(request, response);
    	}
    	}
    	catch(Exception e) {
    		updateBean.setBkNews("修改失败，请检查！");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateM.jsp").forward(request, response);
    	}
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
    }
}
