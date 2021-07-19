package com.company.tgmarket.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Getter
@Setter
public class PingService {

    @Scheduled(fixedRateString = ("1200000"))
    public void pingMe() {
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("Ping {}, OK: response code{}, " + url.getHost() + ", " + connection.getResponseCode());
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("Ping Failed");
            e.printStackTrace();
        }
    }
}
