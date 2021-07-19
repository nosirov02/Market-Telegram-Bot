package com.company.tgmarket.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.LinkedList;
import java.util.List;

public class MyButtons {
    public static ReplyKeyboardMarkup mainButtons() {
        KeyboardButton order = new KeyboardButton("Buyurtma berish");
        KeyboardRow row1 = new KeyboardRow();
        row1.add(order);

        KeyboardButton information = new KeyboardButton("Biz haqimizda");
        KeyboardButton settings = new KeyboardButton("Sozlamalar");
        KeyboardRow row2 = new KeyboardRow();
        row2.add(information);
        row2.add(settings);


        List<KeyboardRow> rows = new LinkedList<>();
        rows.add(row1);
        rows.add(row2);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboard categoryButtons() {
        KeyboardButton b1 = new KeyboardButton("Spalniy");
        KeyboardButton b2 = new KeyboardButton("Divan");
        KeyboardButton b3 = new KeyboardButton("Ugolok");
        KeyboardRow row1 = new KeyboardRow();
        row1.add(b1);
        row1.add(b2);
        row1.add(b3);

        KeyboardButton b4 = new KeyboardButton("Stol Stul");
        KeyboardButton b5 = new KeyboardButton("Shkaf");
        KeyboardButton b6 = new KeyboardButton("Tryumo");
        KeyboardRow row2 = new KeyboardRow();
        row2.add(b4);
        row2.add(b5);
        row2.add(b6);

        KeyboardButton savat = new KeyboardButton("Savatni ko'rish");
        KeyboardRow row3 = new KeyboardRow();
        row3.add(savat);

        KeyboardButton menu = new KeyboardButton("Asosiy menu");
        KeyboardRow row4 = new KeyboardRow();
        row4.add(menu);


        List<KeyboardRow> rows = new LinkedList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        return replyKeyboardMarkup;
    }

    public static InlineKeyboardMarkup photoButtons(Integer id) {
        InlineButtonsUtil util = new InlineButtonsUtil();

        InlineKeyboardButton addToCart = new InlineKeyboardButton();
        addToCart.setText("savatga qoshish");
        addToCart.setCallbackData("add/"+id);
        List<InlineKeyboardButton> row = new LinkedList<>();
        row.add(addToCart);

        List<List<InlineKeyboardButton>> rowColl = new LinkedList<>();
        rowColl.add(row);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowColl);
        return inlineKeyboardMarkup;
    }

    public static ReplyKeyboard productButtons(Integer count) {
        KeyboardRow row = new KeyboardRow();
        List<KeyboardRow> rows = new LinkedList<>();
        for (int i = 1; i <= count; i++) {
            KeyboardButton button = new KeyboardButton(String.valueOf(i));
            row.add(button);
            if (i % 3 == 0) {
                rows.add(row);
                row = new KeyboardRow();
            }else if (i == count){
                rows.add(row);
            }

        }
        KeyboardButton end = new KeyboardButton("Ortga qaytish");
        KeyboardRow endRow = new KeyboardRow();
        endRow.add(end);
        rows.add(endRow);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboard buyButton() {
        InlineKeyboardButton addToCart = new InlineKeyboardButton();
        addToCart.setText("Harid qilish");
        addToCart.setCallbackData("buy");
        List<InlineKeyboardButton> row = new LinkedList<>();
        row.add(addToCart);

        List<List<InlineKeyboardButton>> rowColl = new LinkedList<>();
        rowColl.add(row);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowColl);
        return inlineKeyboardMarkup;
    }

    public static ReplyKeyboard settingsButtons() {

        KeyboardButton contact = new KeyboardButton("Raqamni o'zgartirish");
        KeyboardRow row1 = new KeyboardRow();
        row1.add(contact);

        List<KeyboardRow> rows = new LinkedList<>();
        rows.add(row1);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboard adminButtons() {
        KeyboardButton order = new KeyboardButton("Product qo'shish");
        KeyboardRow row1 = new KeyboardRow();
        row1.add(order);

        List<KeyboardRow> rows = new LinkedList<>();
        rows.add(row1);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboard onlyCategory() {
        KeyboardButton b1 = new KeyboardButton("Spalniy");
        KeyboardButton b2 = new KeyboardButton("Divan");
        KeyboardButton b3 = new KeyboardButton("Ugolok");
        KeyboardRow row1 = new KeyboardRow();
        row1.add(b1);
        row1.add(b2);
        row1.add(b3);

        KeyboardButton b4 = new KeyboardButton("Stol Stul");
        KeyboardButton b5 = new KeyboardButton("Shkaf");
        KeyboardButton b6 = new KeyboardButton("Tryumo");
        KeyboardRow row2 = new KeyboardRow();
        row2.add(b4);
        row2.add(b5);
        row2.add(b6);

        List<KeyboardRow> rows = new LinkedList<>();
        rows.add(row1);
        rows.add(row2);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        return replyKeyboardMarkup;
    }
}
