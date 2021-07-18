package com.company.tgmarket;

import com.company.tgmarket.controllers.BotController;
import com.company.tgmarket.dto.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class Bot extends TelegramLongPollingBot {
    private Boolean activate;
    @Autowired
    private BotController controller;

    public Bot() {
        activate = false;
    }

    @Override
    public String getBotUsername() {
        return "Safira Mebel";
    }

    @Override
    public String getBotToken() {
        return "1831152964:AAFf95ATLDyMbm4JtoOFht80PhPDdbBm9XI";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()){
                if (update.getMessage().getText().equals("/start")) {
                    activate = true;
                    sendMsg(controller.botStart(update));
                } else if (update.getMessage().getText().equals("/stop")) {
                    activate = false;
                    sendMsg(controller.stopBot(update));
                }
            }
        }
        if (activate) {
            sendMsg(controller.main(update));
        } else if (!activate) {
            sendMsg(controller.stopBot(update));
        }

    }

    public void sendMsg(MyMessage message) {
        try {
            switch (message.getMessageType()) {
                case SEND_MESSAGE:
                    execute(message.getSendMessage());
                    break;
                case EDIT_MESSAGE:
                    execute(message.getEditMessageText());
                    break;
                case IMAGE_MESSAGE:
                    execute(message.getSendPhoto());
                    break;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
