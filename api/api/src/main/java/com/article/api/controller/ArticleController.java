package com.article.api.controller;

import com.article.api.dto.ArticleDTO;
import com.article.api.mapper.ArticleMapper;
import com.article.api.service.ArticleService;
import com.article.api.model.ArticleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8080") // Autoriser CORS pour ce contrôleur
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;  // Injecter le mapper

    @GetMapping("/home")
    public List<ArticleDTO> getAllArticles() {
        List<ArticleModel> articles = (List<ArticleModel>) articleService.getAllArticles();
        return articles.stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable("id") Long id) {
        Optional<ArticleModel> articleModel = articleService.getArticleById(id);
        if (articleModel.isPresent()) {
            ArticleDTO articleDTO = articleMapper.toDTO(articleModel.get());
            return ResponseEntity.ok(articleDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/new")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        ArticleModel article = articleMapper.toEntity(articleDTO);
        ArticleModel savedArticle = articleService.saveArticle(article);
        return ResponseEntity.ok(articleMapper.toDTO(savedArticle));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable("id") Long id, @RequestBody ArticleDTO articleDTO) {
        Optional<ArticleModel> a = articleService.getArticleById(id);
        if (a.isPresent()) {
            ArticleModel currentArticle = a.get();

            // Mise à jour des champs
            String title = articleDTO.getTitle();
            if (title != null) {
                currentArticle.setTitle(title);
            }

            String content = articleDTO.getContent();
            if (content != null) {
                currentArticle.setContent(content);
            }

            String publishedDate = articleDTO.getPublishedDate();
            if (publishedDate != null) {
                currentArticle.setPublishedDate(publishedDate);
            }

            ArticleModel updatedArticle = articleService.saveArticle(currentArticle);
            return ResponseEntity.ok(articleMapper.toDTO(updatedArticle));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
