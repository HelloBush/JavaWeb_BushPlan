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
   
//��servlet������Ŀ����޸��ύ���ݿ���£�
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//���ύpost����ȡĿ����Ϣ���������ݿ⣬�����ύҳ��
    	HttpSession session = request.getSession();
		UpdateBean updateBean = (UpdateBean)session.getAttribute("updateBean");
		String m_new= request.getParameter("m");
		String plan_new = request.getParameter("plan");
		String m = updateBean.getM();
		String m_num = updateBean.getM_num();
    	String user = Tool.getUser(request);
    	if(Tool.isnull(m_new)||Tool.isnull(plan_new)) {
    		updateBean.setBkNews("�뽫Ŀ����Ϣ��д������ȷ���޸�Ŷ��");
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
    	if(n!=0) {//��Ӱ��������Ϊ��;
    		updateBean.setBkNews("�޸�"+m+"��Ŀ��ɹ�����ʱ���ʵ�����Ҳ�ǲ���ģ�");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateM.jsp").forward(request, response);
    	}else {//��Ӱ������Ϊ��;
    		updateBean.setBkNews("�޸�ʧ�ܣ����飡");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateM.jsp").forward(request, response);
    	}
    	}
    	catch(Exception e) {
    		updateBean.setBkNews("�޸�ʧ�ܣ����飡");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateM.jsp").forward(request, response);
    	}
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
    }
}
