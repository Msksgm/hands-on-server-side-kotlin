package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.Either

/**
 * 作成済記事リポジトリ
 *
 */
interface ArticleRepository {
    /**
     * slug から作成済記事を取得
     *
     * @param slug
     * @return
     */
    fun findBySlug(slug: Slug): Either<FindBySlugError, CreatedArticle> = throw NotImplementedError()

    /**
     * 作成済記事取得時のエラー
     *
     */
    sealed interface FindBySlugError {
        /**
         * slug に該当する記事が見つからなかった
         *
         * @property slug
         */
        data class NotFound(val slug: Slug) : FindBySlugError
    }

    /**
     * 作成済記事を保存
     *
     * @param createdArticle
     * @return
     */
    fun create(
        createdArticle: CreatedArticle,
    ): Either<CreateArticleError, CreatedArticle> = throw NotImplementedError()

    /**
     * 作成済記事を保存したときのエラー
     *
     */
    sealed interface CreateArticleError

    /**
     * 作成済記事の一覧取得
     *
     * @return
     */
    fun find(): Either<FindError, List<CreatedArticle>> = throw NotImplementedError()

    /**
     * ArticleRepository.find のエラーインタフェース
     *
     * エラーになるパターンが存在しない
     */
    sealed interface FindError
}
