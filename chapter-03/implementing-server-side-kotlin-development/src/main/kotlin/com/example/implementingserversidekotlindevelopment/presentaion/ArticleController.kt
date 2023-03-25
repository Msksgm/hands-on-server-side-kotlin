package com.example.implementingserversidekotlindevelopment.presentaion

import arrow.core.handleError
import com.example.implementingserversidekotlindevelopment.openapi.generated.controller.ArticlesApi
import com.example.implementingserversidekotlindevelopment.openapi.generated.model.Article
import com.example.implementingserversidekotlindevelopment.openapi.generated.model.GenericErrorModel
import com.example.implementingserversidekotlindevelopment.openapi.generated.model.GenericErrorModelErrors
import com.example.implementingserversidekotlindevelopment.openapi.generated.model.MultipleArticleResponse
import com.example.implementingserversidekotlindevelopment.openapi.generated.model.NewArticleRequest
import com.example.implementingserversidekotlindevelopment.openapi.generated.model.SingleArticleResponse
import com.example.implementingserversidekotlindevelopment.openapi.generated.model.UpdateArticleRequest
import com.example.implementingserversidekotlindevelopment.usecase.CreateArticleUseCase
import com.example.implementingserversidekotlindevelopment.usecase.DeleteCreatedArticleUseCase
import com.example.implementingserversidekotlindevelopment.usecase.FeedArticleUseCase
import com.example.implementingserversidekotlindevelopment.usecase.ShowArticleUseCase
import com.example.implementingserversidekotlindevelopment.usecase.UpdateArticleUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

/**
 * 作成済記事記事のコントローラー
 *
 * @property showArticleUseCase 単一記事取得ユースケース
 * @property createdArticleUseCase 記事作成ユースケース
 * @property feedArticleUseCase 記事一覧取得ユースケース
 * @property updateArticleUseCase 記事更新ユースーケース
 * @property deleteCreatedArticleUseCase 記事削除ユースケース
 */
