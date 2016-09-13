package cn.pxl.app.ms.dto;

import java.math.BigDecimal;

public class PrintDto {
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
		private String printTime;		
		//打印杂志金额
		private BigDecimal rechargeAmount;
		//打印杂志ID
		private String magId;
		//文件名
				private String fileName;
		private String username;
		//是否被拆分
		private int cfFlag;
		//是否正在被拆分
		private int cfStatus;
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
		public String getPrintTime() {
			return printTime;
		}
		public void setPrintTime(String printTime) {
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
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
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
