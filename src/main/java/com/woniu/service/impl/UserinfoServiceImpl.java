package com.woniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woniu.mapper.UserinfoMapper;
import com.woniu.model.Userinfo;
import com.woniu.service.IUserinfoService;

@Service
public class UserinfoServiceImpl implements IUserinfoService {
	@Resource
	private UserinfoMapper userinfomapper;

	@Transactional
	public Userinfo Login(String uname, String upass) {
		Userinfo loginUser = userinfomapper.login(uname, upass);
		return loginUser;
	}

	@Transactional(readOnly=true)
	public List findAll() {
		List<Userinfo> list = userinfomapper.selectByExample(null);
		return list;
	}

}
