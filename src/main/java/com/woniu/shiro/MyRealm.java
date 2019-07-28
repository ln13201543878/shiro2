package com.woniu.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.woniu.model.Role;
import com.woniu.model.Tree;
import com.woniu.model.Userinfo;
import com.woniu.service.IUserinfoService;


public class MyRealm extends AuthorizingRealm{

	@Resource
	private IUserinfoService userinfoImpl;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取安全管理器主体中的session 中存放的对象
		Session session = SecurityUtils.getSubject().getSession();
		Userinfo user = (Userinfo) session.getAttribute("userinfo");
		//登录方法在底层已与tree绑定
		Userinfo userinfo = userinfoImpl.Login(user.getUname(), user.getUpass());
		Set set =new HashSet();
		//获取用户的权限
		List<Tree>trees = userinfo.getTrees();
		//获取用户的角色
		List<Role>roles = userinfo.getRoles();
		for (Role role : roles) {
			set.add(role.getRname());
		}
		//开始进行授权
		System.out.println("!!!!!开始给该用户授权");
		//将授了权的set放入管理器中
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo(set);
		System.out.println("授权成功！！！！");
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//获取controller中存到token中登录用户的账号密码
		String loginName=(String) token.getPrincipal();
		System.out.println("登陆账号"+loginName);
		//直接强转String 不可以会报类型转换错误 需要先转换成char数组 
		String loginPass = new String((char[])token.getCredentials());
		System.out.println("登录密码"+loginPass);
		//获取底层的账号密码
		Userinfo user = userinfoImpl.Login(loginName, loginPass);
		System.out.println(user.toString());
		String uname=user.getUname();
		String upass=user.getUpass();
		//用账号给密码设盐值进行加密
		ByteSource byteSource=ByteSource.Util.bytes(uname);
		System.out.println("//用账号给密码设盐值进行加密");
		SimpleHash md5=new SimpleHash("MD5",upass,byteSource,100);
		//shiro帮助我们进行对比   数据库中的账号 ， 加密算法 ，盐值，登录账号
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(uname,md5,byteSource,loginName);
		//session中不需要保存密码，把密码设置成空
		user.setUpass("");
		//将用户信息放入session中
		SecurityUtils.getSubject().getSession().setAttribute("userinfo", user);
		return info;
	}

}
