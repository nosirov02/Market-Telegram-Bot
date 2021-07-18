package com.company.tgmarket.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InlineButtonsUtil {
    public InlineKeyboardButton getButton(String text, String callback){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callback);
        return button;
    }

    public List<InlineKeyboardButton> getRow(InlineKeyboardButton... buttons){
        return new LinkedList<>(Arrays.asList(buttons));
    }


    @SafeVarargs
    public final List<List<InlineKeyboardButton>> getRowColl(List<InlineKeyboardButton>... rows){
        return new LinkedList<>(Arrays.asList(rows));
    }

    public InlineKeyboardMarkup getMarkup(List<List<InlineKeyboardButton>> collection){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(collection);
        return markup;
    }



}
