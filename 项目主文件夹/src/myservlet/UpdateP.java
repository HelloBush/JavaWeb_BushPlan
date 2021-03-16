package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.UpdateBean;
import mytool.Tool;

@WebServlet("/UpdateP")
public class UpdateP extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public UpdateP() {
	        super();
	    }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
   
//��servlet����׶�Ŀ����޸��ύ���ݿ���£�
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//���ύpost����ȡĿ����Ϣ���������ݿ⣬�����ύҳ��
    	HttpSession session = request.getSession();
		UpdateBean updateBean = (UpdateBean)session.getAttribute("updateBean");
		String d1_new= request.getParameter("d1");
		String d2_new= request.getParameter("d2");
		String plan_new = request.getParameter("plan");
		String p_num = updateBean.getP_num();
    	String user = Tool.getUser(request);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		try {
		Date a = sdf.parse(d1_new);
		Date b = sdf.parse(d2_new);
		if((a.after(b))) {
			System.out.print("==========");
			updateBean.setBkNews("�޸�Ŀ��ʧ�ܣ���ʼ�����ڽ�ֹ�ա�");
			session.setAttribute("updateBean", updateBean);
			request.getRequestDispatcher("updateP.jsp").forward(request,response);
			return;
		}
	}catch(Exception e) {
		updateBean.setBkNews("�޸�Ŀ��ʧ�ܣ����ڸ�ʽ����");
		session.setAttribute("updateBean", updateBean);
		request.getRequestDispatcher("updateP.jsp").forward(request,response);
	}
    	if(Tool.isnull(d1_new)||Tool.isnull(d2_new)||Tool.isnull(plan_new)) {
    		updateBean.setBkNews("�뽫Ŀ����Ϣ��д������ȷ���޸�Ŷ��");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateP.jsp").forward(request, response);
    	}
    	try {Class.forName("com.mysql.jdbc.Driver");}
		catch(Exception e){System.out.print(e.toString());}
    	try {
    	Connection con;
		PreparedStatement sql;
		String uri ="jdbc:mysql://127.0.0.1/plan";
		String name="root";
		String pass="admin";
		String condition = "CALL upd_pp(?,?,?,?,?)";
		con = DriverManager.getConnection(uri,name,pass);
		sql = con.prepareStatement(condition);
	    sql.setString(1, user);
	    sql.setString(2, p_num);
	    sql.setString(3,d1_new);
	    sql.setString(4, d2_new);
	    sql.setString(5, Tool.handleString(plan_new));
	    int n = sql.executeUpdate();
    	if(n!=0) {//��Ӱ��������Ϊ��;
    		updateBean.setBkNews("�޸ĵ�"+p_num+"���׶���Ŀ��ɹ�����ʱ���ʵ�����Ҳ�ǲ���ģ�");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateP.jsp").forward(request, response);
    	}else {//��Ӱ������Ϊ��;
    		updateBean.setBkNews("�޸�ʧ�ܣ����飡");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateP.jsp").forward(request, response);
    	}
    	}
    	catch(Exception e) {
    		updateBean.setBkNews("�޸�ʧ�ܣ����飡");
    		session.setAttribute("updateBean", updateBean);
    		request.getRequestDispatcher("updateP.jsp").forward(request, response);
    	}
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
    }
}
