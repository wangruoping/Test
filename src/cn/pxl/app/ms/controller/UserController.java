package cn.pxl.app.ms.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import cn.pxl.app.ms.service.UserService;

@Controller
public class UserController {

	private Logger log = Logger.getLogger(UserController.class);
	
	@Resource(name="userService")
	private UserService userService;
	

	 
}
