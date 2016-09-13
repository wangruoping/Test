package cn.pxl.app.ms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author liuxin
 * 
 * 用户信息表
 * 
 */
@Entity
@Table(name = "company_user")
public class CompanyUserEntity {

	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.pxl.app.ms.util.UUIDGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 图片地址
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

}
