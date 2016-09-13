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
 * @author liux
 * 
 * 消费记录表
 * 
 * */

@Entity
@Table(name = "tbl_consume")
public class ConsumeEntity {
	
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.pxl.app.ms.util.UUIDGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	
	//消费用户ID 外键
	@ManyToOne(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER)
    @JoinColumn(name = "consumeUserId")
	private UserEntity userEntity;
	
	//消费刊物ID	 外键
	@ManyToOne(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER)
    @JoinColumn(name = "consumeMagzinesId")
	private MagzinesEntity magzinesEntity;
	
	//消费时间
	private Date consumeTime;	
	
	//消费金额
	private BigDecimal consumeAmount;
	
	//消费标示
	private String consumeFLag;
	
	//消费状态 1 加钱 2 花钱
	private String consumeStatus;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	public Date getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}
	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}
	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}
	public MagzinesEntity getMagzinesEntity() {
		return magzinesEntity;
	}
	public void setMagzinesEntity(MagzinesEntity magzinesEntity) {
		this.magzinesEntity = magzinesEntity;
	}
	public String getConsumeFLag() {
		return consumeFLag;
	}
	public void setConsumeFLag(String consumeFLag) {
		this.consumeFLag = consumeFLag;
	}
	public String getConsumeStatus() {
		return consumeStatus;
	}
	public void setConsumeStatus(String consumeStatus) {
		this.consumeStatus = consumeStatus;
	}
	
}