@RestController
class ArticleController(
    val showArticleUseCase: ShowArticleUseCase,
    val createdArticleUseCase: CreateArticleUseCase,
    val feedArticleUseCase: FeedArticleUseCase,
    val updateArticleUseCase: UpdateArticleUseCase,
    val deleteCreatedArticleUseCase: DeleteCreatedArticleUseCase,
) : ArticlesApi {
    override fun getArticle(slug: String): ResponseEntity<SingleArticleResponse> {
        /**
         * 作成済記事の取得
         */
        val createdArticle = showArticleUseCase.execute(slug).fold(
            { throw ShowArticleUseCaseErrorException(it) },
            { it }
        )

        return ResponseEntity(
            SingleArticleResponse(
                Article(
                    slug = createdArticle.slug.value,
                    title = createdArticle.title.value,
                    description = createdArticle.description.value,
                    body = createdArticle.body.value
                ),
            ),
            HttpStatus.OK
        )
    }

    /**
     * 単一記事取得ユースケースがエラーを戻したときの Exception
     *
     * このクラスの例外が発生したときに、@ExceptionHandler で例外処理をおこなう
     *
     * @property error
     */
    data class ShowArticleUseCaseErrorException(val error: ShowArticleUseCase.Error) : Exception()

    /**
     * ShowArticleUseCaseErrorException をハンドリングする関数
     *
     * ShowArticleUseCase.Error の型に合わせてレスポンスを分岐させる
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = [ShowArticleUseCaseErrorException::class])
    fun onShowArticleUseCaseErrorException(e: ShowArticleUseCaseErrorException): ResponseEntity<GenericErrorModel> =
        when (val error = e.error) {
            /**
             * 原因: slug に該当する記事が見つからなかった
             */
            is ShowArticleUseCase.Error.NotFoundArticleBySlug -> ResponseEntity<GenericErrorModel>(
                GenericErrorModel(
                    errors = GenericErrorModelErrors(
                        body = listOf("${error.slug.value} に該当する記事は見つかりませんでした")
                    )
                ),
                HttpStatus.NOT_FOUND
            )

            /**
             * 原因: バリデーションエラー
             */
            is ShowArticleUseCase.Error.ValidationErrors -> ResponseEntity<GenericErrorModel>(
                GenericErrorModel(
                    errors = GenericErrorModelErrors(
                        body = error.errors.map { it.message }
                    )
                ),
                HttpStatus.FORBIDDEN
            )
        }

    override fun createArticle(newArticleRequest: NewArticleRequest): ResponseEntity<SingleArticleResponse> {
        /**
         * 記事作成
         */
        val createdArticle = createdArticleUseCase.execute(
            title = newArticleRequest.article.title,
            description = newArticleRequest.article.description,
            body = newArticleRequest.article.body,
        ).fold(
            { throw CreateArticleUseCaseErrorException(it) },
            { it }
        )

        return ResponseEntity(
            SingleArticleResponse(
                article = Article(
                    slug = createdArticle.slug.value,
                    title = createdArticle.title.value,
                    body = createdArticle.body.value,
                    description = createdArticle.description.value,
                ),
            ),
            HttpStatus.CREATED
        )
    }

    /**
     * 記事作成ユースケースがエラーを戻したときの Exception
     *
     * このクラスの例外が発生したときに、@ExceptionHandler で例外をおこなう
     *
     * @property error
     */
    data class CreateArticleUseCaseErrorException(val error: CreateArticleUseCase.Error) : Throwable()

    /**
     * CreateArticleUseCaseErrorException をハンドリングする関数
     *
     * CreateArticleUseCase.Error の型に合わせてレスポンスを分岐する
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = [CreateArticleUseCaseErrorException::class])
    fun onCreateArticleUseCaseErrorException(e: CreateArticleUseCaseErrorException): ResponseEntity<GenericErrorModel> =
        when (val error = e.error) {
            /**
             * 原因: 記事のリクエストが不正
             */
            is CreateArticleUseCase.Error.InvalidArticle -> ResponseEntity<GenericErrorModel>(
                GenericErrorModel(
                    errors = GenericErrorModelErrors(
                        body = error.errors.map { it.message }
                    )
                ),
                HttpStatus.FORBIDDEN
            )
        }

    override fun getArticles(): ResponseEntity<MultipleArticleResponse> {
        val useCaseResult = feedArticleUseCase.execute().fold(
            { throw UnsupportedOperationException("想定外のエラー") },
            { it }
        )
        return ResponseEntity(
            MultipleArticleResponse(
                articleCount = useCaseResult.articlesCount,
                articles = useCaseResult.articles.map {
                    Article(
                        slug = it.slug.value,
                        title = it.title.value,
                        description = it.description.value,
                        body = it.body.value,
                    )
                }
            ),
            HttpStatus.OK
        )
    }

    override fun updateArticle(
        slug: String,
        updateArticleRequest: UpdateArticleRequest,
    ): ResponseEntity<SingleArticleResponse> {
        val updatedArticle = updateArticleUseCase.execute(
            slug = slug,
            title = updateArticleRequest.article.title,
            description = updateArticleRequest.article.description,
            body = updateArticleRequest.article.body
        ).fold({ throw UpdateArticleUseCaseErrorException(it) }, { it })

        return ResponseEntity(
            SingleArticleResponse(
                article = Article(
                    slug = updatedArticle.slug.value,
                    title = updatedArticle.title.value,
                    description = updatedArticle.description.value,
                    body = updatedArticle.body.value,
                ),
            ),
            HttpStatus.OK
        )
    }

    /**
     * 記事更新ユースケースがエラーを戻したときの Exception
     *
     * このクラスの例外が発生したときに、@ExceptionHandler で例外をおこなう
     *
     * @property error
     */
    data class UpdateArticleUseCaseErrorException(val error: UpdateArticleUseCase.Error) : Throwable()

    /**
     * UpdateArticleUseCaseErrorException をハンドリングする関数
     *
     * UpdateArticleUseCase.Error の型に合わせてレスポンスを分岐する
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = [UpdateArticleUseCaseErrorException::class])
    fun onUpdateArticleUseCaseErrorException(e: UpdateArticleUseCaseErrorException): ResponseEntity<GenericErrorModel> =
        when (val error = e.error) {
            is UpdateArticleUseCase.Error.NotFoundArticleBySlug -> ResponseEntity(
                GenericErrorModel(
                    errors = GenericErrorModelErrors(
                        body = listOf("${error.slug.value} に該当する記事は見つかりませんでした")
                    )
                ),
                HttpStatus.NOT_FOUND
            )

            is UpdateArticleUseCase.Error.InvalidArticle -> ResponseEntity(
                GenericErrorModel(
                    errors = GenericErrorModelErrors(
                        body = error.errors.map { it.message }
                    )
                ),
                HttpStatus.FORBIDDEN
            )

            is UpdateArticleUseCase.Error.ValidationErrors -> ResponseEntity(
                GenericErrorModel(
                    errors = GenericErrorModelErrors(
                        body = error.errors.map { it.message }
                    )
                ),
                HttpStatus.FORBIDDEN
            )
        }

    override fun deleteArticle(slug: String): ResponseEntity<Unit> {
        deleteCreatedArticleUseCase.execute(
            slug = slug
        ).handleError { throw DeleteArticleUseCaseErrorException(it) }

        return ResponseEntity(Unit, HttpStatus.OK)
    }

    /**
     * 記事削除ユースケースがエラーを戻したときの Exception
     *
     * このクラスの例外が発生したときに、@ExceptionHandler で例外をおこなう
     *
     * @property error
     */
    data class DeleteArticleUseCaseErrorException(val error: DeleteCreatedArticleUseCase.Error) : Exception()

    /**
     * DeleteArticleUseCaseErrorException をハンドリングする関数
     *
     * DeleteArticleUseCase.Error の型に合わせてレスポンスを分岐する
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = [DeleteArticleUseCaseErrorException::class])
    fun onDeleteArticleUseCaseErrorException(e: DeleteArticleUseCaseErrorException): ResponseEntity<GenericErrorModel> =
        when (val error = e.error) {
            is DeleteCreatedArticleUseCase.Error.NotFoundArticleBySlug -> ResponseEntity(
                GenericErrorModel(
                    errors = GenericErrorModelErrors(
                        body = listOf("${error.slug.value} に該当する記事は見つかりませんでした")
                    )
                ),
                HttpStatus.NOT_FOUND
            )

            is DeleteCreatedArticleUseCase.Error.ValidationErrors -> ResponseEntity(
                GenericErrorModel(
                    errors = GenericErrorModelErrors(
                        body = error.errors.map { it.message }
                    )
                ),
                HttpStatus.FORBIDDEN
            )
        }
}
