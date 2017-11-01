package com.hpe.findlover.realm;

import com.hpe.findlover.model.Writer;
import com.hpe.findlover.service.WriterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class WriterRealm extends AuthorizingRealm {
    private Logger logger = LogManager.getLogger(WriterRealm.class);
    @Autowired
    private WriterService writerService;

    public WriterRealm() {
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
            throw new UnknownAccountException("专栏作家用户名不存在！");
        }else if(writer.getStatus()==0){
            throw new LockedAccountException("专栏作家账户已被锁定！");
        }
        return new SimpleAuthenticationInfo(username, writer.getPassword(), ByteSource.Util.bytes(username), "writerRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
    }
}
