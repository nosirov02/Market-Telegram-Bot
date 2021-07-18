package com.company.tgmarket.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KeyboardButtonsUtil {
    public KeyboardButton getButton(String text){
        KeyboardButton button = new KeyboardButton();
        button.setText(text);
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
