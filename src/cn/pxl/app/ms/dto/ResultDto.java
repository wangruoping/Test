package cn.pxl.app.ms.dto;


public class ResultDto {
	private String status;
	private Object content;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
}
