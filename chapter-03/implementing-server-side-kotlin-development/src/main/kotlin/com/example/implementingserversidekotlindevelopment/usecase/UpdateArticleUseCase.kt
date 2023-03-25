package com.example.implementingserversidekotlindevelopment.usecase

import arrow.core.Either
import com.example.implementingserversidekotlindevelopment.domain.CreatedArticle
import com.example.implementingserversidekotlindevelopment.domain.Slug
import com.example.implementingserversidekotlindevelopment.util.ValidationError
import org.springframework.stereotype.Service

/**
 * 記事更新ユースケース
 *
 */
interface UpdateArticleUseCase {
    /**
     * 記事更新
     *
     * @param title
     * @param description
     * @param body
     * @return
     */
    fun execute(slug: String?, title: String?, description: String?, body: String?): Either<Error, CreatedArticle> =
        throw NotImplementedError()

    /**
     * 記事更新ユースケースのエラー
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
         * 不正な記事
         *
         * @property errors
         */
        data class InvalidArticle(val errors: List<ValidationError>) : Error

        /**
         * slug から記事が見つからなかった
         *
         * @property slug
         */
        data class NotFoundArticleBySlug(val slug: Slug) : Error
    }
}

/**
 * 記事更新ユースケースの具象クラス
 *
 */
@Service
class UpdateArticleUseCaseImpl : UpdateArticleUseCase
