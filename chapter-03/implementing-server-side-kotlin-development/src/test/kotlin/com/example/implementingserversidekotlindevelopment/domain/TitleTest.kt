package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.Invalid
import arrow.core.Valid
import arrow.core.invalidNel
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
                is Invalid -> assert(false) { "原因: ${actual.value}" }
                is Valid -> assertThat(actual.value.value).isEqualTo(expected.value)
            }
        }

        @Property
        fun `準正常系-長さが長すぎる場合、バリデーションエラーが戻り値`(
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
            val expected = Title.CreationError.TooLong(maximumLength).invalidNel()
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `準正常系-null の場合、バリデーションエラーが戻り値`() {
            /**
             * given:
             */
            val nullString = null

            /**
             * when:
             */
            val actual = Title.new(nullString)

            /**
             * then:
             */
            val expected = Title.CreationError.Required.invalidNel()
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        fun `準正常系-空白の場合、バリデーションエラーが戻り値`() {
            /**
             * given:
             */
            val nullString = " "

            /**
             * when:
             */
            val actual = Title.new(nullString)

            /**
             * then:
             */
            val expected = Title.CreationError.Required.invalidNel()
            assertThat(actual).isEqualTo(expected)
        }
    }

    class TitleValidRange : ArbitrarySupplier<String> {
        override fun get(): Arbitrary<String> =
            Arbitraries.strings()
                .ofMinLength(1)
                .ofMaxLength(32)
                .filter { it.isNotBlank() }
    }
}
