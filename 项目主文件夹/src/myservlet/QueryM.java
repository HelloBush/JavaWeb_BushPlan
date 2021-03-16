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
		//1获取用户信息和创建公共信息
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
		if(loginBean!=null) {//从session中获取用户信息
		user = loginBean.getUser();
		}else {//session没有则从Cookie中获取
			Cookie [] cs = request.getCookies();
			for(Cookie cookie : cs) {
				if("user".equals(cookie.getName())) {
					user = cookie.getValue();
				}
				
			}
		}
		//2接收表单区分值，进行不同处理
		String fid = request.getParameter("fid");
		if("m".equals(fid)) {//==============月目标查询===============
			//2.1接收查询参数
			String m = request.getParameter("m");
			if(Tool.isnull(m)) {//判断是否为空
				queryBean.setBkNews("请选择查询月份后再点击查询哦！");
				request.setAttribute("queryBean", queryBean);
				request.getRequestDispatcher("query.jsp").forward(request, response);
			}
				//2.2连接数据库进行查询
				try {Class.forName("com.mysql.jdbc.Driver");}
				catch(Exception e) {queryBean.setBkNews(e.toString());}
				try {
					con = DriverManager.getConnection(uri,name,pass);
					//判断是否全目标查询
					if("all".equals(m)) condition = "SELECT * FROM monthplan WHERE u_name=? ORDER BY m ASC,m_num ASC";
					else condition = "SELECT * FROM monthplan WHERE u_name=? AND  m=? ORDER BY m_num ASC";
					sql = con.prepareStatement(condition);
					sql.setString(1,user);
					if(!"all".equals(m))sql.setString(2,m);
					ResultSet rs = sql.executeQuery();
					//空表判断
					rs.last();
					int i = rs.getRow();
					rs.absolute(0);
					if(request.getAttribute("deleteBean")!=null) {//删除反馈
						DeleteBean deleteBean = (DeleteBean)request.getAttribute("deleteBean");
						String s = "<br/><span id=\"bkNews_d\">"+deleteBean.getBkNews()+"</span>";
						biao.append(s);
					}
					//2.3查询结果处理：有则存入Bean、无则返回信息；
					if(i!=0) {//有目标处理
						biao.append("<table border=\"1\" id=\"biao_m\">");
						biao.append("<tr><th id=\"th1\">月目标序号</th><th id=\"th2\">目标</th><th id=\"tg3\">完成情况</th><th id=\"th4\">评分</th><th id=\"th5\">操作</th></tr>");
							while(rs.next()) {
								biao.append("<tr>");
								String m_num = rs.getString(2);
								String month = rs.getString(3);
								String m_plan = rs.getString(4);
								String m_sum = rs.getString(5);
								int m_point = rs.getInt("m_point");
									biao.append("<td>"+month+"-"+m_num+"</td>");
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+m_plan+"</textarea></td>");
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+(m_sum==null?"暂无进展":m_sum)+"</textarea></td>");
									if(m_point!=0) {//分数不为零
										biao.append("<td>"+m_point+"</td>");
										biao.append("<td><a href=\"Del?m="+month+"&m_num="+m_num+"&user="+user+"&aim=m\" onclick=\"return del()\"><button>删除</button></a></td>");
									}else {//分数为零设为未完成
										biao.append("<td >未完成</td><BR/>");
										biao.append("<td><a href=\"Sub?m="+month+"&m_num="+m_num+"&user="+user+"&aim=m\"><button>提交</button></a><BR/>");
										biao.append("<a href=\"Upd?m="+month+"&m_num="+m_num+"&user="+user+"&aim=m\"><button>修改</button></a><BR/>");
										biao.append("<a href=\"Del?m="+month+"&m_num="+m_num+"&user="+user+"&aim=m\" onclick=\"return del()\"><button>删除</button></a></td>");
									}
								biao.append("</tr>");
							}
						biao.append("</table>");
						if("all".equals(m)) queryBean.setBkNews("以下是您今年全部的月目标。(按时间顺序)");
						else queryBean.setBkNews("以下是你"+m+"月的全部目标。");
						queryBean.setBiao(biao);
						con.close();
						request.setAttribute("queryBean", queryBean);
						request.getRequestDispatcher("query.jsp").forward(request, response);
					}else {//无目标处理
						if("all".equals(m)) queryBean.setBkNews("目前为止您还没有订立月目标哦!");
						else queryBean.setBkNews("此月你还没有订立目标哦！");
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
