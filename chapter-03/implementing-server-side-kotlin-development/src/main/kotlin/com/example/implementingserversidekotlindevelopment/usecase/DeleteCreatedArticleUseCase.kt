package com.example.implementingserversidekotlindevelopment.usecase

import arrow.core.Either
import arrow.core.getOrHandle
import arrow.core.left
import arrow.core.right
import com.example.implementingserversidekotlindevelopment.domain.ArticleRepository
import com.example.implementingserversidekotlindevelopment.domain.Slug
import com.example.implementingserversidekotlindevelopment.util.ValidationError
import org.springframework.stereotype.Service

/**
 * 記事削除ユースケース
 *
 */
interface DeleteCreatedArticleUseCase {
    /**
     * 記事削除
     *
     * @param slug
     * @return
     */
    fun execute(slug: String?): Either<Error, Unit> = throw NotImplementedError()

    /**
     * 記事削除ユースケースのエラー
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
         * Slug に該当する記事が見つからなかった
         *
         * @property slug
         */
        data class NotFoundArticleBySlug(val slug: Slug) : Error
    }
}

/**
 * 記事削除ユースケースの具象クラス
 *
 * @property articleRepository
 */
@Service
class DeleteCreatedArticleUseCaseImpl(
    val articleRepository: ArticleRepository,
) : DeleteCreatedArticleUseCase {
    override fun execute(slug: String?): Either<DeleteCreatedArticleUseCase.Error, Unit> {
        /**
         * slug のバリデーション
         *
         * 不正だった場合、早期 return
         */
        val validatedSlug = Slug.new(slug = slug).fold(
            { return DeleteCreatedArticleUseCase.Error.ValidationErrors(it).left() },
            { it }
        )

        /**
         * 作成済記事の削除
         *
         * slug に該当する記事が存在しない場合、早期 return
         */
        articleRepository.delete(validatedSlug).getOrHandle {
            return when (it) {
                is ArticleRepository.DeleteError.NotFound ->
                    DeleteCreatedArticleUseCase.Error.NotFoundArticleBySlug(validatedSlug).left()
            }
        }

        return Unit.right()
    }
}
