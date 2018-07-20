package zhyu.uicode.login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpCookie;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zhyu.common.security.BaseController;
import zhyu.common.vo.Result;

@RequestMapping("/login")
@Controller()
public class LoginController extends BaseController {

    @Autowired
    @RequestMapping(value = "/loginWithRSA", method = RequestMethod.POST)
    public Result loginWithRSA(String username, String password, HttpSession
            session) throws Exception{
        username = decode(username);
        password = decode(password);
        return null;
    }
	@RequestMapping("/tokenLogin")
	public void tokenLogin(HttpServletRequest req, String pwd, String user, HttpServletResponse res) {
		if (pwd == null || pwd.equals("")) {
			// return "密码为空";
		}
		if (user == null || user.equals("")) {
			// return "用户名为空";
		}
		Integer count = null;
		if (user.equals("zhyu") && pwd.equals("123456")) {
			String id = UUID.randomUUID().toString();
			HttpCookie cookie = new HttpCookie("UUID", id);
			cookie.setMaxAge(60 * 60);
		}
		String word = "你登陆的次数为:" + count;
		System.out.println(word);

		try {
			// 设置页面不缓存
			res.setContentType("application/json");
			res.setHeader("Pragma", "No-cache");
			res.setHeader("Cache-Control", "no-cache");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			out = res.getWriter();
			out.print("word:" + word);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/sessionIndex")
	public String index(HttpServletRequest req, HttpSession session, HttpServletResponse res) {
		Integer count = (Integer) session.getAttribute("count");
		System.out.println(count);
		if (count == null) {
			String ip = req.getLocalAddr();
			int port = req.getLocalPort();
			String path = req.getServletContext().getContextPath();
			path = "http://" + ip + ":" + port  + path + "/html/login.html";
			System.out.println(path);
			return "redirect:" + path;
		}
		return "index";
	}

	@RequestMapping("/sessionLogin")
	@ResponseBody
	public String sessionLogin(Boolean rememberMe ,HttpServletRequest req, String pwd, String user, HttpServletResponse res) {
		if (pwd == null || pwd.equals("")) {
			// return "密码为空";
		}
		if (user == null || user.equals("")) {
			// return "用户名为空";
		}
		Integer count = null;
		if (rememberMe != null && rememberMe && user.equals("zhyu") && pwd.equals("123456")) {
			HttpSession session = req.getSession();
			count = (Integer) session.getAttribute("count");
			if (count == null) {
				count = 0;
			} else {
				count++;
			}
			session.setAttribute("count", count);
		}
		count = count == null?0:count;
		String word = "你登陆的次数为:" + count;
		System.out.println(word);
		return word;
	}
}
