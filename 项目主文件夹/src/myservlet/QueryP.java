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

@WebServlet("/QueryP")
public class QueryP extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public QueryP() {
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
		if("p".equals(fid)) {//==============��Ŀ���ѯ===============
			//2.1���ղ�ѯ����
			String d1 = request.getParameter("d1");
			String d2 = request.getParameter("d2");
			if(Tool.isnull(d1)&&Tool.isnull(d2)) {//�ж��Ƿ�Ϊ��
				queryBean.setBkNews("��ѡ������������ٵ����ѯŶ��");
				request.setAttribute("queryBean", queryBean);
				request.getRequestDispatcher("query.jsp").forward(request, response);
			}
				//2.2�������ݿ���в�ѯ
				try {Class.forName("com.mysql.jdbc.Driver");}
				catch(Exception e) {queryBean.setBkNews(e.toString());}
				try {
					con = DriverManager.getConnection(uri,name,pass);
					//�ж��Ƿ�ȫĿ���ѯ
					if("all".equals(d1)) condition = "SELECT * FROM phaseplan WHERE u_name=? ORDER BY p_num ASC";
					else condition = "SELECT * FROM phaseplan WHERE u_name=? AND p_begin>? AND  p_end<? ";
					sql = con.prepareStatement(condition);
					sql.setString(1,user);
					if(!"all".equals(d1)) {
						sql.setString(2,d1);
						sql.setString(3,d2);
					}
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
						
						biao.append("<table border=\"1\" id=\"biao_p\">");
						biao.append("<tr><th id=\"th1\">ʱ�����޼����</th><th id=\"th2\">Ŀ��</th><th id=\"th3\">������</th><th id=\"th4\">����</th><th id=\"th5\">����</th></tr>");
							while(rs.next()) {
								String p_num = rs.getString(2);
								String p_begin = rs.getString(3);
								String p_end = rs.getString(4);
								String p_plan =rs.getString(5);
								String p_sum = rs.getString(6);
								int p_point = rs.getInt(7); 
								biao.append("<tr>");
									biao.append("<td>"+p_begin+"<BR/>"+p_end+"<br/>("+p_num+")</td>");
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+p_plan+"</textarea></td>");
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+(p_sum==null?"���޽�չ":p_sum)+"</textarea></td>");
									if(p_point!=0) {//������Ϊ��
										biao.append("<td>"+p_point+"</td>");
										biao.append("<td><a href=\"Del?p_num="+p_num+"&user="+user+"&aim=p\" onclick=\"return del()\"><button>ɾ��</button></a></td>");
									}else {//����Ϊ����Ϊδ���
										biao.append("<td>δ���</td><BR/>");
										biao.append("<td><a href=\"Sub?p_num="+p_num+"&user="+user+"&aim=p\"><button>�ύ</button></a><BR/>");
										biao.append("<a href=\"Upd?p_num="+p_num+"&user="+user+"&d1="+p_begin+"&d2="+p_end+"&aim=p\"><button>�޸�</button></a><BR/>");
										biao.append("<a href=\"Del?p_num="+p_num+"&user="+user+"&aim=p\" onclick=\"return del()\"><button>ɾ��</button></a></td>");
									}
								biao.append("</tr>");
							}
						biao.append("</table>");
						if("all".equals(d1)) queryBean.setBkNews("��������ȫ���Ľ׶���Ŀ�ꡣ");
						else queryBean.setBkNews("��������"+Tool.handleDate(d1)+"��"+Tool.handleDate(d2)+"֮��Ľ׶�Ŀ�ꡣ");
						queryBean.setBiao(biao);
						con.close();
						request.setAttribute("queryBean", queryBean);
						request.getRequestDispatcher("query.jsp").forward(request, response);
					}else {//��Ŀ�괦��
						if("all".equals(d1)) queryBean.setBkNews("ĿǰΪֹ����û�ж������׶�Ŀ��Ŷ!");
						else queryBean.setBkNews("�˶�ʱ�����㻹û�ж����׶�Ŀ��Ŷ��");
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
