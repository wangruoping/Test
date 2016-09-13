package cn.pxl.app.ms.entity;

import java.math.BigDecimal;
import java.util.Date;

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
 * */
@Entity
@Table(name = "tbl_user")
public class UserEntity {

	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.pxl.app.ms.util.UUIDGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	//用户名
	private String username;
	//密码
	private String password;
	//图片地址
	private String picPath;
	//用户收货地址
	private String address;
	//性别
	private String sex;
	//联系电话
	private String phone;
	//邮编
	private String youbian;
	//用户余额
	private BigDecimal amount;
	//账户
	private String acount;
	//第三方用户ID
	private String threeUserId;
	//第三方用户地址
	private String userAddress;
	//用户区分
	private String loginType;
	//创建时间
	private Date createDate;
	
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

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public String getThreeUserId() {
		return threeUserId;
	}

	public void setThreeUserId(String threeUserId) {
		this.threeUserId = threeUserId;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getYoubian() {
		return youbian;
	}

	public void setYoubian(String youbian) {
		this.youbian = youbian;
	}
	
	

}
