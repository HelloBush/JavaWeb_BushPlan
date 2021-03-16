package myservlet;

import java.util.Date;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.text.SimpleDateFormat;

import mybean.LoginBean;
import mytool.Tool;
import mybean.CreateBean;
@WebServlet("/Create")
public class Create extends HttpServlet {
	private static final long serialVersionUID = 1L;  
  public Create() {
        super();
    }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ������ֵ,���ݲ�ͬ�����в�ͬ����
		String fid = request.getParameter("fid");
		HttpSession session = request.getSession();
		String user="";
		LoginBean loginBean= (LoginBean)session.getAttribute("loginBean");
		if(loginBean!=null) {//��session�л�ȡ�û���Ϣ
		user = loginBean.getUser();
		}else {//sessionû�����Cookie�л�ȡ
			Cookie [] cs = request.getCookies();
			for(Cookie cookie : cs) {
				if("user".equals(cookie.getName())) {
					user = cookie.getValue();
				}
			}
		}
			//����CreateBean����
		CreateBean createBean = new CreateBean();
		if("d".equals(fid)) {//�����Ŀ�����ݣ��û��������ڡ���Ŀ������
			String d1 = request.getParameter("d1").trim();
			String plan = request.getParameter("plan").trim();
			if(Tool.isnull(d1)||Tool.isnull(plan)) {
				createBean.setBkNews("����Ŀ��ʧ�ܣ��뽫Ŀ����Ϣ��д������");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);
			}else {
			//===========���ݿ�����===========================
			try {Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e) {createBean.setBkNews(e.toString());}
			String uri = "jdbc:mysql://127.0.0.1/plan";
			String name = "root";
			String pass = "admin";
			Connection con;
			PreparedStatement sql;
			try {
				con = DriverManager.getConnection(uri,name,pass);
				String condition = "CALL add_dp(?,?,?)";
				sql  = con.prepareStatement(condition);
				sql.setString(1,user);
				sql.setString(2, Tool.handleString(d1));
				sql.setString(3, Tool.handleString(plan));
				int m = sql.executeUpdate();
				if(m!=0) {
					createBean.setBkNews(d1+"��Ŀ�괴���ɹ�������ֻ�ǿ�ʼ��");
					createBean.setPlan(Tool.handleString(plan));
					createBean.setD1(d1);
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}else {
					createBean.setBkNews("����Ŀ��ʧ�ܣ���������Ƿ�������Ŀ�ꡣ");
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}
			}
			catch(Exception e) {
				createBean.setBkNews("����Ŀ��ʧ�ܣ���������Ƿ�������Ŀ�ꡣ");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);}
			}
		}else if("m".equals(fid)) {//��Ŀ�����ݣ��û������·ݡ���Ŀ�����ݣ�
			String m = request.getParameter("m");
			String plan = request.getParameter("plan").trim();
			if(Tool.isnull(m)||Tool.isnull(plan)) {
				createBean.setBkNews("����Ŀ��ʧ�ܣ��뽫Ŀ����Ϣ��д������");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);
			}else {
			//===========���ݿ�����===========================
			try {Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e) {createBean.setBkNews(e.toString());}
			String uri = "jdbc:mysql://127.0.0.1/plan";
			String name = "root";
			String pass = "admin";
			Connection con;
			PreparedStatement sql;
			try {
				con = DriverManager.getConnection(uri,name,pass);
				String condition = "CALL add_mp(?,?,?)";
				sql  = con.prepareStatement(condition);
				sql.setString(1,user);
				sql.setString(2, m);
				sql.setString(3, Tool.handleString(plan));
				int n = sql.executeUpdate();
				//��ѯ��Ŀ�����
				sql = con.prepareStatement("SELECT Max(m_num) FROM monthplan WHERE m=? AND u_name=?");
				sql.setString(1, m);
				sql.setString(2, user);
				ResultSet rs =  sql.executeQuery();
				rs.next();
				String m_num = rs.getString(1);
				if(n!=0) {
					createBean.setBkNews(m+"�µ�"+m_num+"����Ŀ�괴���ɹ�������ֻ�ǿ�ʼ��");
					createBean.setPlan(Tool.handleString(plan));
					createBean.setM(m.toString());
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}else {
					createBean.setBkNews("����Ŀ��ʧ�ܣ�������д��Ϣ��");
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}
			}
			catch(Exception e) {
				createBean.setBkNews("����Ŀ��ʧ�ܣ�������д��Ϣ��");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);}
			}
		}else if("p".equals(fid)) {//�׶�Ŀ�����ݣ��û�������ʼ�ա���ֹ�ա�Ŀ������
			String d1 = request.getParameter("d1");
			String d2 = request.getParameter("d2");
			String plan = request.getParameter("plan").trim();
			if(Tool.isnull(d1)||Tool.isnull(d2)||Tool.isnull(plan)) {
				createBean.setBkNews("����Ŀ��ʧ�ܣ��뽫Ŀ����Ϣ��д������");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);
			}else {
			//�ж������Ƿ���ȷ
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			try {
			Date a = sdf.parse(d1);
			Date b = sdf.parse(d2);
			if(a.after(b)) {
				createBean.setBkNews("����Ŀ��ʧ�ܣ���ʼ�����ڽ�ֹ�ա�");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);
			}
		}
			catch(Exception e) {
				createBean.setBkNews("����Ŀ��ʧ�ܣ����ڸ�ʽ����");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);
			}
			//===========���ݿ�����===========================
			try {Class.forName("com.mysql.jdbc.Driver");}
			catch(Exception e) {createBean.setBkNews(e.toString());}
			String uri = "jdbc:mysql://127.0.0.1/plan";
			String name = "root";
			String pass = "admin";
			Connection con;
			PreparedStatement sql;
			try {
				con = DriverManager.getConnection(uri,name,pass);
				String condition = "CALL add_pp(?,?,?,?)";
				sql  = con.prepareStatement(condition);
				sql.setString(1,user);
				sql.setString(2, d1);
				sql.setString(3, d2);
				sql.setString(4, Tool.handleString(plan));
				int n = sql.executeUpdate();
				if(n!=0) {
					//���������ڸ�ʽת��
					Date dd1 =  new SimpleDateFormat("yyyy-MM-dd").parse(d1); 
					String ddd1 = new SimpleDateFormat("yyyy��MM��dd��").format(dd1);
					
					Date dd2 =  new SimpleDateFormat("yyyy-MM-dd").parse(d2); 
					String ddd2 = new SimpleDateFormat("yyyy��MM��dd��").format(dd2);
					
					createBean.setBkNews("�µĽ׶���Ŀ�괴���ɹ�����Ҫ��"+ddd1+"��"+ddd2+"֮����������Ŀ��.");
					createBean.setPlan(Tool.handleString(plan));
					createBean.setD1(d1);
					createBean.setD2(d2);
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}else {
					createBean.setBkNews("����Ŀ��ʧ�ܣ�������д��Ϣ��");
					con.close();
					request.setAttribute("createBean", createBean);
					request.getRequestDispatcher("create.jsp").forward(request,response);
				}
			}
			catch(Exception e) {
				System.out.print(e.toString());
				createBean.setBkNews("����Ŀ��ʧ�ܣ�������д��Ϣ��");
				request.setAttribute("createBean", createBean);
				request.getRequestDispatcher("create.jsp").forward(request,response);}
		}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		doPost(request, response);
	}

}
