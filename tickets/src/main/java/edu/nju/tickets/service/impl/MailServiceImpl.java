package edu.nju.tickets.service.impl;

import edu.nju.tickets.service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static edu.nju.tickets.util.Constants.VERIFICATION_CODE_LENGTH;

@Service
public class MailServiceImpl implements MailService {

    private static final String EMAIL = "hx36w35@163.com";

    private ConcurrentHashMap<String, String> codeEmailMap = new ConcurrentHashMap<>();

    @Resource
    private JavaMailSender javaMailSender;

    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < VERIFICATION_CODE_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private void sendEmail(String email, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom(EMAIL);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendVerificationCode(String email) {
        String code;
        do {
            code = generateVerificationCode();
        } while (codeEmailMap.containsKey(code));
        codeEmailMap.put(code, email);
        sendEmail(email, "Tickets验证码", "你的验证码是：" + code);
    }

    @Override
    public void sendMessage(String email, String subject, String content) {
        sendEmail(email, subject, content);
    }

    @Override
    public boolean verifyCode(String email, String code) {
        if (codeEmailMap.containsKey(code) && email.equals(codeEmailMap.get(code))) {
            codeEmailMap.remove(code);
            return true;
        }
        return false;
    }

}
