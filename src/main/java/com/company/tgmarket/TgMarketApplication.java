package com.company.tgmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TgMarketApplication {
    private final Bot bot;

    public TgMarketApplication(Bot bot) {
        this.bot = bot;
    }

    public static void main(String[] args) {
//        ApiContextInitializer.init();
        SpringApplication.run(TgMarketApplication.class, args);

//        TelegramBotsApi telegramBotsApi = null;
//        try {
//
//            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(new Bot());
//
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }

}
