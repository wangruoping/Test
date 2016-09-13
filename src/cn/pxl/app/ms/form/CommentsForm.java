package cn.pxl.app.ms.form;

import java.io.Serializable;
import java.util.Date;

public class CommentsForm implements Serializable{
	

	private static final long serialVersionUID = -4587788687608011296L;
	private String id;
	//评论内容
	private String commentContent;
	//评论人ID
	private String commentUserId;
	//评论人名称
	private String commentUserName;
	//评论人头像地址
	private String commentUserImgPath;
	//评论时间
	private Date commentTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentUserId() {
		return commentUserId;
	}
	public void setCommentUserId(String commentUserId) {
		this.commentUserId = commentUserId;
	}
	public String getCommentUserName() {
		return commentUserName;
	}
	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}
	public String getCommentUserImgPath() {
		return commentUserImgPath;
	}
	public void setCommentUserImgPath(String commentUserImgPath) {
		this.commentUserImgPath = commentUserImgPath;
	}
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
}
