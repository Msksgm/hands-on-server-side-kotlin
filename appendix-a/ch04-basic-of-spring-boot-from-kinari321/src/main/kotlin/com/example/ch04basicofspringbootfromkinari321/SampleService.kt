package com.example.ch04basicofspringbootfromkinari321

import org.springframework.stereotype.Service

interface SampleService {
    fun execute(): ServiceDto
}

@Service
class SampleServiceImpl(val sampleRepository: SampleRepository) : SampleService {
    val samplePersonFromDb = sampleRepository.getPersonFromDb()
    override fun execute(): ServiceDto {
        return ServiceDto(ServicePerson(samplePersonFromDb.name, samplePersonFromDb.age))
    }
}

data class ServiceDto(
    val person: ServicePerson,
)

data class ServicePerson(
    val name: String,
    val age: Int,
)
