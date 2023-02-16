package com.example.ch04basicofspringboot

import org.springframework.stereotype.Service

interface SampleService {
    fun execute(): ServiceDto
}

// DI のために @Service アノテーションが必要
@Service
class SampleServiceImpl(val sampleRepository: SampleRepository) : SampleService {
    val samplePersonFromDb = sampleRepository.getPersonFromDb()
    override fun execute(): ServiceDto {
        return ServiceDto(ServicePerson(samplePersonFromDb.name, samplePersonFromDb.age))
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

