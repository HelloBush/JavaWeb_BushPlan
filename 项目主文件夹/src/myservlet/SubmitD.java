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
   
//��servlet��������ʽ�Ĳ�ͬ���ж�����ת�ύҳ�滹���ύ���Ŀ�꣬���в�ͬ����
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//���ύpost����ȡĿ�������Ϣ���������ݿ⣬�����ύҳ��
    	HttpSession session = request.getSession();
		SubmitBean submitBean = (SubmitBean)session.getAttribute("submitBean");
		String d1 = submitBean.getD1();
		String plan = request.getParameter("plan");
    	String sum = request.getParameter("sum");
    	String point = request.getParameter("point");
    	String user = Tool.getUser(request);
    	if(Tool.isnull(sum)||Tool.isnull(point)) {
    		submitBean.setBkNews("�뽫Ŀ�������Ϣ��д�������ύŶ��");
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
    	if(n!=0) {//��Ӱ��������Ϊ��;
    		submitBean.setBkNews("���"+Tool.handleDate(d1)+"Ŀ��ɹ������������Լ���Ŀ���ֽ���һ����");
    		submitBean.setD1(d1);
    		submitBean.setPlan(plan);
    		submitBean.setSum(sum);
    		session.setAttribute("submitBean", submitBean);
    		request.getRequestDispatcher("submitD.jsp").forward(request, response);
    	}else {//��Ӱ������Ϊ��;
    		submitBean.setBkNews("�ύʧ�ܣ������Ƿ��Ѿ���ɸ�Ŀ�꣡");
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
