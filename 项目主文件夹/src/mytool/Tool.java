package mytool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mybean.LoginBean;
public class Tool {
	//中文编码处理方法
	 public static  String handleString(String s) {
		try {byte bb[] = s.getBytes("ISO-8859-1");
			s = new String(bb);}
		catch(Exception e){}
		return s;
	}
	//字母数字验证方法
	public static boolean verify(String s) {//验证字母、数字组合字符串
		  String regex = "^[a-z0-9A-Z]+$";
		  return s.matches(regex);
	}
	//验证字符串非空非NULL方法
	public static boolean isnull(String s) {//空串判断
		return s==null||s=="";
	}
	public static String handleDate(String s) throws ParseException {//日期字符串转文字
			Date d =  new SimpleDateFormat("yyyy-MM-dd").parse(s); 
			String ds = new SimpleDateFormat("yyyy年MM月dd日").format(d);
			return ds;
	}
	public static String getUser(HttpServletRequest request) {//根据请求request获取当前session和cookie中的用户名方法
		String user=null;
		HttpSession session = request.getSession(true);
		LoginBean loginBean= (LoginBean)session.getAttribute("loginBean");
		if(loginBean!=null) {//从session中获取用户信息
		user = loginBean.getUser();
		}else {//session没有则从Cookie中获取
			if(request.getCookies()!=null) {
			Cookie [] cs = request.getCookies();
			for(Cookie cookie : cs) {
				if("user".equals(cookie.getName())) {
					user = cookie.getValue();
				}
			}
		}
		}
		return user;
	}
	
	
}
