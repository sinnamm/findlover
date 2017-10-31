package com.hpe.findlover.realm.front;

import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserDetail;
import com.hpe.findlover.model.Writer;
import com.hpe.findlover.service.UserAssetService;
import com.hpe.findlover.service.UserDetailService;
import com.hpe.findlover.service.UserService;
import com.hpe.findlover.service.WriterService;
import com.hpe.findlover.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class WriterRealm extends AuthorizingRealm {
    private Logger logger = LogManager.getLogger(WriterRealm.class);
    @Autowired
    private WriterService writerService;

    public WriterRealm() {
        this(new AllowAllCredentialsMatcher());
    }

    public WriterRealm(CredentialsMatcher matcher) {
        super(matcher);
    }

    /**
     * 专栏作家身份认证
     *
     * @param token 封装了专栏作家身份信息
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取专栏作家的输入的账号.
        String username = (String) token.getPrincipal();
        // 通过username从数据库中查找 User对象，如果找到，没找到.
        // 这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        Writer writer;
        if ((writer = writerService.selectByUserName(username)) == null) {
            throw new UnknownAccountException("专栏作家名不存在！");
        } else if (!writer.getPassword().equals(new String((char[]) token.getCredentials()))) {
            throw new IncorrectCredentialsException("专栏作家名或密码错误");
        }
        logger.info("专栏作家验证通过，把数据存入Session");
        SecurityUtils.getSubject().getSession().setAttribute("user", writer);
        return new SimpleAuthenticationInfo(username, token.getCredentials(), null, "writerRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
    }
}
