package cn.pxl.app.ms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.UserDto;
import cn.pxl.app.ms.service.UserService;

@Controller
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);

	@Resource(name = "userService")
	private UserService userService;

	/** 跳转到用户管理页 */
	@RequestMapping("/usersInfo")
	public ModelAndView usersInfo(HttpSession session) {
		logger.info("跳转到用户管理页面");
		Map<String, String> map = new HashMap<String, String>();
		return new ModelAndView("usersInfo", map);
	}

	/**
	 * 用户列表
	 * 
	 */
	@RequestMapping(value = "userList")
	public @ResponseBody PagingDto<UserDto> userList(@RequestParam("page") String page,
			@RequestParam("rows") String rows) {
		logger.info("获取用户分页列表");
		int offset = Integer.parseInt(page);
		int limit = Integer.parseInt(rows);

		return userService.pagingList(offset, limit, 0);
	}

}
