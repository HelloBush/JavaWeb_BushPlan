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
   
//��servlet������Ŀ����޸��ύ���ݿ���£�
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//���ύpost����ȡĿ����Ϣ���������ݿ⣬�����ύҳ��
    	HttpSession session = request.getSession();
		UpdateBean updateBean = (UpdateBean)session.getAttribute("updateBean");
		String d1_new = request.getParameter("d1");
		String plan_new = request.getParameter("plan");
		String d1 = updateBean.getD1();
    	String user = Tool.getUser(request);
    	if(Tool.isnull(d1_new)||Tool.isnull(plan_new)) {
    		updateBean.setBkNews("�뽫Ŀ����Ϣ��д������ȷ���޸�Ŷ��");
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
    	if(n!=0) {//��Ӱ��������Ϊ��;
    		updateBean.setBkNews("�޸�"+Tool.handleDate(d1)+"Ŀ��ɹ�����ʱ���ʵ�����Ҳ�ǲ���ģ�");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateD.jsp").forward(request, response);
    	}else {//��Ӱ������Ϊ��;
    		updateBean.setBkNews("�޸�ʧ�ܣ���������Ŀ�꣡");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateD.jsp").forward(request, response);
    	}
    	}
    	catch(Exception e) {
    		updateBean.setBkNews("�޸�ʧ�ܣ���������Ŀ�꣡");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateD.jsp").forward(request, response);
    	}
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
    }
}
