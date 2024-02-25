package com.example.implementingserversidekotlindevelopment.domain

import arrow.core.Either
import arrow.core.leftNel
import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.ArbitrarySupplier
import net.jqwik.api.ForAll
import net.jqwik.api.From
import net.jqwik.api.Property
import net.jqwik.api.constraints.StringLength
import org.assertj.core.api.Assertions.assertThat

class BodyTest {
    class New {
        @Property
        fun `正常系 長さが有効な場合、検証済の作成済記事の Body が戻り値`(
            @ForAll @From(supplier = BodyValidRange::class) validString: String,
        ) {
            /**
             * given:
             */

            /**
             * when:
             */
            val actual = Body.new(validString)

            /**
             * then:
             */
            val expected = Body.newWithoutValidation(validString)
            when (actual) {
                is Either.Left -> assert(false) { "原因: ${actual.value}" }
                is Either.Right -> assertThat(actual.value.value).isEqualTo(expected.value)
            }
        }

        @Property
        fun `異常系-長さが長すぎる場合、バリデーションエラーが戻り値`(
            @ForAll @StringLength(min = 1025) tooLongString: String,
        ) {
            /**
             * given:
             */
            val maximumLength = 1024

            /**
             * when:
             */
            val actual = Body.new(tooLongString)

            /**
             * then:
             */
            val expected = Body.CreationError.TooLong(maximumLength).leftNel()
            assertThat(actual).isEqualTo(expected)
        }
    }

    class BodyValidRange : ArbitrarySupplier<String> {
        override fun get(): Arbitrary<String> =
            Arbitraries.strings()
                .ofMinLength(0)
                .ofMaxLength(1024)
    }
}
