package com.article.api.mapper;

import com.article.api.dto.ArticleDTO;
import com.article.api.model.ArticleModel;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    // Mapper Entité -> DTO
    public ArticleDTO toDTO(ArticleModel article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setPublishedDate(article.getPublishedDate());
        return dto;
    }

    // Mapper DTO -> Entité
    public ArticleModel toEntity(ArticleDTO dto) {
        ArticleModel article = new ArticleModel();
        article.setId(dto.getId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setPublishedDate(dto.getPublishedDate());
        return article;
    }
}
