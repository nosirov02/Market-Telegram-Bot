package com.company.tgmarket.controllers;

import com.company.tgmarket.dto.MyMessage;
import com.company.tgmarket.enums.MessageType;
import com.company.tgmarket.service.AdminService;
import com.company.tgmarket.util.MyButtons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private AdminService service;

    public MyMessage start(Update update) {
        Message message = update.getMessage();
        if (message.hasText()) {
            MyMessage myMessage = new MyMessage();
            SendMessage sendMessage = new SendMessage();
            Long chatId = message.getChatId();
            sendMessage.setChatId(String.valueOf(chatId));
            myMessage.setSendMessage(sendMessage);
            if (message.getText().equals("/start")) {
                sendMessage.setText("Amin panelga xush kelibsiz");
                sendMessage.setReplyMarkup(MyButtons.adminButtons());
                myMessage.setMessageType(MessageType.SEND_MESSAGE);
            } else if (message.getText().equals("Product qo'shish")) {
                service.createProduct();
                sendMessage.setText("Product nomini yozing");
                myMessage.setMessageType(MessageType.SEND_MESSAGE);
            } else if (service.hasProduct()){
                service.addProduct(message.getText(), sendMessage);
                myMessage.setMessageType(MessageType.SEND_MESSAGE);
            }
            return myMessage;
        } else if (message.hasPhoto()) {
            MyMessage myMessage = new MyMessage();
            SendMessage sendMessage = new SendMessage();
            Long chatId = message.getChatId();
            sendMessage.setChatId(String.valueOf(chatId));
            myMessage.setSendMessage(sendMessage);


            List<PhotoSize> photo = message.getPhoto();
            if (service.hasProduct()){
                service.saveProduct(photo);
                sendMessage.setText("Product qo'shildi");
                sendMessage.setReplyMarkup(MyButtons.adminButtons());
                myMessage.setMessageType(MessageType.SEND_MESSAGE);
            }
            return myMessage;
        }
        return null;
    }
}
