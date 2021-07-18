package com.company.tgmarket.dto;

import com.company.tgmarket.enums.MessageType;
import com.sun.xml.internal.ws.resources.SenderMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Getter
@Setter
@NoArgsConstructor
public class MyMessage {
    public MessageType messageType;
    private SendMessage sendMessage;
    private EditMessageText editMessageText;
    private SendPhoto sendPhoto;
}
