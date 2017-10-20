package com.hpe.findlover.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author wmm
 * Created by wmm on 2017/10/11.
 * 邮件发送的工具类
 */
public class EmailUtil {

    /**
     * 发件人的 邮箱 和 密码
     * PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
     * 对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
     **/
    public static String myEmailAccount = "iyuanyunfeng@163.com";
    public static String myEmailPassword = "yuan12345";
    /**
     * 发件人邮箱的 SMTP 服务器地址必须准确, 不同邮件服务器地址不同,
     * 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
     */
    public static String myEmailSMTPHost = "smtp.163.com";


    /**
     * 使用别人家的邮箱服务器，例如163邮箱，qq邮箱等
     * @param to   给谁发邮件，也就是发送邮件的地址
     * @param url 激活码链接
     */
    public static void sendEmailByWeb(String to, String url) throws Exception {

        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        props.setProperty("mail.smtp.port", "25");            // 需要请求认证
        props.setProperty("mail.smtp.timeout", "50000");            // 需要请求认证
        props.setProperty("mail.smtp.connectiontimeout", "30000");            // 需要请求认证
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmailAccount, myEmailPassword);
            }
        };
        //2.创建链接对象，链接到邮箱服务器
        Session session = Session.getDefaultInstance(props,auth);
        session.setDebug(true);//这里是为了出错的时候在控制台可以看到相信的信息

        //3.创建邮件对象
        Message message = createMimeMessage(session, myEmailAccount, to, url);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        //使用邮箱账号和密码连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        transport.connect(myEmailAccount, myEmailPassword);
        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        // 7. 关闭连接
        transport.close();

    }

    /**
     * 创建一封只包含文本的简单邮件
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String url) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件
        message.setFrom(new InternetAddress(sendMail, "findlover", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "用户", "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject("激活邮件", "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent("<h1>请点击以下链接激活你的账号</h1>" +
                "<h3><a href='"+url+"'>" + url + "</a></h3>", "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
}
