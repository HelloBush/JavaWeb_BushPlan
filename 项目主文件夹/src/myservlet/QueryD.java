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

@WebServlet("/QueryD")
public class QueryD extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public QueryD() {
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
		if("d".equals(fid)) {//==============��Ŀ���ѯ===============
			//2.1���ղ�ѯ����
			String d1 = request.getParameter("d1");
			if(Tool.isnull(d1)) {//�ж��Ƿ�Ϊ��
				queryBean.setBkNews("��ѡ���ѯ���ں��ٵ����ѯŶ��");
				request.setAttribute("queryBean", queryBean);
				request.getRequestDispatcher("query.jsp").forward(request, response);
			}
				//2.2�������ݿ���в�ѯ
				try {Class.forName("com.mysql.jdbc.Driver");}
				catch(Exception e) {queryBean.setBkNews(e.toString());}
				try {
					con = DriverManager.getConnection(uri,name,pass);
					//�ж��Ƿ�ȫĿ���ѯ
					if("all".equals(d1)) condition = "SELECT * FROM dayplan WHERE u_name=? ORDER BY d_num ASC";
					else condition = "SELECT * FROM dayplan WHERE u_name=? AND d_num=? ORDER BY d_num ASC";
					sql = con.prepareStatement(condition);
					sql.setString(1,user);
					if(!"all".equals(d1))sql.setString(2,d1);
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
						
						biao.append("<table border=\"1\" id=\"biao_d\">");
						
						biao.append("<tr><th id=\"th1\">����</th><th id=\"th2\">Ŀ��</th><th id=\"th3\">������</th><th id=\"th4\">����</th><th id=\"th5\">����</th></tr>");
							while(rs.next()) {
								biao.append("<tr>");
								String d_num= rs.getString(2);
								String d_plan = rs.getString(3);
								String d_sum = rs.getString(4);
								int d_point = rs.getInt("d_point");
									biao.append("<td>"+d_num+"</td>");
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+d_plan+"</textarea></td>");
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+(d_sum==null?"���޽�չ":d_sum)+"</textarea></td>");
									if(d_point!=0) {//������Ϊ��
										biao.append("<td>"+d_point+"</td>");
										biao.append("<td><a href=\"Del?d1="+d_num+"&user="+user+"&aim=d\" onclick=\"return del()\"><button >ɾ��</button></a></td>");
									}else {//����Ϊ����Ϊδ���
										biao.append("<td>δ���</td><BR/>");
										biao.append("<td><a href=\"Sub?d1="+d_num+"&user="+user+"&aim=d\"><button>�ύ</button></a><BR/>");
										biao.append("<a href=\"Upd?d1="+d_num+"&user="+user+"&aim=d\"><button>�޸�</button></a><BR/>");
										biao.append("<a href=\"Del?d1="+d_num+"&user="+user+"&aim=d\" onclick=\"return del()\"><button>ɾ��</button></a></td>");
									}
								biao.append("</tr>");
							}
						biao.append("</table>");
						if("all".equals(d1)) queryBean.setBkNews("��������ȫ������Ŀ�ꡣ(��ʱ��˳��)");
						else queryBean.setBkNews("��������"+Tool.handleDate(d1)+"����Ŀ�ꡣ");
						queryBean.setBiao(biao);
						con.close();
						request.setAttribute("queryBean", queryBean);
						request.getRequestDispatcher("query.jsp").forward(request, response);
					}else {//��Ŀ�괦��
						if("all".equals(d1)) queryBean.setBkNews("ĿǰΪֹ����û�ж�����Ŀ��Ŷ!");
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
