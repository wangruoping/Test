package cn.pxl.app.ms.dto;


public class CommentsDto {
	
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
	private String commentTime;
	
	//杂志心情
	private String fileComment;
	//杂志ID
	private String magId;
	
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
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public String getFileComment() {
		return fileComment;
	}
	public void setFileComment(String fileComment) {
		this.fileComment = fileComment;
	}
	public String getMagId() {
		return magId;
	}
	public void setMagId(String magId) {
		this.magId = magId;
	}

}
