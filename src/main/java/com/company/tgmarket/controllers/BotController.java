package com.company.tgmarket.controllers;

import com.company.tgmarket.dto.MyMessage;
import com.company.tgmarket.enums.MessageType;
import com.company.tgmarket.service.BotService;
import com.company.tgmarket.util.MyButtons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Controller
public class BotController {
    @Autowired
    private BotService service;
    private boolean creatingProduct;

    public MyMessage botStart(Update update) {
        Long chatId = update.getMessage().getChatId();
        MyMessage message = new MyMessage();
        SendMessage sendMessage = new SendMessage();
        message.setSendMessage(sendMessage);
        sendMessage.setText("Assalomu alaykum bu bot mebellarni qulay tarzda harid qilish uchun yaratilgan");
        sendMessage.setChatId(String.valueOf(chatId));
        message.setMessageType(MessageType.SEND_MESSAGE);
        return message;
    }


    public MyMessage stopBot(Update update) {
        Long chatId = update.getMessage().getChatId();
        MyMessage message = new MyMessage();
        SendMessage sendMessage = new SendMessage();
        message.setSendMessage(sendMessage);
        sendMessage.setText("Bot o'chirilgan uni yoqish uchun /start buyrug'ini kiriting");
        sendMessage.setChatId(String.valueOf(chatId));
        message.setMessageType(MessageType.SEND_MESSAGE);
        return message;
    }

    public MyMessage main(Update update) {
        if (update.hasCallbackQuery()) {
            MyMessage myMessage = new MyMessage();
            CallbackQuery callback = update.getCallbackQuery();
            String data = callback.getData();
            Long chatId = callback.getFrom().getId();
            if (data.startsWith("add/")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatId));
                myMessage.setSendMessage(sendMessage);
                service.addProductToCart(data);
                sendMessage.setText("Maxsulot savatga qo'shildi. Davom ettirish uchun kategoriyalardan birini tanlang");
                sendMessage.setReplyMarkup(MyButtons.categoryButtons());
                creatingProduct = true;
                myMessage.setMessageType(MessageType.SEND_MESSAGE);
            } else if (data.equals("buy")) {
                Long id = callback.getFrom().getId();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatId));
                myMessage.setSendMessage(sendMessage);
                service.buyProduct(sendMessage, id);
                creatingProduct = false;
                myMessage.setMessageType(MessageType.SEND_MESSAGE);
            }
            return myMessage;
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            MyMessage myMessage = new MyMessage();
            if (message.hasPhoto()) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatId));
                myMessage.setSendMessage(sendMessage);
                sendMessage.setText("Noto'g'ri buyruq");
                myMessage.setMessageType(MessageType.SEND_MESSAGE);
            }
            if (creatingProduct) {
                String text = message.getText();
                if (text.equals("Asosiy menu")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    myMessage.setSendMessage(sendMessage);
                    service.mainMenu(sendMessage);
                    creatingProduct = false;
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                } else if (text.equals("Savatni ko'rish")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    myMessage.setSendMessage(sendMessage);
                    service.getCartList(sendMessage);
                    creatingProduct = false;
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                } else if (text.equals("Ortga qaytish")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    myMessage.setSendMessage(sendMessage);
                    sendMessage.setText("Kategoriyalardan birini tanlang");
                    sendMessage.setReplyMarkup(MyButtons.categoryButtons());
                    creatingProduct = true;
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                } else if (service.checkingCategory(text)) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    myMessage.setSendMessage(sendMessage);
                    service.setCategory(sendMessage, text);
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                } else if (service.checkProducts()) {
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(String.valueOf(chatId));
                    myMessage.setSendPhoto(sendPhoto);
                    service.setProducts(sendPhoto, text);
                    myMessage.setMessageType(MessageType.IMAGE_MESSAGE);
                }
                return myMessage;
            } else if (message.hasText()) {
                String text = message.getText();
                if (!service.checkContact(message)) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    service.getContact(sendMessage);
                    myMessage.setSendMessage(sendMessage);
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                } else if (text.equals("/start")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    myMessage.setSendMessage(sendMessage);
                    service.mainMenu(sendMessage);
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                } else if (text.equals("Buyurtma berish")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    myMessage.setSendMessage(sendMessage);
                    sendMessage.setText("Kategoriyalardan birini tanlang");
                    sendMessage.setReplyMarkup(MyButtons.categoryButtons());
                    creatingProduct = true;
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                } else if (text.equals("Biz haqimizda")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    myMessage.setSendMessage(sendMessage);
                    sendMessage.setText("Bizning dokonda ozingizga qulay " +
                            "mebellarni harid qilishingiz mumkun!");
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                } else if (text.equals("Sozlamalar")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    myMessage.setSendMessage(sendMessage);
                    service.setSettings(sendMessage);
                    sendMessage.setText("Telefon raqamni o'zgartirishingiz mumkun");
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                } else if (text.equals("Raqamni o'zgartirish")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    myMessage.setSendMessage(sendMessage);
                    service.getContact(sendMessage);
                    sendMessage.setText("Yangi raqamni kiriting");
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                } else {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));
                    myMessage.setSendMessage(sendMessage);
                    sendMessage.setText("Noto'g'ri buyruq");
                    myMessage.setMessageType(MessageType.SEND_MESSAGE);
                }
                return myMessage;
            } else if (message.hasContact()) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatId));
                service.createUser(message, message.getContact().getPhoneNumber());
                myMessage.setSendMessage(sendMessage);
                sendMessage.setText("Siz ro'yxatdan o'tdingiz!\n Quyidagilardan birini tanlang");
                sendMessage.setReplyMarkup(MyButtons.mainButtons());
                myMessage.setMessageType(MessageType.SEND_MESSAGE);
                return myMessage;
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatId));
                myMessage.setSendMessage(sendMessage);
                sendMessage.setText("Noto'g'ri buyruq");
                myMessage.setMessageType(MessageType.SEND_MESSAGE);
            }
            return myMessage;
        }
        return null;
    }
}
