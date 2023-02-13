package com.example.ch04basicofspringboot

import org.springframework.stereotype.Service

interface SampleService {
    fun execute(): ServiceDto
}

// DI のために @Service アノテーションが必要
@Service
class SampleServiceImpl : SampleService {
    override fun execute(): ServiceDto {
        return ServiceDto(ServicePerson("Alice", 23))
    }
}

// Controller に渡すためだけの DTO
data class ServiceDto(
    val person: ServicePerson,
)

data class ServicePerson(
    val name: String,
    val age: Int,
)

