package com.article.api.repository;

import com.article.api.model.ArticleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends CrudRepository<ArticleModel, Long> {

}
