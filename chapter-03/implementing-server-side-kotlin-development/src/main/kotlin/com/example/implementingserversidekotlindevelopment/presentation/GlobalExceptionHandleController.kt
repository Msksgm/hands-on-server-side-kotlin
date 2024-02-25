package com.example.implementingserversidekotlindevelopment.presentation

import com.example.implementingserversidekotlindevelopment.presentation.model.GenericErrorModel
import com.example.implementingserversidekotlindevelopment.presentation.model.GenericErrorModelErrors
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.util.Locale

/**
 * グローバルに例外ハンドリグし、エラーレスポンスを実現するコントローラ
 *
 * 予期しない例外が発生した際に、以下を実施する。
 * - 秘密情報をレスポンスに含めないためのエラーハンドリング
 * - 原因調査のためのログ出力
 *
 */
@RestControllerAdvice
class GlobalExceptionHandleController {
    /**
     * 存在しないエンドポイントにリクエストされた時のエラーレスポンスを作成するメソッド
     *
     * @param e
     * @return 404 エラーのレスポンス
     */
    @ExceptionHandler(NoResourceFoundException::class)
    @Suppress("UnusedParameter") // NoResourceFoundException は理由が明確なので、e を使わずに linter を抑制する
    fun noResourceFoundExceptionHandler(e: NoResourceFoundException): ResponseEntity<GenericErrorModel> {
        return ResponseEntity<GenericErrorModel>(
            GenericErrorModel(
                errors = GenericErrorModelErrors(
                    body = listOf("該当するエンドポイントがありませんでした")
                ),
            ),
            HttpStatus.NOT_FOUND
        )
    }

    /**
     * 許可されていないメソッドでリクエストを送った時のエラーレスポンスを作成するメソッド
     *
     * @param e
     * @return 405 エラーのレスポンス
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun noHttpRequestMethodNotSupportedExceptionHandler(
        e: HttpRequestMethodNotSupportedException
    ): ResponseEntity<GenericErrorModel> {
        return ResponseEntity<GenericErrorModel>(
            GenericErrorModel(
                errors = GenericErrorModelErrors(
                    body = listOf("該当エンドポイントで${e.method}メソッドの処理は許可されていません")
                ),
            ),
            HttpStatus.METHOD_NOT_ALLOWED
        )
    }

    /**
     * エンドポイントが想定していない Content-Type でリクエストされた時にエラーレスポンスを作成するメソッド
     *
     * @param e
     * @return 415 エラーのレスポンス
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun noHttpMediaTypeNotSupportedException(
        e: HttpMediaTypeNotSupportedException
    ): ResponseEntity<GenericErrorModel> {
        return ResponseEntity<GenericErrorModel>(
            GenericErrorModel(
                errors = GenericErrorModelErrors(
                    body = listOf("該当エンドポイントで${e.contentType}のリクエストはサポートされていません")
                ),
            ),
            HttpStatus.UNSUPPORTED_MEDIA_TYPE
        )
    }

    /**
     * httpMessageNotReadableExceptionHandler
     *
     * API スキーマが想定していないリクエストだった場合に発生させるエラーレスポンスを作成するメソッド
     *
     * @param e
     * @return 400 エラーのレスポンス
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    @Suppress("UnusedParameter")
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException): ResponseEntity<GenericErrorModel> {
        return ResponseEntity<GenericErrorModel>(
            GenericErrorModel(
                errors = GenericErrorModelErrors(
                    body = listOf("エンドポイントが想定していない形式または型のリクエストが送られました")
                ),
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * リクエストのバリデーションエラーが発生した場合に発生させるエラーレスポンスを作成するメソッド
     *
     * @param e
     * @return 400 エラーのレスポンス
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<GenericErrorModel> {
        // エラーメッセージ本文の作成。FieldError の場合は、フィールド名を含める
        val messages = e.bindingResult.allErrors.map {
            when (it) {
                is FieldError -> "${it.field}は${it.defaultMessage}"
                else -> it.defaultMessage.toString()
            }
        }
        return ResponseEntity<GenericErrorModel>(
            GenericErrorModel(
                errors = GenericErrorModelErrors(
                    body = messages
                ),
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * パスパラメータまたはクエリパラメータのバリデーションエラーが発生した場合に発生させるエラーレスポンスを作成するメソッド
     *
     * @param e
     * @return 400 エラーのレスポンス
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationExceptionExceptionHandler(e: ConstraintViolationException): ResponseEntity<GenericErrorModel> {
        // ConstraintViolationException からレスポンスに利用するエラーメッセージを生成する
        val messages = e.constraintViolations.map {
            /**
             * クエリパラメータをチェインケースに変換
             *
             * propertyPath は `@PathVariable` アノテーションで指定したパスパラメータではなく、メソッドの引数に指定したパラメータ名を取得する
             * 例えば、以下のようなコントローラメソッドの場合、propertyPath が取得するのは、`page-number` ではなく `getArticle.pageNumber` になる
             *
             * ```kotlin
             * fun getArticle(@RequestParam("page-number") @Max(20) pageNumber: String): ResponseEntity<Any>
             * ```
             *
             * API 仕様書と一致しなくなる可能性があるため、チェインケースに変換する。
             * **API 仕様書におけるパスパラメータとクエリパラメータの命名規則は必ず統一**すること。
             */
            val chainCaseRequestParam = it.propertyPath.toString().split('.').last().split(Regex("(?=[A-Z])")).joinToString("-") { param ->
                param.lowercase(
                    Locale.getDefault()
                )
            }
            "${chainCaseRequestParam}は${it.message}"
        }
        return ResponseEntity<GenericErrorModel>(
            GenericErrorModel(
                errors = GenericErrorModelErrors(
                    body = messages
                ),
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}
