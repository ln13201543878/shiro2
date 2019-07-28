package com.woniu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woniu.model.Userinfo;
import com.woniu.service.IUserinfoService;

@RestController
@RequestMapping("/login/")
public class UserinfoController {
	@Resource
	private  IUserinfoService userinfoImpl;
	@RequestMapping("login")
	public Map login(Userinfo user) {
		Map map=new HashMap();
		// 获取到主体对象
		System.out.println("开始进myrealm!!!!");
		Subject subject = SecurityUtils.getSubject();
		// 将账号密码封装到token中
		AuthenticationToken token = new UsernamePasswordToken(user.getUname(), user.getUpass());
		// 登录
		try {
			System.out.println("开始用token进行验证！！！！！");
			subject.login(token);
			boolean tg = subject.isAuthenticated();
			System.out.println("认证是否通过" + tg);
			Userinfo login = userinfoImpl.Login(user.getUname(), user.getUpass());
			List trees = login.getTrees();
			List roles = login.getRoles();
			map.put("trees", trees);
			map.put("roles", roles);
		} catch (Exception e) {
		System.out.println("账号密码错误");
		}
		return map;
	}



}
