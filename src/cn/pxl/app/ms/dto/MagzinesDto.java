package cn.pxl.app.ms.dto;



public class MagzinesDto {
	private String id;
	private String name;
	private String fileName;
	private long fileSize;
	private String userId;
	private String userName;
	private String userImgPath;
	private String uploadTime;
	private String tplName;
	private String fileComment;
	private String agreeCount;
	private String zfCount;
	private String commentCount;
	private String sex;
	private String shopFlag;
	//原始杂志ID
	private String firstMagId;
	
	//登陆用户是否点赞
	private String zanFlag;
	//转发标示
	private String zfMagId;
	//转发杂志
	private MagzinesDto magzinesDto;
	//产品类别名
	private String categoryName;
	//免费开始时间
	private String startTime;	
	//免费结束时间
	private String endTime;	
	//
	private String fileAmount;
	
	//是否被拆分
	private int cfFlag;
	//是否正在被拆分
	private int cfStatus;

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

	public String getUploadTime() {
		return uploadTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUploadTime(String uploadTime) {
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

	public String getAgreeCount() {
		return agreeCount;
	}

	public void setAgreeCount(String agreeCount) {
		this.agreeCount = agreeCount;
	}

	public String getZfCount() {
		return zfCount;
	}

	public void setZfCount(String zfCount) {
		this.zfCount = zfCount;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	public String getUserImgPath() {
		return userImgPath;
	}

	public void setUserImgPath(String userImgPath) {
		this.userImgPath = userImgPath;
	}

	public String getZfMagId() {
		return zfMagId;
	}

	public void setZfMagId(String zfMagId) {
		this.zfMagId = zfMagId;
	}

	public MagzinesDto getMagzinesDto() {
		return magzinesDto;
	}

	public void setMagzinesDto(MagzinesDto magzinesDto) {
		this.magzinesDto = magzinesDto;
	}

	public String getZanFlag() {
		return zanFlag;
	}

	public void setZanFlag(String zanFlag) {
		this.zanFlag = zanFlag;
	}

	public String getFirstMagId() {
		return firstMagId;
	}

	public void setFirstMagId(String firstMagId) {
		this.firstMagId = firstMagId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFileAmount() {
		return fileAmount;
	}

	public void setFileAmount(String fileAmount) {
		this.fileAmount = fileAmount;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getShopFlag() {
		return shopFlag;
	}

	public void setShopFlag(String shopFlag) {
		this.shopFlag = shopFlag;
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
