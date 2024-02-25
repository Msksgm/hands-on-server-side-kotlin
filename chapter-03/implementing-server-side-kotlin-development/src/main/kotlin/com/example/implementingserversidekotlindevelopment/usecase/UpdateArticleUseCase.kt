package com.example.implementingserversidekotlindevelopment.usecase

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import com.example.implementingserversidekotlindevelopment.domain.ArticleRepository
import com.example.implementingserversidekotlindevelopment.domain.CreatedArticle
import com.example.implementingserversidekotlindevelopment.domain.Slug
import com.example.implementingserversidekotlindevelopment.domain.UpdatableCreatedArticle
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
    fun execute(slug: String, title: String, description: String, body: String): Either<Error, CreatedArticle> = throw NotImplementedError()

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
 * @property articleRepository
 */
@Service
class UpdateArticleUseCaseImpl(val articleRepository: ArticleRepository) : UpdateArticleUseCase {
    override fun execute(slug: String, title: String, description: String, body: String): Either<UpdateArticleUseCase.Error, CreatedArticle> {
        /**
         * slug のバリデーション
         *
         * 不正だった場合、早期 return
         */
        val validatedSlug = Slug.new(slug).getOrElse { return UpdateArticleUseCase.Error.ValidationErrors(it).left() }

        /**
         * 更新用作成済記事の生成
         *
         * 不正だった場合、早期 return
         */
        val unsavedUpdatableArticle = UpdatableCreatedArticle.new(title, description, body).getOrElse { return UpdateArticleUseCase.Error.ValidationErrors(it).left() }

        /**
         * 作成済記事の更新
         */
        val createdArticle = articleRepository.update(validatedSlug, unsavedUpdatableArticle).getOrElse { return UpdateArticleUseCase.Error.NotFoundArticleBySlug(validatedSlug).left() }

        return createdArticle.right()
    }
}
