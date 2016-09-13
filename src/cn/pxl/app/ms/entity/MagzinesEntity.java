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

@Entity
@Table(name = "tbl_magzines")
public class MagzinesEntity {

	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.pxl.app.ms.util.UUIDGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	private String name;
	private String fileName;
	private long fileSize;
	
	@ManyToOne(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
	private UserEntity userEntity;
	//刊物创建时间
	private Date uploadTime;
	//刊物名称
	private String tplName;
	//刊物心情
	private String fileComment;
	//免费开始时间
	private Date startTime;	
	//免费结束时间
	private Date endTime;	
	//刊物查看权限
	private Integer viewRoleSet;	
	//刊物所属类型 0： 个人，1：商店
	private Integer publicTypeSet;	
	//使用的模版类型
	private Integer publicTemplateType;
	//转发杂志ID
	private String zfMagId;
	//原始杂志ID
	private String firstMagId;
	//购买杂志ID
	private String buyMagId;
	//购买价格
	private BigDecimal fileAmount;
	//删除标示
	private int deleteFlag;
	//是否被拆分
	private int cfFlag;
	//是否正在被拆分
	private int cfStatus;
	
	//刊物类别ID 外键
	@ManyToOne(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER)
    @JoinColumn(name = "magzinesCategoryId")
	private MagzinesCategoryEntity magzinesCategoryEntity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getFileComment() {
		return fileComment;
	}

	public void setFileComment(String fileComment) {
		this.fileComment = fileComment;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getViewRoleSet() {
		return viewRoleSet;
	}

	public void setViewRoleSet(Integer viewRoleSet) {
		this.viewRoleSet = viewRoleSet;
	}

	public Integer getPublicTypeSet() {
		return publicTypeSet;
	}

	public void setPublicTypeSet(Integer publicTypeSet) {
		this.publicTypeSet = publicTypeSet;
	}

	public String getBuyMagId() {
		return buyMagId;
	}

	public void setBuyMagId(String buyMagId) {
		this.buyMagId = buyMagId;
	}

	public Integer getPublicTemplateType() {
		return publicTemplateType;
	}

	public void setPublicTemplateType(Integer publicTemplateType) {
		this.publicTemplateType = publicTemplateType;
	}

	public MagzinesCategoryEntity getMagzinesCategoryEntity() {
		return magzinesCategoryEntity;
	}

	public void setMagzinesCategoryEntity(
			MagzinesCategoryEntity magzinesCategoryEntity) {
		this.magzinesCategoryEntity = magzinesCategoryEntity;
	}

	public String getZfMagId() {
		return zfMagId;
	}

	public void setZfMagId(String zfMagId) {
		this.zfMagId = zfMagId;
	}

	public String getFirstMagId() {
		return firstMagId;
	}

	public void setFirstMagId(String firstMagId) {
		this.firstMagId = firstMagId;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public BigDecimal getFileAmount() {
		return fileAmount;
	}

	public void setFileAmount(BigDecimal fileAmount) {
		this.fileAmount = fileAmount;
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
