package com.example.implementingserversidekotlindevelopment.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

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
                "ArticleId が一致する場合、他のプロパティが異なっていても、true を戻す",
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
                "ArticleId が一致する場合、他のプロパティが異なっていても、true を戻す",
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
                assertThat(testCase.createdArticle == testCase.otherCreatedArticle).isEqualTo(testCase.expected)
            }
        }
    }
}
