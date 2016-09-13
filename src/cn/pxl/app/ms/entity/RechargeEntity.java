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
 * 用户充值记录表
 * 
 * */
@Entity
@Table(name = "tbl_recharge")
public class RechargeEntity {
	
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.pxl.app.ms.util.UUIDGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	
	//充值时间
	private Date rechargeTime;		
	//充值类型
	private Integer rechargeType;	
	//充值金额
	private BigDecimal rechargeAmount;
	
	//
	private String rechargeFlag;
	
	//充值用户ID 外键
	@ManyToOne(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER)
    @JoinColumn(name = "rechargeUserId")
	private UserEntity userEntity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public Integer getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getRechargeFlag() {
		return rechargeFlag;
	}

	public void setRechargeFlag(String rechargeFlag) {
		this.rechargeFlag = rechargeFlag;
	}

}
