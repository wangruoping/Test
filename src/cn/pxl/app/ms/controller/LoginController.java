package cn.pxl.app.ms.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.pxl.app.ms.service.UserService;
import cn.pxl.app.ms.util.CommonUtils;

@Controller
public class LoginController {

	@Resource(name="userService")
	private UserService userService;

	@RequestMapping("/index.html")
	public String index() {
		return "index";
	}

	@RequestMapping(value="/login.html", method=RequestMethod.POST)
	public String login(@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", required=true) String password) {
		
		String aau = CommonUtils.getConfig("account.admin.username");
		String aap = CommonUtils.getConfig("account.admin.password");
		
		if (aau.equals(username) && aap.equals(password)) {
			return "main";
		} else {
			return "index";
		}
	}
}