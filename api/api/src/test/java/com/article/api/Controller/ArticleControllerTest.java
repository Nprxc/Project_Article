package com.article.api.Controller;

import com.article.api.controller.ArticleController;
import com.article.api.model.ArticleModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;



import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.article.api.service.ArticleService;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleController articleController;

    @MockBean
    private ArticleService articleService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllArticles() throws Exception {
        ArticleModel mockArticle = new ArticleModel();

        mockArticle.setId(1L);
        mockArticle.setTitle("title");
        mockArticle.setContent("content");
        mockArticle.setPublishedDate("1-2-3");


        when(articleController.getAllArticles()).thenReturn(Collections.singletonList(mockArticle));


        mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].content").value("content"))
                .andExpect(jsonPath("$[0].publishedDate").value("1-2-3"));

    }

    @Test
    void testGetArticleById() throws Exception {
        ArticleModel mockArticle = new ArticleModel();

        mockArticle.setId(1L);
        mockArticle.setTitle("title");
        mockArticle.setContent("content");
        mockArticle.setPublishedDate("1-2-3");


        when(articleController.getArticleById(1L)).thenReturn(mockArticle);

        mockMvc.perform(get("/article/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"))
                .andExpect(jsonPath("$.publishedDate").value("1-2-3"));
    }

    @Test
    @Disabled
    void testDeleteArticle() throws Exception {

        ArticleModel mockArticle = new ArticleModel();

        mockArticle.setId(1L);
        mockArticle.setTitle("title");
        mockArticle.setContent("content");
        mockArticle.setPublishedDate("1-2-3");

        // On mock le service qui est appelé par le controller
        doNothing().when(articleService).deleteArticle(mockArticle.getId());

        // Appeler la méthode du controller via MockMvc pour une requête DELETE
        mockMvc.perform(delete("/article/{id}",mockArticle.getId()))
                .andDo(print())
                .andExpect(status().isNoContent()); // Vérifie si le statut HTTP est 204 No Content
    }

    @Test
    void testCreateArticle() throws Exception {
        ArticleModel mockArticle = new ArticleModel();

        mockArticle.setId(1L);
        mockArticle.setTitle("title");
        mockArticle.setContent("content");
        mockArticle.setPublishedDate("1-2-3");

        when(articleController.createArticle(mockArticle)).thenReturn(mockArticle);

        mockMvc.perform(post("/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockArticle)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"))
                .andExpect(jsonPath("$.publishedDate").value("1-2-3"));
    }

    @Test
    @Disabled
    void testUpdateArticle() throws Exception {
        ArticleModel originalArticle = new ArticleModel();

        originalArticle.setId(1L);
        originalArticle.setTitle("title");
        originalArticle.setContent("content");
        originalArticle.setPublishedDate("1-2-3");

        ArticleModel updateArticle = new ArticleModel();

        updateArticle.setId(1L);
        updateArticle.setTitle("Update title");
        updateArticle.setContent("Update content");
        updateArticle.setPublishedDate("Update 1-2-3");


        when(articleService.updateArticle(1L, updateArticle)).thenReturn(updateArticle);

        mockMvc.perform(put("/edit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateArticle)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Update title"))
                .andExpect(jsonPath("$.content").value("Update content"))
                .andExpect(jsonPath("$.publishedDate").value("Update 1-2-3"));

    }
}