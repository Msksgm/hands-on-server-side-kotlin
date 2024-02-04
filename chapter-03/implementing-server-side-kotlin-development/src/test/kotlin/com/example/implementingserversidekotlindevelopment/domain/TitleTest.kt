package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.leftNel
import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.ArbitrarySupplier
import net.jqwik.api.ForAll
import net.jqwik.api.From
import net.jqwik.api.Property
import net.jqwik.api.constraints.NotBlank
import net.jqwik.api.constraints.StringLength
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TitleTest {
    class New {
        @Property
        fun `正常系-長さが有効な場合、検証済の Title が戻り値`(
            @ForAll @From(supplier = TitleValidRange::class) validString: String,
        ) {
            /**
             * given:
             */

            /**
             * when:
             */
            val actual = Title.new(validString)

            /**
             * then:
             */
            val expected = Title.newWithoutValidation(validString)
            when (actual) {
                is Left -> assert(false) { "原因: ${actual.value}" }
                is Right -> assertThat(actual.value.value).isEqualTo(expected.value)
            }
        }

        @Property
        fun `異常系-長さが長すぎる場合、バリデーションエラーが戻り値`(
            @ForAll @NotBlank @StringLength(min = 33) tooLongString: String,
        ) {
            /**
             * given:
             */
            val maximumLength = 32

            /**
             * when:
             */
            val actual = Title.new(tooLongString)

            /**
             * then:
             */
            val expected = Title.CreationError.TooLong(maximumLength).leftNel()
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `異常系-空文字列の場合、バリデーションエラーが戻り値`() {
            /**
             * given:
             */
            val emptyString = ""

            /**
             * when:
             */
            val actual = Title.new(emptyString)

            /**
             * then:
             */
            val expected = Title.CreationError.Required.leftNel()
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `異常系-空白の場合、バリデーションエラーが戻り値`() {
            /**
             * given:
             */
            val blankString = "  "

            /**
             * when:
             */
            val actual = Title.new(blankString)

            /**
             * then:
             */
            val expected = Title.CreationError.Required.leftNel()
            assertThat(actual).isEqualTo(expected)
        }
    }

    class TitleValidRange : ArbitrarySupplier<String> {
        override fun get(): Arbitrary<String> = Arbitraries.strings().ofMinLength(1).ofMaxLength(32).filter { it.isNotBlank() }
    }
}
