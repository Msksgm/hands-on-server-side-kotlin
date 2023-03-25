package com.example.implementingserversidekotlindevelopment.infra

import arrow.core.Either
import arrow.core.right
import com.example.implementingserversidekotlindevelopment.domain.ArticleRepository
import com.example.implementingserversidekotlindevelopment.domain.Body
import com.example.implementingserversidekotlindevelopment.domain.CreatedArticle
import com.example.implementingserversidekotlindevelopment.domain.Description
import com.example.implementingserversidekotlindevelopment.domain.Slug
import com.example.implementingserversidekotlindevelopment.domain.Title
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

class ArticleRepositoryImplTest {
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DBRider
    class FindBySlug {
        @BeforeAll
        fun reset() = DbConnection.resetSequence()

        @Test
        @DataSet(
            value = [
                "datasets/yml/given/articles.yml"
            ]
        )
        fun `正常系-slug に該当する作成済記事が存在する場合、単一の作成済記事が戻り値`() {
            /**
             * given:
             * - 作成済記事が存在する slug 名
             */
            val slug = Slug.newWithoutValidation("slug0000000000000000000000000001")
            val articleRepository = ArticleRepositoryImpl(DbConnection.namedParameterJdbcTemplate)

            /**
             * when:
             */
            val actual = articleRepository.findBySlug(slug = slug)

            /**
             * then:
             */
            val expected = CreatedArticle.newWithoutValidation(
                slug = Slug.newWithoutValidation("slug0000000000000000000000000001"),
                title = Title.newWithoutValidation("dummy-title-01"),
                description = Description.newWithoutValidation("dummy-description-01"),
                body = Body.newWithoutValidation("dummy-body-01")
            )

            when (actual) {
                is Either.Left -> assert(false) { "原因: ${actual.value}" }
                is Either.Right -> {
                    // CreatedArticle 同士を比較すると、slug が同一なだけでテストを通過するので、それ以外のプロパティも比較
                    assertThat(actual.value.slug).isEqualTo(expected.slug)
                    assertThat(actual.value.title).isEqualTo(expected.title)
                    assertThat(actual.value.description).isEqualTo(expected.description)
                    assertThat(actual.value.body).isEqualTo(expected.body)
                }
            }
        }

        @Test
        @DataSet(
            value = [
                "datasets/yml/given/empty-articles.yml"
            ]
        )
        fun `異常系-slug に該当する作成済記事が存在しない場合、FindBySlugError NotFound が戻り値`() {
            /**
             * given:
             * - 作成済記事が存在しない slug 名
             */
            val slug = Slug.newWithoutValidation("dummy-slug-01")
            val articleRepository = ArticleRepositoryImpl(DbConnection.namedParameterJdbcTemplate)

            /**
             * when:
             */
            val actual = articleRepository.findBySlug(slug = slug)

            /**
             * then:
             */
            val expected = ArticleRepository.FindBySlugError.NotFound(slug = slug)

            when (actual) {
                is Either.Left -> {
                    assertThat(actual.value).isEqualTo(expected)
                }

                is Either.Right -> assert(false)
            }
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DBRider
    class Create {
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
        )
        // NOTE: @ExportDataSetはgivenの@DataSetが変更された時用に残しておく
        // @ExportDataSet(
        //     format = DataSetFormat.YML,
        //     outputName = "src/test/resources/datasets/yml/then/created-articles.yml.yml",
        //     includeTables = ["articles"]
        // )
        fun `正常系-作成済記事が保存される`() {
            /**
             * given
             * - 保存する作成済記事
             */
            val unsavedCreatedArticle = CreatedArticle.newWithoutValidation(
                slug = Slug.newWithoutValidation("slug0000000000000000000000000001"),
                title = Title.newWithoutValidation("dummy-title-01"),
                description = Description.newWithoutValidation("dummy-description-01"),
                body = Body.newWithoutValidation("dummy-body-01")
            )
            val articleRepository = ArticleRepositoryImpl(DbConnection.namedParameterJdbcTemplate)

            /**
             * when:
             */
            val actual = articleRepository.create(createdArticle = unsavedCreatedArticle)

            /**
             * then:
             * - 戻り値は引数と同じ
             */
            val expected = CreatedArticle.newWithoutValidation(
                slug = Slug.newWithoutValidation("slug0000000000000000000000000001"),
                title = Title.newWithoutValidation("dummy-title-01"),
                description = Description.newWithoutValidation("dummy-description-01"),
                body = Body.newWithoutValidation("dummy-body-01")
            )
            when (actual) {
                is Either.Left -> assert(false) { "原因: ${actual.value}" }
                is Either.Right -> {
                    // CreatedArticle 同士を比較すると、slug が同一なだけでテストを通過するので、それ以外のプロパティも比較
                    assertThat(actual.value.slug).isEqualTo(expected.slug)
                    assertThat(actual.value.title).isEqualTo(expected.title)
                    assertThat(actual.value.description).isEqualTo(expected.description)
                    assertThat(actual.value.body).isEqualTo(expected.body)
                }
            }
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DBRider
    class Find {
        @BeforeAll
        fun reset() = DbConnection.resetSequence()

        @Test
        @DataSet(
            value = [
                "datasets/yml/given/empty-articles.yml"
            ]
        )
        fun `正常系-作成済記事が 1 つもない場合、からの作成済記事の一覧が戻り値`() {
            /**
             * given:
             */
            val articleRepository = ArticleRepositoryImpl(DbConnection.namedParameterJdbcTemplate)

            /**
             * when:
             */
            val actual = articleRepository.find()

            /**
             * then:
             */
            val expected = listOf<CreatedArticle>().right()
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        @DataSet(
            value = [
                "datasets/yml/given/articles.yml"
            ]
        )
        fun `正常系-DB に作成済記事がある場合、全ての作成済記事の一覧が戻り値`() {
            /**
             * given:
             */
            val articleRepository = ArticleRepositoryImpl(DbConnection.namedParameterJdbcTemplate)

            /**
             * when:
             */
            val actual = articleRepository.find()

            /**
             * then:
             */
            val expected = listOf(
                CreatedArticle.newWithoutValidation(
                    slug = Slug.newWithoutValidation("slug0000000000000000000000000001"),
                    title = Title.newWithoutValidation("dummy-title-01"),
                    description = Description.newWithoutValidation("dummy-description-01"),
                    body = Body.newWithoutValidation("dummy-body-01")
                ),
                CreatedArticle.newWithoutValidation(
                    slug = Slug.newWithoutValidation("slug0000000000000000000000000002"),
                    title = Title.newWithoutValidation("dummy-title-02"),
                    description = Description.newWithoutValidation("dummy-description-02"),
                    body = Body.newWithoutValidation("dummy-body-02")
                ),
            )
            when (actual) {
                is Either.Left -> assert(false)
                is Either.Right -> {
                    val createdArticleList = actual.value
                    assertThat(createdArticleList).hasSameElementsAs(expected)
                    createdArticleList.forEach { actualArticle ->
                        val expectedArticle = expected.find { it.slug == actualArticle.slug }!! // 必ず見つかるので !! でキャストする
                        assertThat(actualArticle.slug).isEqualTo(expectedArticle.slug)
                        assertThat(actualArticle.title).isEqualTo(expectedArticle.title)
                        assertThat(actualArticle.body).isEqualTo(expectedArticle.body)
                        assertThat(actualArticle.description).isEqualTo(expectedArticle.description)
                    }
                }
            }
        }
    }
}
