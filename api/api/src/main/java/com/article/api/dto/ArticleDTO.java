package com.article.api.dto;

import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private String publishedDate;
}
