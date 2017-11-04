package com.hpe.findlover.realm;

import com.hpe.findlover.model.Admin;
import com.hpe.findlover.service.AdminService;
import com.hpe.findlover.service.PermissionService;
import com.hpe.findlover.service.RoleService;
import com.hpe.findlover.util.Identity;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class AdminRealm extends AuthorizingRealm {
	private Logger logger = LogManager.getLogger(AdminRealm.class);
	@Autowired
	private AdminService adminService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;


	/**
	 * 管理员身份认证
	 *
	 * @param token 封装了管理员身份信息
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取用户的输入的账号.
		String username = (String) token.getPrincipal();
		// 通过username从数据库中查找 Admin，如果找到，没找到.
		// 这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		Admin admin = new Admin();
		admin.setUsername(username);
		if ((admin = adminService.selectOne(admin)) == null) {
			throw new UnknownAccountException("用户名不存在！");
		}
		// 加密交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		return new SimpleAuthenticationInfo(username, admin.getPassword(), ByteSource.Util.bytes(username), getName());
	}


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Set<String> roles = new HashSet<>();
		roleService.selectAllByAdminId(SessionUtils.getSessionAttr(Identity.ADMIN.getValue(),Admin.class).getId()).forEach(v -> roles.add(v.getValue()));
		authorizationInfo.setRoles(roles);
		Set<String> permissions = new HashSet<>();
		permissionService.selectAllByAdminId(SessionUtils.getSessionAttr(Identity.ADMIN.getValue(),Admin.class).getId()).forEach(v -> permissions.add(v.getValue()));
		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
	}
}
