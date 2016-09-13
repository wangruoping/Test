package cn.pxl.app.ms.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author liuxin
 * 
 * 打印记录表
 * 
 * */
@Entity
@Table(name = "tbl_print")
public class PrintEntity {
	
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.pxl.app.ms.util.UUIDGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	
	//发送地址
	private String address;
	//联系电话
	private String phone;
	//邮编
	private String youbian;
	//打印状态
	private String status;
	//打印请求时间
	private Date printTime;		
	//打印杂志金额
	private BigDecimal rechargeAmount;
	//打印杂志ID
	private String magId;
	private String fileName;
	//删除标示
	private int deleteFlag;
	//是否被拆分
	private int cfFlag;
	//是否正在被拆分
	private int cfStatus;
	
	//打印用户ID 外键
	@ManyToOne(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER)
	@JoinColumn(name = "printUserId")
	private UserEntity userEntity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getMagId() {
		return magId;
	}

	public void setMagId(String magId) {
		this.magId = magId;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getCfFlag() {
		return cfFlag;
	}

	public void setCfFlag(int cfFlag) {
		this.cfFlag = cfFlag;
	}

	public int getCfStatus() {
		return cfStatus;
	}

	public void setCfStatus(int cfStatus) {
		this.cfStatus = cfStatus;
	}

}
