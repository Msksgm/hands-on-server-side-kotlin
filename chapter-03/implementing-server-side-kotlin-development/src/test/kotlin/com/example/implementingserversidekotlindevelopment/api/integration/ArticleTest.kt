package com.example.implementingserversidekotlindevelopment.api.integration

import com.example.implementingserversidekotlindevelopment.infra.DbConnection
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.comparator.CustomComparator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class ArticleTest {
    @SpringBootTest
    @AutoConfigureMockMvc
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DBRider
    class GetArticle(
        @Autowired val mockMvc: MockMvc,
    ) {
        @BeforeEach
        fun reset() = DbConnection.resetSequence()

        @Test
        @DataSet(
            value = [
                "datasets/yml/given/articles.yml"
            ]
        )
        fun `正常系-slug に該当する作成済記事を取得する`() {
            /**
             * given:
             */
            val slug = "slug0000000000000000000000000001"

            /**
             * when:
             */
            val response = mockMvc.get("/api/articles/$slug") {
                contentType = MediaType.APPLICATION_JSON
            }.andReturn().response
            val actualStatus = response.status
            val actualResponseBody = response.contentAsString

            /**
             * then:
             * - ステータスコードが一致する
             * - レスポンスボディが一致する
             */
            val expectedStatus = HttpStatus.OK.value()
            val expectedResponseBody = """
                {
                  "article": {
                    "slug": "slug0000000000000000000000000001",
                    "title": "dummy-title-01",
                    "description": "dummy-description-01",
                    "body": "dummy-body-01"
                  }
                }
            """.trimIndent()
            assertThat(actualStatus).isEqualTo(expectedStatus)
            JSONAssert.assertEquals(
                expectedResponseBody,
                actualResponseBody,
                JSONCompareMode.STRICT
            )
        }

        @Test
        fun `異常系-slug のフォーマットが不正な場合、バリデーションエラー`() {
            /**
             * given:
             * - 不正なフォーマットの slug
             */
            val slug = "dummy-slug"

            /**
             * when:
             */
            val response = mockMvc.get("/api/articles/$slug") {
                contentType = MediaType.APPLICATION_JSON
            }.andReturn().response
            val actualStatus = response.status
            val actualResponseBody = response.contentAsString

            /**
             * then:
             * - ステータスコードが一致する
             * - レスポンスボディが一致する
             */
            val expectedStatus = HttpStatus.FORBIDDEN.value()
            val expectedResponseBody = """
                {
                  "errors": {
                    "body": [
                      "slug は 32 文字の英小文字数字です。"
                    ]
                  }
                }
            """.trimIndent()
            assertThat(actualStatus).isEqualTo(expectedStatus)
            JSONAssert.assertEquals(
                expectedResponseBody,
                actualResponseBody,
                JSONCompareMode.NON_EXTENSIBLE
            )
        }

        @Test
        @DataSet(
            value = [
                "datasets/yml/given/empty-articles.yml"
            ]
        )
        fun `異常系-slug に該当する作成済記事が存在しない場合、該当する記事が見つからないエラー`() {
            /**
             * given:
             * - DB に存在しない作成済記事
             */
            val slug = "slug0000000000000000000000000001"

            /**
             * when:
             */
            val response = mockMvc.get("/api/articles/$slug") {
                contentType = MediaType.APPLICATION_JSON
            }.andReturn().response
            val actualStatus = response.status
            val actualResponseBody = response.contentAsString

            /**
             * then:
             * - ステータスコードが一致する
             * - レスポンスボディが一致する
             */
            val expectedStatus = HttpStatus.NOT_FOUND.value()
            val expectedResponseBody = """
                {
                  "errors": {
                    "body": [
                      "slug0000000000000000000000000001 に該当する記事は見つかりませんでした"
                    ]
                  }
                }
            """.trimIndent()
            assertThat(actualStatus).isEqualTo(expectedStatus)
            JSONAssert.assertEquals(
                expectedResponseBody,
                actualResponseBody,
                JSONCompareMode.NON_EXTENSIBLE
            )
        }
    }

    @SpringBootTest
    @AutoConfigureMockMvc
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DBRider
    class CreateArticle(
        @Autowired val mockMvc: MockMvc,
    ) {
        @BeforeEach
        fun reset() = DbConnection.resetSequence()

        @Test
        @DataSet(
            value = [
                "datasets/yml/given/empty-articles.yml"
            ]
        )
        @ExpectedDataSet(
            value = ["datasets/yml/then/created-articles.yml"],
            orderBy = ["id"],
            ignoreCols = ["slug"]
        )
        fun `正常系-記事が作成される`() {
            /**
             * given:
             */
            val requestBody = """
                {
                  "article": {
                    "title": "dummy-title-01",
                    "description": "dummy-description-01",
                    "body": "dummy-body-01"
                  }
                }
            """.trimIndent()

            /**
             * when:
             */
            val response = mockMvc.post("/api/articles") {
                contentType = MediaType.APPLICATION_JSON
                content = requestBody
            }.andReturn().response
            val actualStatus = response.status
            val actualResponseBody = response.contentAsString

            /**
             * then:
             * - ステータスコードが一致する
             * - レスポンスボディが一致する
             * - JSON レスポンスを比較する。slug は自動生成されるので、形式だけ確認する
             */
            val expectedStatus = HttpStatus.CREATED.value()
            val expectedResponseBody = """
                {
                    "article": {
                    "slug": "slug0000000000000000000000000001",
                    "title": "dummy-title-01",
                    "description": "dummy-description-01",
                    "body": "dummy-body-01"
                  }
                }
            """.trimIndent()
            assertThat(actualStatus).isEqualTo(expectedStatus)
            JSONAssert.assertEquals(
                expectedResponseBody,
                actualResponseBody,
                CustomComparator(
                    JSONCompareMode.NON_EXTENSIBLE,
                    Customization("article.slug") { actualSlug, _ ->
                        actualSlug.toString().matches(Regex("^[a-z0-9]{32}\$"))
                    }
                )
            )
        }

        @Test
        @DataSet(
            value = [
                "datasets/yml/given/empty-articles.yml"
            ]
        )
        fun `異常系-バリデーションエラー`() {
            /**
             * given:
             * - title が空
             * - description が 64 文字超過
             */
            val responseBody = """
                {
                  "article": {
                    "title": "",
                    "description": "01234567890123456789012345678901234567890123456789012345678901234",
                    "body": ""
                  }
                }
            """.trimIndent()

            /**
             * when:
             */
            val response = mockMvc.post("/api/articles") {
                contentType = MediaType.APPLICATION_JSON
                content = responseBody
            }.andReturn().response
            val actualStatus = response.status
            val actualResponseBody = response.contentAsString

            /**
             * then:
             * - ステータスコードが一致する
             * - レスポンスボディが一致する
             */
            val expectedStatus = HttpStatus.FORBIDDEN.value()
            val expectedResponseBody = """
                {
                  "errors": {
                    "body": [
                      "title は必須です",
                      "description は 64 文字以下にしてください"
                    ]
                  }
                }
            """.trimIndent()
            assertThat(actualStatus).isEqualTo(expectedStatus)
            JSONAssert.assertEquals(
                expectedResponseBody,
                actualResponseBody,
                JSONCompareMode.STRICT,
            )
        }
    }
}
