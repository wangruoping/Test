package cn.pxl.app.ms.dto;

public class UserDto {
	
	//用户ID
	private String userId;
	//用户名
	private String username;
	//用户头像地址
	private String userImgPath;
	//用户地址
	private String address;
	//第三方用户地址
	private String userAddress;
	//用户联系电话
	private String phone;
	//用户邮编
	private String youbian;
	//用户性别
	private String sex;
	//用户第三方ID
	private String userThridId;
	//0:A关注B,1:B关注,2:A,B互相关注
	private String gzFlag;
	//粉丝数
	private String fansCount;
	//关注数
	private String lookCount;
	//杂志数
	private String booksCount;
	//用户余额
	private String amount;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserImgPath() {
		return userImgPath;
	}
	public void setUserImgPath(String userImgPath) {
		this.userImgPath = userImgPath;
	}
	public String getGzFlag() {
		return gzFlag;
	}
	public void setGzFlag(String gzFlag) {
		this.gzFlag = gzFlag;
	}
	public String getFansCount() {
		return fansCount;
	}
	public void setFansCount(String fansCount) {
		this.fansCount = fansCount;
	}
	public String getLookCount() {
		return lookCount;
	}
	public void setLookCount(String lookCount) {
		this.lookCount = lookCount;
	}
	public String getBooksCount() {
		return booksCount;
	}
	public void setBooksCount(String booksCount) {
		this.booksCount = booksCount;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUserThridId() {
		return userThridId;
	}
	public void setUserThridId(String userThridId) {
		this.userThridId = userThridId;
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
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	
}
