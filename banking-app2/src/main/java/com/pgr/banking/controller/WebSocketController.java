package com.pgr.banking.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    
    @MessageMapping("/balance-update")
    @SendTo("/topic/balance-update")
    public String handleBalanceUpdate(String message) {
        return message;
    }
    
    @MessageMapping("/transaction-notification")
    @SendTo("/topic/transaction-notification")
    public String handleTransactionNotification(String message) {
        return message;
    }
}