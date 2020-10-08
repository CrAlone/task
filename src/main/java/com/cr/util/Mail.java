package com.cr.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发送电子邮件
 */
public class Mail {
    //发件人地址、邮箱
    private static String sendEmail = "cr__alone@163.com";

    //发件人账户名、密码
    public static String sendAccount = "cr__alone";

    //密码
    public static String sendPassword = "SJIAZSTGSUSCJDRU";

    private static Properties pop = new Properties();

    //1、创建连接邮件服务器的参数
    static {
        //设置用户认证方式
        pop.setProperty("mail.smtp.auth","true");
        //传输协议smtp
        pop.setProperty("mail.transport.protocol","smtp");
        //设置SMTP的服务器地址 smtp.163.com
        pop.setProperty("mail.smtp.host","smtp.163.com");
    }

    public void sendHTMLMail(String subject,String content,String receiveMail){
        //创建Session对象
        Session session = Session.getInstance(pop);
        //输出调试信息
        session.setDebug(true);
        //创建邮件的对象
        Message message = getMimeMessage(session,subject,content,receiveMail);

        Transport transport = null;
        //根据Session创建Transport对象
        try {
            transport = session.getTransport();

            transport.connect(sendAccount,sendPassword);

            transport.sendMessage(message,message.getAllRecipients());

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            try {
                transport.close();//关闭邮件连接
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private static Message getMimeMessage(Session session, String subject, String content, String receiveMail) {
        //邮件主题、邮件内容、收件人地址
        //使用session创建邮件实例
        MimeMessage message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress(sendEmail));
            //设置邮件主题
            message.setSubject(subject,"UTF-8");
            //设置邮件内容
            message.setContent(content,"text/html;charset=UTF-8");
            //收件人
            message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(receiveMail));

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

//    public static void main(String[] args) {
//        new Mail().sendHTMLMail("测试邮件","大哥你好！","cr__alone@163.com");
//    }

}
