package com.woniu.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class ShiroConfiguration {
	//配置shiro过滤器
	@Bean("shiroFilter")
	//标准参数应该是@Qualifier("securityManager") SecurityManager manager
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiro=new ShiroFilterFactoryBean();
		shiro.setSecurityManager(securityManager);
		 //shiro.setSuccessUrl("/role/success.html");  登录成功的页面
		//shiro.setUnauthorizedUrl("/error.html"); 登录失败的页面
		//未登录就访问页面时，会强制跳转到登录页面
		shiro.setLoginUrl("/login/index.jsp");
		//必须是LinkedHashMap,要保证放入值的先后顺序
		Map<String,String>filterChainMap=new LinkedHashMap<>();
		//静态资源放行
		
		filterChainMap.put("/css/**", "anon");
		filterChainMap.put("/imgs/**", "anon");
		filterChainMap.put("/js/**", "anon");
		//登录URL放行        
		filterChainMap.put("/login/index.jsp", "anon");
		//访问以下路径的时候需要进行认证，差别在于anon/不拦截  authc拦截
		filterChainMap.put("/user/admin*","authc");
		//访问以下路径只有专家角色才可以
		filterChainMap.put("/money.html","roles[expert]");
//   访问以下路径只有具有某某权限的才可以   filterChainMap.put("/user/teacher*/**", "perms[""]");
		 // 配置 logout 过滤器   登出
		filterChainMap.put("/logout", "logout");
		shiro.setFilterChainDefinitionMap(filterChainMap);
		return shiro;
	}
	//配置安全管理器
	@Bean("securityManager")
	public DefaultWebSecurityManager getSecurityManager(){
		DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
//		@Qualifier("shiroRealm") ShiroRealm shiroRealm，
		//也可以直接securityManager.setRealm(shiroRealm);   这叫注册权限管理器
		securityManager.setRealm(getRealm());
		//注册（添加）缓存管理器
		securityManager.setCacheManager(getehCacheManager());
		return securityManager;
		
	}
	
	//定义缓存管理器
	@Bean
	public EhCacheManager getehCacheManager() {
		EhCacheManager ehCacheManager=new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		return ehCacheManager;
	}
	//配置自定义的权限登录器（realm）
	@Bean
	public MyRealm getRealm() {
		MyRealm realm=new MyRealm();
		realm.setCredentialsMatcher(hashed());
		//开启缓存
		realm.setCachingEnabled(true);
		//配置认证缓存
		realm.setAuthenticationCachingEnabled(true);
		realm.setAuthenticationCacheName("authenticationCache");
		//配置授权缓存
		realm.setAuthorizationCachingEnabled(true);
		realm.setAuthorizationCacheName("authorizationCache");
		return realm;
	}
	//配置自定义的密码比较器
	@Bean
	public CredentialsMatcher hashed() {
		HashedCredentialsMatcher hashed=new HashedCredentialsMatcher();
		//添加散列算法 这里是MD5算法
		hashed.setHashAlgorithmName("MD5");
		//散列的次数
		hashed.setHashSalted(true);
		hashed.setHashIterations(100);
		return hashed;
	}
	//shiro生命周期处理器
	@Bean("lifecycle")
	public LifecycleBeanPostProcessor lifecycle(){
		LifecycleBeanPostProcessor lifecycleProcessor=new LifecycleBeanPostProcessor();
		return lifecycleProcessor;
	}
	
	 /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
//	AOP式方法级权限检查，DefaultAdvisorAutoProxyCreator用来扫描上下文，寻找所有的Advistor(通知器），
//	将这些Advisor应用到所有符合切入点的Bean中。
//	LifecycleBeanPostProcessor将Initializable和Destroyable的实现类统一在其内部自动分别调用
//	了Initializable.init()和Destroyable.destroy()方法，从而达到管理shiro bean生命周期的目的。
	@Bean
	@DependsOn("lifecycle")
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=//
				new DefaultAdvisorAutoProxyCreator();
		return defaultAdvisorAutoProxyCreator;
	} 
	//Aop式方法级权限检查
	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor=//
				new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(getSecurityManager());
		return authorizationAttributeSourceAdvisor;
	}
	
	
}
