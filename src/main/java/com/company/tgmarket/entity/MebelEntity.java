package com.company.tgmarket.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = ("mebel"))
@Getter
@Setter
public class MebelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Double price;
    @Column
    private String category;
    @Column
    private String imageURL;
    @Column
    private Integer imageSize;
    @Column(name = ("created_date"))
    private LocalDateTime createdDate;

}