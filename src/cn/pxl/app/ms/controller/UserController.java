package cn.pxl.app.ms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.ResultDto;
import cn.pxl.app.ms.dto.UserDto;
import cn.pxl.app.ms.entity.CompanyUserEntity;
import cn.pxl.app.ms.form.CompanyUserForm;
import cn.pxl.app.ms.service.UserService;
import cn.pxl.app.ms.util.CommonUtils;

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

	/**
	 * 获取用户信息
	 * 
	 */
	@RequestMapping(value = "getUserInfo")
	public @ResponseBody String getUserInfo(HttpSession session,
			@RequestParam("userid") String userid) {
		ResultDto rd = new ResultDto();
		CompanyUserEntity companyUserEntity = userService.getUserInfo(userid);
		if (companyUserEntity != null) {
			UserDto userDto = new UserDto();
			userDto.setUserId(companyUserEntity.getId());
			userDto.setUsername(companyUserEntity.getUsername());
			rd.setStatus("1");
			rd.setContent(userDto);
		} else {
			rd.setStatus("0");
		}
		return CommonUtils.convertResult(rd, session);
	}
	
	/**
     * 用户信息删除
     * 
     * */
    @RequestMapping(value="deleteUserList")
    public @ResponseBody String deleteUserList(HttpSession session,
            @RequestParam("userIds") String userIds){
    	
    	ResultDto rd = new ResultDto();	
		String[] userIdStrings = userIds.split("@");
		if(userIdStrings.length > 0 && userService.deleteUserList(userIdStrings)){				
			rd.setStatus("1");
			rd.setContent("");
		}else{
			rd.setStatus("0");
			rd.setContent("");
		}
		return CommonUtils.convertResult(rd, session);
    }

	/**
	 * 用户信息添加/修改
	 * 
	 */
	@RequestMapping(value = "userInfoHanlde", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String userInfoHanlde(HttpSession session,
			@ModelAttribute("companyUserForm") CompanyUserForm companyUserForm) {
		
		ResultDto rd = new ResultDto();
		if (companyUserForm.getId() != null && !"".equals(companyUserForm.getId())) {
			// 更新
			CompanyUserEntity companyUserEntity = userService
					.getUserInfo(companyUserForm.getId());
			if (companyUserEntity != null) {
				//判断用户名是否存在
				if(companyUserEntity.getUsername().equals(companyUserForm.getUsername())){
					CompanyUserEntity geCompanyUserEntity = userService.getUserInfoByName(companyUserForm.getUsername());
					if(geCompanyUserEntity != null){
						rd.setStatus("3");
						rd.setContent("");
						return CommonUtils.convertResult(rd, session);
					}
				}
				companyUserEntity.setUsername(companyUserForm.getUsername());
				if (userService.updateUser(companyUserEntity)) {
					rd.setStatus("1");
					rd.setContent("");
				} else {
					rd.setStatus("0");
					rd.setContent("");
				}
			} else {
				rd.setStatus("2");
				rd.setContent("");
			}
		} else {
			//判断用户名是否存在
			CompanyUserEntity geCompanyUserEntity = userService.getUserInfoByName(companyUserForm.getUsername());
			if(geCompanyUserEntity != null){
				rd.setStatus("3");
				rd.setContent("");
				return CommonUtils.convertResult(rd, session);
			}
			// 添加
			CompanyUserEntity companyUserEntity = new CompanyUserEntity();
			companyUserEntity.setUsername(companyUserForm.getUsername());
			companyUserEntity.setPassword(CommonUtils.encodePassword("123456"));
			companyUserEntity.setAdmin("0");
			if (userService.addUser(companyUserEntity)) {
				rd.setStatus("1");
				rd.setContent("");
			} else {
				rd.setStatus("0");
				rd.setContent("");
			}
		}
		return CommonUtils.convertResult(rd, session);
	}

}
