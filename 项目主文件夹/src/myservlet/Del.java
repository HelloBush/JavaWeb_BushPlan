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
		//��ȡ������ֵ�����в�ͬ������������������
			//��ȡget�еĴ�ֵ���������ݿ����ɾ����ѯ
				//����ɾ��Bean�����ݱ�����ֵ��ת��ѯҳ��
		String aim = request.getParameter("aim");
		String user = request.getParameter("user");
		Connection con;
		PreparedStatement sql;
		String condition;
		String uri ="jdbc:mysql://127.0.0.1/plan";
		String name="root";
		String pass="admin";
		DeleteBean deleteBean  = new DeleteBean();
		if("d".equals(aim)) {//��Ŀ��ɾ��
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
					deleteBean.setBkNews("ɾ��"+Tool.handleDate(d1)+"��Ŀ��ɹ���");
					
				}else {
					deleteBean.setBkNews("ɾ��"+Tool.handleDate(d1)+"��Ŀ��ʧ�ܣ�");
				}
				request.setAttribute("deleteBean", deleteBean);
				request.getRequestDispatcher("QueryD?fid=d&d1=all").forward(request,response);
			}
			catch(Exception e) {System.out.print(e.toString());}
		}else if("m".equals(aim)) {//��Ŀ��ɾ��
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
					deleteBean.setBkNews("ɾ��"+m+"�µ�Ŀ��ɹ���");
					
				}else {
					deleteBean.setBkNews("ɾ��"+m+"�µ�Ŀ��ʧ�ܣ�");
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
					deleteBean.setBkNews("ɾ�����Ϊ"+p_num+"�Ľ׶�Ŀ��ɹ���");
					
				}else {
					deleteBean.setBkNews("ɾ�����Ϊ"+p_num+"�Ľ׶�Ŀ��ʧ�ܣ�");
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
