package com.example.demo;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

@Service
public class Sms {


    private static final String ACCOUNT_SID = "ACf8ec6b5e02f25200f14b1bdabe376f55";
    private static final String AUTH_TOKEN = "bbf5fa72dd29e0dc702c2319ece6a54f";
    @Async
    public void sendSmsLocker(String email) {

    }


    @Async
    public void sendSmsToken(String token) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+50376288036"),
                new com.twilio.type.PhoneNumber("+15203919132"),
                "su codigo es" + token
        ).create();
        System.out.println(message.getSid());
    }
}