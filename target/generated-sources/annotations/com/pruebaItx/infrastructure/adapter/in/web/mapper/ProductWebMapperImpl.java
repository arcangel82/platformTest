package com.pruebaItx.infrastructure.adapter.in.web.mapper;

import com.pruebaItx.domain.model.Product;
import com.pruebaItx.domain.model.Stock;
import com.pruebaItx.web.application.entities.Article;
import com.pruebaItx.web.application.entities.ArticleStock;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-23T12:55:50+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Amazon.com Inc.)"
)
@Component
public class ProductWebMapperImpl implements ProductWebMapper {

    @Override
    public Article toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Article article = new Article();

        article.setStock( stockToArticleStock( product.getStock() ) );
        article.setId( product.getId() );
        article.setName( product.getName() );
        article.setSales( product.getSalesUnits() );

        return article;
    }

    protected ArticleStock stockToArticleStock(Stock stock) {
        if ( stock == null ) {
            return null;
        }

        ArticleStock articleStock = new ArticleStock();

        articleStock.setSizeS( stock.getSizeS() );
        articleStock.setSizeM( stock.getSizeM() );
        articleStock.setSizeL( stock.getSizeL() );

        return articleStock;
    }
}
