package com.example.demo;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

@Service
public class Sms {


    private static final String ACCOUNT_SID = "ACd532c779181ca12b3cf9b8e6bf96d0c5";
    private static final String AUTH_TOKEN = "8362c7905b4ee6f00d9a6b07e9bd2f26";

    @Async
    public void sendSmsLocker(String email) {

    }


    @Async
    public void sendSmsToken(String token) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+50375330538"),
                new com.twilio.type.PhoneNumber("+13185133334"),
                "su codigo es " + token
        ).create();
    }
}