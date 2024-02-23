package com.example.implementingserversidekotlindevelopment.usecase

import arrow.core.Either
import arrow.core.getOrElse
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
 * @property articleRepository
 */
@Service
class ShowArticleUseCaseImpl(val articleRepository: ArticleRepository) : ShowArticleUseCase {
    override fun execute(slug: String): Either<ShowArticleUseCase.Error, CreatedArticle> {
        /**
         * slug の検証
         *
         * 不正な slug だった場合、早期 return
         */
        val validatedSlug = Slug.new(slug).getOrElse { return ShowArticleUseCase.Error.ValidationErrors(it.all).left() }

        /**
         * 記事の取得
         *
         * 取得失敗した場合、早期 return
         */
        val createdArticle = articleRepository.findBySlug(validatedSlug).getOrElse {
            return when (it) {
                is ArticleRepository.FindBySlugError.NotFound -> ShowArticleUseCase.Error.NotFoundArticleBySlug(it.slug).left()
            }
        }

        return createdArticle.right()
    }
}
