package com.hpe.findlover.authenticator;

import com.hpe.findlover.token.CustomToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

/**
 * 自定义验证器
 * 分别定义Realm时，对应Realm的name必须包含对应token中的type，如UserRealm必须包含user
 * @author Gss
 */
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {
	private final Logger logger = LogManager.getLogger(CustomModularRealmAuthenticator.class);

	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
		logger.info("Token ClassName = "+ authenticationToken.getClass().getSimpleName());
		CustomToken customToken = (CustomToken) authenticationToken;
		for (Realm realm : getRealms()) {
			logger.info("RealmName=" + realm.getName() + ",type=" + customToken.getType().getValue());
			if (realm.getName().contains(customToken.getType().getValue())) {
				return doSingleRealmAuthentication(realm, customToken);
			}
		}
		throw new AuthenticationException("没有找到对应Realm，请检查Realm名称是否正确！");
	}
}
