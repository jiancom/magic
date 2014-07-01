package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONType;
import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Dict;
import com.resgain.base.annotation.Label;
import com.resgain.base.service.DeptService;
import com.resgain.dragon.annotation.HtmlParameter;
import com.resgain.dragon.annotation.HtmlParameter.LEVEL;

/**
 * 员工信息实体类
 * @author gyl
 */
@Entity
@Table(name = "BASE_USER")
@Desc("用户信息")
@JSONType(ignores = {"dept"})
public class User extends AbstractPersistentObject
{
    private static final long serialVersionUID = 1L;

    @Column(name="CODE")
	@Label(name = "用户代码", nullFlag = false, sortable = true)
	private String code;

    @Column(name="PASS")
	@Label(name = "登录密码", nullFlag = false)
	private String pass;

    @Column(name="NAME")
	@Label(name = "姓名", nullFlag = false, sortable = true)
	private String name;

    @Column(name="STATUS")
	@Label(name = "状态", value = "0", nullFlag = false, sortable = true, xtype = "combo")
	@Dict(category = "USER_STATUS")
	private int status = 0;// 0--正常 1--密码锁定 3--已离职

    @Column(name="DEPT_ID")
	@Label(name = "所属部门", nullFlag = false, sortable = true, xtype = "combo")
	@Dict(category = "BASE_DEPT", impl = DeptService.class)
	private String deptId;

    @Column(name="TEL")
	@Label(name = "电话号码", nullFlag = true, sortable = true)
	private String tel;

    @Column(name="MOBILE")
	@Label(name = "手机号码", nullFlag = true, sortable = true)
	private String mobile;

    @Column(name="REMARK")
	@Label(name = "备注", nullFlag = true, xtype = "memo")
	@HtmlParameter(LEVEL.Normal)
	private String remark;


	public User() {
		super();
	}

	public User(String deptId, String code, String pass, String name) {
		super();
		this.deptId = deptId;
		this.code = code;
		this.pass = pass;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}