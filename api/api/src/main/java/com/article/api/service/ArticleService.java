package com.article.api.service;


import com.article.api.model.ArticleModel;
import com.article.api.repository.ArticlesRepository;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class ArticleService {
    @Autowired
    private ArticlesRepository articlesRepository;

    public Optional<ArticleModel> getArticleById(final Long id) {
        return articlesRepository.findById(id);
    }

    public Iterable<ArticleModel> getAllArticles() {
        return articlesRepository.findAll();
    }

    public ArticleModel saveArticle(ArticleModel article) {
        return articlesRepository.save(article);
    }

    public void deleteArticle(final Long id) {
        articlesRepository.deleteById(id);
    }

    public ArticleModel updateArticle(final Long id, ArticleModel article) {
        // Vérifier si l'article existe
        Optional<ArticleModel> existingArticle = articlesRepository.findById(id);

        ArticleModel updatedArticle = null;
        if (existingArticle.isPresent()) {
            // Si l'article existe, mettre à jour les champs nécessaires
            updatedArticle = existingArticle.get();
            updatedArticle.setTitle(article.getTitle());
            updatedArticle.setContent(article.getContent());
            updatedArticle.setPublishedDate(article.getPublishedDate());

            // Sauvegarder les modifications
            return articlesRepository.save(updatedArticle);
        } else {
            return null;
        }
    }
}
