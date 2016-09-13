package cn.pxl.app.ms.entity;

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
 * 用户评论信息表
 * 
 * */
@Entity
@Table(name = "tbl_comments")
public class CommentsEntity {
	
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.pxl.app.ms.util.UUIDGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	
	//评论用户ID 外键
	@ManyToOne(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER)
    @JoinColumn(name = "commentUserId")
	private UserEntity userEntity;
	//private String commentUserId;
	
	
	//刊物ID	 外键
	@ManyToOne(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER)
    @JoinColumn(name = "commentMagzinesId")
	private MagzinesEntity magzinesEntity;
	//private String commentPublishId;
	
	//评论内容
	private String commentContent;
	//评论时间
	private Date commentTime;

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

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	

}
