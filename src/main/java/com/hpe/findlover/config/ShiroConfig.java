package com.hpe.findlover.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.hpe.findlover.authenticator.CustomModularRealmAuthenticator;
import com.hpe.findlover.filter.IdentityFilter;
import com.hpe.findlover.realm.AdminRealm;
import com.hpe.findlover.realm.UserRealm;
import com.hpe.findlover.realm.WriterRealm;
import com.hpe.findlover.util.Identity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置 Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 * 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 */
@Configuration
public class ShiroConfig {
	private Logger logger = LogManager.getLogger(ShiroConfig.class);

	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

//	@Bean
//	@ConfigurationProperties(prefix = "spring.mail")
//	public MimeMessageHelper getMessageHelper(){
//		return new MimeMessageHelper(new MimeMessage(), true);
//	}

	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是报错的，因为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager());

		// 设置filters，让不同的用户身份使用不同的filters
		Map<String, Filter> filterMap = new LinkedHashMap<>(3);

		filterMap.put("userAuth", new IdentityFilter(Identity.USER));
		filterMap.put("adminAuth", new IdentityFilter(Identity.ADMIN));
		filterMap.put("writerAuth", new IdentityFilter(Identity.WRITER));

		shiroFilterFactoryBean.setFilters(filterMap);


		// 配置路径过滤链.
		Map<String, String> filterChainMap = new LinkedHashMap<>();

		// 配置公共资源文件过滤路径
		filterChainMap.put("/js/**", "anon");
		filterChainMap.put("/css/**", "anon");
		filterChainMap.put("/images/**", "anon");
		filterChainMap.put("/fonts/**", "anon");
		filterChainMap.put("/jquery/**", "anon");
		filterChainMap.put("/json/**", "anon");
		filterChainMap.put("/plugins/**", "anon");
		filterChainMap.put("/ueditor/**", "anon");
		filterChainMap.put("/file/**", "anon");

		//管理员配置
		filterChainMap.put("/admin/**", "adminAuth");

		//作家配置
		filterChainMap.put("/writer/**", "writerAuth");

		//用户配置
		filterChainMap.put("/logout", "anon");
		filterChainMap.put("/register/**", "anon");
		filterChainMap.put("/dicts/**", "anon");
		filterChainMap.put("/checkEmail/**", "anon");
		filterChainMap.put("/**", "userAuth");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

		// 未授权界面
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		return shiroFilterFactoryBean;
	}

	/**
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * 所以我们需要修改下doGetAuthenticationInfo中的代码; ）
	 *
	 * @return
	 **/
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(1);// 散列的次数，比如散列两次，相当于md5(md5(""));
		return hashedCredentialsMatcher;
	}

	/**
	 * 身份认证realm
	 *
	 * @return
	 */
	@Bean
	public UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
		userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		userRealm.setName("userRealm");
		return userRealm;
	}

	@Bean
	public WriterRealm writerRealm() {
		WriterRealm writerRealm = new WriterRealm();
		writerRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		writerRealm.setName("writerRealm");
		return writerRealm;
	}

	@Bean
	public AdminRealm adminRealm() {
		AdminRealm adminRealm = new AdminRealm();
		adminRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		adminRealm.setName("adminRealm");
		return adminRealm;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealms(Arrays.asList(userRealm(), writerRealm(), adminRealm()));
		//验证器
		securityManager.setAuthenticator(realmAuthenticator());
		// 注入记住我管理器;
		securityManager.setRememberMeManager(rememberMeManager());
		securityManager.setCacheManager(cacheManager());
		return securityManager;
	}

	/**
	 * 自定义验证器，可以实现指定特定Realm处理特定类型的验证
	 *
	 * @return
	 */
	@Bean
	public Authenticator realmAuthenticator() {
		CustomModularRealmAuthenticator realmAuthenticator = new CustomModularRealmAuthenticator();
		realmAuthenticator.setRealms(Arrays.asList(userRealm(), writerRealm(), adminRealm()));
		return realmAuthenticator;
	}

	/**
	 * cookie对象;
	 *
	 * @return
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		// 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// <!-- 记住我cookie生效时间30天 ,单位秒;-->
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(259200);
		return simpleCookie;
	}

	/**
	 * cookie管理对象;
	 *
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		cookieRememberMeManager.setCipherKey(Base64.decode("RmluZExvdmVy"));
		return cookieRememberMeManager;
	}

	/**
	 * shiro缓存管理器;
	 * 需要注入对应的其它的实体类中：
	 * 1、安全管理器：securityManager
	 * 可见securityManager是整个shiro的核心；
	 *
	 * @return
	 */
	@Bean("shiroCacheManager")
	public CacheManager cacheManager() {
		return new MemoryConstrainedCacheManager();
	}

	/**
	 * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
	 *
	 * @return
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	/**
	 * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
