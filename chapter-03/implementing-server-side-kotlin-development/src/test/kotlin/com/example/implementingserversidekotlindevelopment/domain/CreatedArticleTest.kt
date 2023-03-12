package com.example.implementingserversidekotlindevelopment.domain

import java.util.stream.Stream
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class CreatedArticleTest {
    data class TestCase(
        val title: String,
        val createdArticle: CreatedArticle,
        val otherCreatedArticle: CreatedArticle,
        val expected: Boolean,
    )

    @TestFactory
    fun articleEqualTest(): Stream<DynamicNode> {
        return Stream.of(
            TestCase(
                "Slug が一致する場合、他のプロパティが異なっていても、true を戻す",
                CreatedArticle.newWithoutValidation(
                    slug = Slug.newWithoutValidation("dummy-slug"),
                    title = Title.newWithoutValidation("dummy-title"),
                    description = Description.newWithoutValidation("dummy-description"),
                    body = Body.newWithoutValidation("dummy-body")
                ),
                CreatedArticle.newWithoutValidation(
                    slug = Slug.newWithoutValidation("dummy-slug"), // slug が同じ
                    title = Title.newWithoutValidation("other-dummy-title"),
                    description = Description.newWithoutValidation("other-dummy-description"),
                    body = Body.newWithoutValidation("other-dummy-body")
                ),
                true
            ),
            TestCase(
                "Slug が一致しない場合、他のプロパティが一致していても、false を戻す",
                CreatedArticle.newWithoutValidation(
                    slug = Slug.newWithoutValidation("dummy-slug"),
                    title = Title.newWithoutValidation("dummy-title"),
                    description = Description.newWithoutValidation("dummy-description"),
                    body = Body.newWithoutValidation("dummy-body")
                ),
                CreatedArticle.newWithoutValidation(
                    slug = Slug.newWithoutValidation("other-dummy-slug"), // slug が異なる
                    title = Title.newWithoutValidation("dummy-title"),
                    description = Description.newWithoutValidation("dummy-description"),
                    body = Body.newWithoutValidation("dummy-body")
                ),
                false
            )
        ).map { testCase ->
            dynamicTest(testCase.title) {
                /**
                 * given:
                 */

                /**
                 * when:
                 * - 作成済み記事を比較
                 */
                val actual = testCase.createdArticle == testCase.otherCreatedArticle

                /**
                 * then:
                 */
                assertThat(actual).isEqualTo(testCase.expected)
            }
        }
    }
}
