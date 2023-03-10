package com.example.ch04basicofspringboot

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class SampleControllerTest {
    @Test
    fun `SampleService を DI したテスト`() {
        /**
         * given:
         * - SampleService.execute のスタブを作成
         * - SampleController をスタブ化した SampleService でインスタンス化することで、状態が確定する
         */
        val sampleService = object : SampleService {
            override fun execute(): ServiceDto = ServiceDto(ServicePerson("Bob", 24))
        }
        val sampleController = SampleController(sampleService)

        /**
         * when:
         * - メソッドの呼び出し（操作）は、スタブの利用前と利用後で変わらない
         */
        val actual = sampleController.getPerson()

        /**
         * then:
         * - スタブ化した値から ResponseEntity が生成されていることを確認する
         */
        val expected = ResponseEntity(PersonResponse(Person(name = "Bob", age = 24)), HttpStatus.OK)
        assertEquals(actual, expected)
    }
}
