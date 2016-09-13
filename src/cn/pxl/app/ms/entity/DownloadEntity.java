package cn.pxl.app.ms.entity;

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
 * 杂志下载信息表
 * 
 * */
@Entity
@Table(name = "tbl_download")
public class DownloadEntity {

	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.pxl.app.ms.util.UUIDGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	
	//下载用户ID 外键
	@ManyToOne(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER)
    @JoinColumn(name = "downloadUserId")
	private UserEntity userEntity;
	
	//刊物ID	 外键
	@ManyToOne(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER)
    @JoinColumn(name = "downloadMagzinesId")
	private MagzinesEntity magzinesEntity;

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

	public MagzinesEntity getMagzinesEntity() {
		return magzinesEntity;
	}

	public void setMagzinesEntity(MagzinesEntity magzinesEntity) {
		this.magzinesEntity = magzinesEntity;
	}
	
}
