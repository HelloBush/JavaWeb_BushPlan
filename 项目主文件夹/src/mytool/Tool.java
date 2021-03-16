package mytool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mybean.LoginBean;
public class Tool {
	//���ı��봦����
	 public static  String handleString(String s) {
		try {byte bb[] = s.getBytes("ISO-8859-1");
			s = new String(bb);}
		catch(Exception e){}
		return s;
	}
	//��ĸ������֤����
	public static boolean verify(String s) {//��֤��ĸ����������ַ���
		  String regex = "^[a-z0-9A-Z]+$";
		  return s.matches(regex);
	}
	//��֤�ַ����ǿշ�NULL����
	public static boolean isnull(String s) {//�մ��ж�
		return s==null||s=="";
	}
	public static String handleDate(String s) throws ParseException {//�����ַ���ת����
			Date d =  new SimpleDateFormat("yyyy-MM-dd").parse(s); 
			String ds = new SimpleDateFormat("yyyy��MM��dd��").format(d);
			return ds;
	}
	public static String getUser(HttpServletRequest request) {//��������request��ȡ��ǰsession��cookie�е��û�������
		String user=null;
		HttpSession session = request.getSession(true);
		LoginBean loginBean= (LoginBean)session.getAttribute("loginBean");
		if(loginBean!=null) {//��session�л�ȡ�û���Ϣ
		user = loginBean.getUser();
		}else {//sessionû�����Cookie�л�ȡ
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
