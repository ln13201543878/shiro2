package com.woniu.service;

import java.util.List;

import com.woniu.model.Userinfo;

public interface IUserinfoService {
	Userinfo Login(String uname, String upass);

	List findAll();
}
