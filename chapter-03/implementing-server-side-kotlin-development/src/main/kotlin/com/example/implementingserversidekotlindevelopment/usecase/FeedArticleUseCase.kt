package com.example.implementingserversidekotlindevelopment.usecase

import arrow.core.Either
import arrow.core.right
import com.example.implementingserversidekotlindevelopment.domain.ArticleRepository
import com.example.implementingserversidekotlindevelopment.domain.CreatedArticle
import org.springframework.stereotype.Service

/**
 * 作成済記事一覧表示ユースケース
 *
 */
interface FeedArticleUseCase {
    /**
     * 作成済記事一覧表示ユースケースの DTO
     *
     * @property articles
     * @property articlesCount
     */
    data class FeedCreatedArticles(
        val articles: List<CreatedArticle>,
        val articlesCount: Int,
    )

    /**
     * 作成済記事一覧表示ユースケースの実行メソッド
     *
     * @return
     */
    fun execute(): Either<Error, FeedCreatedArticles> = throw NotImplementedError()

    /**
     * 作成済記事一覧表示ユースケースのエラー
     *
     * エラーになるパターンが存在しない
     *
     */
    sealed interface Error
}

/**
 * 作成済記事一覧表示ユースケースの具象クラス
 *
 * @property articleRepository
 */
@Service
class FeedArticleUseCaseImpl(val articleRepository: ArticleRepository) : FeedArticleUseCase {
    override fun execute(): Either<FeedArticleUseCase.Error, FeedArticleUseCase.FeedCreatedArticles> {
        /**
         * 記事を全て取得する
         */
        val createdArticles = articleRepository.find().fold(
            { throw UnsupportedOperationException("想定外のエラー") },
            { it }
        )

        return FeedArticleUseCase.FeedCreatedArticles(
            articles = createdArticles,
            articlesCount = createdArticles.size
        ).right()
    }
}
