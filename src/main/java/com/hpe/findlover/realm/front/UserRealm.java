package com.hpe.findlover.realm.front;

import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserDetail;
import com.hpe.findlover.service.front.UserAssetService;
import com.hpe.findlover.service.front.UserDetailService;
import com.hpe.findlover.service.front.UserService;
import com.hpe.findlover.util.Constant;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UserRealm extends AuthorizingRealm {
	private Logger logger = LogManager.getLogger(UserRealm.class);
	@Autowired
	private UserService userService;
	@Autowired
	public UserAssetService userAssetService;
	@Autowired
	public UserDetailService userDetailService;

	public UserRealm() {
		this(new AllowAllCredentialsMatcher());
	}

	public UserRealm(CredentialsMatcher matcher) {
		super(matcher);
	}

	/**
	 * 用户身份认证
	 *
	 * @param token 封装了用户身份信息
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取用户的输入的账号.
		String email = (String) token.getPrincipal();
		// 通过username从数据库中查找 User对象，如果找到，没找到.
		// 这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		UserBasic userBasic;
		if ((userBasic = userService.selectByEmail(email)) == null) {
			throw new UnknownAccountException("用户名不存在！");
		} else if (!userBasic.getPassword().equals(new String((char[]) token.getCredentials()))) {
			throw new IncorrectCredentialsException("用户名或密码错误");
		} else if (userBasic.getStatus() == Constant.USER_LOCKED_STATUS) {
			throw new LockedAccountException("用户被锁定");
		} else if (userBasic.getStatus() == Constant.USER_DISABLED_STATUS) {
			throw new DisabledAccountException("用户未激活");
		}
		UserAsset userAsset = userAssetService.selectByPrimaryKey(userBasic.getId());
		//身份是否认证过
		userBasic.setConfirm(userDetailService.selectByPrimaryKey(userBasic.getId()).getCardnumber()!=null);
		if (userAsset == null || userAsset.getVipDeadline() == null) {
			userBasic.setVip(false);
		} else {
			userBasic.setVip(!userAsset.getVipDeadline().before(new Date()));
		}
		if (userAsset == null || userAsset.getStarDeadline() == null) {
			userBasic.setStar(false);
		} else {
			userBasic.setStar(!userAsset.getStarDeadline().before(new Date()));
		}
		logger.info("用户验证通过，把数据存入Session");
		SecurityUtils.getSubject().getSession().setAttribute("user", userBasic);
		// userInfo.setPermissions(userService.findPermissions(user));
		// 加密交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		return new SimpleAuthenticationInfo(email, token.getCredentials(), null, "userRealm");
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		return null;
	}
}
