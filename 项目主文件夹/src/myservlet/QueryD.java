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
		if("d".equals(fid)) {//==============日目标查询===============
			//2.1接收查询参数
			String d1 = request.getParameter("d1");
			if(Tool.isnull(d1)) {//判断是否为空
				queryBean.setBkNews("请选择查询日期后再点击查询哦！");
				request.setAttribute("queryBean", queryBean);
				request.getRequestDispatcher("query.jsp").forward(request, response);
			}
				//2.2连接数据库进行查询
				try {Class.forName("com.mysql.jdbc.Driver");}
				catch(Exception e) {queryBean.setBkNews(e.toString());}
				try {
					con = DriverManager.getConnection(uri,name,pass);
					//判断是否全目标查询
					if("all".equals(d1)) condition = "SELECT * FROM dayplan WHERE u_name=? ORDER BY d_num ASC";
					else condition = "SELECT * FROM dayplan WHERE u_name=? AND d_num=? ORDER BY d_num ASC";
					sql = con.prepareStatement(condition);
					sql.setString(1,user);
					if(!"all".equals(d1))sql.setString(2,d1);
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
						
						biao.append("<table border=\"1\" id=\"biao_d\">");
						
						biao.append("<tr><th id=\"th1\">日期</th><th id=\"th2\">目标</th><th id=\"th3\">完成情况</th><th id=\"th4\">评分</th><th id=\"th5\">操作</th></tr>");
							while(rs.next()) {
								biao.append("<tr>");
								String d_num= rs.getString(2);
								String d_plan = rs.getString(3);
								String d_sum = rs.getString(4);
								int d_point = rs.getInt("d_point");
									biao.append("<td>"+d_num+"</td>");
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+d_plan+"</textarea></td>");
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+(d_sum==null?"暂无进展":d_sum)+"</textarea></td>");
									if(d_point!=0) {//分数不为零
										biao.append("<td>"+d_point+"</td>");
										biao.append("<td><a href=\"Del?d1="+d_num+"&user="+user+"&aim=d\" onclick=\"return del()\"><button >删除</button></a></td>");
									}else {//分数为零设为未完成
										biao.append("<td>未完成</td><BR/>");
										biao.append("<td><a href=\"Sub?d1="+d_num+"&user="+user+"&aim=d\"><button>提交</button></a><BR/>");
										biao.append("<a href=\"Upd?d1="+d_num+"&user="+user+"&aim=d\"><button>修改</button></a><BR/>");
										biao.append("<a href=\"Del?d1="+d_num+"&user="+user+"&aim=d\" onclick=\"return del()\"><button>删除</button></a></td>");
									}
								biao.append("</tr>");
							}
						biao.append("</table>");
						if("all".equals(d1)) queryBean.setBkNews("以下是您全部的日目标。(按时间顺序)");
						else queryBean.setBkNews("以下是你"+Tool.handleDate(d1)+"的日目标。");
						queryBean.setBiao(biao);
						con.close();
						request.setAttribute("queryBean", queryBean);
						request.getRequestDispatcher("query.jsp").forward(request, response);
					}else {//无目标处理
						if("all".equals(d1)) queryBean.setBkNews("目前为止您还没有订立日目标哦!");
						else queryBean.setBkNews("此日你还没有订立目标哦！");
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
