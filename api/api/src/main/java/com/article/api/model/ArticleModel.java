package com.article.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "articles")
public class ArticleModel {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "published_date")
    private String publishedDate;

}
