package com.example.implementingserversidekotlindevelopment.usecase

import arrow.core.Either
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
 */
@Service
class FeedArticleUseCaseImpl : FeedArticleUseCase
