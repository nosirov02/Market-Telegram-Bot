package com.company.tgmarket.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ("profile"))
public class ProfileEntity {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String username;
    @Column
    private Long chatId;
    @Column(name = ("register_date"))
    private LocalDateTime registerDate;
    @Column
    private String contact;
}
