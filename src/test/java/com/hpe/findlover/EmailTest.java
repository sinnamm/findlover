package com.hpe.findlover;

import com.hpe.findlover.util.EmailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;


/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {
    @Autowired
    private JavaMailSender javaMailSender;
    @Test
    public void data() throws Exception {
        EmailUtil.sendEmailByWeb("383196019@qq.com","test");
    }
    @Test
    public void base(){
        System.out.println(new String(Base64.getDecoder().decode("dXNlcm5hbWU6")));
        System.out.println(new String(Base64.getDecoder().decode("UGFzc3dvcmQ6")));
        System.out.println(new String(Base64.getEncoder().encode(("jackiegss@163.com").getBytes())));;
        System.out.println(new String(Base64.getEncoder().encode(("hellogss991").getBytes())));;
    }
    @Test
    public void sendMail() throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("383196019@qq.com");
        message.setTo("jackiegss@163.com");
        message.setSubject("spring boot mail test!!!!!!!!!!!!!!");
        message.setText("spring boot mail test!!!!!!!!!!!!!!");
        javaMailSender.send(message);
    }

}
