package com.example.implementingserversidekotlindevelopment.infra

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.implementingserversidekotlindevelopment.domain.ArticleRepository
import com.example.implementingserversidekotlindevelopment.domain.Body
import com.example.implementingserversidekotlindevelopment.domain.CreatedArticle
import com.example.implementingserversidekotlindevelopment.domain.Description
import com.example.implementingserversidekotlindevelopment.domain.Slug
import com.example.implementingserversidekotlindevelopment.domain.Title
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

/**
 * 作成済記事リポジトリの具象クラス
 *
 * @property namedParameterJdbcTemplate
 */
@Repository
class ArticleRepositoryImpl(val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : ArticleRepository {
    override fun findBySlug(slug: Slug): Either<ArticleRepository.FindBySlugError, CreatedArticle> {
        val sql = """
            SELECT
                articles.slug
                , articles.title
                , articles.body
                , articles.description
            FROM
                articles
            WHERE
                slug = :slug
        """.trimIndent()
        val articleMapList = namedParameterJdbcTemplate.queryForList(sql, MapSqlParameterSource().addValue("slug", slug.value))

        /**
         * DB から作成済記事が見つからなかった場合、早期 return
         */
        if (articleMapList.isEmpty()) {
            return ArticleRepository.FindBySlugError.NotFound(slug = slug).left()
        }

        val articleMap = articleMapList.first()

        return CreatedArticle.newWithoutValidation(
            slug = Slug.newWithoutValidation(articleMap["slug"].toString()),
            title = Title.newWithoutValidation(articleMap["title"].toString()),
            description = Description.newWithoutValidation(articleMap["description"].toString()),
            body = Body.newWithoutValidation(articleMap["body"].toString()),
        ).right()
    }

    override fun create(createdArticle: CreatedArticle): Either<ArticleRepository.CreateArticleError, CreatedArticle> {
        val sql = """
            INSERT INTO articles (
                slug
                , title
                , body
                , description
            )
            VALUES (
                :slug
                , :title
                , :body
                , :description
            )
        """.trimIndent()
        namedParameterJdbcTemplate.update(
            sql,
            MapSqlParameterSource()
                .addValue("slug", createdArticle.slug.value)
                .addValue("title", createdArticle.title.value)
                .addValue("body", createdArticle.body.value)
                .addValue("description", createdArticle.description.value)
        )
        return createdArticle.right()
    }

    override fun all(): Either<ArticleRepository.FindError, List<CreatedArticle>> {
        val sql = """
            SELECT
                articles.slug
                , articles.title
                , articles.body
                , articles.description
            FROM
                articles
            ;
        """.trimIndent()
        val articleMap = namedParameterJdbcTemplate.queryForList(sql, MapSqlParameterSource())
        return articleMap.map {
            CreatedArticle.newWithoutValidation(
                Slug.newWithoutValidation(it["slug"].toString()),
                Title.newWithoutValidation(it["title"].toString()),
                Description.newWithoutValidation(it["description"].toString()),
                Body.newWithoutValidation(it["body"].toString())
            )
        }.right()
    }
}
