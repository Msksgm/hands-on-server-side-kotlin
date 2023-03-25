package com.example.implementingserversidekotlindevelopment.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.implementingserversidekotlindevelopment.domain.ArticleRepository
import com.example.implementingserversidekotlindevelopment.domain.CreatedArticle
import com.example.implementingserversidekotlindevelopment.domain.Slug
import com.example.implementingserversidekotlindevelopment.util.ValidationError
import org.springframework.stereotype.Service

/**
 * 作成済記事の単一取得ユースケース
 *
 * @constructor Create empty Show article use case
 */
interface ShowArticleUseCase {
    /**
     * 単一記事取得
     *
     * @param slug
     * @return
     */
    fun execute(slug: String?): Either<Error, CreatedArticle> = throw NotImplementedError()

    /**
     * 単一記事取得のエラー
     *
     */
    sealed interface Error {
        /**
         * バリデーションエラー
         *
         * @property errors
         */
        data class ValidationErrors(val errors: List<ValidationError>) : Error

        /**
         * slug から記事が見つからなかった
         *
         * @property slug
         */
        data class NotFoundArticleBySlug(val slug: Slug) : Error
    }
}

/**
 * 作成済記事の単一取得ユースケースの具象クラス
 *
 * @property articleRepository
 */
@Service
class ShowArticleUseCaseImpl(val articleRepository: ArticleRepository) : ShowArticleUseCase {
    override fun execute(slug: String?): Either<ShowArticleUseCase.Error, CreatedArticle> {
        /**
         * slug の検証
         *
         * 不正な slug だった場合、早期 return
         */
        val validatedSlug = Slug.new(slug).fold(
            { return ShowArticleUseCase.Error.ValidationErrors(it.all).left() },
            { it }
        )

        /**
         * 記事の取得
         */
        val createdArticle = articleRepository.findBySlug(validatedSlug).fold(
            /**
             * 取得失敗
             */
            {
                return when (it) {
                    /**
                     * 原因: 記事が見つからなかった
                     */
                    is ArticleRepository.FindBySlugError.NotFound -> ShowArticleUseCase.Error.NotFoundArticleBySlug(
                        validatedSlug
                    ).left()
                }
            },
            /**
             * 取得成功
             */
            { it }
        )

        return createdArticle.right()
    }
}
