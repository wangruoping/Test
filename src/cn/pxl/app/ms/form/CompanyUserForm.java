package cn.pxl.app.ms.form;

import java.io.Serializable;

public class CompanyUserForm implements Serializable {

	private static final long serialVersionUID = -4587788687608011296L;
	private String id;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 是否是管理员
	private String admin;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
