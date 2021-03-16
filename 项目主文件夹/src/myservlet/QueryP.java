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
		if("p".equals(fid)) {//==============日目标查询===============
			//2.1接收查询参数
			String d1 = request.getParameter("d1");
			String d2 = request.getParameter("d2");
			if(Tool.isnull(d1)&&Tool.isnull(d2)) {//判断是否为空
				queryBean.setBkNews("请选择日期区间后再点击查询哦！");
				request.setAttribute("queryBean", queryBean);
				request.getRequestDispatcher("query.jsp").forward(request, response);
			}
				//2.2连接数据库进行查询
				try {Class.forName("com.mysql.jdbc.Driver");}
				catch(Exception e) {queryBean.setBkNews(e.toString());}
				try {
					con = DriverManager.getConnection(uri,name,pass);
					//判断是否全目标查询
					if("all".equals(d1)) condition = "SELECT * FROM phaseplan WHERE u_name=? ORDER BY p_num ASC";
					else condition = "SELECT * FROM phaseplan WHERE u_name=? AND p_begin>? AND  p_end<? ";
					sql = con.prepareStatement(condition);
					sql.setString(1,user);
					if(!"all".equals(d1)) {
						sql.setString(2,d1);
						sql.setString(3,d2);
					}
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
						
						biao.append("<table border=\"1\" id=\"biao_p\">");
						biao.append("<tr><th id=\"th1\">时间期限及序号</th><th id=\"th2\">目标</th><th id=\"th3\">完成情况</th><th id=\"th4\">评分</th><th id=\"th5\">操作</th></tr>");
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
									biao.append("<td><textarea rows=\"5\" cols=\"30\" disabled=\"false\">"+(p_sum==null?"暂无进展":p_sum)+"</textarea></td>");
									if(p_point!=0) {//分数不为零
										biao.append("<td>"+p_point+"</td>");
										biao.append("<td><a href=\"Del?p_num="+p_num+"&user="+user+"&aim=p\" onclick=\"return del()\"><button>删除</button></a></td>");
									}else {//分数为零设为未完成
										biao.append("<td>未完成</td><BR/>");
										biao.append("<td><a href=\"Sub?p_num="+p_num+"&user="+user+"&aim=p\"><button>提交</button></a><BR/>");
										biao.append("<a href=\"Upd?p_num="+p_num+"&user="+user+"&d1="+p_begin+"&d2="+p_end+"&aim=p\"><button>修改</button></a><BR/>");
										biao.append("<a href=\"Del?p_num="+p_num+"&user="+user+"&aim=p\" onclick=\"return del()\"><button>删除</button></a></td>");
									}
								biao.append("</tr>");
							}
						biao.append("</table>");
						if("all".equals(d1)) queryBean.setBkNews("以下是您全部的阶段性目标。");
						else queryBean.setBkNews("以下是你"+Tool.handleDate(d1)+"到"+Tool.handleDate(d2)+"之间的阶段目标。");
						queryBean.setBiao(biao);
						con.close();
						request.setAttribute("queryBean", queryBean);
						request.getRequestDispatcher("query.jsp").forward(request, response);
					}else {//无目标处理
						if("all".equals(d1)) queryBean.setBkNews("目前为止您还没有订立过阶段目标哦!");
						else queryBean.setBkNews("此段时间内你还没有订立阶段目标哦！");
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
