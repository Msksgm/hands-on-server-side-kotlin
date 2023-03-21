package com.example.implementingserversidekotlindevelopment.usecase

import arrow.core.Either
import com.example.implementingserversidekotlindevelopment.domain.CreatedArticle
import com.example.implementingserversidekotlindevelopment.util.ValidationError
import org.springframework.stereotype.Service

/**
 * 記事作成ユースケース
 *
 */
interface CreateArticleUseCase {
    /**
     * 記事作成
     *
     * @param title
     * @param description
     * @param body
     * @return
     */
    fun execute(title: String?, description: String?, body: String?): Either<Error, CreatedArticle> =
        throw NotImplementedError()

    /**
     * 記事作成ユースケースのエラー
     *
     */
    sealed interface Error {
        /**
         * 記事のリクエストが不正だった
         *
         * @property errors
         */
        data class InvalidArticle(val errors: List<ValidationError>) : Error
    }
}

/**
 * 作成済記事の作成ユースケース取得クラス
 *
 */
@Service
class CreateArticleUseCaseImpl : CreateArticleUseCase
