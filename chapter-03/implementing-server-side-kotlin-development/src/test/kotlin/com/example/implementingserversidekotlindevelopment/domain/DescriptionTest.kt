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

class DescriptionTest {
    class New {
        @Property
        fun `正常系-長さが有効な場合、検証済の作成済記事の Description が戻り値`(
            @ForAll @From(supplier = DescriptionValidRange::class) validString: String,
        ) {
            /**
             * given:
             */

            /**
             * when:
             */
            val actual = Description.new(validString)

            /**
             * then:
             */
            val expected = Description.newWithoutValidation(validString)
            when (actual) {
                is Either.Left -> assert(false) { "原因: ${actual.value}" }
                is Either.Right -> assertThat(actual.value.value).isEqualTo(expected.value)
            }
        }

        @Property
        fun `異常系-長さが長すぎる場合、バリデーションエラーが戻り値`(
            @ForAll @StringLength(min = 65) tooLongString: String,
        ) {
            /**
             * given:
             */
            val maximumLength = 64

            /**
             * when:
             */
            val actual = Description.new(tooLongString)

            /**
             * then:
             */
            val expected = Description.CreationError.TooLong(maximumLength).leftNel()
            assertThat(actual).isEqualTo(expected)
        }
    }

    /**
     * Description の有効な範囲の String プロパティ
     *
     */
    class DescriptionValidRange : ArbitrarySupplier<String> {
        override fun get(): Arbitrary<String> =
            Arbitraries
                .strings()
                .ofMinLength(0)
                .ofMaxLength(64)
    }
}
