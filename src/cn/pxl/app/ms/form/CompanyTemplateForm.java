package cn.pxl.app.ms.form;

import java.io.Serializable;

public class CompanyTemplateForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2351103782628899666L;
	private String id;
	// 模板名称
	private String name;
	// 模板宽度
	private Integer width;
	// 模板高度
	private Integer height;
	// 模板图片路径
	private String templateImagePath;
	
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
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getTemplateImagePath() {
		return templateImagePath;
	}
	public void setTemplateImagePath(String templateImagePath) {
		this.templateImagePath = templateImagePath;
	}

}
