package myservlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.DeleteBean;
import mybean.LoginBean;
import mybean.QueryBean;
import mytool.Tool;

import java.sql.*;

@WebServlet("/QueryM")
public class QueryM extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public QueryM() {
        super();
    }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1��ȡ�û���Ϣ�ʹ���������Ϣ
		String user="";
		StringBuffer biao=new StringBuffer();
		
		Connection con;
		PreparedStatement sql;
		String condition;
		String uri ="jdbc:mysql://127.0.0.1/plan";
		String name="root";
		String pass="admin";
		HttpSession session = request.getSession();
		LoginBean loginBean= (LoginBean)session.getAttribute("loginBean");
		QueryBean queryBean  = new QueryBean();
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
		//2���ձ�����ֵ�����в�ͬ����
		String fid = request.getParameter("fid");
		if("m".equals(fid)) {//==============��Ŀ���ѯ===============
			//2.1���ղ�ѯ����
			String m = request.getParameter("m");
			if(Tool.isnull(m)) {//�ж��Ƿ�Ϊ��
				queryBean.setBkNews("��ѡ���ѯ�·ݺ��ٵ����ѯŶ��");
				request.setAttribute("queryBean", queryBean);
				request.getRequestDispatcher("query.jsp").forward(request, response);
			}
				//2.2�������ݿ���в�ѯ
				try {Class.forName("com.mysql.jdbc.Driver");}
				catch(Exception e) {queryBean.setBkNews(e.toString());}
				try {
					con = DriverManager.getConnection(uri,name,pass);
					//�ж��Ƿ�ȫĿ���ѯ
					if("all".equals(m)) condition = "SELECT * FROM monthplan WHERE u_name=? ORDER BY m ASC,m_num ASC";
					else condition = "SELECT * FROM monthplan WHERE u_name=? AND  m=? ORDER BY m_num ASC";
					sql = con.prepareStatement(condition);
					sql.setString(1,user);
					if(!"all".equals(m))sql.setString(2,m);
					ResultSet rs = sql.executeQuery();
					//�ձ��ж�
					rs.last();
					int i = rs.getRow();
					rs.absolute(0);
					if(request.getAttribute("deleteBean")!=null) {//ɾ������
						DeleteBean deleteBean = (DeleteBean)request.getAttribute("deleteBean");
						String s = "<br/><span id=\"bkNews_d\">"+deleteBean.getBkNews()+"</span>";
						biao.append(s);
					}
					//2.3��ѯ��������������Bean�����򷵻���Ϣ��
					if(i!=0) {//��Ŀ�괦��
						biao.append("<table border=\"1\" id=\"biao_m\">");
						biao.append("<tr><th id=\"th1\">��Ŀ�����</th><th id=\"th2\">Ŀ��</th><th id=\"tg3\">������</th><th id=\"th4\">����</th><th id=\"th5\">����</th></tr>");
							while(rs.next()) {
								biao.append("<tr>");
								String m_num = rs.getString(2);
								String month = rs.getString(3);
								String m_plan = rs.getString(4);
								String m_sum = rs.getString(5);
								int m_point = rs.getInt("m_point");
									biao.append("<td>"+month+"-"+m_num+"</td>");
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+m_plan+"</textarea></td>");
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+(m_sum==null?"���޽�չ":m_sum)+"</textarea></td>");
									if(m_point!=0) {//������Ϊ��
										biao.append("<td>"+m_point+"</td>");
										biao.append("<td><a href=\"Del?m="+month+"&m_num="+m_num+"&user="+user+"&aim=m\" onclick=\"return del()\"><button>ɾ��</button></a></td>");
									}else {//����Ϊ����Ϊδ���
										biao.append("<td >δ���</td><BR/>");
										biao.append("<td><a href=\"Sub?m="+month+"&m_num="+m_num+"&user="+user+"&aim=m\"><button>�ύ</button></a><BR/>");
										biao.append("<a href=\"Upd?m="+month+"&m_num="+m_num+"&user="+user+"&aim=m\"><button>�޸�</button></a><BR/>");
										biao.append("<a href=\"Del?m="+month+"&m_num="+m_num+"&user="+user+"&aim=m\" onclick=\"return del()\"><button>ɾ��</button></a></td>");
									}
								biao.append("</tr>");
							}
						biao.append("</table>");
						if("all".equals(m)) queryBean.setBkNews("������������ȫ������Ŀ�ꡣ(��ʱ��˳��)");
						else queryBean.setBkNews("��������"+m+"�µ�ȫ��Ŀ�ꡣ");
						queryBean.setBiao(biao);
						con.close();
						request.setAttribute("queryBean", queryBean);
						request.getRequestDispatcher("query.jsp").forward(request, response);
					}else {//��Ŀ�괦��
						if("all".equals(m)) queryBean.setBkNews("ĿǰΪֹ����û�ж�����Ŀ��Ŷ!");
						else queryBean.setBkNews("�����㻹û�ж���Ŀ��Ŷ��");
						con.close();
						request.setAttribute("queryBean", queryBean);
						request.getRequestDispatcher("query.jsp").forward(request, response);
					}
				}
				catch(Exception e) {System.out.print(e.toString());}
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
