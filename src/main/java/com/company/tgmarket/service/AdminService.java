package com.company.tgmarket.service;

import com.company.tgmarket.entity.ProductEntity;
import com.company.tgmarket.repository.ProductRepository;
import com.company.tgmarket.util.MyButtons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.List;

@Service
public class AdminService {
    private ProductEntity productEntity;
    @Autowired
    private ProductRepository repository;

    public void createProduct() {
        productEntity = new ProductEntity();
    }

    public boolean hasProduct() {
        return productEntity != null;
    }

    public void addProduct(String text, SendMessage sendMessage) {
        if (productEntity.getName() == (null)) {
            productEntity.setName(text);
            sendMessage.setText("Product kategoriyasini tanlang");
            sendMessage.setReplyMarkup(MyButtons.onlyCategory());
        } else if (productEntity.getCategory() == (null)) {
            productEntity.setCategory(text);
            sendMessage.setText("Product narxini kiriting");
        } else if (productEntity.getPrice() == (null)) {
            productEntity.setPrice(Double.valueOf(text));
            sendMessage.setText("Product rasmini tashlang");
        }
    }

    public void saveProduct(List<PhotoSize> photoList) {
        PhotoSize photo = photoList.get(photoList.size() - 1);
        productEntity.setImageId(photo.getFileId());
        repository.save(productEntity);
    }
}
