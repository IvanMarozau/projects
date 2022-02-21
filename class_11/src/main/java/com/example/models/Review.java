package com.example.models;

import javax.persistence.*;
import com.example.models.User;

@Entity //говорим что в бд будет созданна таблица Review
public class Review {

    @Id//уникальный ключь
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")//переименование
    private Long id;

    private String title,text;

    @ManyToOne(fetch = FetchType.EAGER)//говорит о том что у одного пользователя может быть много отзывов EAGER говорит о том что автор будет выбираться сразу же при выборке самого отзыва
    @JoinColumn(name="user_id") //название для поля в базе данных
    private User author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Review() {
    }

    public Review(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.author=user;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
