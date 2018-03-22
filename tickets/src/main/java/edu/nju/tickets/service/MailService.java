package edu.nju.tickets.service;

import com.sun.mail.smtp.SMTPAddressFailedException;

public interface MailService {

    /**
     * 发送验证码
     *
     * @param email 发送邮箱
     */
    void sendVerificationCode(final String email) throws SMTPAddressFailedException;

    /**
     * 发送邮件
     *
     * @param subject   主题
     * @param content   内容
     */
    void sendMessage(final String email, final String subject, final String content) throws SMTPAddressFailedException;

    /**
     * 验证邮箱与验证码
     *
     * @param email 邮箱
     * @param code  验证码
     * @return      验证结果
     */
    boolean verifyCode(final String email, final String code);

}
