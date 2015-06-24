/**
 * 
 */
package com.wonders.attach.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: DeptCode 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月23日 上午10:20:10 
 *  
 */
@Entity
@Table(name = "T_DEPT_CODE")
public class DeptCode implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8533676647321012224L;
	private String id;
	private String code;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "DEPT_CODE", length = 50)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
