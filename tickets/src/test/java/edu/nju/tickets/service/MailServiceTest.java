package edu.nju.tickets.service;

import com.sun.mail.smtp.SMTPAddressFailedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml"})
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendCode() throws SMTPAddressFailedException {
//        mailService.sendVerificationCode("767085586@qq.com");
    }

    @Test
    public void testSendMessage() throws SMTPAddressFailedException {
//        mailService.sendMessage("123", "test", "test");
    }

}
