package com.company.tgmarket.service;

import com.company.tgmarket.Bot;
import com.company.tgmarket.entity.ProductEntity;
import com.company.tgmarket.entity.ProfileEntity;
import com.company.tgmarket.repository.ProductRepository;
import com.company.tgmarket.repository.ProfileRepository;
import com.company.tgmarket.util.MyButtons;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class BotService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProductRepository productRepository;

    @Value("${chat.id}")
    private Long chatId;

    private String category;
    private List<ProductEntity> productList;
    private List<ProductEntity> cartList;

    public void getContact(SendMessage sendMessage) {
        sendMessage.setText("Biz sizga aloqaga chiqishimiz uchun telefon raqamingiz bilan ulashin: ");
        KeyboardButton button = new KeyboardButton("Telefon raqam");
        button.setRequestContact(true);
        KeyboardRow row = new KeyboardRow();
        row.add(button);
        List<KeyboardRow> keyboard = new LinkedList<>();
        keyboard.add(row);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    public void createUser(Message message, String contact) {
        User user = message.getFrom();
        ProfileEntity entity = new ProfileEntity();
        entity.setId(user.getId());
        entity.setName(user.getFirstName());
        entity.setRegisterDate(LocalDateTime.now());
        if (!user.getUserName().isEmpty()) {
            entity.setUsername(user.getUserName());
        }
        entity.setChatId(message.getChatId());
        entity.setContact(contact);
        profileRepository.save(entity);
    }

    public boolean checkContact(Message message) {
        Optional<ProfileEntity> entity = profileRepository.findById(message.getFrom().getId());
        return entity.isPresent();
    }

    public void mainMenu(SendMessage sendMessage) {
        cartList = new LinkedList<>();
        String downFinger = EmojiParser.parseToUnicode(":point_down:");
        sendMessage.setText("Quyidagilardan birini tanlang " + downFinger);
        sendMessage.setReplyMarkup(MyButtons.mainButtons());
    }

    public boolean checkingCategory(String text) {
        return text.equals("Spalniy")
                || text.equals("Divan")
                || text.equals("Ugolok")
                || text.equals("Stol Stul")
                || text.equals("Shkaf")
                || text.equals("Tryumo");
    }

    public void setCategory(SendMessage sendMessage, String text) {
        category = text;
        String downFinger = EmojiParser.parseToUnicode(":point_down:");
        sendMessage.setText("Quyidagilardan birini tanlang " + downFinger);
        productList = productRepository.findAllByCategory(category);
        Integer productCount = productList.size();
        sendMessage.setReplyMarkup(MyButtons.productButtons(productCount));
    }

    public void setProducts(SendPhoto sendPhoto, String text) {
        ProductEntity entity = productList.get(Integer.parseInt(text) - 1);
        sendPhoto.setPhoto(new InputFile(entity.getImageId()));
        sendPhoto.setCaption("<b>Mebel nomi - </b>" + entity.getName() + "\n"
                + "<b>Narxi - </b>" + entity.getPrice());
        sendPhoto.setParseMode("HTML");
        sendPhoto.setReplyMarkup(MyButtons.photoButtons(entity.getId()));
    }

    public boolean checkProducts() {
        return !productList.isEmpty();
    }

    public void addProductToCart(String data) {
        String productId = data.split("/")[1];
        Integer id = Integer.parseInt(productId);
        Optional<ProductEntity> optional = productRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException();
        }
        ProductEntity product = optional.get();
        cartList.add(product);
    }

    public boolean getCartList(SendMessage sendMessage) {
        if (cartList == null) {
            String empty = EmojiParser.parseToUnicode(":shopping_trolley: :no_entry_sign:");
            sendMessage.setText("Savatcha to'ldirilmagan " + empty);
            return true;
        } else if (cartList.isEmpty()) {
            String empty = EmojiParser.parseToUnicode(":no_entry_sign:");
            String cart = EmojiParser.parseToUnicode("\uD83D\uDED2");

            sendMessage.setText("Savatcha to'ldirilmagan " + cart + empty);
            return true;
        } else {
            StringBuilder products = new StringBuilder();
            Double totalPrice = 0.0;
            for (ProductEntity entity : cartList) {
                products.append(entity.getName()).append("\n");
                totalPrice += entity.getPrice();
            }
            sendMessage.setText("Savatchada: \n" + products + "\n" + "Umumiy narxi " + totalPrice);
            sendMessage.setReplyMarkup(MyButtons.buyButton());
            return false;
        }
    }

    public void buyProduct(SendMessage sendMessage, Long id) {
        sendMessage.setText("Xaridingiz qabul qilindi. Bizni xodimlarimi tez orada siz bilam bog'lanishadi");
        sendMessage.setReplyMarkup(MyButtons.mainButtons());
        Bot bot = new Bot();
        SendMessage buyMessage = new SendMessage();
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException();
        }
        ProfileEntity profile = optional.get();
        StringBuilder products = new StringBuilder();
        Double totalPrice = 0.0;
        for (ProductEntity entity : cartList) {
            products.append(entity.getName()).append("\n");
            totalPrice += entity.getPrice();
        }
        buyMessage.setText("Zakaz: \n" + products
                + "\n" + "Umumiy narxi " + totalPrice
                + "\n" + "Telefon raqam" + profile.getContact());
        buyMessage.setChatId(String.valueOf(chatId));
        try {
            bot.execute(buyMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        cartList = new LinkedList<>();
        productList = new LinkedList<>();
    }

    public void setSettings(SendMessage sendMessage) {
        sendMessage.setReplyMarkup(MyButtons.settingsButtons());
    }
}
