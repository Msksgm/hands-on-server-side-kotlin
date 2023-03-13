package com.example.ch04basicofspringbootfromkinari321

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SampleServiceTest {
    @Test
    fun `SampleRepository を DI したテスト`() {
        /**
         * given:
         */
        val sampleRepository = object : SampleRepository {
            override fun getPersonFromDb(): PersonFromDb = PersonFromDb("Carol", 24)
        }
        val sampleService = SampleServiceImpl(sampleRepository)

        /**
         * when:
         */
        val actual = sampleService.execute()

        /**
         * then:
         */
        val expected = ServiceDto(ServicePerson("Carol", 24))
        assertEquals(actual, expected)
    }
}
