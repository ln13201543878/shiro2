package com.woniu.model;

import java.util.List;
import java.util.Set;

public class Userinfo {
	private Integer uid;

	private String uname;

	private String upass;

	private List roles;

	private List trees;

	public List getRoles() {
		return roles;
	}

	public void setRoles(List roles) {
		this.roles = roles;
	}

	public List getTrees() {
		return trees;
	}

	public void setTrees(List trees) {
		this.trees = trees;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpass() {
		return upass;
	}

	public void setUpass(String upass) {
		this.upass = upass;
	}
}