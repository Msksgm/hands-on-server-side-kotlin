package com.example.implementingserversidekotlindevelopment.usecase

import arrow.core.Either
import com.example.implementingserversidekotlindevelopment.domain.CreatedArticle
import com.example.implementingserversidekotlindevelopment.domain.Slug
import com.example.implementingserversidekotlindevelopment.util.ValidationError
import org.springframework.stereotype.Service

/**
 * 作成済記事の単一取得ユースケース
 *
 */
interface ShowArticleUseCase {
    /**
     * 単一記事取得
     *
     * @param slug
     * @return
     */
    fun execute(slug: String): Either<Error, CreatedArticle> = throw NotImplementedError()

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
 */
@Service
class ShowArticleUseCaseImpl : ShowArticleUseCase
