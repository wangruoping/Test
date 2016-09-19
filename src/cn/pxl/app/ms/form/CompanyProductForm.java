package cn.pxl.app.ms.form;

public class CompanyProductForm {

	private String id;
	// 商品编码
	private String code;
	// 是否是新加的记录，不是得话为null
	private Integer isNewRecord;
	private Integer status;
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIsNewRecord() {
		return isNewRecord;
	}

	public void setIsNewRecord(Integer isNewRecord) {
		this.isNewRecord = isNewRecord;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
