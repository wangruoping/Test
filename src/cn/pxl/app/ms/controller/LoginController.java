package cn.pxl.app.ms.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.pxl.app.ms.entity.CompanyUserEntity;
import cn.pxl.app.ms.service.UserService;

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
		
		CompanyUserEntity companyUserEntity = userService.getUserInfoByNameAndPass(username, password);
		if (companyUserEntity != null) {
			//登陆成功
			return "main";
		} else {
			//登陆失败
			return "index";
		}
	}
}