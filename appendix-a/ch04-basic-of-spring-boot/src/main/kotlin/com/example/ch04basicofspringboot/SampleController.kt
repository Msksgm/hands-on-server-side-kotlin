package com.example.ch04basicofspringboot

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

// @RestController でエンドポイントになる
@RestController
class SampleController(val sampleService: SampleService) {
    // @GetMapping() で引数のパスに GET リクエストが送れるようになる
    @GetMapping("/")
    fun getPerson(): ResponseEntity<PersonResponse> {
        val result = sampleService.execute()
        return ResponseEntity(PersonResponse(Person(name = result.person.name, age = result.person.age)), HttpStatus.OK)
    }
}

// @JsonProperty で ResponseEntity クラスに渡したとき、レスポンスが JSON に変換される
data class PersonResponse(
    @JsonProperty("person", required = true) val person: Person,
)

data class Person(
    @JsonProperty("name", required = true) val name: String,
    @JsonProperty("age", required = true) val age: Int,
)
