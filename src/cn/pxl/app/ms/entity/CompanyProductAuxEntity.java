package cn.pxl.app.ms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liuxin
 * 
 *         商品信息附表
 * 
 */
@Entity
@Table(name = "company_product_aux")
public class CompanyProductAuxEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	// 商品字段名
	private String name;
	// 商品字段显示名
	private String disname;
	// 是否显示该字段
	private Integer disen;
	// 商品字段显示下标，按从小到大
	private Integer disindex;

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

	public String getDisname() {
		return disname;
	}

	public void setDisname(String disname) {
		this.disname = disname;
	}

	public Integer getDisen() {
		return disen;
	}

	public void setDisen(Integer disen) {
		this.disen = disen;
	}

	public Integer getDisindex() {
		return disindex;
	}

	public void setDisindex(Integer disindex) {
		this.disindex = disindex;
	}

}
