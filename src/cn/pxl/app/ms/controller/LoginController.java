package cn.pxl.app.ms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.pxl.app.ms.entity.CompanyUserEntity;
import cn.pxl.app.ms.service.UserService;

@Controller
public class LoginController {

	@Resource(name = "userService")
	private UserService userService;

	@RequestMapping("/index.html")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/login.html", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password, HttpSession session) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		CompanyUserEntity companyUserEntity = userService.getUserInfoByNameAndPass(username, password);
		if (companyUserEntity != null) {
			session.setAttribute("userInfo", companyUserEntity);
			// 登陆成功
			map.put("userInfo", companyUserEntity);
			return new ModelAndView("main", map);
		} else {
			// 登陆失败
			return new ModelAndView("index", map);
		}
	}
}