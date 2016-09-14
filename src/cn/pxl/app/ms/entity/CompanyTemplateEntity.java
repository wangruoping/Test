package cn.pxl.app.ms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liuxin
 * 
 *         模板信息表
 * 
 */
@Entity
@Table(name = "company_template")
public class CompanyTemplateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
