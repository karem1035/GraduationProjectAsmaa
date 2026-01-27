package com.example.Toda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpProducer {

    @Autowired
    private JmsTemplate jmsTemplate;
    public void sendOtpMessage(String email, String otp) {
        Map<String, String> messageData = new HashMap<>();
        messageData.put("email", email);
        messageData.put("otp", otp);
        jmsTemplate.convertAndSend("otp-queue", messageData);
        System.out.println("Sent to ActiveMQ: " + email);
    }

}

